package org.ie.consumer;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import org.ie.model.AvEvent;
import io.smallrye.reactive.messaging.kafka.Record;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class AvEventConsumer {

	final Logger logger = Logger.getLogger(AvEventConsumer.class);

	@Incoming("av-event")
	public void receiveAv(Record<Integer, String> record) {

		logger.infof("Consumed an AV_Event: Id = " + record.key() + "\n");

		try {
			convertJsonStringToAV_Event(record.value());
		} catch (JsonProcessingException e) {

			e.printStackTrace();
		}

	}

	public void convertJsonStringToAV_Event(String jsonString) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			AvEvent avEvent = objectMapper.readValue(jsonString, AvEvent.class);
			// Use the avEvent object as needed
			System.out.println(avEvent.toString());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

}
