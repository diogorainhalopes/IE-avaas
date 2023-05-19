package org.ie.reactive.repo;

import io.vertx.mutiny.mysqlclient.MySQLPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Av {

	@JsonProperty("id")
	private int id;

	@JsonProperty("brand")
	private String brand;

	@JsonProperty("model")
	private String model;

	public void setId(int id) {
		this.id = id;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Av() {
		// Does nothing
	}

	public Av(int id, String brand, String model) {
		super();
		this.id = id;
		this.brand = brand;
		this.model = model;
	}

	public int getId() {
		return id;
	}

	public String getBrand() {
		return brand;
	}

	public String getModel() {
		return model;
	}

	public String toJson() {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			return ow.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ow.toString();
	}

	private static Av from(Row row) {
		return new Av(row.getInteger("id"), row.getString("brand"), row.getString("model"));
	}

	public static Multi<Av> findAll(MySQLPool client) {
		return client.query("SELECT id, brand, model FROM av ORDER BY brand ASC").execute()
				.onItem().transformToMulti(set -> Multi.createFrom().iterable(set))
				.onItem().transform(Av::from);
	}

	public static Uni<Av> findById(MySQLPool client, Integer id) {
		return client.preparedQuery("SELECT id, brand, model FROM av WHERE id = ?").execute(Tuple.of(id))
				.onItem().transform(RowSet::iterator)
				.onItem().transform(iterator -> iterator.hasNext() ? from(iterator.next()) : null);
	}

	public Uni<Boolean> save(MySQLPool client) {
		return client.preparedQuery("INSERT INTO av(id, brand, model) VALUES (?, ?, ?)")
				.execute(Tuple.of(id, brand, model)).onItem().transform(pgRowSet -> pgRowSet.rowCount() == 1);
	}

	public static Uni<Boolean> delete(MySQLPool client, Integer id) {
		return client.preparedQuery("DELETE FROM av WHERE id = ?").execute(Tuple.of(id)).onItem()
				.transform(pgRowSet -> pgRowSet.rowCount() == 1);
	}

	public static Uni<Boolean> updateBrand(MySQLPool client, Integer id, String brand) {
		return client.preparedQuery("UPDATE av SET brand = ? WHERE id = ?").execute(Tuple.of(brand, id))
				.onItem().transform(pgRowSet -> pgRowSet.rowCount() == 1);
	}

	public static Uni<Boolean> updateModel(MySQLPool client, Integer id, String model) {
		return client.preparedQuery("UPDATE av SET model = ? WHERE id = ?").execute(Tuple.of(model, id))
				.onItem().transform(pgRowSet -> pgRowSet.rowCount() == 1);
	}

}
