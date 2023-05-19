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

@Path("apilot_dev")
public class APilotDevResource {
    @Inject
    io.vertx.mutiny.mysqlclient.MySQLPool client;

    @GET
    @Path("/all")
    public Multi<APilotDev> get() {
        return APilotDev.findAll(client);
    }

    @GET
    @Path("{company}")
    public Uni<Response> getSingle(@Param String company) {
        return APilotDev.findById(client, company)
                .onItem()
                .transform(apilotDev -> apilotDev != null ? Response.ok(apilotDev)
                        : Response.status(Status.NOT_FOUND))
                .onItem().transform(ResponseBuilder::build);
    }

    @POST
    public Uni<Response> create(APilotDev apilotDev) {
        return apilotDev.save(client)
                .onItem().transform(company -> URI.create("/apilot_dev/" + company))
                .onItem().transform(uri -> Response.created(uri).build());
    }

    @DELETE
    @Path("{company}")
    public Uni<Response> delete(@Param String company) {
        return APilotDev.delete(client, company)
                .onItem().transform(deleted -> Boolean.TRUE.equals(deleted) ? Status.NO_CONTENT : Status.NOT_FOUND)
                .onItem().transform(status -> Response.status(status).build());
    }

}
