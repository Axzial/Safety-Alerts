package fr.axzial.safetyalterts.service;

import fr.axzial.safetyalterts.dto.FireStationCountDto;
import fr.axzial.safetyalterts.exception.FireStationNotFoundException;
import fr.axzial.safetyalterts.model.FireStation;
import fr.axzial.safetyalterts.model.MedicalRecord;
import fr.axzial.safetyalterts.model.Person;
import fr.axzial.safetyalterts.repository.FireStationRepository;
import fr.axzial.safetyalterts.util.TimeUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
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
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<FireStation> cq = cb.createQuery(FireStation.class);
        Root<FireStation> root = cq.from(FireStation.class);

        List<Predicate> predicates = new ArrayList<>();
        if (!stations.isEmpty()){
            stations.forEach(e -> {
                predicates.add(cb.equal(root.get("station"), e));
            });
        }

        cq.select(root).where(cb.and(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(cq).getResultList();
    }

    /**
     * Get a FireStation by it's Address
     * @param address
     * @return
     */
    @Override
    public Optional<FireStation> getFireStationByAddress(String address){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<FireStation> cq = cb.createQuery(FireStation.class);
        Root<FireStation> root = cq.from(FireStation.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(root.get("address"), address));

        cq.select(root).where(cb.and(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(cq).getResultList().stream().findAny();
    }

    /**
     * Get a FireStation by it's Name
     * @param station
     * @return
     */
    @Override
    public Optional<FireStation> getFireStationByStation(String station) {
        FireStation fireStation = new FireStation();
        fireStation.setStation(station);
        return fireStationRepository.findOne(Example.of(fireStation));
    }


    @Override
    public FireStationCountDto getUsersFromFireStation(String stationNumber){
        FireStation fireStation = getFireStationByStation(stationNumber).orElseThrow(FireStationNotFoundException::new);
        List<Person> personList = personService.getPersonByCity(fireStation.getAddress());
        List<MedicalRecord> medicalRecords = medicalRecordService.getRecordsFromPersons(personList);
        long minors = medicalRecords.stream()
                .filter(e -> TimeUtils.getAgeFromBirthday(e.getBirthdate()) <= LEGAL_AGE).count();
        return new FireStationCountDto(fireStation, personList, minors, medicalRecords.size() - minors);
    }

    @Override
    public Map<String, List<Person>> getFireStationWithPersons(List<String> stations){
        List<FireStation> fireStations = getFireStationsByNames(stations);
        if (fireStations.isEmpty()) throw new FireStationNotFoundException();
        List<Person> personList = personService.getPersonByCities(fireStations.stream().map(FireStation::getAddress).collect(Collectors.toList()));
        return personList.stream().collect(Collectors.groupingBy(Person::getAddress, Collectors.toList()));
    }
}
