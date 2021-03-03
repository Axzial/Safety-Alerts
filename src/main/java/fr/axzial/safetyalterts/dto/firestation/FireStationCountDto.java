package fr.axzial.safetyalterts.dto.firestation;

import fr.axzial.safetyalterts.model.FireStation;
import fr.axzial.safetyalterts.model.Person;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class FireStationCountDto {

    FireStation fireStation;
    List<Person> personList;
    Long minors;
    Long majors;

}
