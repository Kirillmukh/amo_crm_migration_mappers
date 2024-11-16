package com.example.dbeaver_migration_mappers.util;

import com.example.dbeaver_migration_mappers.client.AmoCRMRestClient;
import com.example.dbeaver_migration_mappers.util.file.FileUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TagsCacheTest {
    @Mock
    AmoCRMRestClient amoCRMRestClient;
    @Autowired
    @Mock
    ObjectMapper objectMapper;
    @Autowired
    @Mock
    FileUtil fileUtil;
    @Autowired
    @Mock
    MapThreadExecutor mapThreadExecutor;
    @InjectMocks
    TagsCache tagsCache;
}