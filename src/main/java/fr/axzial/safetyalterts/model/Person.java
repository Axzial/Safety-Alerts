package fr.axzial.safetyalterts.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table
public class Person {

    @Id
    int id;

    String firstName;

    String lastName;

    String adress;

    String city;

    String zip;

    String phone;

    String email;
}
