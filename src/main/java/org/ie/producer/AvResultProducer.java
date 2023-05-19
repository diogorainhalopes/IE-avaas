package org.ie.producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import io.smallrye.reactive.messaging.kafka.Record;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.protocol.types.Field.UUID;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.ie.model.AvResult;
import org.ie.model.wrappers.AvResultWrapper;

import jakarta.inject.Inject;

public class AvResultProducer {

	@Inject
	@Channel("av-result-out")
	Emitter<Record<String, String>> emitter;

	public void sendAvResultToKafka(AvResultWrapper avResultWrapper) {

		emitter.send(Record.of(avResultWrapper.getAvResult().getAvId(), avResultWrapper.toJson()));
	}

}
