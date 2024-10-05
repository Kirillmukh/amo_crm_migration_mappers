package com.example.dbeaver_migration_mappers.clients;

import com.example.dbeaver_migration_mappers.input_models.request.RequestCompany;
import com.example.dbeaver_migration_mappers.input_models.request.RequestContact;
import com.example.dbeaver_migration_mappers.input_models.request.RequestLead;
import com.example.dbeaver_migration_mappers.input_models.request.RequestOpportunity;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class MigrationRestRequestClient implements MigrationRestClient {
    private final RestClient restClient;
    @Override
    public RequestLead findLeadById(String id, @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date) {
        return null;
    }

    @Override
    public List<RequestLead> findLeads(int limit, int offset, @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date) {
        return null;
    }

    @Override
    public RequestCompany findCompanyById(String id, @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date) {
        return null;
    }

    @Override
    public List<RequestCompany> findCompanies(int limit, int offset, @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date) {
        return null;
    }

    @Override
    public RequestContact findContactsById(String id, @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date) {
        return null;
    }

    @Override
    public List<RequestContact> findContacts(int limit, int offset, @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date) {
        return null;
    }

    @Override
    public RequestOpportunity findOpportunityById(String id, @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date) {
        return null;
    }

    @Override
    public List<RequestOpportunity> findOpportunities(int limit, int offset, @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date) {
        return null;
    }
}
