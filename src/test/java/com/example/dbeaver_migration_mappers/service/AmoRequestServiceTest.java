package com.example.dbeaver_migration_mappers.service;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class AmoRequestServiceTest {

    @Test
    void orderStream_test() {
        List<List<Integer>> list = new ArrayList<>();
        list.add(new ArrayList<>(List.of(1, 2)));
        list.add(new ArrayList<>(List.of(3, 4)));

        List<Integer> collect = list.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        assertEquals(List.of(1, 2, 3, 4), collect);
    }
}