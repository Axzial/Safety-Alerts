package fr.axzial.safetyalterts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(FireStationNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDto fireStationNotFound(FireStationNotFoundException e){
        return new ExceptionDto(e.getClass().getSimpleName(), Arrays.toString(e.getStackTrace()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MailNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDto mailNotFound(MailNotFoundException e){
        return new ExceptionDto(e.getClass().getSimpleName(), Arrays.toString(e.getStackTrace()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(PersonNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDto personNotFound(PersonNotFoundException e){
        return new ExceptionDto(e.getClass().getSimpleName(), Arrays.toString(e.getStackTrace()));
    }

}
