package fr.axzial.safetyalterts.service;

import fr.axzial.safetyalterts.model.MedicalRecord;
import fr.axzial.safetyalterts.model.Person;
import fr.axzial.safetyalterts.repository.MedicalRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MedicalRecordServiceImpl implements MedicalRecordService {

    MedicalRecordRepository medicalRecordRepository;
    EntityManager entityManager;

    public MedicalRecordServiceImpl(MedicalRecordRepository medicalRecordRepository, EntityManager entityManager) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.entityManager = entityManager;
    }

    @Override
    public List<MedicalRecord> getRecordsFromPersons(List<Person> personList){
        return medicalRecordRepository
                .findAllByLastNameInAndFirstNameIn(personList.stream().map(Person::getLastName).collect(Collectors.toList()), personList.stream().map(Person::getFirstName).collect(Collectors.toList()));
    }
}
