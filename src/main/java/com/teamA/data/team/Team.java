package com.teamA.data.team;

import com.teamA.data.AbstractEntity;

public class Team extends AbstractEntity {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Team{" +
                "name='" + name + '\'' +
                "} " + super.toString();
    }
}
