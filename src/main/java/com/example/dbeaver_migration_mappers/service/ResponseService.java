package com.example.dbeaver_migration_mappers.service;

import com.example.dbeaver_migration_mappers.input_models.hateoas.Link;
import com.example.dbeaver_migration_mappers.input_models.request.RequestCompany;
import com.example.dbeaver_migration_mappers.input_models.request.RequestContact;
import com.example.dbeaver_migration_mappers.input_models.request.RequestLead;
import com.example.dbeaver_migration_mappers.input_models.request.RequestOpportunity;
import jakarta.ws.rs.core.Response;

public interface ResponseService {
    RequestCompany getRequestCompany(Response response);
    RequestContact getRequestContact(Response response);
    RequestLead getRequestLead(Response response);
    RequestOpportunity getRequestOpportunity(Response response);
}
