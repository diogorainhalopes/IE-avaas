package org.ie.reactive.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;

import org.ie.reactive.repo.Purchase;
import org.jboss.logging.annotations.Param;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@Path("purchase")
public class PurchaseResource {

    @Inject
    io.vertx.mutiny.mysqlclient.MySQLPool client;

    @GET
    @Path("/all")
    public Multi<Purchase> get() {
        return Purchase.findAll(client);
    }

    @GET
    @Path("{id}")
    public Uni<Response> getSingle(@Param Integer id) {
        return Purchase.findById(client, id)
                .onItem().transform(user -> user != null ? Response.ok(user) : Response.status(Status.NOT_FOUND))
                .onItem().transform(ResponseBuilder::build);
    }

}