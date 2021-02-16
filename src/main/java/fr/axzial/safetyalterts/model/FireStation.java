package fr.axzial.safetyalterts.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table
public class FireStation {

    @Id
    int id;

    String address;

    String station;

}
