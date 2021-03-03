package fr.axzial.safetyalterts.dto;

import fr.axzial.safetyalterts.model.MedicalRecord;
import fr.axzial.safetyalterts.model.Person;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonWithMedicationsDto extends Person{

    MedicalRecord medicalRecord;
}
