package fr.axzial.safetyalterts.service;

import fr.axzial.safetyalterts.dto.PersonWithMedicationsDto;
import fr.axzial.safetyalterts.exception.MailNotFoundException;
import fr.axzial.safetyalterts.exception.PersonNotFoundException;
import fr.axzial.safetyalterts.model.MedicalRecord;
import fr.axzial.safetyalterts.model.Person;
import fr.axzial.safetyalterts.repository.PersonRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final MedicalRecordService medicalRecordService;
    private final EntityManager entityManager;
    private final ModelMapper modelMapper;

    public PersonServiceImpl(PersonRepository personRepository, MedicalRecordService medicalRecordService, EntityManager entityManager) {
        this.personRepository = personRepository;
        this.medicalRecordService = medicalRecordService;
        this.entityManager = entityManager;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public List<String> getMailsByCity(String city){
        return personRepository.findAllByCity(city).stream().map(Person::getEmail).collect(Collectors.toList());
    }

    @Override
    public List<Person> getPersonsByNames(String firstName, String lastName) {
        return personRepository.findAllByLastNameAndFirstName(lastName, firstName);
    }

    @Override
    public List<Person> getPersonByAddresses(List<String> addresses){
       return personRepository.findAllByAddressIn(addresses);
    }

    @Override
    public List<Person> getPersonByAddress(String address){
        return personRepository.findAllByAddress(address);
    }

    @Override
    public List<String> getCityMails(String city){
        List<String> mails = getMailsByCity(city);
        if (mails.isEmpty()) throw new MailNotFoundException();
        return mails;
    }

    @Override
    public List<PersonWithMedicationsDto> getPersonsInfos(String firstName, String lastName){
        List<Person> persons = getPersonsByNames(firstName, lastName);
        if (persons.isEmpty()) throw new PersonNotFoundException();
        List<MedicalRecord> medicalRecords = medicalRecordService.getRecordsFromPersons(persons);
        return persons.stream().map(e -> {
            PersonWithMedicationsDto personWithMedicationsDto = modelMapper.map(e, PersonWithMedicationsDto.class);
            medicalRecords.stream()
                    .filter(x -> x.getFirstName().equalsIgnoreCase(e.getFirstName()) && x.getLastName().equalsIgnoreCase(e.getLastName()))
                    .forEach(personWithMedicationsDto::setMedicalRecord);
            return personWithMedicationsDto;
        }).collect(Collectors.toList());
    }
}
