package fr.axzial.safetyalterts.dto;

import fr.axzial.safetyalterts.model.FireStation;
import fr.axzial.safetyalterts.model.Person;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Data
public class FireStationCountDto {

    FireStation fireStation;
    List<Person> personList;
    Long minors;
    Long majors;

}
