package fr.axzial.safetyalterts.controllers;

import fr.axzial.safetyalterts.dto.FireStationCountDto;
import fr.axzial.safetyalterts.model.Person;
import fr.axzial.safetyalterts.service.FireStationService;
import fr.axzial.safetyalterts.service.MedicalRecordService;
import fr.axzial.safetyalterts.service.PersonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class FireStationController {


    FireStationService fireStationService;
    PersonService personService;
    MedicalRecordService medicalRecordService;

    public FireStationController(FireStationService fireStationService, PersonService personService, MedicalRecordService medicalRecordService) {
        this.fireStationService = fireStationService;
        this.personService = personService;
        this.medicalRecordService = medicalRecordService;
    }

    /**
     * Cette url doit retourner une liste de tous les foyers desservis par la caserne. Cette liste doit regrouper les
     * personnes par adresse. Elle doit aussi inclure le nom, le numéro de téléphone et l'âge des habitants, et
     * faire figurer leurs antécédents médicaux (médicaments, posologie et allergies) à côté de chaque nom
     * @param stations
     * @return
     */
    @GetMapping("/flood/stations")
    public Map<String, List<Person>> getFireStationWithPersons(@RequestParam(name = "stations") List<String> stations){
        return fireStationService.getFireStationWithPersons(stations);
    }

    @GetMapping("/fireStation")
    public FireStationCountDto getUsersFromFireStation(@RequestParam(name = "stationNumber") String stationNumber){
        return fireStationService.getUsersFromFireStation(stationNumber);
    }

}
