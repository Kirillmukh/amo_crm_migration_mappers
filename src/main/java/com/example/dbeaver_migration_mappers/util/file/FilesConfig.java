package com.example.dbeaver_migration_mappers.util.file;

import com.example.dbeaver_migration_mappers.DbeaverMigrationMappersApplication;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class FilesConfig {
    private final String targetDirectoryPath;
    public FilesConfig() {
        targetDirectoryPath = getTargetDirectory();
    }
    private String getTargetDirectory() {
        Class<?> clazz = DbeaverMigrationMappersApplication.class;
        File file = new File(clazz.getProtectionDomain().getCodeSource().getLocation().getFile());
        while (!file.getName().equals("target")) {
            file = file.getParentFile();
        }
        return file.getPath();
    }
    @Bean
    public FileUtil companyFile() {
        return new FileUtil(targetDirectoryPath, "company_tags.json");
    }
    @Bean
    public FileUtil savedCompanies() {
        return new FileUtil(targetDirectoryPath, "company_ids.txt");
    }
    @Bean
    public FileUtil savedLeads() {
        return new FileUtil(targetDirectoryPath, "leads_ids.txt");
    }
    @Bean
    public FileUtil savedContacts() {
        return new FileUtil(targetDirectoryPath, "contacts_ids.txt");
    }
}
