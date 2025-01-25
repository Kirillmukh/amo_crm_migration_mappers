package com.example.dbeaver_migration_mappers.client.collector;

import com.example.dbeaver_migration_mappers.client.DatabaseRequestRestClient;
import com.example.dbeaver_migration_mappers.client.HateoasLinkRestClient;
import com.example.dbeaver_migration_mappers.client.hateoas_link.CompanyWithContactsHateoasRestClient;
import com.example.dbeaver_migration_mappers.client.hateoas_link.ContactWithoutCompanyHateoasRestClient;
import com.example.dbeaver_migration_mappers.input_models.hateoas.ListHateoasEntity;
import com.example.dbeaver_migration_mappers.input_models.request.RequestCompanyWithContactsDTO;
import com.example.dbeaver_migration_mappers.input_models.request.RequestContactWithoutCompanyDTO;
import com.example.dbeaver_migration_mappers.input_models.request.RequestLead;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CollectorConfiguration {
    @Bean
    public RequestCollector<RequestLead> requestLeadCollector(
            HateoasLinkRestClient<ListHateoasEntity<RequestLead>> leadHateoasRestClient,
            DatabaseRequestRestClient<ListHateoasEntity<RequestLead>> leadDatabaseRestClient) {
        return new RequestCollector<>(leadHateoasRestClient, leadDatabaseRestClient::request);
    }

    @Bean
    public RequestCollector<RequestCompanyWithContactsDTO> requestCompanyWithContactsCollector(
            CompanyWithContactsHateoasRestClient companyHateoasRestClient,
            DatabaseRequestRestClient<ListHateoasEntity<RequestCompanyWithContactsDTO>> companyDatabaseRestClient) {
        return new RequestCollector<>(companyHateoasRestClient, companyDatabaseRestClient::request);
    }
    @Bean
    public RequestCollector<RequestContactWithoutCompanyDTO> requestContactWithoutCompanyCollector(
            ContactWithoutCompanyHateoasRestClient contactHateoasRestClient,
            DatabaseRequestRestClient<ListHateoasEntity<RequestContactWithoutCompanyDTO>> contactDatabaseRestClient) {
        return new RequestCollector<>(contactHateoasRestClient, contactDatabaseRestClient::request);
    }
}
