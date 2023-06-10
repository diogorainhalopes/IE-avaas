package org.ie.kafka.consumer;

import io.smallrye.reactive.messaging.kafka.Record;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.ie.kafka.producer.AvResultProducer;
import org.ie.model.APilot;
import org.ie.model.wrappers.APilotWrapper;

import io.smallrye.reactive.messaging.kafka.Record;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.enterprise.context.ApplicationScoped;

/**
 * The APilotConsumer class is an application-scoped class that listens for
 * APilot messages from the "apilot-in" Kafka topic. When a message is
 * received, it prints the consumed APilot details to the console.
 */
@ApplicationScoped
public class APilotConsumer {

    @Incoming("apilot-in")
    public void receiveAPilot(Record<String, String> record) throws Exception {
        APilot apilot = new APilot();
        try {
            apilot = convertJsonStringToAPilot(record.value());
        } catch (JsonProcessingException e) {

            e.printStackTrace();
        }

        if (apilot != null)
            AvResultProducer.MediationToIQEQAQ(apilot);
        else
            System.out.println("APilot is null");
    }

    public APilot convertJsonStringToAPilot(String jsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            APilotWrapper wrapper = objectMapper.readValue(jsonString, APilotWrapper.class);
            System.out.println(wrapper.toJson());
            return wrapper.getApilot();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
