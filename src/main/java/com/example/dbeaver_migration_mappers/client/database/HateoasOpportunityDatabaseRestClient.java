package com.example.dbeaver_migration_mappers.client.database;

import com.example.dbeaver_migration_mappers.client.DatabaseRestClient;
import com.example.dbeaver_migration_mappers.input_models.hateoas.ListHateoasEntity;
import com.example.dbeaver_migration_mappers.input_models.request.RequestOpportunity;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
public class HateoasOpportunityDatabaseRestClient implements DatabaseRestClient<RequestOpportunity, ListHateoasEntity<RequestOpportunity>>  {
    private final RestClient restClient;
    @Override
    public Optional<ListHateoasEntity<RequestOpportunity>> request() {
        ListHateoasEntity<RequestOpportunity> result = null;
        try (Response response = restClient.get()
                .uri("opportunity")
                .retrieve()
                .body(Response.class)) {
            if (response.hasEntity()) {
                result = (ListHateoasEntity<RequestOpportunity>) response.getEntity();
            }
        }
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<ListHateoasEntity<RequestOpportunity>> request(int limit, int offset) {
        ListHateoasEntity<RequestOpportunity> result = null;
        try (Response response = restClient.get()
                .uri("opportunity?limit={limit}&offset={offset}", limit, offset)
                .retrieve()
                .body(Response.class)) {
            if (response.hasEntity()) {
                result = (ListHateoasEntity<RequestOpportunity>) response.getEntity();
            }
        }
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<ListHateoasEntity<RequestOpportunity>> request(@DateTimeFormat(pattern = "${config.dateFormat}") LocalDate date, int limit, int offset) {
        ListHateoasEntity<RequestOpportunity> result = null;
        try (Response response = restClient.get()
                .uri("opportunity?limit={limit}&offset={offset}&date={date}", limit, offset, date)
                .retrieve()
                .body(Response.class)) {
            if (response.hasEntity()) {
                result = (ListHateoasEntity<RequestOpportunity>) response.getEntity();
            }
        }
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<RequestOpportunity> requestById(String id) {
        RequestOpportunity result = null;
        try (Response response = restClient.get()
                .uri("opportunity/{id}", id)
                .retrieve()
                .body(Response.class)) {
            if (response.hasEntity()) {
                result = (RequestOpportunity) response.getEntity();
            }
        }
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<RequestOpportunity> requestById(String id, @DateTimeFormat(pattern = "${config.dateFormat}") LocalDate date) {
        RequestOpportunity result = null;
        try (Response response = restClient.get()
                .uri("opportunity/{id}?date={date}", id, date)
                .retrieve()
                .body(Response.class)) {
            if (response.hasEntity()) {
                result = (RequestOpportunity) response.getEntity();
            }
        }
        return Optional.ofNullable(result);
    }
}
