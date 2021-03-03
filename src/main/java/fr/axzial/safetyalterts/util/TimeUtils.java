package fr.axzial.safetyalterts.util;

import lombok.Data;
import lombok.experimental.UtilityClass;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

@UtilityClass
public class TimeUtils {

    public Integer getAgeFromBirthday(Timestamp birthday){
        Calendar calendar = Calendar.getInstance();
        long nowMillis= calendar.getTimeInMillis();
        calendar.setTimeInMillis(birthday.getTime());
        long birthMillis= calendar.getTimeInMillis();

        return Math.toIntExact((nowMillis - birthMillis) / (365L * 24 * 60 * 60 * 1000));
    }

    public Timestamp addYears(Timestamp ts, int years){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ts.getTime());
        calendar.add(Calendar.YEAR, years);
        return new Timestamp(calendar.getTime().getTime());
    }

    public Timestamp getTimestampFromFormat(String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(
                    "dd/MM/yyyy");
            Date newDate = format.parse(date);
            return new Timestamp(newDate.getTime());
        }catch (ParseException e){
            e.printStackTrace();
        }
        return Timestamp.from(Instant.now());
    }

}