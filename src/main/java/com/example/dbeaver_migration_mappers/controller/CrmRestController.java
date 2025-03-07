package com.example.dbeaver_migration_mappers.controller;

import com.example.dbeaver_migration_mappers.facade.ApplicationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("crm")
public class CrmRestController {
    private final ApplicationFacade applicationFacade;
    @GetMapping("complex-leads")
    public void loadComplexLeads(@RequestParam(required = false, defaultValue = "0") int offset) {
        if (true) return;
        new Thread(() -> applicationFacade.loadComplexLead(offset)).start();
    }
    @GetMapping("new-companies")
    public void loadNewCompanies(@RequestParam(required = false, defaultValue = "0") int offset) {
        if (true) return;
        new Thread(() -> applicationFacade.loadNewCompanies(offset)).start();
    }
    @GetMapping("new-contacts")
    public void loadNewContacts(@RequestParam(required = false, defaultValue = "0") int offset) {
        if (true) return;
        new Thread(() -> applicationFacade.loadNewContacts(offset)).start();
    }
    @GetMapping("new-contacts-old-companies")
    public void loadNewContactOldCompanies(@RequestParam(required = false, defaultValue = "0") int offset) {
        if (true) return;
        new Thread(() -> applicationFacade.loadNewContactsOldCompanies(offset)).start();
    }
}
