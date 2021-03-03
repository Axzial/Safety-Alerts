package fr.axzial.safetyalterts.controllers;

import fr.axzial.safetyalterts.service.FireStationService;
import fr.axzial.safetyalterts.service.MedicalRecordService;
import fr.axzial.safetyalterts.service.PersonService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = FireStationController.class)
class FireStationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireStationService fireStationService;
    @MockBean
    private PersonService personService;
    @MockBean
    private MedicalRecordService medicalRecordService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getFireStationWithPersons() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/flood/stations")
                .contentType(MediaType.APPLICATION_JSON)
                .param("stations", "999", "888"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void getUsersFromFireStation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .param("stationNumber", "999"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();
    }
}