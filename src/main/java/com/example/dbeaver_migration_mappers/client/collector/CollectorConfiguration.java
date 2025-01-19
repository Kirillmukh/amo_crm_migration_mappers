package com.example.dbeaver_migration_mappers.client.collector;

import com.example.dbeaver_migration_mappers.client.DatabaseRestClient;
import com.example.dbeaver_migration_mappers.client.HateoasLinkRestClient;
import com.example.dbeaver_migration_mappers.client.database.HateoasCompanyDatabaseRestClient;
import com.example.dbeaver_migration_mappers.client.database.HateoasContactDatabaseRestClient;
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
            DatabaseRestClient<RequestLead, ListHateoasEntity<RequestLead>> leadDatabaseRestClient) {
        return new RequestCollector<>(leadHateoasRestClient, leadDatabaseRestClient::request);
    }

    @Bean
    public RequestCollector<RequestCompanyWithContactsDTO> requestCompanyWithContactsCollector(
            CompanyWithContactsHateoasRestClient companyHateoasRestClient,
            HateoasCompanyDatabaseRestClient companyDatabaseRestClient) {
        return new RequestCollector<>(companyHateoasRestClient, companyDatabaseRestClient::requestCompanyWithContacts);
    }
    @Bean
    public RequestCollector<RequestContactWithoutCompanyDTO> requestContactWithoutCompanyCollector(
            ContactWithoutCompanyHateoasRestClient contactHateoasRestClient,
            HateoasContactDatabaseRestClient contactDatabaseRestClient) {
        return new RequestCollector<>(contactHateoasRestClient, contactDatabaseRestClient::requestContactWithoutCompany);
    }
}
