package com.example.dbeaver_migration_mappers.client.hateoas_link;

import com.example.dbeaver_migration_mappers.client.HateoasLinkRestClient;
import com.example.dbeaver_migration_mappers.client.dto.ListResponseNewContactsOldCompaniesDTO;
import com.example.dbeaver_migration_mappers.input_models.hateoas.Link;
import com.example.dbeaver_migration_mappers.input_models.hateoas.ListHateoasEntity;
import com.example.dbeaver_migration_mappers.input_models.request.RequestContactAndCompany;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class NewContactOldCompanyHateoasRestClient implements HateoasLinkRestClient<ListHateoasEntity<RequestContactAndCompany>> {
    private final RestClient restClient;

    @Override
    public Optional<ListHateoasEntity<RequestContactAndCompany>> request(Link value) {
        log.info("request to link: {}", value);
        return Optional.ofNullable(restClient.get()
                .uri(value.href())
                .retrieve()
                .body(ListResponseNewContactsOldCompaniesDTO.class))
                .map(ListResponseNewContactsOldCompaniesDTO::getEntity);
    }
}
