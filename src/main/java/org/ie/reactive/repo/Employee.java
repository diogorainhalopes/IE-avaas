package org.ie.reactive.repo;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.mysqlclient.MySQLPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;

public class Employee {

	public int eid;
	public String ename;

	public Employee() {
		// Does nothing
	}

	public Employee(int eid, String ename) {
		super();
		this.eid = eid;
		this.ename = ename;
	}

	private static Employee from(Row row) {
		return new Employee(row.getInteger("eid"), row.getString("ename"));
	}

	public static Multi<Employee> findAll(MySQLPool client) {
		return client.query("SELECT eid, ename FROM employee").execute()
				.onItem().transformToMulti(set -> Multi.createFrom().iterable(set))
				.onItem().transform(Employee::from);
	}

	public static Uni<Employee> findById(MySQLPool client, Integer eid) {
		return client.preparedQuery("SELECT eid FROM employee WHERE eid = ?").execute(Tuple.of(eid))
				.onItem().transform(RowSet::iterator)
				.onItem().transform(iterator -> iterator.hasNext() ? from(iterator.next()) : null);
	}

	public Uni<Boolean> save(MySQLPool client) {
		return client.preparedQuery("INSERT INTO employee(eid, ename) VALUES (?, ?)").execute(Tuple.of(eid, ename))
				.onItem().transform(pgRowSet -> pgRowSet.rowCount() == 1);
	}

	public static Uni<Boolean> delete(MySQLPool client, Integer eid) {
		return client.preparedQuery("DELETE FROM employee WHERE eid = ?").execute(Tuple.of(eid)).onItem()
				.transform(pgRowSet -> pgRowSet.rowCount() == 1);
	}

}
