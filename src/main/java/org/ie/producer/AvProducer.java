package org.ie.producer;

import io.smallrye.reactive.messaging.kafka.Record;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.ie.reactive.repo.Av;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AvProducer {

    @Inject
    @Channel("av-out")
    Emitter<Record<Integer, String>> emitter;

    public void sendAvToKafka(Av av) {
        List<String> brandAndModel = new ArrayList<>();
        brandAndModel.add(av.brand);
        brandAndModel.add(av.model);
        emitter.send(Record.of(av.id, brandAndModel.toString()));
        System.out.println(av.toJson());
    }
}
