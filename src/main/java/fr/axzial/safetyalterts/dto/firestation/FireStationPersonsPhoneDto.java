package fr.axzial.safetyalterts.dto.firestation;

import fr.axzial.safetyalterts.model.FireStation;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class FireStationPersonsPhoneDto {

    FireStation fireStation;
    List<String> phones;

}
