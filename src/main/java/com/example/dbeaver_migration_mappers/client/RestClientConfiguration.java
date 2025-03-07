package com.example.dbeaver_migration_mappers.client;

import com.example.dbeaver_migration_mappers.client.crm.DefaultAmoCRMRestClientImpl;
import com.example.dbeaver_migration_mappers.client.crm.DeleteAmoCRMSourceRestClient;
import com.example.dbeaver_migration_mappers.client.database.*;
import com.example.dbeaver_migration_mappers.client.hateoas_link.CompanyWithContactsHateoasRestClient;
import com.example.dbeaver_migration_mappers.client.hateoas_link.ContactWithoutCompanyHateoasRestClient;
import com.example.dbeaver_migration_mappers.client.hateoas_link.LeadHateoasRestClient;
import com.example.dbeaver_migration_mappers.client.hateoas_link.NewContactOldCompanyHateoasRestClient;
import com.example.dbeaver_migration_mappers.input_models.hateoas.ListHateoasEntity;
import com.example.dbeaver_migration_mappers.input_models.request.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
@Configuration
@Slf4j
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
    // DATABASE
    @Bean
    public DatabaseRequestRestClient<ListHateoasEntity<RequestCompany>> companyDatabaseRestClient() {
        RestClient restClient = databaseRestClient();
        return new HateoasCompanyDatabaseRestClient(restClient);
    }
    @Bean
    public DatabaseRequestRestClient<ListHateoasEntity<RequestContact>> contactDatabaseRestClient() {
        RestClient restClient = databaseRestClient();
        return new HateoasContactDatabaseRestClient(restClient);
    }
    @Bean
    public DatabaseRequestRestClient<ListHateoasEntity<RequestLead>> leadDatabaseRestClient() {
        RestClient restClient = databaseRestClient();
        return new HateoasLeadDatabaseRestClient(restClient);
    }
//    @Bean
//    public DatabaseRequestRestClient<ListHateoasEntity<RequestOpportunity>> opportunityDatabaseRestClient() {
//        RestClient restClient = databaseRestClient();
//        return new HateoasOpportunityDatabaseRestClient(restClient);
//    }
    @Bean
    public DatabaseRequestRestClient<ListHateoasEntity<RequestCompanyWithContactsDTO>> companyWithContactsDatabaseRestClient() {
        RestClient restClient = databaseRestClient();
        return new HateoasCompanyWithContactsDatabaseRestClient(restClient);
    }
    @Bean
    public DatabaseRequestRestClient<ListHateoasEntity<RequestContactWithoutCompanyDTO>> contactsWithoutCompanyDatabaseRestClient() {
        RestClient restClient = databaseRestClient();
        return new HateoasContactWithoutCompanyDatabaseRestClient(restClient);
    }
    @Bean
    public DatabaseRequestRestClient<ListHateoasEntity<RequestContactAndCompany>> newContactsOldCompaniesRestClient() {
        RestClient restClient = databaseRestClient();
        return new HateoasNewContactOldCompany(restClient);
    }

    @Bean
    public LeadHateoasRestClient leadHateoasRestClient() {
        RestClient restClient = RestClient.builder()
                .build();
        return new LeadHateoasRestClient(restClient);
    }
    // HATEOAS
    @Bean
    public CompanyWithContactsHateoasRestClient companyWithContactsHateoasRestClient() {
        RestClient restClient = emptyHateaosRestClient();
        return new CompanyWithContactsHateoasRestClient(restClient);
    }
    @Bean
    public ContactWithoutCompanyHateoasRestClient contactWithoutCompanyHateoasRestClient() {
        RestClient restClient = emptyHateaosRestClient();
        return new ContactWithoutCompanyHateoasRestClient(restClient);
    }
    @Bean
    public NewContactOldCompanyHateoasRestClient newContactOldCompanyHateoasRestClient() {
        RestClient restClient = emptyHateaosRestClient();
        return new NewContactOldCompanyHateoasRestClient(restClient);
    }
    @Bean
    public AmoCRMSourceRestClient amoCRMSourceRestClient(@Value("${config.crm.sourceUrl}") String baseUrl, @Value("${config.crm.token}") String token) {
        RestClient restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("authorization", "Bearer " + token)
                .build();
        return new DeleteAmoCRMSourceRestClient(restClient);
    }
    private RestClient databaseRestClient() {
        return RestClient.builder().baseUrl(databaseUrl).build();
    }
    private RestClient emptyHateaosRestClient() {
        return RestClient.builder().build();
    }
}
