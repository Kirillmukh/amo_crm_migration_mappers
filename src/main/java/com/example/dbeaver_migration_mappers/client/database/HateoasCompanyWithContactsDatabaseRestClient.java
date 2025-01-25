package com.example.dbeaver_migration_mappers.client.database;

import com.example.dbeaver_migration_mappers.client.DatabaseRequestRestClient;
import com.example.dbeaver_migration_mappers.client.dto.ListResponseCompanyWithContactsDTO;
import com.example.dbeaver_migration_mappers.input_models.hateoas.ListHateoasEntity;
import com.example.dbeaver_migration_mappers.input_models.request.RequestCompanyWithContactsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
public class HateoasCompanyWithContactsDatabaseRestClient implements DatabaseRequestRestClient<ListHateoasEntity<RequestCompanyWithContactsDTO>> {
    private final RestClient restClient;
    public Optional<ListHateoasEntity<RequestCompanyWithContactsDTO>> request() {
        ListResponseCompanyWithContactsDTO body = restClient.get()
                .uri("new-companies")
                .retrieve()
                .body(ListResponseCompanyWithContactsDTO.class);
        return Optional.ofNullable(body.getEntity());
    }
    public Optional<ListHateoasEntity<RequestCompanyWithContactsDTO>> request(@DateTimeFormat(pattern = "${config.dateFormat}") LocalDate date) {
        ListResponseCompanyWithContactsDTO body = restClient.get()
                .uri("new-companies?date={date}", date)
                .retrieve()
                .body(ListResponseCompanyWithContactsDTO.class);
        return Optional.ofNullable(body.getEntity());
    }
    public Optional<ListHateoasEntity<RequestCompanyWithContactsDTO>> request(@DateTimeFormat(pattern = "${config.dateFormat}") LocalDate date, int limit, int offset) {
        ListResponseCompanyWithContactsDTO body = restClient.get()
                .uri("new-companies?limit={limit}&offset={offset}&date={date}", limit, offset, date)
                .retrieve()
                .body(ListResponseCompanyWithContactsDTO.class);
        return Optional.ofNullable(body.getEntity());
    }
}
