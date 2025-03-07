package com.example.dbeaver_migration_mappers.controller;

import com.example.dbeaver_migration_mappers.facade.ApplicationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("db")
@RequiredArgsConstructor
public class DatabaseRestController {
    private final ApplicationFacade applicationFacade;
    @GetMapping("complex-leads")
    public void jsonComplexLead() {
        applicationFacade.saveComplexLead();
    }
    @GetMapping("new-contacts-old-companies")
    public void jsonNewContactOldCompanies() {
        applicationFacade.saveNewContactsOldCompanies();
    }
    @GetMapping("new-contacts")
    public void jsonNewContacts() {
        applicationFacade.saveNewContacts();
    }
    @GetMapping("new-companies")
    public void jsonNewCompanies() {
        applicationFacade.saveNewCompaniesNewContacts();
    }
}
