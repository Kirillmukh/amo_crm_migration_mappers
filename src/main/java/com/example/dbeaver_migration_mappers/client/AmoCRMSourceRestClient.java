package com.example.dbeaver_migration_mappers.client;

public interface AmoCRMSourceRestClient {
    void deleteCompany(String companyId);
    void deleteLead(String leadId);
    void deleteContact(String contactId);
}
