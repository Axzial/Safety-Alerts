package fr.axzial.safetyalterts.dto.firestation;

import fr.axzial.safetyalterts.model.FireStation;
import fr.axzial.safetyalterts.model.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FireStationPersonsDto {

    FireStation fireStation;
    List<Person> personList;

}
