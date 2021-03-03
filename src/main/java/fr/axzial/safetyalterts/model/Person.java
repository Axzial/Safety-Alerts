package fr.axzial.safetyalterts.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String firstName;

    String lastName;

    String address;

    String city;

    String zip;

    String phone;

    String email;
}
