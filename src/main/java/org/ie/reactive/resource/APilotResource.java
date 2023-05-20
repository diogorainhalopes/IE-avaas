package org.ie.reactive.resource;

import org.ie.reactive.repo.APilot;
import org.jboss.logging.annotations.Param;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;

/**
 * The APilotResource class represents the resource for APilot.
 */
@Path("apilot")
public class APilotResource {

    @Inject
    io.vertx.mutiny.mysqlclient.MySQLPool client;

    /**
     * Retrieves all APilot objects.
     *
     * @return a Multi of APilot objects
     */
    @GET
    @Path("/all")
    public Multi<APilot> get() {
        return APilot.findAll(client);
    }

    /**
     * Retrieves a single APilot object by ID.
     *
     * @param id the ID of the APilot object
     * @return a Uni of Response object
     */
    @GET
    @Path("{id}")
    public Uni<Response> getSingle(@Param Integer id) {
        return APilot.findById(client, id)
                .onItem().transform(apilot -> apilot != null
                        ? Response.ok(apilot)
                        : Response.status(Status.NOT_FOUND))
                .onItem().transform(ResponseBuilder::build);
    }

}
