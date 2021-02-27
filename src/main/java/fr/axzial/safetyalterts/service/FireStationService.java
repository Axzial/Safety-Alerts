package fr.axzial.safetyalterts.service;

import fr.axzial.safetyalterts.dto.FireStationCountDto;
import fr.axzial.safetyalterts.model.FireStation;
import fr.axzial.safetyalterts.model.Person;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface FireStationService {

    List<FireStation> getFireStationsByNames(List<String> stations);

    Optional<FireStation> getFireStationByAddress(String address);

    Optional<FireStation> getFireStationByStation(String station);

    FireStationCountDto getUsersFromFireStation(String stationNumber);

    Map<String, List<Person>> getFireStationWithPersons(List<String> stations);
}
