package com.example.importer.repository;

import com.example.importer.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    Organization findByShortname(String shortname);
}

