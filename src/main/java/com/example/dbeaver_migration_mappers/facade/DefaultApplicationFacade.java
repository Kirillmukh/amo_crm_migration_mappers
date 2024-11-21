package com.example.dbeaver_migration_mappers.facade;

import com.example.dbeaver_migration_mappers.client.DatabaseRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultApplicationFacade implements ApplicationFacade {
    private final DatabaseRestClient companyRestClient;
    @Override
    public void transferCompany() {

    }
}
