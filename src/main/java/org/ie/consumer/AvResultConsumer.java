package org.ie.consumer;

import io.smallrye.reactive.messaging.kafka.Record;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AvResultConsumer {

    @Incoming("av-result-in")
    public void receiveAvResult(Record<String, String> record) {
        System.out.println("Consumed AV_RESULT: Id = " + record.key() + " from topic: av-result-in");
        System.out.println(record.value());
    }

}
