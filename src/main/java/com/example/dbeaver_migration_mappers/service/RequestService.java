package com.example.dbeaver_migration_mappers.service;

import com.example.dbeaver_migration_mappers.crm_models.entity.wrapper.CRMCompanyCRMContactsListWrapper;
import com.example.dbeaver_migration_mappers.crm_models.entity.wrapper.InputCompaniesCRMContactsWrapper;
import com.example.dbeaver_migration_mappers.crm_models.request.CRMContactRequest;
import com.example.dbeaver_migration_mappers.crm_models.request.CRMLeadRequest;

public interface RequestService {
    void saveComplexLead(CRMLeadRequest crmLeadRequest, int offset);
    void saveNewContacts(CRMContactRequest crmContactRequest, int offset);
    void saveNewCompaniesNewContacts(InputCompaniesCRMContactsWrapper listWrapper, int offset);
    void saveNewContactsOldCompanies(CRMCompanyCRMContactsListWrapper crmCompanyRequest, int offset);
    void deleteContacts(int offset, boolean deleteFile);
    void deleteCompanies(int offset, boolean deleteFromFile);
    void deleteLeads(int offset, boolean deleteFromFile);
}
