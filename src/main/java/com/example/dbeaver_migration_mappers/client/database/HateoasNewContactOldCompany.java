package com.example.dbeaver_migration_mappers.client.database;

import com.example.dbeaver_migration_mappers.client.DatabaseRequestRestClient;
import com.example.dbeaver_migration_mappers.client.dto.ListResponseNewContactsOldCompaniesDTO;
import com.example.dbeaver_migration_mappers.input_models.hateoas.ListHateoasEntity;
import com.example.dbeaver_migration_mappers.input_models.request.RequestContactAndCompany;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
public class HateoasNewContactOldCompany implements DatabaseRequestRestClient<ListHateoasEntity<RequestContactAndCompany>> {
    private final RestClient restClient;
    @Override
    public Optional<ListHateoasEntity<RequestContactAndCompany>> request() {
        ListResponseNewContactsOldCompaniesDTO body = restClient.get()
                .uri("new-contacts-old-companies")
                .retrieve()
                .body(ListResponseNewContactsOldCompaniesDTO.class);
        return Optional.ofNullable(body.getEntity());
    }

    @Override
    public Optional<ListHateoasEntity<RequestContactAndCompany>> request(LocalDate date) {
        ListResponseNewContactsOldCompaniesDTO body = restClient.get()
                .uri("new-contacts-old-companies?date={date}", date)
                .retrieve()
                .body(ListResponseNewContactsOldCompaniesDTO.class);
        return Optional.ofNullable(body.getEntity());
    }

    @Override
    public Optional<ListHateoasEntity<RequestContactAndCompany>> request(LocalDate date, int limit, int offset) {
        ListResponseNewContactsOldCompaniesDTO body = restClient.get()
                .uri("new-contacts-old-companies?limit={limit}&offset={offset}&date={date}", limit, offset, date)
                .retrieve()
                .body(ListResponseNewContactsOldCompaniesDTO.class);
        return Optional.ofNullable(body.getEntity());
    }
}
