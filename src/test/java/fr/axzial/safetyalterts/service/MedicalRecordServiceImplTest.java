package fr.axzial.safetyalterts.service;

import fr.axzial.safetyalterts.model.MedicalRecord;
import fr.axzial.safetyalterts.repository.MedicalRecordRepository;
import fr.axzial.safetyalterts.util.TimeUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class MedicalRecordServiceImplTest {

    @MockBean
    MedicalRecordRepository medicalRecordRepository;

    @MockBean
    MedicalRecordService medicalRecordService;

    @BeforeEach
    void setUp() {
        medicalRecordService = new MedicalRecordServiceImpl(medicalRecordRepository);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getRecordsFromPersons() {

        MedicalRecord medicalRecord1 = new MedicalRecord();
        medicalRecord1.setFirstName("Vic");
        medicalRecord1.setLastName("tor");
        medicalRecord1.setBirth(TimeUtils.addYears(Timestamp.from(Instant.now()), -27));
        MedicalRecord medicalRecord2 = new MedicalRecord();
        medicalRecord2.setFirstName("Yac");
        medicalRecord2.setLastName("ine");
        medicalRecord2.setBirth(TimeUtils.addYears(Timestamp.from(Instant.now()), -12));

        when(medicalRecordRepository.findAllByLastNameInAndFirstNameIn(anyCollection(), anyCollection())).thenReturn(Arrays.asList(medicalRecord1, medicalRecord2));

        List<MedicalRecord> medicalRecords = medicalRecordService.getRecordsFromPersons(new ArrayList<>());
        assertEquals(medicalRecords.get(0).getFirstName(), "Vic");
    }
}