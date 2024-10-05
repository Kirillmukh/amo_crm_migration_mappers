package com.example.dbeaver_migration_mappers.clients;

import org.springframework.format.annotation.DateTimeFormat;
import com.example.dbeaver_migration_mappers.input_models.request.*;

import java.time.LocalDate;
import java.util.List;

public interface MigrationRestClient {
    RequestLead findLeadById(String id, LocalDate date);
    List<RequestLead> findLeads(int limit, int offset, LocalDate date);
    RequestCompany findCompanyById(String id, @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date);
    List<RequestCompany> findCompanies(int limit, int offset, @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date);
    RequestContact findContactsById(String id, @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date);
    List<RequestContact> findContacts(int limit, int offset, @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date);
    RequestOpportunity findOpportunityById(String id, @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date);
    List<RequestOpportunity> findOpportunities(int limit, int offset, @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date);
}
