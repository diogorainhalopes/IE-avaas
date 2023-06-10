package org.ie.kafka.producer;

import io.smallrye.reactive.messaging.kafka.Record;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.ie.model.APilot;
import org.ie.model.AvEvent;
import org.ie.model.wrappers.APilotWrapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * The APilotProducer class represents an object that is responsible for
 * producing APilot messages to a Kafka topic.
 * 
 * The APilotProducer class provides a method to send APilot objects
 * to a Kafka topic using an Emitter.
 */
@ApplicationScoped
public class APilotProducer {

    // @ConfigProperty(name = "kafka.bootstrap.servers")
    static String bootstrapServers = "ec2-44-202-70-152.compute-1.amazonaws.com:9092";

    static String topicName = "apilot";

    public static void sendAPilotToKafka(APilotWrapper apilotWrapper) {

        System.out.println("--- APilotProducer Begin sendAPilotToKafka ---");
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> kafkaProducer = new KafkaProducer<String, String>(props);

        System.out.println("--- APilotProducer KafkaProducer Props: " + props.toString() + " ---");

        try {
            ProducerRecord<String, String> record = new ProducerRecord<>(
                    topicName,
                    (topicName + apilotWrapper.getApilot().getAvId() + String.valueOf(((Double) (Math.random() * 10))
                            .intValue())),
                    apilotWrapper.toJson());

            kafkaProducer.send(record, new Callback() {
                @Override
                public void onCompletion(org.apache.kafka.clients.producer.RecordMetadata metadata,
                        Exception exception) {
                    if (exception != null) {
                        System.out.println("APilotProducer send Callback failed.");
                        exception.printStackTrace();
                    } else {
                        System.out.println("The offset of the record we just sent is: " + metadata.offset());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            kafkaProducer.close();
        }

        System.out.println("--- APilotProducer End sendAPilotToKafka ---");
    }

    public static void simulateAPilot(AvEvent avEvent) {

        System.out.println("--- APilotProducer simulateAPilot ---");

        APilot apilot = new APilot();

        apilot.setTimeStamp(avEvent.getTimeStamp());
        apilot.setAvId(avEvent.getAvId());

        apilot.setSpeed(avEvent.getSpeed());
        apilot.setRegenerativeBraking(Integer.parseInt(avEvent.getSpeed()) > 80 ? "Yes" : "No");
        apilot.setTrafficLight(avEvent.getTrafficLight());

        apilot.setBatteryLevel(avEvent.getBatteryLevel());
        apilot.setChargeAv(Integer.parseInt(avEvent.getBatteryLevel()) < 20 ? "Yes" : "No");

        apilot.setDriverTirenessLevel(avEvent.getDriverTirenessLevel());
        apilot.setSugestStop(Integer.parseInt(avEvent.getDriverTirenessLevel()) > 70 ? "Yes" : "No");

        apilot.setLocation(avEvent.getLocation());
        apilot.setDanger(avEvent.getLocation().equals("Unknown") ? "Yes" : "No");

        apilot.setEnvironmentalLightning(avEvent.getEnvironmentalLightning());
        apilot.setVisibility(
                avEvent.getEnvironmentalLightning().equals("N/A") || avEvent.getEnvironmentalLightning().equals("Bad")
                        ? "Bad"
                        : "Good");

        if (avEvent.getEnvironmentalLightning().equals("N/A") || avEvent.getEnvironmentalLightning().equals("Bad"))
            apilot.setHeadlightsLevel("High");
        else if (avEvent.getEnvironmentalLightning().equals("Sufficient"))
            apilot.setHeadlightsLevel("Medium");
        else
            apilot.setHeadlightsLevel("Low");

        apilot.setRainConditions(avEvent.getRainConditions());
        apilot.setFogConditions(avEvent.getFogConditions());
        if (avEvent.getRainConditions().equals("Heavy Rain") || avEvent.getFogConditions().equals("Dense Fog"))
            apilot.setGeneralWeather("Bad");
        else if (avEvent.getRainConditions().equals("Light Rain") || avEvent.getRainConditions().equals("Medium Rain")
                || avEvent.getFogConditions().equals("Light Fog") || avEvent.getFogConditions().equals("Medium Fog"))
            apilot.setGeneralWeather("Decent");
        else
            apilot.setGeneralWeather("Good");

        apilot.setTractionWheelsLevel(avEvent.getTractionWheelsLevel());
        apilot.setAverageConsumptionLevel(avEvent.getAverageConsumptionLevel());
        apilot.setObstacleProximity(avEvent.getObstacleProximity());
        apilot.setPedestrianProximity(avEvent.getPedestrianProximity());

        apilot.setApplyBrakes(
                Integer.parseInt(avEvent.getSpeed()) > 100
                        || avEvent.getTrafficLight().equals("Red")
                        || Integer.parseInt(avEvent.getObstacleProximity()) < 65
                        || Integer.parseInt(avEvent.getPedestrianProximity()) < 75
                                ? "Yes"
                                : "No");

        APilotWrapper wrapper = new APilotWrapper(apilot);

        // Send Message

        sendAPilotToKafka(wrapper);
    }
}
