package org.ie.service.vehicles;

import java.net.URI;

import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import org.ie.reactive.repo.Av;
import org.jboss.logging.annotations.Param;

import io.smallrye.mutiny.Uni;

/**
 * The ManufacturerService class represents a RESTful web service for
 * managing Av objects.
 */
@Path("manufacturer_service")
public class ManufacturerService {

	@Inject
	io.vertx.mutiny.mysqlclient.MySQLPool client;

	@POST
	@Path("/enter")
	public Uni<Response> create(Av av) {

		return isAvValid(av)
				? av.save(client)
						.onItem().transform(id -> URI.create("/manufacturer_service/enter/" + id))
						.onItem().transform(uri -> Response.created(uri).build())
						.onFailure()
						.recoverWithUni(Uni.createFrom().item(() -> Response.status(Response.Status.ACCEPTED)
								.entity("ERROR INVALID AV\n")
								.build()))
				: Uni.createFrom().item(() -> Response.status(Response.Status.ACCEPTED)
						.entity("ERROR ADDING AV\n"
								+ "RULES:\n"
								+ " -> AV id > 0\n"
								+ " -> Brand length > 0 and 100 characters\n"
								+ " -> Model length > 0 and 100 characters\n")
						.build());

	}

	private boolean isAvValid(Av av) {
		return av.getId() > 0 && av.getBrand().length() > 0 && av.getBrand().length() <= 100
				&& av.getModel().length() > 0 && av.getModel().length() <= 100;
	}

	@DELETE
	@Path("remove/{id}")
	public Uni<Response> delete(@Param Integer id) {
		return Av.delete(client, id)
				.onItem().transform(deleted -> Boolean.TRUE.equals(deleted) ? Status.NO_CONTENT : Status.NOT_FOUND)
				.onItem().transform(status -> Response.status(status).build());
	}

	@PUT
	@Path("/update/{id}/{model}")
	public Uni<Response> updateModel(@Param Integer id, @Param String model) {
		return Av.updateModel(client, id, model)
				.onItem().transform(updated -> Boolean.TRUE.equals(updated) ? Status.NO_CONTENT : Status.NOT_FOUND)
				.onItem().transform(status -> Response.status(status).build());
	}

}
