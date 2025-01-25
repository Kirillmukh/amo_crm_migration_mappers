package com.example.dbeaver_migration_mappers.client.database;

import com.example.dbeaver_migration_mappers.client.DatabaseRequestRestClient;
import com.example.dbeaver_migration_mappers.client.dto.ListResponseContactsWithoutContactDTO;
import com.example.dbeaver_migration_mappers.input_models.hateoas.ListHateoasEntity;
import com.example.dbeaver_migration_mappers.input_models.request.RequestContactWithoutCompanyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
public class HateoasContactWithoutCompanyDatabaseRestClient implements DatabaseRequestRestClient<ListHateoasEntity<RequestContactWithoutCompanyDTO>> {
    private final RestClient restClient;
    public Optional<ListHateoasEntity<RequestContactWithoutCompanyDTO>> request() {
        ListResponseContactsWithoutContactDTO body = restClient.get()
                .uri("new-contacts")
                .retrieve()
                .body(ListResponseContactsWithoutContactDTO.class);
        return Optional.ofNullable(body.getEntity());
    }
    public Optional<ListHateoasEntity<RequestContactWithoutCompanyDTO>> request(@DateTimeFormat(pattern = "${config.dateFormat}") LocalDate date) {
        ListResponseContactsWithoutContactDTO body = restClient.get()
                .uri("new-contacts?date={date}", date)
                .retrieve()
                .body(ListResponseContactsWithoutContactDTO.class);
        return Optional.ofNullable(body.getEntity());
    }
    public Optional<ListHateoasEntity<RequestContactWithoutCompanyDTO>> request(@DateTimeFormat(pattern = "${config.dateFormat}") LocalDate date, int limit, int offset) {
        ListResponseContactsWithoutContactDTO body = restClient.get()
                .uri("new-contacts?limit={limit}&offset={offset}&date={date}", limit, offset, date)
                .retrieve()
                .body(ListResponseContactsWithoutContactDTO.class);
        return Optional.ofNullable(body.getEntity());
    }
}
