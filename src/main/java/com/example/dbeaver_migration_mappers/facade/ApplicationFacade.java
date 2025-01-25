package com.example.dbeaver_migration_mappers.facade;

import com.example.dbeaver_migration_mappers.crm_models.entity.wrapper.CRMCompanyCRMContactsListWrapper;
import com.example.dbeaver_migration_mappers.crm_models.request.CRMContactRequest;

import java.util.List;

public interface ApplicationFacade {
    void loadLeadsByGUID(List<String> guids);
    void loadComplexLead();
    CRMCompanyCRMContactsListWrapper loadCompaniesAndContacts();

    CRMContactRequest loadContactsWithoutCompany();

    void rollback();
}
