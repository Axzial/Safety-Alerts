package fr.axzial.safetyalterts.controllers;

import fr.axzial.safetyalterts.dto.ChildMedicalRecordDto;
import fr.axzial.safetyalterts.dto.FireStationMedicalRecordsDto;
import fr.axzial.safetyalterts.dto.FireStationPersonsDto;
import fr.axzial.safetyalterts.dto.FireStationPersonsPhoneDto;
import fr.axzial.safetyalterts.exception.FireStationNotFoundException;
import fr.axzial.safetyalterts.mapper.MedicalRecordMapper;
import fr.axzial.safetyalterts.model.FireStation;
import fr.axzial.safetyalterts.model.MedicalRecord;
import fr.axzial.safetyalterts.model.Person;
import fr.axzial.safetyalterts.service.AlertService;
import fr.axzial.safetyalterts.service.FireStationService;
import fr.axzial.safetyalterts.service.MedicalRecordService;
import fr.axzial.safetyalterts.service.PersonService;
import fr.axzial.safetyalterts.util.TimeUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class AlertController {

    private final FireStationService fireStationService;
    private final PersonService personService;
    private final AlertService alertService;
    private final MedicalRecordService medicalRecordService;

    public AlertController(FireStationService fireStationService, PersonService personService, AlertService alertService, MedicalRecordService medicalRecordService) {
        this.fireStationService = fireStationService;
        this.personService = personService;
        this.alertService = alertService;
        this.medicalRecordService = medicalRecordService;
    }

    /**
     * Cette url doit retourner la liste des habitants vivant à l’adresse donnée ainsi que le numéro de la caserne
     * de pompiers la desservant. La liste doit inclure le nom, le numéro de téléphone, l'âge et les antécédents
     * médicaux (médicaments, posologie et allergies) de chaque personne
     * @param address
     * @return
     */
    @GetMapping("/fire")
    public ResponseEntity<FireStationPersonsDto> alertFire(@RequestParam(name = "address") String address){
        return ResponseEntity.ok(alertService.alertFire(address));
    }

    /**
     * Cette url doit retourner une liste des numéros de téléphone des résidents desservis par la caserne de
     * pompiers. Nous l'utiliserons pour envoyer des messages texte d'urgence à des foyers spécifiques
     * @param station
     * @return
     */
    @GetMapping("/phoneAlert")
    public ResponseEntity<FireStationPersonsPhoneDto> alertPhone(@RequestParam(name = "fireStation") String station){
        FireStation fireStation = fireStationService.getFireStationByStation(station).orElseThrow(FireStationNotFoundException::new);
        List<Person> personList = personService.getPersonByCity(fireStation.getAddress());
        return new ResponseEntity<>(new FireStationPersonsPhoneDto(fireStation, personList.stream().map(Person::getPhone).collect(Collectors.toList())), new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Cette url doit retourner une liste d'enfants (tout individu âgé de 18 ans ou moins) habitant à cette adresse.
     * La liste doit comprendre le prénom et le nom de famille de chaque enfant, son âge et une liste des autres
     * membres du foyer. S'il n'y a pas d'enfant, cette url peut renvoyer une chaîne vide
     * @param address
     * @return
     */
    @GetMapping("/childAlert")
    public ResponseEntity<FireStationMedicalRecordsDto> alertChild(@RequestParam(name = "address") String address){
        FireStation fireStation = fireStationService.getFireStationByAddress(address).orElseThrow(FireStationNotFoundException::new);
        List<Person> personList = personService.getPersonByCity(fireStation.getAddress());
        List<MedicalRecord> medicalRecords = medicalRecordService.getRecordsFromPersons(personList).stream()
                .filter(e -> TimeUtils.getAgeFromBirthday(e.getBirthdate()) <= 18)
                .collect(Collectors.toList());
        return new ResponseEntity<>(new FireStationMedicalRecordsDto(fireStation, medicalRecords.stream().map(MedicalRecordMapper::mapChildMedicalRecordDto).collect(Collectors.toList())), new HttpHeaders(), HttpStatus.OK);
    }


}
