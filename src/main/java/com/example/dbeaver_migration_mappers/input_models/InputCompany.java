package com.example.dbeaver_migration_mappers.input_models;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class InputCompany {
    private String name;
    private String alternativeName;
    private String type; // TypeId -> name
    private String web;
    private String phone;
    private String category; // accountCategoryId -> name
    private String industry; // industryId (AccountIndustry.java) -> name
    private boolean usrCompanyUseEDM;
    private String usrArchiveEvents;
    private String usrEventsOfRival;
    private String usrPrimKontr;
}
