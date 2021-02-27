package fr.axzial.safetyalterts.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class TimeUtilsTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAgeFromBirthday() {
        Timestamp ts = TimeUtils.addYears(Timestamp.from(Instant.now()), -24);
        Integer i = TimeUtils.getAgeFromBirthday(ts);
        Assertions.assertEquals(24, i);
    }
}