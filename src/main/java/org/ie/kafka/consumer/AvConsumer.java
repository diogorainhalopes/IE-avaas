package org.ie.kafka.consumer;

import io.smallrye.reactive.messaging.kafka.Record;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import jakarta.enterprise.context.ApplicationScoped;

/**
 * The AvConsumer class is an application-scoped class that listens for
 * Av messages from the "av-in" Kafka topic. When a message is
 * received, it prints the consumed Av details to the console.
 */
@ApplicationScoped
public class AvConsumer {

	@Incoming("av-in")
	public void receiveAv(Record<Integer, String> record) {
		System.out.println("Consumed AV: Id = " + record.key() + " from topic: av-in");
		System.out.println(record.value());
	}
}
