package fr.axzial.safetyalterts.dto;

import fr.axzial.safetyalterts.model.FireStation;
import fr.axzial.safetyalterts.model.MedicalRecord;
import fr.axzial.safetyalterts.model.Person;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class FireStationMedicalRecordsDto {

    FireStation fireStation;
    List<ChildMedicalRecordDto> medicalRecords;
}
