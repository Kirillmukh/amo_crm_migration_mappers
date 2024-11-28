package com.example.dbeaver_migration_mappers.service;

import com.example.dbeaver_migration_mappers.input_models.hateoas.HateoasEntity;
import com.example.dbeaver_migration_mappers.input_models.hateoas.Link;
import com.example.dbeaver_migration_mappers.input_models.request.RequestCompany;
import com.example.dbeaver_migration_mappers.input_models.request.RequestContact;
import com.example.dbeaver_migration_mappers.input_models.request.RequestLead;
import com.example.dbeaver_migration_mappers.input_models.request.RequestOpportunity;
import jakarta.ws.rs.core.Response;
import org.springframework.stereotype.Service;

@Service
public class HateoasResponseService implements ResponseService, HateoasService {
    @Override
    public RequestCompany getRequestCompany(Response response) {
        return ((HateoasEntity<RequestCompany>) response.getEntity()).content();
    }

    @Override
    public RequestContact getRequestContact(Response response) {
        return ((HateoasEntity<RequestContact>) response.getEntity()).content();
    }

    @Override
    public RequestLead getRequestLead(Response response) {
        return ((HateoasEntity<RequestLead>) response.getEntity()).content();
    }

    @Override
    public RequestOpportunity getRequestOpportunity(Response response) {
        return ((HateoasEntity<RequestOpportunity>) response.getEntity()).content();
    }

    @Override
    public Iterable<Link> getRequestCompanyLinks(Response response) {
        return ((HateoasEntity<RequestCompany>) response.getEntity()).links();
    }

    @Override
    public Iterable<Link> getRequestContactLinks(Response response) {
        return ((HateoasEntity<RequestContact>) response.getEntity()).links();
    }

    @Override
    public Iterable<Link> getRequestLeadLinks(Response response) {
        return ((HateoasEntity<RequestLead>) response.getEntity()).links();
    }

    @Override
    public Iterable<Link> getRequestOpportunityLinks(Response response) {
        return ((HateoasEntity<RequestOpportunity>) response.getEntity()).links();
    }
}
