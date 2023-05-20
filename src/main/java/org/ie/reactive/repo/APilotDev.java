package org.ie.reactive.repo;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.mysqlclient.MySQLPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;

/**
 * The APilotDev class represents a APilotDev object.
 */
public class APilotDev {

    @JsonProperty("company")
    private String company;

    public APilotDev() {
    }

    public APilotDev(String company) {
        this.company = company;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    private static APilotDev from(Row row) {
        return new APilotDev(row.getString("company"));
    }

    public static Multi<APilotDev> findAll(MySQLPool client) {
        return client.query("SELECT company FROM apilot_dev ORDER BY company ASC").execute()
                .onItem().transformToMulti(set -> Multi.createFrom().iterable(set))
                .onItem().transform(APilotDev::from);
    }

    public static Uni<APilotDev> findById(MySQLPool client, String company) {
        return client.preparedQuery("SELECT company FROM apilot_dev WHERE company = ?").execute(Tuple.of(company))
                .onItem().transform(RowSet::iterator)
                .onItem().transform(iterator -> iterator.hasNext() ? from(iterator.next()) : null);
    }

    public Uni<Boolean> save(MySQLPool client) {
        return client.preparedQuery("INSERT INTO apilot_dev(company) VALUES (?)").execute(Tuple.of(company)).onItem()
                .transform(pgRowSet -> pgRowSet.rowCount() == 1);
    }

    public static Uni<Boolean> delete(MySQLPool client, String company) {
        return client.preparedQuery("DELETE FROM apilot_dev WHERE company = ?").execute(Tuple.of(company)).onItem()
                .transform(pgRowSet -> pgRowSet.rowCount() == 1);
    }

    public static Uni<Boolean> updateCompany(MySQLPool client, String company) {
        return client.preparedQuery("UPDATE apilot_dev SET company = ? WHERE company = ?").execute(Tuple.of(company))
                .onItem().transform(pgRowSet -> pgRowSet.rowCount() == 1);
    }
}
