package org.ie.producer;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.ie.model.wrappers.AvResultWrapper;

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

	@Inject
	@Channel("av-result-out")
	Emitter<Record<String, String>> emitter;

	public void sendAvResultToKafka(AvResultWrapper avResultWrapper) {

		emitter.send(Record.of(avResultWrapper.getAvResult().getAvId(), avResultWrapper.toJson()));
	}

}
