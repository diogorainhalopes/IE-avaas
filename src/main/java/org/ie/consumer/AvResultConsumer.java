package org.ie.consumer;

import io.smallrye.reactive.messaging.kafka.Record;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import jakarta.enterprise.context.ApplicationScoped;

/**
 * The AvResultConsumer class is an application-scoped class that listens for
 * AvResult messages from the "av-result-in" Kafka topic. When a message is
 * received, it prints the consumed AvResult details to the console.
 */
@ApplicationScoped
public class AvResultConsumer {

    @Incoming("av-result-in")
    public void receiveAvResult(Record<String, String> record) {
        System.out.println("Consumed AV_RESULT: Id = " + record.key() + " from topic: av-result-in");
        System.out.println(record.value());
    }

}
