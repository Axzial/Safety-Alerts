package fr.axzial.safetyalterts.util;

import lombok.experimental.UtilityClass;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

@UtilityClass
public class TimeUtils {

    public Integer getAgeFromBirthday(Timestamp birthday){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Timestamp.from(Instant.now()).getTime());
        int nowYear = calendar.get(Calendar.YEAR);
        calendar.setTimeInMillis(birthday.getTime());
        int birthYear = calendar.get(Calendar.YEAR);
        return nowYear - birthYear;
    }

    public Timestamp addYears(Timestamp ts, int years){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ts.getTime());
        calendar.add(Calendar.YEAR, years);
        return new Timestamp(calendar.getTime().getTime());
    }

}
