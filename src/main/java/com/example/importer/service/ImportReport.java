package com.example.importer.service;

import com.example.importer.model.Organization;

public class ImportReport {
    private int importedCases;

    public void addCase(Organization org) {
        importedCases++;
    }

    public int getImportedCases() {
        return importedCases;
    }

    public void setImportedCases(int importedCases) {
        this.importedCases = importedCases;
    }
}

