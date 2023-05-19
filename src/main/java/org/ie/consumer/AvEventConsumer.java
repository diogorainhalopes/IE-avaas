package org.ie.consumer;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import org.ie.model.AvEvent;
import org.ie.model.wrappers.AvEventWrapper;

import io.smallrye.reactive.messaging.kafka.Record;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AvEventConsumer {

	final Logger logger = Logger.getLogger(AvEventConsumer.class);

	@Incoming("av-event")
	public void receiveAv(Record<Integer, String> record) {

		System.out.println("Consumed AV_Event: Id = " + record.key() + "from topic: av-event");

		try {
			convertJsonStringToAV_Event(record.value());
		} catch (JsonProcessingException e) {

			e.printStackTrace();
		}

	}

	public void convertJsonStringToAV_Event(String jsonString) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			AvEventWrapper eventWrapper = objectMapper.readValue(jsonString, AvEventWrapper.class);
			AvEvent avEvent = eventWrapper.getAvEvent();
			// AvEvent avEvent = objectMapper.readValue(jsonString, AvEvent.class);
			// Use the avEvent object as needed
			System.out.println(avEvent.toJson());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}
