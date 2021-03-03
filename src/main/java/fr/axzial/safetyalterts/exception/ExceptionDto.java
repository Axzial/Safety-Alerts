package fr.axzial.safetyalterts.exception;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExceptionDto {
    String title;
    String stackTrace;
}
