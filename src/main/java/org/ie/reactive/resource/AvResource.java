package org.ie.reactive.resource;

import java.net.URI;

import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;

import org.ie.reactive.repo.Av;
import org.jboss.logging.annotations.Param;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@Path("av")
public class AvResource {

	@Inject
	io.vertx.mutiny.mysqlclient.MySQLPool client;

	@GET
	@Path("/all")
	public Multi<Av> get() {
		return Av.findAll(client);
	}

	@GET
	@Path("{id}")
	public Uni<Response> getSingle(@Param Integer id) {
		return Av.findById(client, id)
				.onItem().transform(av -> av != null
						? Response.ok(av)
						: Response.status(Status.NOT_FOUND))
				.onItem().transform(ResponseBuilder::build);
	}

	@POST
	public Uni<Response> create(Av av) {
		return av.save(client)
				.onItem().transform(id -> URI.create("/av/" + id))
				.onItem().transform(uri -> Response.created(uri).build());
	}

	@DELETE
	@Path("{id}")
	public Uni<Response> delete(@Param Integer id) {
		return Av.delete(client, id)
				.onItem().transform(deleted -> Boolean.TRUE.equals(deleted) ? Status.NO_CONTENT : Status.NOT_FOUND)
				.onItem().transform(status -> Response.status(status).build());
	}

	@PUT
	@Path("/brand/{id}/{brand}")
	public Uni<Response> updateBrand(@Param Integer id, @Param String brand) {
		return Av.updateBrand(client, id, brand)
				.onItem().transform(updated -> Boolean.TRUE.equals(updated) ? Status.NO_CONTENT : Status.NOT_FOUND)
				.onItem().transform(status -> Response.status(status).build());
	}

	@PUT
	@Path("/model/{id}/{model}")
	public Uni<Response> updateModel(@Param Integer id, @Param String model) {
		return Av.updateModel(client, id, model)
				.onItem().transform(updated -> Boolean.TRUE.equals(updated) ? Status.NO_CONTENT : Status.NOT_FOUND)
				.onItem().transform(status -> Response.status(status).build());
	}
}
