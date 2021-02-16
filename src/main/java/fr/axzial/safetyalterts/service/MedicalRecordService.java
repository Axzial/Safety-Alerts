package fr.axzial.safetyalterts.service;

import fr.axzial.safetyalterts.model.MedicalRecord;
import fr.axzial.safetyalterts.model.Person;

import java.util.List;

public interface MedicalRecordService {
    List<MedicalRecord> getRecordsFromPersons(List<Person> personList);
}
