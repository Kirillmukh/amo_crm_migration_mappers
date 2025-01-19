package com.example.dbeaver_migration_mappers.facade;

import java.util.List;

public interface ApplicationFacade {
    void loadLeadsByGUID(List<String> guids);
    void loadComplexLead();
    void loadCompaniesAndContacts();

    void loadContactsWithoutCompany();

    void rollback();
}
