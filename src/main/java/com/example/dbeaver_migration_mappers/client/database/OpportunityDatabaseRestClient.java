package com.example.dbeaver_migration_mappers.client.database;

import com.example.dbeaver_migration_mappers.client.DatabaseRestClient;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;

@RequiredArgsConstructor
public class OpportunityDatabaseRestClient implements DatabaseRestClient  {
    private final RestClient restClient;
    @Override
    public Response request() {
        return restClient.get()
                .uri("opportunity")
                .retrieve()
                .body(Response.class);
    }

    @Override
    public Response request(int limit, int offset) {
        return restClient.get()
                .uri("opportunity?limit={limit}&offset={offset}", limit, offset)
                .retrieve()
                .body(Response.class);
    }

    @Override
    public Response request(@DateTimeFormat(pattern = "${config.dateFormat}") LocalDate date, int limit, int offset) {
        return restClient.get()
                .uri("opportunity?limit={limit}&offset={offset}&date={date}", limit, offset, date)
                .retrieve()
                .body(Response.class);
    }

    @Override
    public Response requestById(String id) {
        return restClient.get()
                .uri("opportunity/{id}", id)
                .retrieve()
                .body(Response.class);
    }

    @Override
    public Response requestById(String id, @DateTimeFormat(pattern = "${config.dateFormat}") LocalDate date) {
        return restClient.get()
                .uri("opportunity/{id}?date={date}", id, date)
                .retrieve()
                .body(Response.class);
    }
}
