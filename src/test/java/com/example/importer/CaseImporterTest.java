package com.example.importer;

import com.example.importer.exception.OrgNotFoundException;
import com.example.importer.model.Case;
import com.example.importer.model.Organization;
import com.example.importer.repository.CaseRepository;
import com.example.importer.repository.OrganizationRepository;
import com.example.importer.service.CaseImporter;
import com.example.importer.service.ImportReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CaseImporterTest {

    @Mock
    private CaseRepository caseRepository;

    @Mock
    private OrganizationRepository organizationRepository;

    @InjectMocks
    private CaseImporter caseImporter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testImportStream_NewCases() throws Exception {
        // Given
        String csvData = "mrn,name\n12345,John Doe\n67890,Jane Smith";
        InputStream inputStream = new ByteArrayInputStream(csvData.getBytes());

        Organization organization = new Organization();
        organization.setId(1L);
        organization.setShortname("org1");

        when(organizationRepository.findByShortname("org1")).thenReturn(organization);
        when(caseRepository.save(any(Case.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        // When
        ImportReport report = caseImporter.importStream(inputStream, "org1", ',');

        // Then
        assertNotNull(report);
        assertEquals(2, report.getImportedCases());  // Ensure method name matches

        verify(caseRepository, times(2)).save(any(Case.class));
        verify(organizationRepository).findByShortname("org1");
    }

    @Test
    void testImportStream_OrganizationNotFound() throws Exception {
        // Given
        String csvData = "mrn,name\n12345,John Doe";
        InputStream inputStream = new ByteArrayInputStream(csvData.getBytes());

        when(organizationRepository.findByShortname("org1")).thenReturn(null);

        // When / Then
        OrgNotFoundException thrown = assertThrows(OrgNotFoundException.class, () -> {
            caseImporter.importStream(inputStream, "org1", ',');
        });

        assertEquals("The org org1 was not found", thrown.getMessage());
    }
}