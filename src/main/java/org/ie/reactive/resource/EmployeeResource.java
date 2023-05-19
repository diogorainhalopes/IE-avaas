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

import org.ie.reactive.repo.Employee;
import org.jboss.logging.annotations.Param;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@Path("employee")
public class EmployeeResource {

	@Inject
	io.vertx.mutiny.mysqlclient.MySQLPool client;

	@GET
	@Path("/all")
	public Multi<Employee> get() {
		return Employee.findAll(client);
	}

	@GET
	@Path("{userId}")
	public Uni<Response> getSingle(@Param Integer userId) {
		return Employee.findById(client, userId)
				.onItem()
				.transform(employee -> employee != null ? Response.ok(employee) : Response.status(Status.NOT_FOUND))
				.onItem().transform(ResponseBuilder::build);
	}

	@POST
	public Uni<Response> create(Employee employee) {
		return employee.save(client)
				.onItem().transform(userId -> URI.create("/employee/" + userId))
				.onItem().transform(uri -> Response.created(uri).build());
	}

	@DELETE
	@Path("{userId}")
	public Uni<Response> delete(@Param Integer userId) {
		return Employee.delete(client, userId)
				.onItem().transform(deleted -> deleted ? Status.NO_CONTENT : Status.NOT_FOUND)
				.onItem().transform(status -> Response.status(status).build());
	}

	@PUT
	@Path("/role/{userId}/{role}")
	public Uni<Response> updateRole(@Param Integer userId, @Param String role) {
		return Employee.updateRole(client, userId, role)
				.onItem().transform(updated -> updated ? Status.NO_CONTENT : Status.NOT_FOUND)
				.onItem().transform(status -> Response.status(status).build());
	}

}
