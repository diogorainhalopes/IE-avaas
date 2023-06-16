package org.ie.service.purchase;

import java.net.URI;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import org.ie.reactive.repo.Purchase;
import org.jboss.logging.annotations.Param;

import io.smallrye.mutiny.Uni;

/**
 * The PurchaseService class represents a RESTful web service for
 * managing Purchase objects.
 */
@Path("purchase/service")
public class PurchaseService {

    @Inject
    io.vertx.mutiny.mysqlclient.MySQLPool client;

    @POST
    @Path("/av/buy")
    public Uni<Response> buyAV(Purchase purchase) {

        return isPurchaseValid(purchase) ? purchase.save(client)
                .onItem().transform(id -> URI.create("/purchase/service/av/buy/" + id))
                .onItem().transform(uri -> Response.created(uri).build())
                .onFailure().recoverWithUni(Uni.createFrom().item(() -> Response.status(Response.Status.ACCEPTED)
                        .entity("Error purchasing.\n"
                                + "Verify if Purchase id already exists\n"
                                + "Verify if User id exists\n"
                                + "Verify if AV id exists\n"
                                + "Verify if APilot id exists\n")
                        .build()))
                : Uni.createFrom().item(() -> Response.status(Response.Status.ACCEPTED)
                        .entity("ERROR PURCHASING\n"
                                + "RULES:\n"
                                + " -> Id > 0\n"
                                + " -> User id > 0\n"
                                + " -> AV id > 0\n"
                                + " -> APilot id > 0\n")
                        .build());
    }

    private boolean isPurchaseValid(Purchase purchase) {
        return purchase.getId() > 0 &&
                purchase.getAvId() > 0 &&
                purchase.getApilotId() > 0 &&
                purchase.getUserId() > 0;
    }

    @PUT
    @Path("/av/sell/{id}")
    public Uni<Response> sellAV(@Param Integer id) {
        return Purchase.sellAV(client, id)
                .onItem().transform(updated -> Boolean.TRUE.equals(updated) ? Status.NO_CONTENT : Status.NOT_FOUND)
                .onItem().transform(status -> Response.status(status).build());
    }

    @PUT
    @Path("/apilot/select/{id}/{apilotId}")
    public Uni<Response> selectAPilot(@Param Integer id, @Param Integer apilotId) {
        return Purchase.selectAPilot(client, id, apilotId)
                .onItem().transform(updated -> Boolean.TRUE.equals(updated) ? Status.NO_CONTENT : Status.NOT_FOUND)
                .onItem().transform(status -> Response.status(status).build());
    }

    @PUT
    @Path("/apilot/unselect/{id}")
    public Uni<Response> unselectAPilot(@Param Integer id) {
        return Purchase.unselectAPilot(client, id)
                .onItem().transform(updated -> Boolean.TRUE.equals(updated) ? Status.NO_CONTENT : Status.NOT_FOUND)
                .onItem().transform(status -> Response.status(status).build());
    }
}