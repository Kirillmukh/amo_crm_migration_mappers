package com.example.dbeaver_migration_mappers.client;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Optional;

public interface DatabaseRestClient<T, ListOfT> {
    Optional<ListOfT> request();
    Optional<ListOfT> request(@DateTimeFormat(pattern = "${config.dateFormat}") LocalDate date);
    Optional<ListOfT> request(@DateTimeFormat(pattern = "${config.dateFormat}") LocalDate date, int limit, int offset);
    Optional<T> requestById(String id);
    Optional<T> requestById(String id, @DateTimeFormat(pattern = "${config.dateFormat}") LocalDate date);
}
