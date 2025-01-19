package com.example.dbeaver_migration_mappers.client.database;

import com.example.dbeaver_migration_mappers.client.DatabaseRestClient;
import com.example.dbeaver_migration_mappers.client.dto.ListResponseContactsWithoutContactDTO;
import com.example.dbeaver_migration_mappers.input_models.hateoas.ListHateoasEntity;
import com.example.dbeaver_migration_mappers.input_models.request.RequestContact;
import com.example.dbeaver_migration_mappers.input_models.request.RequestContactWithoutCompanyDTO;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
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
    public Optional<ListHateoasEntity<RequestContact>> request(@DateTimeFormat(pattern = "${config.dateFormat}") LocalDate date) {
        ListHateoasEntity<RequestContact> result = null;
        try (Response response = restClient.get()
                .uri("contact?date={date}", date)
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
    public Optional<ListHateoasEntity<RequestContactWithoutCompanyDTO>> requestContactWithoutCompany(@DateTimeFormat(pattern = "${config.dateFormat}") LocalDate date, int limit, int offset) {
        ListResponseContactsWithoutContactDTO body = restClient.get()
                .uri("new-companies?limit={limit}&offset={offset}&date={date}", limit, offset, date)
                .retrieve()
                .body(ListResponseContactsWithoutContactDTO.class);
        return Optional.ofNullable(body.getEntity());
    }
    public Optional<ListHateoasEntity<RequestContactWithoutCompanyDTO>> requestContactWithoutCompany(@DateTimeFormat(pattern = "${config.dateFormat}") LocalDate date) {
        ListResponseContactsWithoutContactDTO body = restClient.get()
                .uri("new-companies?date={date}", date)
                .retrieve()
                .body(ListResponseContactsWithoutContactDTO.class);
        return Optional.ofNullable(body.getEntity());
    }
    public Optional<ListHateoasEntity<RequestContactWithoutCompanyDTO>> requestContactWithoutCompany() {
        ListResponseContactsWithoutContactDTO body = restClient.get()
                .uri("new-companies")
                .retrieve()
                .body(ListResponseContactsWithoutContactDTO.class);
        return Optional.ofNullable(body.getEntity());
    }
}
