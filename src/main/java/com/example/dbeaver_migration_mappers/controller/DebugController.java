package com.example.dbeaver_migration_mappers.controller;

import com.example.dbeaver_migration_mappers.mapper.CompanyMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;


@RestController
@Slf4j
public class DebugController {
    @Autowired
    private CompanyMapper companyMapper;
    @GetMapping("log")
    public void log() {
        Set<String> set = companyMapper.getSet();

        set.forEach(log::info);
    }
}
