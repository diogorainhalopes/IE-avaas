package org.ie.producer;

import io.smallrye.reactive.messaging.kafka.Record;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.ie.model.wrappers.APilotWrapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * The APilotProducer class represents an object that is responsible for
 * producing APilot messages to a Kafka topic.
 * 
 * The APilotProducer class provides a method to send APilot objects
 * to a Kafka topic using an Emitter.
 */
@ApplicationScoped
public class APilotProducer {

    @Inject
    @Channel("apilot-out")
    Emitter<Record<Integer, Integer>> emitter;

    public void sendAPilotToKafka(APilotWrapper apilotWrapper) {
        emitter.send(Record.of(apilotWrapper.getAvid(), apilotWrapper.getApilotid()));
    }
}
