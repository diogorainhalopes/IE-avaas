package org.ie.reactive.resource;

import java.net.URI;

import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;

import org.ie.reactive.repo.APilotDev;
import org.jboss.logging.annotations.Param;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

/**
 * The APilotDevResource class represents the resource for APilotDev.
 */
@Path("apilot_dev")
public class APilotDevResource {
    @Inject
    io.vertx.mutiny.mysqlclient.MySQLPool client;

    /**
     * Retrieves all APilotDev objects.
     *
     * @return a Multi of APilotDev objects
     */
    @GET
    @Path("/all")
    public Multi<APilotDev> get() {
        return APilotDev.findAll(client);
    }

    /**
     * Retrieves a single APilotDev object by company name.
     *
     * @param company the company name
     * @return a Uni of Response object
     */
    @GET
    @Path("{company}")
    public Uni<Response> getSingle(@Param String company) {
        return APilotDev.findById(client, company)
                .onItem()
                .transform(apilotDev -> apilotDev != null ? Response.ok(apilotDev)
                        : Response.status(Status.NOT_FOUND))
                .onItem().transform(ResponseBuilder::build);
    }

    /**
     * Creates a new APilotDev object.
     *
     * @param apilotDev the APilotDev object to create
     * @return a Uni of Response object
     */
    @POST
    public Uni<Response> create(APilotDev apilotDev) {
        return apilotDev.save(client)
                .onItem().transform(company -> URI.create("/apilot_dev/" + company))
                .onItem().transform(uri -> Response.created(uri).build());
    }

    /**
     * Deletes an APilotDev object by company name.
     *
     * @param company the company name
     * @return a Uni of Response object
     */
    @DELETE
    @Path("{company}")
    public Uni<Response> delete(@Param String company) {
        return APilotDev.delete(client, company)
                .onItem().transform(deleted -> Boolean.TRUE.equals(deleted) ? Status.NO_CONTENT : Status.NOT_FOUND)
                .onItem().transform(status -> Response.status(status).build());
    }

}
