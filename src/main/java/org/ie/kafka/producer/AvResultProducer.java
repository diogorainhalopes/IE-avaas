package org.ie.kafka.producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.ie.model.APilot;
import org.ie.model.AvResult;
import org.ie.model.wrappers.AvResultWrapper;
import org.ie.openweathermap.WeatherApiClient;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.smallrye.reactive.messaging.kafka.Record;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * The AvResultProducer class represents an object that is responsible for
 * producing AvResultWrapper messages to a Kafka topic.
 * 
 * The AvResultProducer class provides a method to send AvResultWrapper objects
 * to a Kafka topic using an Emitter.
 */
@ApplicationScoped
public class AvResultProducer {

	// @ConfigProperty(name = "kafka.bootstrap.servers")
	static String bootstrapServers = "ec2-18-234-198-196.compute-1.amazonaws.com:9092";

	static String topicName = "av-result";

	public static void sendAvResultToKafka(AvResultWrapper avResultWrapper) {

		System.out.println("--- AvResultProducer Begin sendAvResultToKafka ---");
		Properties props = new Properties();
		props.put("bootstrap.servers", bootstrapServers);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		Producer<String, String> kafkaProducer = new KafkaProducer<String, String>(props);

		System.out.println("--- AvResultProducer KafkaProducer Props: " + props.toString() + " ---");

		try {
			ProducerRecord<String, String> record = new ProducerRecord<>(
					topicName,
					topicName + avResultWrapper.getAvResult().getAvId() + String.valueOf(((Double) (Math.random() * 10))
							.intValue()),
					avResultWrapper.toJson());

			kafkaProducer.send(record, new Callback() {
				@Override
				public void onCompletion(org.apache.kafka.clients.producer.RecordMetadata metadata,
						Exception exception) {
					if (exception != null) {
						System.out.println("AvResultProducer send Callback failed.");
						exception.printStackTrace();
					} else {
						System.out.println("The offset of the record we just sent is: " + metadata.offset());
					}
					kafkaProducer.close();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("--- AvResultProducer End sendAvResultToKafka ---");
	}

	public static void MediationToIQEQAQ(APilot apilot) throws Exception {

		AvResult avResult = new AvResult();
		String weather;
		String temp;
		String wind;
		String humidity;

		System.out.println("--- AV Result Produce MediationToIQEQAQ ---");

		if (!apilot.getLocation().equals("Unknown")) {
			System.out.println("--- AV Result Get OpenWeatherMap data ---");
			String[] coords = apilot.getLocation().split(", ");
			String latitude = coords[0];
			String longitude = coords[1];
			String weatherData = WeatherApiClient.getWeatherData(latitude, longitude);
			System.out.println(weatherData);

			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(weatherData);

			weather = jsonNode.get("weather").get(0).get("main").asText();
			temp = jsonNode.get("main").get("temp").asText();
			wind = jsonNode.get("wind").get("speed").asText();
			humidity = jsonNode.get("main").get("humidity").asText();

			if (weather.equals("Clouds") || weather.equals("Rain") ||
					weather.equals("Snow") || weather.equals("Extreme") ||
					Double.parseDouble(temp) < 5 || Double.parseDouble(temp) > 40 ||
					Double.parseDouble(wind) < 18 || Double.parseDouble(humidity) < 75) {
				apilot.setGeneralWeather("Bad");
			} else {
				apilot.setGeneralWeather("Good");
			}

		}

		avResult.setTimeStamp(apilot.getTimeStamp());
		avResult.setAvId(apilot.getAvId());
		avResult.setSpeed(apilot.getSpeed());

		if (apilot.getRainConditions().equals("Heavy Rain")
				|| apilot.getFogConditions().equals("Dense Fog")
				|| apilot.getChargeAv().equals("Yes")) {
			avResult.setIctInfrastructure("Weak");
		} else {
			avResult.setIctInfrastructure("Strong");
		}
		if (apilot.getGeneralWeather().equals("Bad")
				&& (apilot.getVisibility().equals("Bad") || apilot.getVisibility().equals("Terrible"))) {
			avResult.setRoadConditions("Bad Conditions");
		} else {
			avResult.setRoadConditions("Good Conditions");
		}

		if (apilot.getDanger().equals("Yes")) {
			avResult.setSecurity("Low");
		} else {
			avResult.setSecurity("High");
		}

		if (apilot.getSugestStop().equals("Yes")) {
			avResult.setUtilitarianism("Positive (Increases Driver Safety)");
		} else {
			avResult.setUtilitarianism("Negative (Does Not Increases Driver Safety)");
		}

		if (apilot.getApplyBrakes().equals("Yes")) {
			avResult.setLegitimacy("Driving Conditions need to change");
		} else {
			avResult.setLegitimacy("Driving Conditions don't need to change");
		}

		if (apilot.getSugestStop().equals("Yes")
				&& Integer.parseInt(apilot.getPedestrianProximity()) < 35
				&& Integer.parseInt(apilot.getObstacleProximity()) < 35) {
			avResult.setSocialResponsibility("Responsible (If accident occurs)");
		} else {
			avResult.setSocialResponsibility("Not Responsible (If accident occurs)");
		}

		if (apilot.getVisibility().equals("Bad")) {
			avResult.setDetection("Weak Space Visualization");
		} else {
			avResult.setDetection("Strong Space Visualization");
		}

		if (apilot.getGeneralWeather().equals("Bad") && apilot.getVisibility().equals("Bad")) {
			avResult.setIdentification("Unidentifiable Space");
		} else {
			avResult.setIdentification("Identifiable Space");
		}

		if (apilot.getDanger().equals("Yes") && apilot.getGeneralWeather().equals("Bad")) {
			avResult.setRiskAnalysis("High");
		} else if (apilot.getDanger().equals("Yes") || apilot.getGeneralWeather().equals("Bad")) {
			avResult.setRiskAnalysis("Medium");
		} else {
			avResult.setRiskAnalysis("Low");
		}

		if (apilot.getGeneralWeather().equals("Bad")
				&& Integer.parseInt(apilot.getDriverTirenessLevel()) > 65) {
			avResult.setReaction("Slowest");
		} else if (apilot.getGeneralWeather().equals("Bad")
				&& Integer.parseInt(apilot.getDriverTirenessLevel()) < 65) {
			avResult.setReaction("Slower");
		} else if (apilot.getGeneralWeather().equals("Good")
				&& Integer.parseInt(apilot.getDriverTirenessLevel()) < 35) {
			avResult.setReaction("Good");
		} else {
			avResult.setReaction("Optimal");
		}

		if (avResult.getRiskAnalysis().equals("High")
				|| avResult.getSecurity().equals("Low")) {
			avResult.setExecution("Slow Down Suggestion");
		} else {
			avResult.setExecution("Continue Suggestion");
		}

		AvResultWrapper wrapper = new AvResultWrapper(avResult);

		// Send Message

		sendAvResultToKafka(wrapper);

	}
}