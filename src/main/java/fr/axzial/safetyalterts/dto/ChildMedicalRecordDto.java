package fr.axzial.safetyalterts.dto;

import lombok.Data;

import java.util.List;

@Data
public class ChildMedicalRecordDto {

    String firstName;

    String lastName;

    Integer age;

    List<String> medications;

    List<String> allergies;

}
