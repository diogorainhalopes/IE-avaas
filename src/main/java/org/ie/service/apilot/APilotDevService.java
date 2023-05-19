package org.ie.service.apilot;

import java.net.URI;

import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import org.ie.reactive.repo.APilot;
import org.ie.reactive.repo.Av;
import org.jboss.logging.annotations.Param;

import io.smallrye.mutiny.Uni;

@Path("apilot_dev/service")
public class APilotDevService {

    @Inject
    io.vertx.mutiny.mysqlclient.MySQLPool client;

    @POST
    @Path("/enter/apilot")
    public Uni<Response> create(APilot apilot) {

        return isValid(apilot)
                ? apilot.save(client)
                        .onItem().transform(id -> URI.create("/apilot_dev/service/enter/apilot/" + id))
                        .onItem().transform(uri -> Response.created(uri).build())
                        .onFailure()
                        .recoverWithUni(Uni.createFrom().item(() -> Response.status(Response.Status.ACCEPTED)
                                .entity("ERROR INVALID APILOT\n")
                                .build()))
                : Uni.createFrom().item(() -> Response.status(Response.Status.ACCEPTED)
                        .entity("ERROR ADDING APILOT\n"
                                + "RULES:\n"
                                + " -> AV id > 0\n"
                                + " -> Company length > 0 and 100 characters\n"
                                + " -> Model length > 0 and 100 characters\n")
                        .build());
    }

    private boolean isValid(APilot apilot) {
        return apilot.getId() > 0 && apilot.getCompany().length() > 0 && apilot.getCompany().length() <= 100
                && apilot.getModel().length() > 0 && apilot.getModel().length() <= 100;
    }

    @DELETE
    @Path("remove/apilot/{id}")
    public Uni<Response> delete(@Param Integer id) {
        return Av.delete(client, id)
                .onItem().transform(deleted -> Boolean.TRUE.equals(deleted) ? Status.NO_CONTENT : Status.NOT_FOUND)
                .onItem().transform(status -> Response.status(status).build());
    }

    @PUT
    @Path("/update/apilot/model/{id}/{model}")
    public Uni<Response> updateModel(@Param Integer id, @Param String model) {
        return Av.updateModel(client, id, model)
                .onItem().transform(updated -> Boolean.TRUE.equals(updated) ? Status.NO_CONTENT : Status.NOT_FOUND)
                .onItem().transform(status -> Response.status(status).build());
    }

}
