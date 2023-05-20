package org.ie.reactive.repo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import io.vertx.mutiny.mysqlclient.MySQLPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

/**
 * The APilot class represents a APilot object.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class APilot {

    @JsonProperty("id")
    private int id;

    @JsonProperty("company")
    private String company;

    @JsonProperty("model")
    private String model;

    public int getId() {
        return id;
    }

    public APilot() {
    }

    public APilot(int id, String company, String model) {
        this.id = id;
        this.company = company;
        this.model = model;
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

    public String getCompany() {
        return company;
    }

    public String getModel() {
        return model;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setModel(String model) {
        this.model = model;
    }

    private static APilot from(Row row) {
        return new APilot(row.getInteger("id"), row.getString("company"), row.getString("model"));
    }

    public static Multi<APilot> findAll(MySQLPool client) {
        return client.query("SELECT id, company, model FROM apilot ORDER BY company ASC").execute()
                .onItem().transformToMulti(set -> Multi.createFrom().iterable(set))
                .onItem().transform(APilot::from);
    }

    public static Uni<APilot> findById(MySQLPool client, Integer id) {
        return client.preparedQuery("SELECT id, company, model FROM apilot WHERE id = ?").execute(Tuple.of(id))
                .onItem().transform(RowSet::iterator)
                .onItem().transform(iterator -> iterator.hasNext() ? from(iterator.next()) : null);
    }

    public Uni<Boolean> save(MySQLPool client) {
        return client.preparedQuery("INSERT INTO apilot(id, company, model) VALUES (?, ?, ?)")
                .execute(Tuple.of(id, company, model)).onItem().transform(pgRowSet -> pgRowSet.rowCount() == 1);
    }

    public static Uni<Boolean> delete(MySQLPool client, Integer id) {
        return client.preparedQuery("DELETE FROM apilot WHERE id = ?").execute(Tuple.of(id)).onItem()
                .transform(pgRowSet -> pgRowSet.rowCount() == 1);
    }

    public static Uni<Boolean> updateCompany(MySQLPool client, Integer id, String company) {
        return client.preparedQuery("UPDATE apilot SET company = ? WHERE id = ?").execute(Tuple.of(company, id))
                .onItem().transform(pgRowSet -> pgRowSet.rowCount() == 1);
    }

    public static Uni<Boolean> updateModel(MySQLPool client, Integer id, String model) {
        return client.preparedQuery("UPDATE apilot SET model = ? WHERE id = ?").execute(Tuple.of(model, id))
                .onItem().transform(pgRowSet -> pgRowSet.rowCount() == 1);
    }
}
