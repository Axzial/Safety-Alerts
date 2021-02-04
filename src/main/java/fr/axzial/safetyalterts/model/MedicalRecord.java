package fr.axzial.safetyalterts.model;

import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Entity
@Data
@Table
public class MedicalRecord {

    @Id
    String firstName;

    String lastName;

    String birthdate;

    @ElementCollection
    List<String> medications;

    @ElementCollection
    List<String> allergies;

}
