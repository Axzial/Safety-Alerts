package fr.axzial.safetyalterts.controllers;

import fr.axzial.safetyalterts.dto.InsertDataDto;
import fr.axzial.safetyalterts.model.MedicalRecord;
import fr.axzial.safetyalterts.repository.FireStationRepository;
import fr.axzial.safetyalterts.repository.MedicalRecordRepository;
import fr.axzial.safetyalterts.repository.PersonRepository;
import fr.axzial.safetyalterts.util.TimeUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/insert")
public class InsertDataController {

    ModelMapper modelMapper = new ModelMapper();

    PersonRepository personRepository;
    FireStationRepository fireStationRepository;
    MedicalRecordRepository medicalRecordService;

    public InsertDataController(PersonRepository personRepository, FireStationRepository fireStationRepository, MedicalRecordRepository medicalRecordService) {
        this.personRepository = personRepository;
        this.fireStationRepository = fireStationRepository;
        this.medicalRecordService = medicalRecordService;
    }

    @PostMapping
    public ResponseEntity<Void> insertData(@RequestBody InsertDataDto insertDataDto){
        personRepository.saveAll(insertDataDto.getPersons());
        fireStationRepository.saveAll(insertDataDto.getFirestations());
        medicalRecordService.saveAll(insertDataDto.getMedicalrecords().stream().map(e -> {
            MedicalRecord medicalRecord = modelMapper.map(e, MedicalRecord.class);
            medicalRecord.setBirth(TimeUtils.getTimestampFromFormat(e.getBirthdate()));
            return medicalRecord;
        }).collect(Collectors.toList()));
        return ResponseEntity.ok().build();
    }

}
