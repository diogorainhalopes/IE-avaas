package org.ie.kafka;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.ie.producer.AvProducer;
import org.ie.reactive.repo.Av;

@Path("kafka/produce/av")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AvKafkaResource {

    @Inject
    AvProducer producer;

    @POST
    public Response send(Av av) {
        producer.sendAvToKafka(av);
        // Return an 202 - Accepted response.
        System.out.println("KAFKA SENT:\n" + av.toJson());
        return Response.accepted().build();
    }
}
