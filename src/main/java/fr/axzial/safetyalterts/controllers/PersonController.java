package fr.axzial.safetyalterts.controllers;

import fr.axzial.safetyalterts.exception.MailNotFoundException;
import fr.axzial.safetyalterts.exception.PersonNotFoundException;
import fr.axzial.safetyalterts.model.Person;
import fr.axzial.safetyalterts.service.PersonService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonController {

    PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Cette url doit retourner les adresses mail de tous les habitants de la ville
     * @param city
     * @return
     */
    @GetMapping("/communityEmails")
    public ResponseEntity<List<String>> getCityMails(@RequestParam(name = "city", required = true) String city){
        return ResponseEntity.ok(personService.getMailsByCity(city));
    }

    /**
     * Cette url doit retourner le nom, l'adresse, l'âge, l'adresse mail et les antécédents médicaux (médicaments,
     * posologie, allergies) de chaque habitant. Si plusieurs personnes portent le même nom, elles doivent
     * toutes apparaître
     * @param firstName
     * @param lastName
     * @return
     */
    @GetMapping("personInfo")
    public ResponseEntity<List<Person>> getPersonsInfos(@RequestParam(name = "firstName") String firstName,
    @RequestParam(name = "lastName") String lastName){
        return ResponseEntity.ok(personService.getPersonsInfos(firstName, lastName));
    }

}
