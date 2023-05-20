package org.ie.kafka;

import org.ie.model.wrappers.AvResultWrapper;
import org.ie.producer.AvResultProducer;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * The AvResultKafkaResource class provides an HTTP POST endpoint to send
 * AvResultWrapper objects to a Kafka topic using the AvResultProducer.
 */
@Path("kafka/produce/av-result")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AvResultKafkaResource {

    @Inject
    AvResultProducer producer;

    @POST
    public Response send(AvResultWrapper avResultwWrapper) {
        producer.sendAvResultToKafka(avResultwWrapper);

        System.out.println("AV-RESULT KAFKA SENT:\n" + avResultwWrapper.toJson());
        return Response.accepted().build();
    }
}
