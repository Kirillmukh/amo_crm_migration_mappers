package com.example.dbeaver_migration_mappers.controller;

import com.example.dbeaver_migration_mappers.facade.Facade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CrmRestRequestController {
    private final Facade facade;
}
