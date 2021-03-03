package fr.axzial.safetyalterts.controllers;

import fr.axzial.safetyalterts.dto.PersonWithMedicationsDto;
import fr.axzial.safetyalterts.model.Person;
import fr.axzial.safetyalterts.service.PersonService;
import org.springframework.web.bind.annotation.GetMapping;
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
    @GetMapping("/communityEmail")
    public List<String> getCityMails(@RequestParam(name = "city", required = true) String city){
        return personService.getMailsByCity(city);
    }

    /**
     * Cette url doit retourner le nom, l'adresse, l'âge, l'adresse mail et les antécédents médicaux (médicaments,
     * posologie, allergies) de chaque habitant. Si plusieurs personnes portent le même nom, elles doivent
     * toutes apparaître
     * @param firstName
     * @param lastName
     * @return
     */
    @GetMapping("/personInfo")
    public List<PersonWithMedicationsDto> getPersonsInfos(@RequestParam(name = "firstName") String firstName,
                                                          @RequestParam(name = "lastName") String lastName){
        return personService.getPersonsInfos(firstName, lastName);
    }

}
