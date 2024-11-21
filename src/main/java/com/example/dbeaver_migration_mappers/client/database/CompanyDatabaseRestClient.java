package com.example.dbeaver_migration_mappers.client.database;

import com.example.dbeaver_migration_mappers.client.DatabaseRestClient;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;

@RequiredArgsConstructor
public class CompanyDatabaseRestClient implements DatabaseRestClient  {
    private final RestClient restClient;
    @Override
    public Response request() {
        return restClient.get()
                .uri("company")
                .retrieve()
                .body(Response.class);
    }

    @Override
    public Response request(int limit, int offset) {
        return restClient.get()
                .uri("company?limit={limit}&offset={offset}", limit, offset)
                .retrieve()
                .body(Response.class);
    }

    @Override
    public Response request(@DateTimeFormat(pattern = "${config.date_format}") LocalDate date, int limit, int offset) {
        return restClient.get()
                .uri("company?limit={limit}&offset={offset}&date={date}", limit, offset, date)
                .retrieve()
                .body(Response.class);
    }

    @Override
    public Response requestById(String id) {
        return restClient.get()
                .uri("company/{id}", id)
                .retrieve()
                .body(Response.class);
    }

    @Override
    public Response requestById(String id, @DateTimeFormat(pattern = "${config.date_format}") LocalDate date) {
        return restClient.get()
                .uri("company/{id}?date={date}", id, date)
                .retrieve()
                .body(Response.class);
    }
}
