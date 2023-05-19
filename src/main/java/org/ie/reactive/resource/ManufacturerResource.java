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

import org.ie.reactive.repo.Manufacturer;
import org.jboss.logging.annotations.Param;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@Path("av_manufacturer")
public class ManufacturerResource {

	@Inject
	io.vertx.mutiny.mysqlclient.MySQLPool client;

	@GET
	@Path("/all")
	public Multi<Manufacturer> get() {
		return Manufacturer.findAll(client);
	}

	@GET
	@Path("{brand}")
	public Uni<Response> getSingle(@Param String brand) {
		return Manufacturer.findById(client, brand)
				.onItem()
				.transform(carManufacturer -> carManufacturer != null ? Response.ok(carManufacturer)
						: Response.status(Status.NOT_FOUND))
				.onItem().transform(ResponseBuilder::build);
	}

	@POST
	public Uni<Response> create(Manufacturer Manufacturer) {
		return Manufacturer.save(client)
				.onItem().transform(brand -> URI.create("/av_manufacturer/" + brand))
				.onItem().transform(uri -> Response.created(uri).build());
	}

	@DELETE
	@Path("{brand}")
	public Uni<Response> delete(@Param String brand) {
		return Manufacturer.delete(client, brand)
				.onItem().transform(deleted -> Boolean.TRUE.equals(deleted) ? Status.NO_CONTENT : Status.NOT_FOUND)
				.onItem().transform(status -> Response.status(status).build());
	}

}
