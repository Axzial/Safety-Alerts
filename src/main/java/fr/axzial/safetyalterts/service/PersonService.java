package fr.axzial.safetyalterts.service;

import fr.axzial.safetyalterts.dto.PersonWithMedicationsDto;
import fr.axzial.safetyalterts.model.Person;

import java.util.List;

public interface PersonService {

    List<String> getMailsByCity(String city);

    List<Person> getPersonsByNames(String firstName, String lastName);

    List<Person> getPersonByAddresses(List<String> adresses);

    List<Person> getPersonByAddress(String address);

    List<String> getCityMails(String city);

    List<PersonWithMedicationsDto> getPersonsInfos(String firstName, String lastName);
}
