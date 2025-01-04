package com.example.dbeaver_migration_mappers.input_models.request;

import com.example.dbeaver_migration_mappers.input_models.InputCompany;
import com.example.dbeaver_migration_mappers.input_models.InputContact;
import com.example.dbeaver_migration_mappers.input_models.InputLead;
import com.example.dbeaver_migration_mappers.input_models.InputOpportunity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestContact {
    private InputContact contact;
    private List<InputLead> leads;
    private List<InputOpportunity> opportunities;
    private InputCompany company;
}
