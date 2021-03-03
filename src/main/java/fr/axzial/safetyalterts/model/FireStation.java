package fr.axzial.safetyalterts.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table
public class FireStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String address;

    String station;

}
