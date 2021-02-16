package fr.axzial.safetyalterts.dto;

import fr.axzial.safetyalterts.model.FireStation;
import fr.axzial.safetyalterts.model.Person;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class FireStationPersonsDto {

    FireStation fireStation;
    List<Person> personList;
}
