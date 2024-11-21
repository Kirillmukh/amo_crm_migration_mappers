package com.example.dbeaver_migration_mappers.util.executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExecutorsConfig {
    @Bean
    public StringThreadExecutor companyExecutor() {
        return new StringThreadExecutor();
    }
}
