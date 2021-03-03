package fr.axzial.safetyalterts.service;

import fr.axzial.safetyalterts.dto.firestation.FireStationMedicalRecordsDto;
import fr.axzial.safetyalterts.dto.firestation.FireStationPersonsDto;
import fr.axzial.safetyalterts.dto.firestation.FireStationPersonsPhoneDto;
import fr.axzial.safetyalterts.model.FireStation;
import fr.axzial.safetyalterts.model.MedicalRecord;
import fr.axzial.safetyalterts.model.Person;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AlertServiceImplTest {

    @MockBean
    private FireStationService fireStationService;
    @MockBean
    private PersonService personService;
    @MockBean
    private MedicalRecordService medicalRecordService;
    @MockBean
    private AlertService alertService;
    @BeforeEach
    void setUp() {
        alertService = new AlertServiceImpl(fireStationService, personService, medicalRecordService);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void alertFire() {

        FireStation testFireStation = new FireStation();
        testFireStation.setStation("TESTING");
        testFireStation.setAddress("999");
        testFireStation.setId(0);

        Person person1 = new Person();
        person1.setFirstName("Vic");
        person1.setLastName("tor");
        person1.setAddress("999");
        Person person2 = new Person();
        person2.setFirstName("Yac");
        person2.setLastName("ine");
        person2.setAddress("999");

        MedicalRecord medicalRecord1 = new MedicalRecord();
        medicalRecord1.setFirstName("Vic");
        medicalRecord1.setLastName("tor");
        medicalRecord1.setBirth(TimeUtils.addYears(Timestamp.from(Instant.now()), -27));
        MedicalRecord medicalRecord2 = new MedicalRecord();
        medicalRecord2.setFirstName("Yac");
        medicalRecord2.setLastName("ine");
        medicalRecord2.setBirth(TimeUtils.addYears(Timestamp.from(Instant.now()), -12));

        when(fireStationService.getFireStationByAddress(anyString())).thenReturn(java.util.Optional.of(testFireStation));
        when(personService.getPersonByAddress(anyString())).thenReturn(Arrays.asList(person1, person2));
        when(medicalRecordService.getRecordsFromPersons(anyList())).thenReturn(Arrays.asList(medicalRecord1, medicalRecord2));

        FireStationPersonsDto fireStationPersonsDto = alertService.alertFire("999");
        Assertions.assertEquals(fireStationPersonsDto.getFireStation().getAddress(), "999");
    }

    @Test
    void alertPhone() {

        FireStation fireStation = new FireStation();
        fireStation.setStation("TESTING");
        fireStation.setAddress("999");
        fireStation.setId(0);

        Person person1 = new Person();
        person1.setFirstName("Vic");
        person1.setLastName("tor");
        person1.setAddress("999");
        Person person2 = new Person();
        person2.setFirstName("Yac");
        person2.setLastName("ine");
        person2.setAddress("999");

        when(fireStationService.getFireStationByStation("3")).thenReturn(java.util.Optional.of(fireStation));
        when(personService.getPersonByAddress(anyString())).thenReturn(Arrays.asList(person1, person2));

        FireStationPersonsPhoneDto phones = alertService.alertPhone("3");
        Assertions.assertEquals(phones.getFireStation().getAddress(), "999");
    }

    @Test
    void alertChild() {

        FireStation testFireStation = new FireStation();
        testFireStation.setStation("TESTING");
        testFireStation.setAddress("999");
        testFireStation.setId(0);

        Person person1 = new Person();
        person1.setFirstName("Vic");
        person1.setLastName("tor");
        person1.setAddress("999");
        Person person2 = new Person();
        person2.setFirstName("Yac");
        person2.setLastName("ine");
        person2.setAddress("999");

        MedicalRecord medicalRecord1 = new MedicalRecord();
        medicalRecord1.setFirstName("Vic");
        medicalRecord1.setLastName("tor");
        medicalRecord1.setBirth(TimeUtils.addYears(Timestamp.from(Instant.now()), -27));
        MedicalRecord medicalRecord2 = new MedicalRecord();
        medicalRecord2.setFirstName("Yac");
        medicalRecord2.setLastName("ine");
        medicalRecord2.setBirth(TimeUtils.addYears(Timestamp.from(Instant.now()), -12));

        when(fireStationService.getFireStationByAddress(anyString())).thenReturn(java.util.Optional.of(testFireStation));
        when(personService.getPersonByAddress(anyString())).thenReturn(Arrays.asList(person1, person2));
        when(medicalRecordService.getRecordsFromPersons(anyList())).thenReturn(Arrays.asList(medicalRecord1, medicalRecord2));

        FireStationMedicalRecordsDto fireStationMedicalRecordsDto = alertService.alertChild("999");
        Assertions.assertEquals(fireStationMedicalRecordsDto.getFireStation().getAddress(), "999");
    }
}