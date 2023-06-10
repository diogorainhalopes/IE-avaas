package org.ie.kafka.producer;

import io.smallrye.reactive.messaging.kafka.Record;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.ie.model.wrappers.AvEventWrapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * The AvEventProducer class represents an object that is responsible for
 * producing AvEvent messages to a Kafka topic.
 * 
 * The AvEventProducer class provides a method to send AvEvent objects
 * to a Kafka topic using an Emitter.
 */
@ApplicationScoped
public class AvEventProducer {

    @Inject
    @Channel("av-event-out")
    Emitter<Record<String, String>> emitter;

    public void sendAvEventToKafka(AvEventWrapper avEventWrapper) {
        emitter.send(Record.of(
                "av-event" + "_" + String.valueOf(((Double) (Math.random() * 10)).intValue()),
                avEventWrapper.toJson()));
    }
}
