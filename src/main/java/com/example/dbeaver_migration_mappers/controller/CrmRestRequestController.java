package com.example.dbeaver_migration_mappers.controller;

import com.example.dbeaver_migration_mappers.input_models.InputLead;
import com.example.dbeaver_migration_mappers.mapper.LeadMapper;
import com.example.dbeaver_migration_mappers.output_models.response.OutputLead;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CrmRestRequestController {
    private final LeadMapper leadMapper;
    @PostMapping
    public OutputLead handleLead(@RequestBody InputLead inputLead) {
        return leadMapper.mapToOutput(inputLead);
    }

}
