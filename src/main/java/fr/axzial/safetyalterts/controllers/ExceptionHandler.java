package fr.axzial.safetyalterts.controllers;

import fr.axzial.safetyalterts.dto.ErrorDto;
import fr.axzial.safetyalterts.exception.FireStationNotFoundException;
import fr.axzial.safetyalterts.exception.MailNotFoundException;
import fr.axzial.safetyalterts.exception.PersonNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(FireStationNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto fireStationNotFound(FireStationNotFoundException fireStationNotFoundException){
        return ErrorDto.builder().title(fireStationNotFoundException.getClass().getName()).description(fireStationNotFoundException.getCause().getMessage()).build();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MailNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto mailNotFound(MailNotFoundException mailNotFoundException){
        return ErrorDto.builder().title(mailNotFoundException.getClass().getName()).description(mailNotFoundException.getCause().getMessage()).build();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(PersonNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto fireStationNotFound(PersonNotFoundException personNotFoundException){
        return ErrorDto.builder().title(personNotFoundException.getClass().getName()).description(personNotFoundException.getCause().getMessage()).build();
    }

}
