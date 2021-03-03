package fr.axzial.safetyalterts.service;

import fr.axzial.safetyalterts.dto.firestation.FireStationMedicalRecordsDto;
import fr.axzial.safetyalterts.dto.firestation.FireStationPersonsDto;
import fr.axzial.safetyalterts.dto.firestation.FireStationPersonsPhoneDto;
import fr.axzial.safetyalterts.exception.FireStationNotFoundException;
import fr.axzial.safetyalterts.mapper.MedicalRecordMapper;
import fr.axzial.safetyalterts.model.FireStation;
import fr.axzial.safetyalterts.model.MedicalRecord;
import fr.axzial.safetyalterts.model.Person;
import fr.axzial.safetyalterts.util.TimeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AlertServiceImpl implements AlertService {

    public final static Integer LEGAL_AGE = 18;

    private final FireStationService fireStationService;
    private final PersonService personService;
    private final MedicalRecordService medicalRecordService;

    public AlertServiceImpl(FireStationService fireStationService, PersonService personService, MedicalRecordService medicalRecordService) {
        this.fireStationService = fireStationService;
        this.personService = personService;
        this.medicalRecordService = medicalRecordService;
    }

    @Override
    public FireStationPersonsDto alertFire(String address) { ;
        Optional<FireStation> fireStation = fireStationService.getFireStationByAddress(address);
        if (fireStation.isEmpty()) throw new FireStationNotFoundException();
        List<Person> personList = personService.getPersonByAddress(fireStation.get().getAddress());
        /*if (CollectionUtils.isEmpty(personList)) {
            throw new PersonNotFoundException();
        }*/
        return new FireStationPersonsDto(fireStation.get(), personList);
    }

    @Override
    public FireStationPersonsPhoneDto alertPhone(String station){
        Optional<FireStation> optionalFireStation = fireStationService.getFireStationByStation(station);
        if (optionalFireStation.isEmpty()) throw new FireStationNotFoundException();
        List<Person> personList = personService.getPersonByAddress(optionalFireStation.get().getAddress());
        return new FireStationPersonsPhoneDto(optionalFireStation.get(), personList.stream().map(Person::getPhone).collect(Collectors.toList()));
    }

    @Override
    public FireStationMedicalRecordsDto alertChild(String address){
        Optional<FireStation> optionalFireStation = fireStationService.getFireStationByAddress(address);
        if (optionalFireStation.isEmpty()) throw new FireStationNotFoundException();
        List<Person> personList = personService.getPersonByAddress(optionalFireStation.get().getAddress());
        List<MedicalRecord> medicalRecords = medicalRecordService.getRecordsFromPersons(personList).stream()
                .filter(e -> TimeUtils.getAgeFromBirthday(e.getBirth()) <= LEGAL_AGE)
                .collect(Collectors.toList());
        return new FireStationMedicalRecordsDto(optionalFireStation.get(), medicalRecords.stream().map(MedicalRecordMapper::mapChildMedicalRecordDto).collect(Collectors.toList()));
    }
}
