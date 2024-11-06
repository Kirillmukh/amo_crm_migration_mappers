package com.example.dbeaver_migration_mappers.client;

import com.example.dbeaver_migration_mappers.output_models.response.OutputCompany;
import com.example.dbeaver_migration_mappers.output_models.util.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

@RequiredArgsConstructor
public class DefaultAmoCRMRestClientImpl implements AmoCRMRestClient {
    private final RestClient restClient;
    private static final ParameterizedTypeReference<List<Tag>> TAGS_TYPE_REFERENCE = new ParameterizedTypeReference<List<Tag>>() {
    };
    @Override
    public OutputCompany getCompanies(int id) {
        return restClient.get()
                .uri("companies/{id}", id)
                .retrieve()
                .body(OutputCompany.class);
    }

    @Override
    public List<Tag> createTags(List<Tag> tags, EntityType entityType) {
        return restClient.post()
                .uri("{entityType}/tags", entityType.getName())
                .body(tags)
                .retrieve()
                .body(TAGS_TYPE_REFERENCE);
    }
}
