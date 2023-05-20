package org.ie.kafka;

import org.ie.model.wrappers.APilotWrapper;
import org.ie.producer.APilotProducer;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * The APilotKafkaResource class provides an HTTP POST endpoint to send
 * APilot objects to a Kafka topic using the APilotProducer.
 */
@Path("kafka/produce/apilot")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class APilotKafkaResource {

    @Inject
    APilotProducer producer;

    @POST
    public Response send(APilotWrapper wrapper) {
        producer.sendAPilotToKafka(wrapper);

        System.out.println("APilot KAFKA SENT:\n" + wrapper.toJson());
        return Response.accepted().build();
    }
}
