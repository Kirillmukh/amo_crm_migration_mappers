package com.example.dbeaver_migration_mappers.client;

import com.example.dbeaver_migration_mappers.client.crm.DefaultAmoCRMRestClientImpl;
import com.example.dbeaver_migration_mappers.client.database.HateoasCompanyDatabaseRestClient;
import com.example.dbeaver_migration_mappers.client.database.HateoasContactDatabaseRestClient;
import com.example.dbeaver_migration_mappers.client.database.HateoasLeadDatabaseRestClient;
import com.example.dbeaver_migration_mappers.client.database.HateoasOpportunityDatabaseRestClient;
import com.example.dbeaver_migration_mappers.input_models.hateoas.ListHateoasEntity;
import com.example.dbeaver_migration_mappers.input_models.request.RequestCompany;
import com.example.dbeaver_migration_mappers.input_models.request.RequestContact;
import com.example.dbeaver_migration_mappers.input_models.request.RequestLead;
import com.example.dbeaver_migration_mappers.input_models.request.RequestOpportunity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
@Configuration
public class RestClientConfiguration {
    @Value("${config.databaseUrl}")
    private String databaseUrl;
    @Bean
    public AmoCRMRestClient amoCRMRestClient(
            @Value("${config.baseUrl}") String baseUrl,
            @Value("${config.token}") String token) {
        RestClient restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("authorization", "Bearer " + token)
                .build();
        return new DefaultAmoCRMRestClientImpl(restClient);
    }
    @Bean
    public DatabaseRestClient<RequestCompany, ListHateoasEntity<RequestCompany>> companyDatabaseRestClient() {
        RestClient restClient = RestClient.builder()
                .baseUrl(databaseUrl)
                .build();
        return new HateoasCompanyDatabaseRestClient(restClient);
    }
    @Bean
    public DatabaseRestClient<RequestContact, ListHateoasEntity<RequestContact>> contactDatabaseRestClient() {
        RestClient restClient = RestClient.builder()
                .baseUrl(databaseUrl)
                .build();
        return new HateoasContactDatabaseRestClient(restClient);
    }
    @Bean
    public DatabaseRestClient<RequestLead, ListHateoasEntity<RequestLead>> leadDatabaseRestClient() {
        RestClient restClient = RestClient.builder()
                .baseUrl(databaseUrl)
                .build();
        return new HateoasLeadDatabaseRestClient(restClient);
    }
    @Bean
    public DatabaseRestClient<RequestOpportunity, ListHateoasEntity<RequestOpportunity>> opportunityDatabaseRestClient() {
        RestClient restClient = RestClient.builder()
                .baseUrl(databaseUrl)
                .build();
        return new HateoasOpportunityDatabaseRestClient(restClient);
    }
}
