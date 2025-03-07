package com.example.dbeaver_migration_mappers.util.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@RequiredArgsConstructor
@Service
@Slf4j
public class JsonService {
    private final ObjectMapper objectMapper;
    public void saveObject(Object content) {
        try (FileWriter writer = new FileWriter(content.getClass().getName() + ".json")) {
            objectMapper.writeValue(writer, content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public <T> T loadObject(Class<T> clazz) {
        try {
            return objectMapper.readValue(new File(clazz.getName() + ".json"), clazz);
        } catch (IOException e) {
            log.error("Couldn't read {} object from file {}.json", clazz, clazz.getName());
            throw new RuntimeException("Couldn't read %s object from file %s.json".formatted(clazz.toString(), clazz.getName()));
        }
    }
}
