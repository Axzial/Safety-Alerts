package fr.axzial.safetyalterts.service;

import fr.axzial.safetyalterts.dto.FireStationMedicalRecordsDto;
import fr.axzial.safetyalterts.dto.FireStationPersonsDto;
import fr.axzial.safetyalterts.dto.FireStationPersonsPhoneDto;
import fr.axzial.safetyalterts.exception.FireStationNotFoundException;
import fr.axzial.safetyalterts.exception.PersonNotFoundException;
import fr.axzial.safetyalterts.mapper.MedicalRecordMapper;
import fr.axzial.safetyalterts.model.FireStation;
import fr.axzial.safetyalterts.model.MedicalRecord;
import fr.axzial.safetyalterts.model.Person;
import fr.axzial.safetyalterts.util.TimeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AlertServiceImpl implements AlertService {

    private final FireStationService fireStationService;
    private final PersonService personService;
    private final MedicalRecordService medicalRecordService;

    public AlertServiceImpl(FireStationService fireStationService, PersonService personService, MedicalRecordService medicalRecordService) {
        this.fireStationService = fireStationService;
        this.personService = personService;
        this.medicalRecordService = medicalRecordService;
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

    @Override
    public FireStationPersonsPhoneDto alertPhone(String station){
        FireStation fireStation = fireStationService.getFireStationByStation(station).orElseThrow(FireStationNotFoundException::new);
        List<Person> personList = personService.getPersonByCity(fireStation.getAddress());
        return new FireStationPersonsPhoneDto(fireStation, personList.stream().map(Person::getPhone).collect(Collectors.toList()));
    }

    @Override
    public FireStationMedicalRecordsDto alertChild(String address){
        FireStation fireStation = fireStationService.getFireStationByAddress(address).orElseThrow(FireStationNotFoundException::new);
        List<Person> personList = personService.getPersonByCity(fireStation.getAddress());
        List<MedicalRecord> medicalRecords = medicalRecordService.getRecordsFromPersons(personList).stream()
                .filter(e -> TimeUtils.getAgeFromBirthday(e.getBirthdate()) <= 18)
                .collect(Collectors.toList());
        return new FireStationMedicalRecordsDto(fireStation, medicalRecords.stream().map(MedicalRecordMapper::mapChildMedicalRecordDto).collect(Collectors.toList()));
    }
}
