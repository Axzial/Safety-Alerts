package fr.axzial.safetyalterts.controllers;

import com.google.gson.Gson;
import fr.axzial.safetyalterts.service.AlertService;
import fr.axzial.safetyalterts.service.FireStationService;
import fr.axzial.safetyalterts.service.MedicalRecordService;
import fr.axzial.safetyalterts.service.PersonService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(AlertController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AlertControllerTest {

    private static final String ENDPOINT_URL = "/alert";

    @MockBean
    FireStationService fireStationService;
    @MockBean
    PersonService personService;
    @MockBean
    AlertService alertService;
    @MockBean
    MedicalRecordService medicalRecordService;

    @Autowired
    AlertController alertController;

    @Autowired
    private MockMvc mockMvc;

    Gson gson = new Gson();

    @BeforeAll
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(alertController).build();
    }

    @SneakyThrows
    @Test
    void alertFire() {
        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL + "/fire")
                .contentType(MediaType.APPLICATION_JSON)
                .param("address", "test"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @SneakyThrows
    @Test
    void alertPhone() {
        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL + "/phoneAlert")
                .contentType(MediaType.APPLICATION_JSON).param("fireStation", "void"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @SneakyThrows
    @Test
    void alertChild() {
        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL + "/childAlert")
                .contentType(MediaType.APPLICATION_JSON)
                .param("address", "test"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}