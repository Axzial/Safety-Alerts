package fr.axzial.safetyalterts.service;

import fr.axzial.safetyalterts.model.Person;

import java.util.List;

public interface PersonService {

    List<String> getMailsByCity(String city);

    List<Person> getPersonsByNames(String firstName, String lastName);

    List<Person> getPersonByCities(List<String> adresses);

    List<Person> getPersonByCity(String address);

    List<String> getCityMails(String city);

    List<Person> getPersonsInfos(String firstName, String lastName);
}
