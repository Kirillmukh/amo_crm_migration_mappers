package com.example.dbeaver_migration_mappers.client.database;

import com.example.dbeaver_migration_mappers.client.DatabaseRestClient;
import com.example.dbeaver_migration_mappers.input_models.hateoas.ListHateoasEntity;
import com.example.dbeaver_migration_mappers.input_models.request.RequestCompany;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
public class HateoasCompanyDatabaseRestClient implements DatabaseRestClient<RequestCompany, ListHateoasEntity<RequestCompany>> {
    private final RestClient restClient;
    @Override
    public Optional<ListHateoasEntity<RequestCompany>> request() {
        ListHateoasEntity<RequestCompany> result = null;
        try (Response response = restClient.get()
                .uri("company")
                .retrieve()
                .body(Response.class)) {
            if (response.hasEntity()) {
                result = (ListHateoasEntity<RequestCompany>) response.getEntity();
            }
        }
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<ListHateoasEntity<RequestCompany>> request(int limit, int offset) {
        ListHateoasEntity<RequestCompany> result = null;
        try (Response response = restClient.get()
                .uri("company?limit={limit}&offset={offset}", limit, offset)
                .retrieve()
                .body(Response.class)) {
            if (response.hasEntity()) {
                result = (ListHateoasEntity<RequestCompany>) response.getEntity();
            }
        }
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<ListHateoasEntity<RequestCompany>> request(@DateTimeFormat(pattern = "${config.dateFormat}") LocalDate date, int limit, int offset) {
        ListHateoasEntity<RequestCompany> result = null;
        try (Response response = restClient.get()
                .uri("company?limit={limit}&offset={offset}&date={date}", limit, offset, date)
                .retrieve()
                .body(Response.class)) {
            if (response.hasEntity()) {
                result = (ListHateoasEntity<RequestCompany>) response.getEntity();
            }
        }
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<RequestCompany> requestById(String id) {
        RequestCompany result = null;
        try (Response response = restClient.get()
                .uri("company/{id}", id)
                .retrieve()
                .body(Response.class)) {
            if (response.hasEntity()) {
                result = (RequestCompany) response.getEntity();
            }
        }
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<RequestCompany> requestById(String id, @DateTimeFormat(pattern = "${config.dateFormat}") LocalDate date) {
        RequestCompany result = null;
        try (Response response = restClient.get()
                .uri("company/{id}?date={date}", id, date)
                .retrieve()
                .body(Response.class)) {
            if (response.hasEntity()) {
                result = (RequestCompany) response.getEntity();
            }
        }
        return Optional.ofNullable(result);
    }

}
