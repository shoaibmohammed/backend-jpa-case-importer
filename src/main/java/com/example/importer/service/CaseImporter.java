package com.example.importer.service;

import com.example.importer.exception.OrgNotFoundException;
import com.example.importer.model.Case;
import com.example.importer.model.Organization;
import com.example.importer.repository.CaseRepository;
import com.example.importer.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class CaseImporter {

    @Autowired
    private CaseRepository caseRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Transactional
    public ImportReport importStream(InputStream data, String orgShortname, char delimiter) throws IOException {
        Organization org = organizationRepository.findByShortname(orgShortname);
        if (org == null) {
            throw new OrgNotFoundException("The org " + orgShortname + " was not found");
        }

        ImportReport report = new ImportReport();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(data))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // Skip header
                    continue;
                }

                String[] fields = line.split(String.valueOf(delimiter));
                String mrn = fields[0];
                String name = fields[1];

                Case aCase = new Case();
                aCase.setMrn(mrn);
                aCase.setName(name);
                aCase.setOrg(org);

                importCase(aCase, org);
                report.addCase(org);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return report;

    }

    private void importCase(Case aCase, Organization org) {

        Case existingCase = caseRepository.findByMrnAndOrg(aCase.getMrn(), org);

        if (existingCase != null) {
            // Update existing case
            existingCase.setName(aCase.getName());
            caseRepository.save(existingCase);
        } else {
            // Insert new case
            aCase.setOrg(org);
            caseRepository.save(aCase);
        }
    }
}

