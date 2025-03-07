package com.example.dbeaver_migration_mappers.util.file;

import com.example.dbeaver_migration_mappers.input_models.InputCompany;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class RequestSaverTest {
    private ObjectMapper objectMapper = new ObjectMapper();
    private JsonService requestSaver = new JsonService(objectMapper);

    @Test
    void test1() throws IOException {
        String path = "output/hello";
        InputCompany inputCompany = new InputCompany("id", "name", "surname", "type", "url", "898", "human", "IT", false, "no", "no", "segment", "ok");

        requestSaver.saveObject(path, inputCompany);

        System.out.println("-".repeat(10));
        Files.readAllLines(Path.of("output/hello1.json")).forEach(System.out::println);
    }
}