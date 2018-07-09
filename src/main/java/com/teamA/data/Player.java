package com.teamA.data;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
public class Player extends AbstractEntity {

    private String firstName;
    private String lastName;
    private int birthYear;

    Player(String firstName, String lastName, int birthYear) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthYear = birthYear;
    }

    Player(long id, String firstName, String lastName, int birthYear) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthYear = birthYear;
    }

    protected Player() {}

    public String getFirstName() {
        return firstName;
    }

//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getBirthYear() {
        return birthYear;
    }

//    public void setBirthYear(int birthYear) {
//        this.birthYear = birthYear;
//    }

    @Override
    public String toString() {
        return "Player{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthYear=" + birthYear +
                "} " + super.toString();
    }
}
