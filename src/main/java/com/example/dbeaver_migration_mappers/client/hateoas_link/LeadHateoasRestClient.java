package com.example.dbeaver_migration_mappers.client.hateoas_link;

import com.example.dbeaver_migration_mappers.client.HateoasLinkRestClient;
import com.example.dbeaver_migration_mappers.input_models.hateoas.Link;
import com.example.dbeaver_migration_mappers.input_models.hateoas.ListHateoasEntity;
import com.example.dbeaver_migration_mappers.input_models.request.RequestLead;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@RequiredArgsConstructor
public class LeadHateoasRestClient implements HateoasLinkRestClient<ListHateoasEntity<RequestLead>> {
    private final RestClient restClient;
    private static final ParameterizedTypeReference<ListHateoasEntity<RequestLead>> LIST_RESPONSE_TYPE_REFERENCE = new ParameterizedTypeReference<>() {
    };

    @Override
    public Optional<ListHateoasEntity<RequestLead>> request(Link value) {
        return Optional.ofNullable(restClient.get()
                .uri(value.href())
                .retrieve()
                .body(LIST_RESPONSE_TYPE_REFERENCE));
    }
}
