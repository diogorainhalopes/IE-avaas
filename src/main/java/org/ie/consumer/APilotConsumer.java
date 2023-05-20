package org.ie.consumer;

import io.smallrye.reactive.messaging.kafka.Record;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.ie.model.wrappers.APilotWrapper;

import jakarta.enterprise.context.ApplicationScoped;

/**
 * The APilotConsumer class is an application-scoped class that listens for
 * APilot messages from the "apilot-in" Kafka topic. When a message is
 * received, it prints the consumed APilot details to the console.
 */
@ApplicationScoped
public class APilotConsumer {

    @Incoming("apilot-in")
    public void receiveAv(Record<Integer, Integer> record) {
        APilotWrapper wrapper = new APilotWrapper(record.key(), record.value());
        System.out.println(
                "Consumed APilot to Av from topic apilot-in:\n" + wrapper.toJson() + "\n");
    }
}
