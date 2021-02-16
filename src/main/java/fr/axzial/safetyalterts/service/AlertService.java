package fr.axzial.safetyalterts.service;

import fr.axzial.safetyalterts.dto.FireStationCountDto;
import fr.axzial.safetyalterts.dto.FireStationMedicalRecordsDto;
import fr.axzial.safetyalterts.dto.FireStationPersonsDto;
import fr.axzial.safetyalterts.dto.FireStationPersonsPhoneDto;

public interface AlertService {

    FireStationPersonsDto alertFire(String address);

    FireStationPersonsPhoneDto alertPhone(String station);

    FireStationMedicalRecordsDto alertChild(String address);
}
