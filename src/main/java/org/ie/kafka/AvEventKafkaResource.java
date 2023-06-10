package org.ie.kafka;

import org.ie.kafka.producer.AvEventProducer;
import org.ie.model.wrappers.AvEventWrapper;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * The AvEventKafkaResource class provides an HTTP POST endpoint to send
 * AvEvent objects to a Kafka topic using the AvEventProducer.
 */
@Path("kafka/produce/av-event")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AvEventKafkaResource {

    @Inject
    AvEventProducer producer;

    @POST
    public Response send(AvEventWrapper wrapper) {
        producer.sendAvEventToKafka(wrapper);

        System.out.println("AvEvent AvEventKafkaResource:\n" + wrapper.toJson());
        return Response.accepted().build();
    }
}
