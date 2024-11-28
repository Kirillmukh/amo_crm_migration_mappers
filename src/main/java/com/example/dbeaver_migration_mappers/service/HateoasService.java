package com.example.dbeaver_migration_mappers.service;

import com.example.dbeaver_migration_mappers.input_models.hateoas.Link;
import jakarta.ws.rs.core.Response;

public interface HateoasService {
    Iterable<Link> getRequestCompanyLinks(Response response);
    Iterable<Link> getRequestContactLinks(Response response);
    Iterable<Link> getRequestLeadLinks(Response response);
    Iterable<Link> getRequestOpportunityLinks(Response response);
}
