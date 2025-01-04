package com.example.dbeaver_migration_mappers.client.hateoas_link;

import com.example.dbeaver_migration_mappers.client.HateoasLinkRestClient;
import com.example.dbeaver_migration_mappers.client.dto.ListResponseLeadDTO;
import com.example.dbeaver_migration_mappers.input_models.hateoas.Link;
import com.example.dbeaver_migration_mappers.input_models.hateoas.ListHateoasEntity;
import com.example.dbeaver_migration_mappers.input_models.request.RequestLead;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class LeadHateoasRestClient implements HateoasLinkRestClient<ListHateoasEntity<RequestLead>> {
    private final RestClient restClient;

    @Override
    public Optional<ListHateoasEntity<RequestLead>> request(Link value) {
        log.info("request to link: {}", value);
        return Optional.ofNullable(restClient.get()
                .uri(value.href())
                .retrieve()
                .body(ListResponseLeadDTO.class)).map(ListResponseLeadDTO::getEntity);
    }
}
