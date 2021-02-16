package fr.axzial.safetyalterts.service;

import fr.axzial.safetyalterts.model.FireStation;

import java.util.List;
import java.util.Optional;

public interface FireStationService {

    List<FireStation> getFireStationByIds(List<String> stations);

    Optional<FireStation> getFireStationByAddress(String address);

    Optional<FireStation> getFireStationByStation(String station);
}
