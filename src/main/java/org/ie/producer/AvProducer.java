package org.ie.producer;

import io.smallrye.reactive.messaging.kafka.Record;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.ie.reactive.repo.Av;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AvProducer {

    @Inject
    @Channel("av-out")
    Emitter<Record<Integer, String>> emitter;

    public void sendAvToKafka(Av av) {
        emitter.send(Record.of(av.getId(), av.toJson()));
    }
}
