package fr.axzial.safetyalterts.service;

import fr.axzial.safetyalterts.dto.firestation.FireStationMedicalRecordsDto;
import fr.axzial.safetyalterts.dto.firestation.FireStationPersonsDto;
import fr.axzial.safetyalterts.dto.firestation.FireStationPersonsPhoneDto;

public interface AlertService {

    FireStationPersonsDto alertFire(String address);

    FireStationPersonsPhoneDto alertPhone(String station);

    FireStationMedicalRecordsDto alertChild(String address);
}
