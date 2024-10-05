package com.example.dbeaver_migration_mappers.config;

import com.example.dbeaver_migration_mappers.clients.MigrationRestClient;
import com.example.dbeaver_migration_mappers.clients.MigrationRestRequestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientConfiguration {
    @Bean
    public MigrationRestClient migrationRestClient(@Value("config.baseUrl") String url) {
        return new MigrationRestRequestClient(RestClient.builder().baseUrl(url).build());
    }
}
