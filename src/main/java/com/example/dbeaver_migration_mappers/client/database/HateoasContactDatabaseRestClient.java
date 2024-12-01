package com.example.dbeaver_migration_mappers.client.database;

import com.example.dbeaver_migration_mappers.client.DatabaseRestClient;
import com.example.dbeaver_migration_mappers.input_models.hateoas.ListHateoasEntity;
import com.example.dbeaver_migration_mappers.input_models.request.RequestContact;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
public class HateoasContactDatabaseRestClient implements DatabaseRestClient<RequestContact, ListHateoasEntity<RequestContact>> {
    private final RestClient restClient;
    @Override
    public Optional<ListHateoasEntity<RequestContact>> request() {
        ListHateoasEntity<RequestContact> result = null;
        try (Response response = restClient.get()
                .uri("contact")
                .retrieve()
                .body(Response.class)) {
            if (response.hasEntity()) {
                result = (ListHateoasEntity<RequestContact>) response.getEntity();
            }
        }
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<ListHateoasEntity<RequestContact>> request(int limit, int offset) {
        ListHateoasEntity<RequestContact> result = null;
        try (Response response = restClient.get()
                .uri("contact?limit={limit}&offset={offset}", limit, offset)
                .retrieve()
                .body(Response.class)) {
            if (response.hasEntity()) {
                result = (ListHateoasEntity<RequestContact>) response.getEntity();
            }
        }
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<ListHateoasEntity<RequestContact>> request(@DateTimeFormat(pattern = "${config.dateFormat}") LocalDate date, int limit, int offset) {
        ListHateoasEntity<RequestContact> result = null;
        try (Response response = restClient.get()
                .uri("contact?limit={limit}&offset={offset}&date={date}", limit, offset, date)
                .retrieve()
                .body(Response.class)) {
            if (response.hasEntity()) {
                result = (ListHateoasEntity<RequestContact>) response.getEntity();
            }
        }
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<RequestContact> requestById(String id) {
        RequestContact result = null;
        try (Response response = restClient.get()
                .uri("contact/{id}", id)
                .retrieve()
                .body(Response.class)) {
            if (response.hasEntity()) {
                result = (RequestContact) response.getEntity();
            }
        }
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<RequestContact> requestById(String id, @DateTimeFormat(pattern = "${config.dateFormat}") LocalDate date) {
        RequestContact result = null;
        try (Response response = restClient.get()
                .uri("contact/{id}?date={date}", id, date)
                .retrieve()
                .body(Response.class)) {
            if (response.hasEntity()) {
                result = (RequestContact) response.getEntity();
            }
        }
        return Optional.ofNullable(result);
    }
}
