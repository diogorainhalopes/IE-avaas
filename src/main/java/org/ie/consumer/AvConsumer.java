package org.ie.consumer;

import io.smallrye.reactive.messaging.kafka.Record;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AvConsumer {

	@Incoming("av-in")
	public void receiveAv(Record<Integer, String> record) {
		System.out.println("Consumed AV: Id = " + record.key() + " from topic: av-in");
		System.out.println(record.value());
	}
}
