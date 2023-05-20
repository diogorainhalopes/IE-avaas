package org.ie.reactive.resource;

import org.ie.reactive.repo.Av;
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
 * The AvResource class represents the resource for Av.
 */
@Path("av")
public class AvResource {

	@Inject
	io.vertx.mutiny.mysqlclient.MySQLPool client;

	/**
	 * Retrieves all Av objects.
	 *
	 * @return a Multi of Av objects
	 */
	@GET
	@Path("/all")
	public Multi<Av> get() {
		return Av.findAll(client);
	}

	/**
	 * Retrieves a single Av object by ID.
	 *
	 * @param id the ID of the Av object
	 * @return a Uni of Response object
	 */
	@GET
	@Path("{id}")
	public Uni<Response> getSingle(@Param Integer id) {
		return Av.findById(client, id)
				.onItem().transform(av -> av != null
						? Response.ok(av)
						: Response.status(Status.NOT_FOUND))
				.onItem().transform(ResponseBuilder::build);
	}

}
