package fr.axzial.safetyalterts.service;

import fr.axzial.safetyalterts.dto.PersonWithMedicationsDto;
import fr.axzial.safetyalterts.dto.firestation.FireStationCountDto;
import fr.axzial.safetyalterts.model.FireStation;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface FireStationService {

    List<FireStation> getFireStationsByNames(List<String> stations);

    Optional<FireStation> getFireStationByAddress(String address);

    Optional<FireStation> getFireStationByStation(String station);

    FireStationCountDto getUsersFromFireStationNumber(String stationNumber);

    Map<String, List<PersonWithMedicationsDto>> getFireStationWithPersons(List<String> stations);
}
