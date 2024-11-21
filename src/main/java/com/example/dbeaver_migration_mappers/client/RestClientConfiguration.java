package com.example.dbeaver_migration_mappers.client;

import com.example.dbeaver_migration_mappers.client.crm.DefaultAmoCRMRestClientImpl;
import com.example.dbeaver_migration_mappers.client.database.CompanyDatabaseRestClient;
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
}
