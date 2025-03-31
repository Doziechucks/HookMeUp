package org.example.data.models;

import java.time.LocalDate;

public class Profile {
    private String firstname;
    private String lastname;
    private String email;
    private LocalDate dateOfBirth;
    private Gender gender;
    private Weight weight;
    private Height height;

    public Profile(String firstname, String lastname, String email, LocalDate dateOfBirth, Gender gender, Weight weight, Height height) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
    }
    public Profile() {}

    public String getFirstname() {
        return firstname;
    }

    public Profile setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public Profile setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Profile setEmail(String email) {
        this.email = email;
        return this;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Profile setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public Gender getGender() {
        return gender;
    }

    public Profile setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public Weight getWeight() {
        return weight;
    }

    public Profile setWeight(Weight weight) {
        this.weight = weight;
        return this;
    }

    public Height getHeight() {
        return height;
    }

    public Profile setHeight(Height height) {
        this.height = height;
        return this;
    }


}
