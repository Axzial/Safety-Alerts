package fr.axzial.safetyalterts.mapper;

import fr.axzial.safetyalterts.dto.ChildMedicalRecordDto;
import fr.axzial.safetyalterts.model.MedicalRecord;
import fr.axzial.safetyalterts.util.TimeUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class MedicalRecordMapperTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void mapChildMedicalRecordDto() {
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setFirstName("Victor");
        medicalRecord.setLastName("Axzial");
        medicalRecord.setAllergies(Collections.singletonList("Nothing"));
        medicalRecord.setMedications(Collections.singletonList("Doliprane"));
        medicalRecord.setBirth(TimeUtils.addYears(Timestamp.from(Instant.now()), -27));

        ChildMedicalRecordDto childMedicalRecordDto = MedicalRecordMapper.mapChildMedicalRecordDto(medicalRecord);
        Assertions.assertEquals(childMedicalRecordDto.getAge(), TimeUtils.getAgeFromBirthday(medicalRecord.getBirth()));
    }
}