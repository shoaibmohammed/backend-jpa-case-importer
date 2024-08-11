package com.example.importer.controller;

import com.example.importer.service.CaseImporter;
import com.example.importer.service.ImportReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/cases")
public class CaseController {

    @Autowired
    private CaseImporter caseImporter;

    @PostMapping("/import")
    public ResponseEntity<ImportReport> importCases(@RequestParam("file") MultipartFile file,
                                                    @RequestParam("orgShortname") String orgShortname,
                                                    @RequestParam(value = "delimiter", defaultValue = ",") char delimiter) {
        try {
            ImportReport report = caseImporter.importStream(file.getInputStream(), orgShortname, delimiter);
            return new ResponseEntity<>(report, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

