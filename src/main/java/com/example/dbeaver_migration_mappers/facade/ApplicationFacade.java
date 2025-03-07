package com.example.dbeaver_migration_mappers.facade;

public interface ApplicationFacade {
    void saveComplexLead();
    void saveNewContacts();
    void saveNewCompaniesNewContacts();
    void saveNewContactsOldCompanies();
    void loadComplexLead(int offset);
    void loadNewCompanies(int offset);
    void loadNewContacts(int offset);
    void loadNewContactsOldCompanies(int offset);
    void deleteContacts(int offset, boolean deleteFromFile);
    void deleteCompanies(int offset, boolean deleteFromFile);
    void deleteLeads(int offset, boolean deleteFromFile);
}
