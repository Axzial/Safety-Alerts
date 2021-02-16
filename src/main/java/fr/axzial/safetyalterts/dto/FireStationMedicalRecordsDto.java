package fr.axzial.safetyalterts.dto;

import fr.axzial.safetyalterts.model.FireStation;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class FireStationMedicalRecordsDto {

    FireStation fireStation;
    List<ChildMedicalRecordDto> medicalRecords;
}
