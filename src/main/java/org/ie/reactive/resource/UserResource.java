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

import org.ie.reactive.repo.User;
import org.jboss.logging.annotations.Param;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@Path("user")
public class UserResource {

	@Inject
	io.vertx.mutiny.mysqlclient.MySQLPool client;

	@GET
	@Path("/all")
	public Multi<User> get() {
		return User.findAll(client);
	}

	@GET
	@Path("{id}")
	public Uni<Response> getSingle(@Param Integer id) {
		return User.findById(client, id)
				.onItem().transform(user -> user != null ? Response.ok(user) : Response.status(Status.NOT_FOUND))
				.onItem().transform(ResponseBuilder::build);
	}

}
