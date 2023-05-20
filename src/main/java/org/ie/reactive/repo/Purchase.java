package org.ie.reactive.repo;

import io.vertx.mutiny.mysqlclient.MySQLPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

/**
 * The Purchase class represents a Purchase object.
 */
public class Purchase {

	private int id;
	private int userId;
	private int avId;

	public Purchase() {
		// Does nothing
	}

	public Purchase(int id, int userId, Integer avId) {
		super();
		this.id = id;
		this.userId = userId;
		this.avId = avId;
	}

	public int getId() {
		return id;
	}

	public int getUserId() {
		return userId;
	}

	public Integer getAvId() {
		return avId;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setAvId(Integer avId) {
		this.avId = avId;
	}

	@Override
	public String toString() {
		return "{ \" \"purchase\": { \"id\": = " + id + ", \"userId\": = " + userId + ", \"avId\": " + avId + "} }";
	}

	private static Purchase from(Row row) {
		return new Purchase(row.getInteger("id"), row.getInteger("userId"), row.getInteger("avId"));
	}

	public static Multi<Purchase> findAll(MySQLPool client) {
		return client.query("SELECT id, userId, avId FROM purchase").execute()
				.onItem().transformToMulti(set -> Multi.createFrom().iterable(set))
				.onItem().transform(Purchase::from);
	}

	public static Uni<Purchase> findById(MySQLPool client, Integer id) {
		return client.preparedQuery("SELECT id, userId, avId FROM purchase WHERE id = ?")
				.execute(Tuple.of(id))
				.onItem().transform(RowSet::iterator)
				.onItem().transform(iterator -> iterator.hasNext() ? from(iterator.next()) : null);
	}

	public Uni<Boolean> save(MySQLPool client) {
		return client.preparedQuery("INSERT INTO purchase(id, userId, avId ) VALUES (?, ?, ?)")
				.execute(Tuple.of(id, userId, avId, 0)).onItem().transform(pgRowSet -> pgRowSet.rowCount() == 1);
	}

	public static Uni<Boolean> delete(MySQLPool client, Integer id) {
		return client.preparedQuery("DELETE FROM purchase WHERE id = ?").execute(Tuple.of(id)).onItem()
				.transform(pgRowSet -> pgRowSet.rowCount() == 1);
	}

	public static Uni<Boolean> updateUser(MySQLPool client, Integer id, Integer userId) {
		return client.preparedQuery("UPDATE purchase SET userId = ? WHERE id = ?").execute(Tuple.of(userId, id))
				.onItem().transform(pgRowSet -> pgRowSet.rowCount() == 1);
	}

	public static Uni<Boolean> updateAV(MySQLPool client, Integer id, Integer avId) {
		return client.preparedQuery("UPDATE purchase SET avId = ? WHERE id = ?").execute(Tuple.of(avId, id))
				.onItem().transform(pgRowSet -> pgRowSet.rowCount() == 1);
	}

	public static Uni<Boolean> sellAV(MySQLPool client, Integer id) {
		return client.preparedQuery("UPDATE purchase SET avId = NULL WHERE id = ?").execute(Tuple.of(id))
				.onItem().transform(pgRowSet -> pgRowSet.rowCount() == 1);
	}

}
