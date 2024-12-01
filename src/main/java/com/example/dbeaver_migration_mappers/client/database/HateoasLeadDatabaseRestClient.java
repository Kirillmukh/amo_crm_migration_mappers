package com.example.dbeaver_migration_mappers.client.database;

import com.example.dbeaver_migration_mappers.client.DatabaseRestClient;
import com.example.dbeaver_migration_mappers.input_models.hateoas.ListHateoasEntity;
import com.example.dbeaver_migration_mappers.input_models.request.RequestLead;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
public class HateoasLeadDatabaseRestClient implements DatabaseRestClient<RequestLead, ListHateoasEntity<RequestLead>>  {
    private final RestClient restClient;
    @Override
    public Optional<ListHateoasEntity<RequestLead>> request() {
        ListHateoasEntity<RequestLead> result = null;
        try (Response response = restClient.get()
                .uri("lead")
                .retrieve()
                .body(Response.class)) {
            if (response.hasEntity()) {
                result = (ListHateoasEntity<RequestLead>) response.getEntity();
            }
        }
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<ListHateoasEntity<RequestLead>> request(int limit, int offset) {
        ListHateoasEntity<RequestLead> result = null;
        try (Response response = restClient.get()
                .uri("lead?limit={limit}&offset={offset}", limit, offset)
                .retrieve()
                .body(Response.class)) {
            if (response.hasEntity()) {
                result = (ListHateoasEntity<RequestLead>) response.getEntity();
            }
        }
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<ListHateoasEntity<RequestLead>> request(@DateTimeFormat(pattern = "${config.dateFormat}") LocalDate date, int limit, int offset) {
        ListHateoasEntity<RequestLead> result = null;
        try (Response response = restClient.get()
                .uri("lead?limit={limit}&offset={offset}&date={date}", limit, offset, date)
                .retrieve()
                .body(Response.class)) {
            if (response.hasEntity()) {
                result = (ListHateoasEntity<RequestLead>) response.getEntity();
            }
        }
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<RequestLead> requestById(String id) {
        RequestLead result = null;
        try (Response response = restClient.get()
                .uri("lead/{id}", id)
                .retrieve()
                .body(Response.class)) {
            if (response.hasEntity()) {
                result = (RequestLead) response.getEntity();
            }
        }
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<RequestLead> requestById(String id, @DateTimeFormat(pattern = "${config.dateFormat}") LocalDate date) {
        RequestLead result = null;
        try (Response response = restClient.get()
                .uri("lead/{id}?date={date}", id, date)
                .retrieve()
                .body(Response.class)) {
            if (response.hasEntity()) {
                result = (RequestLead) response.getEntity();
            }
        }
        return Optional.ofNullable(result);
    }
}
