package com.example.dbeaver_migration_mappers.client;

import jakarta.ws.rs.core.Response;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public interface DatabaseRestClient {
    Response request();
    Response request(int limit, int offset);
    Response request(@DateTimeFormat(pattern = "${config.date_format}") LocalDate date, int limit, int offset);
    Response requestById(String id);
    Response requestById(String id, @DateTimeFormat(pattern = "${config.date_format}") LocalDate date);
}
