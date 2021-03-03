package fr.axzial.safetyalterts.service;

import fr.axzial.safetyalterts.exception.MailNotFoundException;
import fr.axzial.safetyalterts.exception.PersonNotFoundException;
import fr.axzial.safetyalterts.model.Person;
import fr.axzial.safetyalterts.repository.PersonRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final EntityManager entityManager;

    public PersonServiceImpl(PersonRepository personRepository, EntityManager entityManager) {
        this.personRepository = personRepository;
        this.entityManager = entityManager;
    }

    @Override
    public List<String> getMailsByCity(String city){
        return personRepository.findAllByCity(city).stream().map(Person::getEmail).collect(Collectors.toList());
    }

    @Override
    public List<Person> getPersonsByNames(String firstName, String lastName) {
        Person example = new Person();
        example.setFirstName(firstName);
        example.setLastName(lastName);
        return personRepository.findAll(Example.of(example));
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
    public List<Person> getPersonsInfos(String firstName, String lastName){
        List<Person> persons = getPersonsByNames(firstName, lastName);
        if (persons.isEmpty()) throw new PersonNotFoundException();
        return persons;
    }
}
