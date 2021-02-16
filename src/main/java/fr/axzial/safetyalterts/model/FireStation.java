package fr.axzial.safetyalterts.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table
public class FireStation {

    @Id
    int id;

    String address;

    String station;

}
