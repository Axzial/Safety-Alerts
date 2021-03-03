package fr.axzial.safetyalterts.service;

import fr.axzial.safetyalterts.dto.PersonWithMedicationsDto;
import fr.axzial.safetyalterts.dto.firestation.FireStationCountDto;
import fr.axzial.safetyalterts.model.FireStation;
import fr.axzial.safetyalterts.model.MedicalRecord;
import fr.axzial.safetyalterts.model.Person;
import fr.axzial.safetyalterts.repository.FireStationRepository;
import fr.axzial.safetyalterts.util.TimeUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class FireStationServiceImplTest {

    @MockBean
    FireStationRepository fireStationRepository;
    @MockBean
    MedicalRecordService medicalRecordService;
    @MockBean
    PersonService personService;

    @MockBean
    FireStationService fireStationService;

    @PersistenceContext(name = "TESTING")
    EntityManager entityManager;

    @BeforeEach
    void setUp() {
        fireStationService = new FireStationServiceImpl(fireStationRepository, medicalRecordService, personService, entityManager);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getFireStationByNames() {

        FireStation testFireStation = new FireStation();
        testFireStation.setStation("TESTING");
        testFireStation.setAddress("999");
        testFireStation.setId(0);

        when(fireStationRepository.findAllByStationIn(anyList())).thenReturn(Collections.singletonList(testFireStation));

        List<FireStation> fireStation = fireStationService.getFireStationsByNames(Collections.singletonList("999"));
        assertEquals(fireStation.get(0).getAddress(), "999");
    }

    @Test
    void getFireStationByAddress() {

        FireStation testFireStation = new FireStation();
        testFireStation.setStation("TESTING");
        testFireStation.setAddress("999");
        testFireStation.setId(0);

        when(fireStationRepository.findAllByAddress(anyString())).thenReturn(Collections.singletonList(testFireStation));

        Optional<FireStation> fireStation = fireStationService.getFireStationByAddress("999");
        assertTrue(fireStation.isPresent());
    }

    @Test
    void getFireStationByStation() {

        FireStation testFireStation = new FireStation();
        testFireStation.setStation("TESTING");
        testFireStation.setAddress("999");
        testFireStation.setId(0);

        when(fireStationRepository.findAllByStation(anyString())).thenReturn(Collections.singletonList(testFireStation));

        Optional<FireStation> optionalFireStation = fireStationService.getFireStationByStation("999");
        assertTrue(optionalFireStation.isPresent());
    }

    @Test
    void getUsersFromFireStation() {

        FireStation testFireStation = new FireStation();
        testFireStation.setStation("TESTING");
        testFireStation.setAddress("999");
        testFireStation.setId(0);

        Person person1 = new Person();
        person1.setFirstName("Vic");
        person1.setLastName("tor");
        Person person2 = new Person();
        person2.setFirstName("Yac");
        person2.setLastName("ine");

        MedicalRecord medicalRecord1 = new MedicalRecord();
        medicalRecord1.setFirstName("Vic");
        medicalRecord1.setLastName("tor");
        medicalRecord1.setBirth(TimeUtils.addYears(Timestamp.from(Instant.now()), -27));
        MedicalRecord medicalRecord2 = new MedicalRecord();
        medicalRecord2.setFirstName("Yac");
        medicalRecord2.setLastName("ine");
        medicalRecord2.setBirth(TimeUtils.addYears(Timestamp.from(Instant.now()), -12));

        when(fireStationRepository.findAllByStation(anyString())).thenReturn(Collections.singletonList(testFireStation));
        when(personService.getPersonByAddress(anyString())).thenReturn(Arrays.asList(person1, person2));
        when(medicalRecordService.getRecordsFromPersons(anyList())).thenReturn(Arrays.asList(medicalRecord1, medicalRecord2));

        FireStationCountDto fireStationCountDto = fireStationService.getUsersFromFireStationNumber("999");
        assertEquals(fireStationCountDto.getMinors(), 1);
    }

    @Test
    void getFireStationWithPersons() {
        FireStation fireStation = new FireStation();
        fireStation.setStation("TESTING");
        fireStation.setAddress("999");
        fireStation.setId(0);

        Person person = new Person();
        person.setFirstName("Yac");
        person.setLastName("ine");
        person.setAddress("999");

        when(fireStationRepository.findAllByStationIn(anyCollection())).thenReturn(Collections.singletonList(fireStation));
        when(personService.getPersonByAddresses(Collections.singletonList(fireStation.getAddress()))).thenReturn(Collections.singletonList(person));

        Map<String, List<PersonWithMedicationsDto>> fireStations = fireStationService.getFireStationWithPersons(Collections.singletonList("TESTING"));

        assertEquals(fireStations.size(), 1);
        assertIterableEquals(fireStations.get("999"), Collections.singleton(person));
    }
}