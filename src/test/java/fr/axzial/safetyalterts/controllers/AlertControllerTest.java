package fr.axzial.safetyalterts.controllers;

import com.google.gson.Gson;
import fr.axzial.safetyalterts.dto.PersonWithMedicationsDto;
import fr.axzial.safetyalterts.dto.firestation.FireStationPersonsDto;
import fr.axzial.safetyalterts.model.FireStation;
import fr.axzial.safetyalterts.model.MedicalRecord;
import fr.axzial.safetyalterts.model.Person;
import fr.axzial.safetyalterts.repository.FireStationRepository;
import fr.axzial.safetyalterts.repository.PersonRepository;
import fr.axzial.safetyalterts.service.AlertService;
import fr.axzial.safetyalterts.service.FireStationService;
import fr.axzial.safetyalterts.service.MedicalRecordService;
import fr.axzial.safetyalterts.service.PersonService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.Collections;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AlertController.class)
class AlertControllerTest {

    ModelMapper modelMapper = new ModelMapper();
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    FireStationService fireStationService;
    @MockBean
    FireStationRepository fireStationRepo;
    @MockBean
    PersonService personService;
    @MockBean
    PersonRepository personRepository;
    @MockBean
    AlertService alertService;
    @MockBean
    MedicalRecordService medicalRecordService;

    @Autowired
    AlertController alertController;

    private FireStation fireStation;
    private Person person;
    private FireStationPersonsDto fireStationPersonsDto;

    @BeforeEach
    void setUp() {

    }

    @SneakyThrows
    @Test
    void alertFire() {
        mockMvc.perform(MockMvcRequestBuilders.get("/fire")
                .contentType(MediaType.APPLICATION_JSON)
                .param("address", "999"))
                .andExpect(status().isOk())
                .andReturn();

    }

    @SneakyThrows
    @Test
    void alertPhone() {
        mockMvc.perform(MockMvcRequestBuilders.get("/phoneAlert")
                .contentType(MediaType.APPLICATION_JSON).param("firestation", "void"))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void alertChild() {
        mockMvc.perform(MockMvcRequestBuilders.get("/childAlert")
                .contentType(MediaType.APPLICATION_JSON)
                .param("address", "test"))
                .andExpect(status().isOk());
    }
}