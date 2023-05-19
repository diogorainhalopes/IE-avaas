package org.ie.consumer;

import io.smallrye.reactive.messaging.kafka.Record;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AvConsumer {

	@Incoming("av-in")
	public void receiveAv(Record<Integer, String> record) {
		// TODO: 2021-10-20T16:00:00.000Z: 1. Add code to parse the JSON string into an
		// Av object.

		System.out.println(record.toString());
	}
}
