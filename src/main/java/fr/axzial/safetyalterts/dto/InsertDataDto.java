package fr.axzial.safetyalterts.dto;

import fr.axzial.safetyalterts.model.FireStation;
import fr.axzial.safetyalterts.model.MedicalRecord;
import fr.axzial.safetyalterts.model.Person;
import lombok.Data;

import java.util.List;

@Data
public class InsertDataDto {

    List<Person> persons;
    List<FireStation> firestations;
    List<MedicalRecordDto> medicalrecords;

}
