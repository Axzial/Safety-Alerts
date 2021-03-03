package fr.axzial.safetyalterts.dto.firestation;

import fr.axzial.safetyalterts.dto.ChildMedicalRecordDto;
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
