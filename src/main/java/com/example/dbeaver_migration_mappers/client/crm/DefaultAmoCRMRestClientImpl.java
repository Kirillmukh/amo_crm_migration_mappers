package com.example.dbeaver_migration_mappers.client.crm;

import com.example.dbeaver_migration_mappers.client.AmoCRMRestClient;
import com.example.dbeaver_migration_mappers.crm_models.response.CRMCompany;
import com.example.dbeaver_migration_mappers.crm_models.util.ResponseTag;
import com.example.dbeaver_migration_mappers.crm_models.util.Tag;
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
    public CRMCompany getCompanies(int id) {
        return restClient.get()
                .uri("companies/{id}", id)
                .retrieve()
                .body(CRMCompany.class);
    }

    @Override
    public ResponseTag createTags(List<Tag> tags, EntityType entityType) {
        String type = entityType.getValues();
        return restClient.post()
                .uri("{type}/tags", type)
                .body(tags)
                .retrieve()
                .body(ResponseTag.class);
    }

    @Override
    public ResponseTag getTags(EntityType entityType, int page) {
        String type = entityType.getValues();
        return restClient.get()
                .uri("{type}/tags?page={page}&limit=250", type, page)
                .retrieve()
                .body(ResponseTag.class);
    }

//    @Override
//    public void createComplexCompany(CRMComplexCompany complexCompanyCRM) {
////         todo
//    }
}
