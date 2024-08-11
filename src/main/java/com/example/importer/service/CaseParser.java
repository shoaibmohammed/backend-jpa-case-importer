package com.example.importer.service;

import com.example.importer.model.Case;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CaseParser {

    private final BufferedReader reader;
    private final char delimiter;

    public CaseParser(InputStream inputStream, char delimiter) {
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.delimiter = delimiter;
    }

    public List<Case> getCases() throws IOException {
        List<Case> cases = new ArrayList<>();
        String line;

        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(String.valueOf(delimiter));

            if (fields.length < 3) continue; // Skip invalid lines

            Case aCase = new Case();
            aCase.setMrn(fields[0]);
            aCase.setName(fields[1]);
            // Assuming 'name' in your case is just a string field; adjust as needed
            cases.add(aCase);
        }

        reader.close();
        return cases;
    }
}

