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

/**
 * The ManufacturerResource class represents the resource for Manufacturer.
 */
@Path("av_manufacturer")
public class ManufacturerResource {

	@Inject
	io.vertx.mutiny.mysqlclient.MySQLPool client;

	/**
	 * Retrieves all Manufacturer objects.
	 *
	 * @return a Multi of Manufacturer objects
	 */
	@GET
	@Path("/all")
	public Multi<Manufacturer> get() {
		return Manufacturer.findAll(client);
	}

	/**
	 * Retrieves a single Manufacturer object by brand.
	 *
	 * @param brand the brand of the Manufacturer object
	 * @return a Uni of Response object
	 */
	@GET
	@Path("{brand}")
	public Uni<Response> getSingle(@Param String brand) {
		return Manufacturer.findById(client, brand)
				.onItem()
				.transform(carManufacturer -> carManufacturer != null ? Response.ok(carManufacturer)
						: Response.status(Status.NOT_FOUND))
				.onItem().transform(ResponseBuilder::build);
	}

	/**
	 * Creates a new Manufacturer object.
	 *
	 * @param manufacturer the Manufacturer object to create
	 * @return a Uni of Response object
	 */
	@POST
	public Uni<Response> create(Manufacturer Manufacturer) {
		return Manufacturer.save(client)
				.onItem().transform(brand -> URI.create("/av_manufacturer/" + brand))
				.onItem().transform(uri -> Response.created(uri).build());
	}

	/**
	 * Deletes a Manufacturer object by brand.
	 *
	 * @param brand the brand of the Manufacturer object to delete
	 * @return a Uni of Response object
	 */
	@DELETE
	@Path("{brand}")
	public Uni<Response> delete(@Param String brand) {
		return Manufacturer.delete(client, brand)
				.onItem().transform(deleted -> Boolean.TRUE.equals(deleted) ? Status.NO_CONTENT : Status.NOT_FOUND)
				.onItem().transform(status -> Response.status(status).build());
	}

}
