package fr.axzial.safetyalterts.dto;

import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.List;

@Data
public class MedicalRecordDto {

    @Id
    String firstName;

    String lastName;

    String birthdate;

    @ElementCollection
    List<String> medications;

    @ElementCollection
    List<String> allergies;

}
