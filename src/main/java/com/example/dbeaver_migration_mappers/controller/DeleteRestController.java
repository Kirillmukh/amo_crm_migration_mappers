package com.example.dbeaver_migration_mappers.controller;

import com.example.dbeaver_migration_mappers.facade.ApplicationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("delete")
@RequiredArgsConstructor
public class DeleteRestController {
    private final ApplicationFacade applicationFacade;
    @DeleteMapping("contacts")
    public void deleteContacts(@RequestParam(required = false, defaultValue = "0") int offset,
                               @RequestParam(required = false, defaultValue = "false") boolean deleteFromFile) {
        if (true) return;
        new Thread(() -> applicationFacade.deleteContacts(offset, deleteFromFile)).start();
    }
    @DeleteMapping("companies")
    public void deleteCompanies(@RequestParam(required = false, defaultValue = "0") int offset,
                                @RequestParam(required = false, defaultValue = "false") boolean deleteFromFile) {
        if (true) return;
        new Thread(() -> applicationFacade.deleteCompanies(offset, deleteFromFile)).start();
    }
    @DeleteMapping("leads")
    public void deleteLeads(@RequestParam(required = false, defaultValue = "0") int offset,
                                @RequestParam(required = false, defaultValue = "false") boolean deleteFromFile) {
        if (true) return;
        new Thread(() -> applicationFacade.deleteLeads(offset, deleteFromFile)).start();
    }
}
