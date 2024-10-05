package com.example.dbeaver_migration_mappers;

import com.example.dbeaver_migration_mappers.mapper.LeadMapper;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class DbeaverMigrationMappersApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbeaverMigrationMappersApplication.class, args);
    }
    @Bean
    public CommandLineRunner main(LeadMapper leadMapper) {
        return args -> {
            log.info("leadMapper was injected");
        };
    }

}
