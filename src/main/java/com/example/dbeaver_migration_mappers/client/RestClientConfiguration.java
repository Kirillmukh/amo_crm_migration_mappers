package com.example.dbeaver_migration_mappers.client;

import com.example.dbeaver_migration_mappers.client.crm.DefaultAmoCRMRestClientImpl;
import com.example.dbeaver_migration_mappers.client.database.HateoasCompanyDatabaseRestClient;
import com.example.dbeaver_migration_mappers.client.database.HateoasContactDatabaseRestClient;
import com.example.dbeaver_migration_mappers.client.database.HateoasLeadDatabaseRestClient;
import com.example.dbeaver_migration_mappers.client.database.HateoasOpportunityDatabaseRestClient;
import com.example.dbeaver_migration_mappers.client.hateoas_link.CompanyWithContactsHateoasRestClient;
import com.example.dbeaver_migration_mappers.client.hateoas_link.ContactWithoutCompanyHateoasRestClient;
import com.example.dbeaver_migration_mappers.client.hateoas_link.LeadHateoasRestClient;
import com.example.dbeaver_migration_mappers.input_models.hateoas.ListHateoasEntity;
import com.example.dbeaver_migration_mappers.input_models.request.RequestCompany;
import com.example.dbeaver_migration_mappers.input_models.request.RequestContact;
import com.example.dbeaver_migration_mappers.input_models.request.RequestLead;
import com.example.dbeaver_migration_mappers.input_models.request.RequestOpportunity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
@Configuration
public class RestClientConfiguration {
    @Value("${config.database.baseUrl}")
    private String databaseUrl;
    @Bean
    public AmoCRMRestClient amoCRMRestClient(
            @Value("${config.crm.baseUrl}") String baseUrl,
            @Value("${config.crm.token}") String token) {
        RestClient restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("authorization", "Bearer " + token)
                .build();
        return new DefaultAmoCRMRestClientImpl(restClient);
    }
    @Bean
    public DatabaseRestClient<RequestCompany, ListHateoasEntity<RequestCompany>> companyDatabaseRestClient() {
        RestClient restClient = databaseRestClient();
        return new HateoasCompanyDatabaseRestClient(restClient);
    }
    @Bean
    public DatabaseRestClient<RequestContact, ListHateoasEntity<RequestContact>> contactDatabaseRestClient() {
        RestClient restClient = databaseRestClient();
        return new HateoasContactDatabaseRestClient(restClient);
    }
    @Bean
    public DatabaseRestClient<RequestLead, ListHateoasEntity<RequestLead>> leadDatabaseRestClient() {
        RestClient restClient = databaseRestClient();
        return new HateoasLeadDatabaseRestClient(restClient);
    }
    @Bean
    public DatabaseRestClient<RequestOpportunity, ListHateoasEntity<RequestOpportunity>> opportunityDatabaseRestClient() {
        RestClient restClient = databaseRestClient();
        return new HateoasOpportunityDatabaseRestClient(restClient);
    }

    @Bean
    public LeadHateoasRestClient leadHateoasRestClient() {
        RestClient restClient = RestClient.builder()
                .build();
        return new LeadHateoasRestClient(restClient);
    }
    @Bean
    public CompanyWithContactsHateoasRestClient companyWithContactsHateoasRestClient() {
        RestClient restClient = RestClient.builder().build();
        return new CompanyWithContactsHateoasRestClient(restClient);
    }
    @Bean
    public ContactWithoutCompanyHateoasRestClient contactWithoutCompanyHateoasRestClient() {
        RestClient restClient = RestClient.builder().build();
        return new ContactWithoutCompanyHateoasRestClient(restClient);
    }

    @Bean
    public HateoasCompanyDatabaseRestClient hateoasCompanyDatabaseRestClient() {
        RestClient restClient = databaseRestClient();
        return new HateoasCompanyDatabaseRestClient(restClient);
    }
    @Bean
    public HateoasContactDatabaseRestClient hateoasContactDatabaseRestClient() {
        RestClient restClient = databaseRestClient();
        return new HateoasContactDatabaseRestClient(restClient);
    }
    private RestClient databaseRestClient() {
        return RestClient.builder().baseUrl(databaseUrl).build();
    }
}
