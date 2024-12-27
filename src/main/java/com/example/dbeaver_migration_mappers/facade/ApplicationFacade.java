package com.example.dbeaver_migration_mappers.facade;

import java.util.List;

public interface ApplicationFacade {
    // TODO: 28.12.2024 delete this method?
    void transferComplexCompany();
    // TODO: 28.12.2024 delete this method?
    void loadCompaniesByUUID(List<String> guids);
    void loadComplexLead();
}
