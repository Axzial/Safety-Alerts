package fr.axzial.safetyalterts.service;

import fr.axzial.safetyalterts.dto.PersonWithMedicationsDto;
import fr.axzial.safetyalterts.dto.firestation.FireStationCountDto;
import fr.axzial.safetyalterts.exception.FireStationNotFoundException;
import fr.axzial.safetyalterts.model.FireStation;
import fr.axzial.safetyalterts.model.MedicalRecord;
import fr.axzial.safetyalterts.model.Person;
import fr.axzial.safetyalterts.repository.FireStationRepository;
import fr.axzial.safetyalterts.util.TimeUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class FireStationServiceImpl implements FireStationService {

    private static final int LEGAL_AGE = 18;

    private final FireStationRepository fireStationRepository;
    private final MedicalRecordService medicalRecordService;
    private final PersonService personService;
    EntityManager entityManager;
    ModelMapper modelMapper;

    public FireStationServiceImpl(FireStationRepository fireStationRepository, MedicalRecordService medicalRecordService, PersonService personService, EntityManager entityManager) {
        this.fireStationRepository = fireStationRepository;
        this.medicalRecordService = medicalRecordService;
        this.personService = personService;
        this.entityManager = entityManager;
        this.modelMapper = new ModelMapper();
    }

    /**
     * Search FireStations by Names
     * @param stations
     * @return
     */
    @Override
    public List<FireStation> getFireStationsByNames(List<String> stations) {
        return fireStationRepository.findAllByStationIn(stations);
    }

    /**
     * Get a FireStation by it's Address
     * @param address
     * @return
     */
    @Override
    public Optional<FireStation> getFireStationByAddress(String address){
        return fireStationRepository.findAllByAddress(address).stream().findFirst();
    }

    /**
     * Get a FireStation by it's Name
     * @param station
     * @return
     */
    @Override
    public Optional<FireStation> getFireStationByStation(String station) {
        return fireStationRepository.findAllByStation(station).stream().findFirst();
    }


    @Override
    public FireStationCountDto getUsersFromFireStationNumber(String stationNumber){

        Optional<FireStation> fireStation = getFireStationByStation(stationNumber);

        if (fireStation.isEmpty()) throw new FireStationNotFoundException();

        List<Person> personList = personService.getPersonByAddress(fireStation.get().getAddress());
        List<MedicalRecord> medicalRecords = medicalRecordService.getRecordsFromPersons(personList);

        long minors = medicalRecords.stream()
                .filter(e -> TimeUtils.getAgeFromBirthday(e.getBirth()) <= LEGAL_AGE).count();

        return new FireStationCountDto(fireStation.get(), personList, minors, medicalRecords.size() - minors);
    }

    @Override
    public Map<String, List<PersonWithMedicationsDto>> getFireStationWithPersons(List<String> stations){

        List<FireStation> fireStations = getFireStationsByNames(stations);

        if (fireStations.isEmpty()) throw new FireStationNotFoundException();

        List<Person> personList = personService.getPersonByAddresses(fireStations.stream().map(FireStation::getAddress).collect(Collectors.toList()));
        List<MedicalRecord> medicalRecords = medicalRecordService.getRecordsFromPersons(personList);

        return personList.stream().map(e -> {
            PersonWithMedicationsDto personWithMedicationsDto = modelMapper.map(e, PersonWithMedicationsDto.class);
            medicalRecords.stream()
                    .filter(x -> x.getFirstName().equalsIgnoreCase(e.getFirstName()) && x.getLastName().equalsIgnoreCase(e.getLastName()))
                    .forEach(personWithMedicationsDto::setMedicalRecord);
            return personWithMedicationsDto;
        }).collect(Collectors.groupingBy(Person::getAddress, Collectors.toList()));
    }
}
