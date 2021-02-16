package fr.axzial.safetyalterts.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorDto {
    String title;
    String description;
}
