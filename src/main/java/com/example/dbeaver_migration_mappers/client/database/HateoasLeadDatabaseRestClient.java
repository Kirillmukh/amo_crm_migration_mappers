package com.example.dbeaver_migration_mappers.client.database;

import com.example.dbeaver_migration_mappers.client.DatabaseRestClient;
import com.example.dbeaver_migration_mappers.client.dto.ListResponseLeadDTO;
import com.example.dbeaver_migration_mappers.client.dto.ResponseLeadDTO;
import com.example.dbeaver_migration_mappers.input_models.hateoas.ListHateoasEntity;
import com.example.dbeaver_migration_mappers.input_models.request.RequestLead;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class HateoasLeadDatabaseRestClient implements DatabaseRestClient<RequestLead, ListHateoasEntity<RequestLead>> {
    private final RestClient restClient;

    @Override
    public Optional<ListHateoasEntity<RequestLead>> request() {
        ListResponseLeadDTO response = restClient.get()
                .uri("lead")
                .retrieve()
                .body(ListResponseLeadDTO.class);
        return Optional.ofNullable(response.getEntity());
    }

    @Override
    public Optional<ListHateoasEntity<RequestLead>> request(@DateTimeFormat(pattern = "${config.dateFormat}") LocalDate date) {
        ListResponseLeadDTO response = restClient.get()
                .uri("lead?date={date}", date)
                .retrieve()
                .body(ListResponseLeadDTO.class);
        return Optional.ofNullable(response.getEntity());
    }

    @Override
    public Optional<ListHateoasEntity<RequestLead>> request(@DateTimeFormat(pattern = "${config.dateFormat}") LocalDate date, int limit, int offset) {
        ListResponseLeadDTO response = restClient.get()
                .uri("lead?limit={limit}&offset={offset}&date={date}", limit, offset, date)
                .retrieve()
                .body(ListResponseLeadDTO.class);
        return Optional.ofNullable(response.getEntity());
    }

    @Override
    public Optional<RequestLead> requestById(String id) {
        ResponseLeadDTO response = restClient.get()
                .uri("lead/{id}", id)
                .retrieve()
                .body(ResponseLeadDTO.class);
        return Optional.ofNullable(response.getEntity());
    }

    @Override
    public Optional<RequestLead> requestById(String id, @DateTimeFormat(pattern = "${config.dateFormat}") LocalDate date) {
        ResponseLeadDTO response = restClient.get()

                .uri("lead/{id}?date={date}", id, date)
                .retrieve()
                .body(ResponseLeadDTO.class);
        return Optional.ofNullable(response.getEntity());
    }
}
