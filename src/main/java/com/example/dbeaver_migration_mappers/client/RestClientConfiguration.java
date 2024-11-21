package com.example.dbeaver_migration_mappers.client;

import com.example.dbeaver_migration_mappers.client.crm.DefaultAmoCRMRestClientImpl;
import com.example.dbeaver_migration_mappers.client.database.CompanyDatabaseRestClient;
import com.example.dbeaver_migration_mappers.client.database.ContactDatabaseRestClient;
import com.example.dbeaver_migration_mappers.client.database.LeadDatabaseRestClient;
import com.example.dbeaver_migration_mappers.client.database.OpportunityDatabaseRestClient;
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
    public DatabaseRestClient companyDatabaseRestClient() {
        RestClient restClient = RestClient.builder()
                .baseUrl(databaseUrl)
                .build();
        return new CompanyDatabaseRestClient(restClient);
    }
    @Bean
    public DatabaseRestClient contactDatabaseRestClient() {
        RestClient restClient = RestClient.builder()
                .baseUrl(databaseUrl)
                .build();
        return new ContactDatabaseRestClient(restClient);
    }
    @Bean
    public DatabaseRestClient leadDatabaseRestClient() {
        RestClient restClient = RestClient.builder()
                .baseUrl(databaseUrl)
                .build();
        return new LeadDatabaseRestClient(restClient);
    }
    @Bean
    public DatabaseRestClient opportunityDatabaseRestClient() {
        RestClient restClient = RestClient.builder()
                .baseUrl(databaseUrl)
                .build();
        return new OpportunityDatabaseRestClient(restClient);
    }
}
