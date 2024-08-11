package com.example.importer.repository;

import com.example.importer.model.Case;
import com.example.importer.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaseRepository extends JpaRepository<Case, Long> {
    Case findByMrnAndOrg(String mrn, Organization org);

}
