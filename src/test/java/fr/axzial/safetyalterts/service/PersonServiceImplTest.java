package fr.axzial.safetyalterts.service;

import fr.axzial.safetyalterts.dto.PersonWithMedicationsDto;
import fr.axzial.safetyalterts.model.MedicalRecord;
import fr.axzial.safetyalterts.model.Person;
import fr.axzial.safetyalterts.repository.FireStationRepository;
import fr.axzial.safetyalterts.repository.PersonRepository;
import fr.axzial.safetyalterts.util.TimeUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class PersonServiceImplTest {

    @MockBean
    private PersonRepository personRepository;
    @MockBean
    private MedicalRecordService medicalRecordService;
    @MockBean
    PersonService personService;

    @BeforeEach
    void setUp() {
        personService = new PersonServiceImpl(personRepository, medicalRecordService);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getMailsByCity() {

        Person person1 = new Person();
        person1.setFirstName("Vic");
        person1.setLastName("tor");
        person1.setAddress("999");
        person1.setEmail("test@mail");
        Person person2 = new Person();
        person2.setFirstName("Yac");
        person2.setLastName("ine");
        person2.setAddress("999");
        person2.setEmail("testing@mail");

        when(personRepository.findAllByCity(anyString())).thenReturn(Arrays.asList(person1, person2));

        List<String> mails = personService.getMailsByCity("999");
        assertEquals(2, mails.size());
    }

    @Test
    void getPersonsByNames() {

        Person person1 = new Person();
        person1.setFirstName("Vic");
        person1.setLastName("tor");
        person1.setAddress("999");
        person1.setEmail("test@mail");
        Person person2 = new Person();
        person2.setFirstName("Yac");
        person2.setLastName("ine");
        person2.setAddress("999");
        person2.setEmail("testing@mail");

        when(personRepository.findAllByLastNameAndFirstName(anyString(), anyString())).thenReturn(Arrays.asList(person1, person2));


        List<Person> names = personService.getPersonsByNames("Vic", "tor");
        assertEquals(names.size(), 2);
    }

    @Test
    void getPersonByCities() {

        Person person1 = new Person();
        person1.setFirstName("Vic");
        person1.setLastName("tor");
        person1.setAddress("999");
        person1.setEmail("test@mail");
        Person person2 = new Person();
        person2.setFirstName("Yac");
        person2.setLastName("ine");
        person2.setAddress("999");
        person2.setEmail("testing@mail");

        when(personRepository.findAllByAddressIn(anyList())).thenReturn(Arrays.asList(person1, person2));
        assertEquals(personService.getPersonByAddresses(Arrays.asList("valeria", "Winterfel")), Arrays.asList(person1, person2));
    }

    @Test
    void getPersonByAddresses() {

        Person person1 = new Person();
        person1.setFirstName("Vic");
        person1.setLastName("tor");
        person1.setAddress("999");
        person1.setCity("Hyrule");
        person1.setEmail("test@mail");
        Person person2 = new Person();
        person2.setFirstName("Yac");
        person2.setLastName("ine");
        person2.setAddress("999");
        person2.setCity("Hyrule");
        person2.setEmail("testing@mail");

        when(personRepository.findAllByAddressIn(anyCollection())).thenReturn(Arrays.asList(person1, person2));

        List<Person> persons = personService.getPersonByAddresses(Collections.singletonList("999"));
        assertEquals(2, persons.size());
    }

    @Test
    void getPersonByAdress(){
        Person person1 = new Person();
        person1.setFirstName("Vic");
        person1.setLastName("tor");
        person1.setAddress("999");
        person1.setCity("Hyrule");
        person1.setEmail("test@mail");
        Person person2 = new Person();
        person2.setFirstName("Yac");
        person2.setLastName("ine");
        person2.setAddress("999");
        person2.setCity("Hyrule");
        person2.setEmail("testing@mail");

        when(personRepository.findAllByAddress(any())).thenReturn(Arrays.asList(person1, person2));

        List<Person> persons = personService.getPersonByAddress("999");
        assertEquals(2, persons.size());
    }

    @Test
    void getCityMails() {

        Person person1 = new Person();
        person1.setFirstName("Vic");
        person1.setLastName("tor");
        person1.setAddress("999");
        person1.setCity("Hyrule");
        person1.setEmail("test@mail");
        Person person2 = new Person();
        person2.setFirstName("Yac");
        person2.setLastName("ine");
        person2.setAddress("999");
        person2.setCity("Hyrule");
        person2.setEmail("testing@mail");

        when(personRepository.findAllByCity(anyString())).thenReturn(Arrays.asList(person1, person2));

        List<String> mails = personService.getCityMails("999");

        assertTrue(mails.get(0).equalsIgnoreCase("test@mail"));
    }

    @Test
    void getPersonsInfos() {

        Person person1 = new Person();
        person1.setFirstName("Vic");
        person1.setLastName("tor");
        person1.setAddress("999");
        person1.setEmail("test@mail");
        Person person2 = new Person();
        person2.setFirstName("Yac");
        person2.setLastName("ine");
        person2.setAddress("999");
        person2.setEmail("testing@mail");

        MedicalRecord medicalRecord1 = new MedicalRecord();
        medicalRecord1.setFirstName("Vic");
        medicalRecord1.setLastName("tor");
        medicalRecord1.setBirth(TimeUtils.addYears(Timestamp.from(Instant.now()), -27));
        MedicalRecord medicalRecord2 = new MedicalRecord();
        medicalRecord2.setFirstName("Yac");
        medicalRecord2.setLastName("ine");
        medicalRecord2.setBirth(TimeUtils.addYears(Timestamp.from(Instant.now()), -12));

        when(personRepository.findAllByLastNameAndFirstName(anyString(), anyString())).thenReturn(Arrays.asList(person1, person2));
        when(medicalRecordService.getRecordsFromPersons(anyList())).thenReturn(Arrays.asList(medicalRecord1, medicalRecord2));

        List<PersonWithMedicationsDto> medicationsDtoList = personService.getPersonsInfos("Vic", "tor");

        assertEquals(medicationsDtoList.get(0).getAddress(), "999");

    }
}