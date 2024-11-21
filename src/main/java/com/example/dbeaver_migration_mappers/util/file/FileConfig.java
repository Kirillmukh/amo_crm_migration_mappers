package com.example.dbeaver_migration_mappers.util.file;

import com.example.dbeaver_migration_mappers.DbeaverMigrationMappersApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class FileConfig {

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
        return new FileUtil(getTargetDirectory(), "company_data.json");
    }
}
