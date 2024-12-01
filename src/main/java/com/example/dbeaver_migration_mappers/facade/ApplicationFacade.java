package com.example.dbeaver_migration_mappers.facade;

import java.util.List;

public interface ApplicationFacade {
    void transferComplexCompany();
    void loadCompaniesByUUID(List<String> guids);
}
