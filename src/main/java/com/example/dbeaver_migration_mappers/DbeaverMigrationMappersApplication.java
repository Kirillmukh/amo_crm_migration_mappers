package com.example.dbeaver_migration_mappers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@Slf4j
public class DbeaverMigrationMappersApplication {
    // TODO: 13.01.2025 rewrite database contact and company models
    public static void main(String[] args) {
        SpringApplication.run(DbeaverMigrationMappersApplication.class, args);
    }
}
