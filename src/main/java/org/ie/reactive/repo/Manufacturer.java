package org.ie.reactive.repo;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.mysqlclient.MySQLPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;

/**
 * The Manufacturer class represents a Manufacturer object.
 */
public class Manufacturer {

	private String brand;

	public Manufacturer() {
	}

	public Manufacturer(String brand) {
		this.brand = brand;
	}

	private static Manufacturer from(Row row) {
		return new Manufacturer(row.getString("brand"));
	}

	public static Multi<Manufacturer> findAll(MySQLPool client) {
		return client.query("SELECT brand FROM manufacturer ORDER BY brand ASC").execute()
				.onItem().transformToMulti(set -> Multi.createFrom().iterable(set))
				.onItem().transform(Manufacturer::from);
	}

	public static Uni<Manufacturer> findById(MySQLPool client, String brand) {
		return client.preparedQuery("SELECT brand FROM manufacturer WHERE brand = ?").execute(Tuple.of(brand))
				.onItem().transform(RowSet::iterator)
				.onItem().transform(iterator -> iterator.hasNext() ? from(iterator.next()) : null);
	}

	public Uni<Boolean> save(MySQLPool client) {
		return client.preparedQuery("INSERT INTO manufacturer(brand) VALUES (?)").execute(Tuple.of(brand)).onItem()
				.transform(pgRowSet -> pgRowSet.rowCount() == 1);
	}

	public static Uni<Boolean> delete(MySQLPool client, String brand) {
		return client.preparedQuery("DELETE FROM manufacturer WHERE brand = ?").execute(Tuple.of(brand)).onItem()
				.transform(pgRowSet -> pgRowSet.rowCount() == 1);
	}

	public static Uni<Boolean> updateBrand(MySQLPool client, String brand) {
		return client.preparedQuery("UPDATE manufacturer SET brand = ? WHERE brand = ?").execute(Tuple.of(brand))
				.onItem().transform(pgRowSet -> pgRowSet.rowCount() == 1);
	}

}
