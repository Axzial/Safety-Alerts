package fr.axzial.safetyalterts.service;

import fr.axzial.safetyalterts.dto.FireStationCountDto;
import fr.axzial.safetyalterts.dto.FireStationPersonsDto;

public interface AlertService {

    FireStationPersonsDto alertFire(String address);
}
