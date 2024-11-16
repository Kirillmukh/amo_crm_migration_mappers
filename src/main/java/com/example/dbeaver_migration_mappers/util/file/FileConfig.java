package com.example.dbeaver_migration_mappers.util.file;

import com.example.dbeaver_migration_mappers.DbeaverMigrationMappersApplication;
import com.example.dbeaver_migration_mappers.util.file.exception.FileCreationException;
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
    FileUtil fileUtil() throws FileCreationException {
        return new FileUtil(getTargetDirectory(), "data.json");
    }
}
