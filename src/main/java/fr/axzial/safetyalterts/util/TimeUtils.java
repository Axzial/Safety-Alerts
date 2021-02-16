package fr.axzial.safetyalterts.util;

import lombok.experimental.UtilityClass;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class TimeUtils {

    public LocalDateTime getTimeStampFromBirthday(String birthday){
        String pattern = "dd/mm/yyyy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.from(formatter.parse(birthday));
    }

    public Integer getAgeFromBirthday(String birthday){
        String pattern = "dd/mm/yyyy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime localDateTime = LocalDateTime.from(formatter.parse(birthday));
        return LocalDateTime.now().compareTo(localDateTime);
    }

}
