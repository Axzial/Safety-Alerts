package fr.axzial.safetyalterts.controllers;

import fr.axzial.safetyalterts.dto.FireStationMedicalRecordsDto;
import fr.axzial.safetyalterts.dto.FireStationPersonsDto;
import fr.axzial.safetyalterts.dto.FireStationPersonsPhoneDto;
import fr.axzial.safetyalterts.service.AlertService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alert")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    /**
     * Cette url doit retourner la liste des habitants vivant à l’adresse donnée ainsi que le numéro de la caserne
     * de pompiers la desservant. La liste doit inclure le nom, le numéro de téléphone, l'âge et les antécédents
     * médicaux (médicaments, posologie et allergies) de chaque personne
     * @param address
     * @return
     */
    @GetMapping("/fire")
    public FireStationPersonsDto alertFire(@RequestParam(name = "address") String address){
        return alertService.alertFire(address);
    }

    /**
     * Cette url doit retourner une liste des numéros de téléphone des résidents desservis par la caserne de
     * pompiers. Nous l'utiliserons pour envoyer des messages texte d'urgence à des foyers spécifiques
     * @param station
     * @return
     */
    @GetMapping("/phoneAlert")
    public FireStationPersonsPhoneDto alertPhone(@RequestParam(name = "fireStation") String station){
        return alertService.alertPhone(station);
    }

    /**
     * Cette url doit retourner une liste d'enfants (tout individu âgé de 18 ans ou moins) habitant à cette adresse.
     * La liste doit comprendre le prénom et le nom de famille de chaque enfant, son âge et une liste des autres
     * membres du foyer. S'il n'y a pas d'enfant, cette url peut renvoyer une chaîne vide
     * @param address
     * @return
     */
    @GetMapping("/childAlert")
    public FireStationMedicalRecordsDto alertChild(@RequestParam(name = "address") String address){
        return alertService.alertChild(address);
    }


}
