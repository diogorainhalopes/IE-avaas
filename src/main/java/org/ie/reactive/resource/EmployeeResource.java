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
	@Path("{eid}")
	public Uni<Response> getSingle(@Param Integer eid) {
		return Employee.findById(client, eid)
				.onItem()
				.transform(employee -> employee != null ? Response.ok(employee) : Response.status(Status.NOT_FOUND))
				.onItem().transform(ResponseBuilder::build);
	}

	@POST
	public Uni<Response> create(Employee employee) {
		return employee.save(client)
				.onItem().transform(eid -> URI.create("/employee/" + eid))
				.onItem().transform(uri -> Response.created(uri).build());
	}

	@DELETE
	@Path("{eid}")
	public Uni<Response> delete(@Param Integer eid) {
		return Employee.delete(client, eid)
				.onItem().transform(deleted -> Boolean.TRUE.equals(deleted) ? Status.NO_CONTENT : Status.NOT_FOUND)
				.onItem().transform(status -> Response.status(status).build());
	}

}
