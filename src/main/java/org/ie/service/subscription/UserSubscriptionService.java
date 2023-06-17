package org.ie.service.subscription;

import java.net.URI;

import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import org.ie.reactive.repo.User;
import org.jboss.logging.annotations.Param;

import io.smallrye.mutiny.Uni;

/**
 * The UserSubscriptionService class represents a RESTful web service for
 * managing User objects.
 */
@Path("subscription")
public class UserSubscriptionService {

	@Inject
	io.vertx.mutiny.mysqlclient.MySQLPool client;

	@POST
	@Path("/")
	public Uni<Response> create(User user) {

		return isValid(user)
				? user.save(client)
						.onItem().transform(id -> URI.create("/subscription/" + id))
						.onItem().transform(uri -> Response.created(uri).build())
						.onFailure()
						.recoverWithUni(Uni.createFrom().item(() -> Response.status(Response.Status.ACCEPTED)
								.entity("WARNING User already subscribed").build()))
				: Uni.createFrom().item(() -> Response.status(Response.Status.ACCEPTED)
						.entity("ERROR SUBSCRIBING\n"
								+ "RULES:\n"
								+ " -> Id > 0\n"
								+ " -> Name length > 0 and < 100 characters\n"
								+ " -> Age > 18 and < 120\n")
						.build());

	}

	private boolean isValid(User user) {
		return user.getId() > 0 &&
				user.getName().length() > 0 && user.getName().length() <= 100 &&
				user.getAge() > 18;
	}

	@DELETE
	@Path("/unsubscribe/{id}")
	public Uni<Response> delete(@Param Integer id) {
		return User.delete(client, id)
				.onItem().transform(deleted -> Boolean.TRUE.equals(deleted) ? Status.NO_CONTENT : Status.NOT_FOUND)
				.onItem().transform(status -> Response.status(status).build());
	}

}
