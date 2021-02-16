package fr.axzial.safetyalterts.service;

import fr.axzial.safetyalterts.dto.FireStationCountDto;
import fr.axzial.safetyalterts.dto.FireStationPersonsDto;
import fr.axzial.safetyalterts.exception.FireStationNotFoundException;
import fr.axzial.safetyalterts.exception.PersonNotFoundException;
import fr.axzial.safetyalterts.model.FireStation;
import fr.axzial.safetyalterts.model.Person;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Transactional
public class AlertServiceImpl implements AlertService {

    private final FireStationService fireStationService;
    private final PersonService personService;

    public AlertServiceImpl(FireStationService fireStationService, PersonService personService) {
        this.fireStationService = fireStationService;
        this.personService = personService;
    }

    @Override
    public FireStationPersonsDto alertFire(String address) {
        FireStation fireStation = fireStationService.getFireStationByAddress(address).orElseThrow(FireStationNotFoundException::new);
        List<Person> personList = personService.getPersonByCity(fireStation.getAddress());
        if (CollectionUtils.isEmpty(personList)) {
            throw new PersonNotFoundException();
        }
        return new FireStationPersonsDto(fireStation, personList);
    }
}
