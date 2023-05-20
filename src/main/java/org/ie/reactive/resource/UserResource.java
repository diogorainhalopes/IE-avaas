package org.ie.reactive.resource;

import org.ie.reactive.repo.User;
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
 * This class represents the resource for User.
 * 
 */
@Path("user")
public class UserResource {

	@Inject
	io.vertx.mutiny.mysqlclient.MySQLPool client;

	/**
	 * Retrieves all User objects.
	 *
	 * @return a Multi of User objects
	 */
	@GET
	@Path("/all")
	public Multi<User> get() {
		return User.findAll(client);
	}

	/**
	 * Retrieves a single User object by ID.
	 *
	 * @param id the ID of the User object
	 * @return a Uni of Response object
	 */
	@GET
	@Path("{id}")
	public Uni<Response> getSingle(@Param Integer id) {
		return User.findById(client, id)
				.onItem().transform(user -> user != null ? Response.ok(user) : Response.status(Status.NOT_FOUND))
				.onItem().transform(ResponseBuilder::build);
	}

}
