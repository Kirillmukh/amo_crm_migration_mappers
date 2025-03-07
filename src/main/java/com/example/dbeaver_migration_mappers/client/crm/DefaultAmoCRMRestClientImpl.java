package com.example.dbeaver_migration_mappers.client.crm;

import com.example.dbeaver_migration_mappers.client.AmoCRMRestClient;
import com.example.dbeaver_migration_mappers.crm_models.request.*;
import com.example.dbeaver_migration_mappers.crm_models.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

@RequiredArgsConstructor
public class DefaultAmoCRMRestClientImpl implements AmoCRMRestClient {
    private final RestClient restClient;
    private static final ParameterizedTypeReference<List<CRMComplexLeadResponse>> LEAD_TYPE_REFERENCE = new ParameterizedTypeReference<List<CRMComplexLeadResponse>>() {
    };
    @Override
    public List<CRMComplexLeadResponse> createComplexLead(CRMLeadRequest crmLeadRequest) {
        return restClient.post()
                .uri("leads/complex")
                .body(crmLeadRequest.crmLead())
                .retrieve()
                .body(LEAD_TYPE_REFERENCE);
    }

    @Override
    public CRMLeadResponse createLead(CRMLeadRequest crmLeadRequest) {
        return restClient.post()
                .uri("leads")
                .body(crmLeadRequest.crmLead())
                .retrieve()
                .body(CRMLeadResponse.class);
    }

    @Override
    public CRMContactResponse createContact(CRMContactRequest crmContactRequest) {
        return restClient.post()
                .uri("contacts")
                .body(crmContactRequest.crmContactList())
                .retrieve()
                .body(CRMContactResponse.class);
    }

    @Override
    public CRMToEntityResponse linkLead(int leadId, CRMToEntityRequest crmToEntityRequest) {
        return restClient.post()
                .uri("leads/{leadId}/link", leadId)
                .body(crmToEntityRequest.crmToEntityList())
                .retrieve()
                .body(CRMToEntityResponse.class); // TODO: 31.12.2024 check status 200
    }

    @Override
    public CRMToEntityLinksResponse linkLeads(CRMToEntityLinksRequest crmToEntityLinksRequest) {
        return restClient.post()
                .uri("leads/link")
                .body(crmToEntityLinksRequest.linksRequestList())
                .retrieve()
                .body(CRMToEntityLinksResponse.class);
    }

    @Override
    public CRMCompanyResponse createCompany(CRMCompanyRequest companyRequest) {
        return restClient.post()
                .uri("companies")
                .body(companyRequest.crmCompanyList())
                .retrieve()
                .body(CRMCompanyResponse.class);
    }

    @Override
    public CRMToEntityResponse linkCompany(int companyId, CRMToEntityRequest crmToEntityRequest) {
        return restClient.post()
                .uri("companies/{companyId}/link", companyId)
                .body(crmToEntityRequest.crmToEntityList())
                .retrieve()
                .body(CRMToEntityResponse.class);
    }

    @Override
    public CRMCompaniesResponse findCompanyByName(String name) {
        return restClient.get()
                .uri("companies?query={name}", name)
                .retrieve()
                .body(CRMCompaniesResponse.class);
    }

    @Override
    public CRMUpdateContactResponse updateContact(CRMUpdateContactRequest request) {
        return restClient.patch()
                .uri("contacts")
                .body(request.list())
                .retrieve()
                .body(CRMUpdateContactResponse.class);
    }
}
