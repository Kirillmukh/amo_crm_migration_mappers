package com.example.dbeaver_migration_mappers.input_models.request;

import com.example.dbeaver_migration_mappers.input_models.InputCompany;
import com.example.dbeaver_migration_mappers.input_models.InputContact;
import com.example.dbeaver_migration_mappers.input_models.InputLead;
import com.example.dbeaver_migration_mappers.input_models.InputOpportunity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestCompany {
    private InputCompany company;
    private List<InputLead> leads;
    private List<InputOpportunity> opportunities;
    private List<InputContact> contacts;
}
