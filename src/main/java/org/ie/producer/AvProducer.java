package org.ie.producer;

import io.smallrye.reactive.messaging.kafka.Record;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.ie.reactive.repo.Av;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * The AvProducer class represents an object that is responsible for
 * producing Av messages to a Kafka topic.
 * 
 * The AvProducer class provides a method to send Av objects
 * to a Kafka topic using an Emitter.
 */
@ApplicationScoped
public class AvProducer {

    @Inject
    @Channel("av-out")
    Emitter<Record<Integer, String>> emitter;

    public void sendAvToKafka(Av av) {
        emitter.send(Record.of(av.getId(), av.toJson()));
    }
}
