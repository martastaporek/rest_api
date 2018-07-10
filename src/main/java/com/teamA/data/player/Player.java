package com.teamA.data.player;

import com.teamA.data.AbstractEntity;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity(name = "player")
public class Player extends AbstractEntity {

    private String firstName;
    private String lastName;
    private int birthYear;

    Player(String firstName, String lastName, int birthYear) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthYear = birthYear;
    }

    protected Player() {
        super();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    @Override
    public String toString() {
        return "Player{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthYear=" + birthYear +
                "} " + super.toString();
    }
}
