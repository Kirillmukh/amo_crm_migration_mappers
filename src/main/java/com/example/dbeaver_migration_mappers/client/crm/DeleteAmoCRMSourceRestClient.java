package com.example.dbeaver_migration_mappers.client.crm;

import com.example.dbeaver_migration_mappers.client.AmoCRMSourceRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class DeleteAmoCRMSourceRestClient implements AmoCRMSourceRestClient {
    private final RestClient restClient;
    @Override
    public void deleteCompany(String companyId) {
        deleteEntity(companyId, Entity.COMPANIES);
    }

    @Override
    public void deleteLead(String leadId) {
        deleteEntity(leadId, Entity.LEADS);
    }

    @Override
    public void deleteContact(String contactId) {
        deleteEntity(contactId, Entity.CONTACTS);
    }
    private void deleteEntity(String id, Entity entity) {
        String requestBody = "ID=" + URLEncoder.encode(id, StandardCharsets.UTF_8);
        restClient.post()
                .uri("ajax/{entity}/multiple/delete", entity.toString())
                .header("X-Requested-With", "XMLHttpRequest")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(requestBody)
                .retrieve();
    }

    private enum Entity {
        COMPANIES,
        CONTACTS,
        LEADS;
        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }
}
