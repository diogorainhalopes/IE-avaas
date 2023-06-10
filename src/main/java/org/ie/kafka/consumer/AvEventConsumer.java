package org.ie.kafka.consumer;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.ie.kafka.producer.APilotProducer;
import org.ie.model.AvEvent;
import org.ie.model.wrappers.AvEventWrapper;

import io.smallrye.reactive.messaging.kafka.Record;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The AvEventConsumer class is responsible for consuming AvEvent messages from
 * the "av-event" Kafka topic. When a message is received, it converts the JSON
 * string representation of the AvEvent into an AvEvent object and performs
 * further processing as needed.
 */
public class AvEventConsumer {

	@Incoming("av-event-in")
	public void receiveAvEvent(Record<Integer, String> record) {
		AvEvent avEvent = new AvEvent();
		System.out.println("--- Consumed AV_Event: Id = " + record.key() + " from topic: av-event ---");

		try {
			avEvent = convertJsonStringToAVEvent(record.value());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		if (avEvent != null)
			APilotProducer.simulateAPilot(avEvent);
		else
			System.out.println("AvEvent is null");
	}

	public AvEvent convertJsonStringToAVEvent(String jsonString) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			AvEventWrapper wrapper = objectMapper.readValue(jsonString, AvEventWrapper.class);
			System.out.println("-- convertJsonStringToAVEvent ---");
			System.out.println(wrapper.toJson());
			return wrapper.getAvEvent();

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
