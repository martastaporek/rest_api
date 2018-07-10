package com.teamA.data.team;

import com.teamA.data.AbstractEntity;
import com.teamA.data.player.Player;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;


@Entity(name = "team")
public class Team extends AbstractEntity {

    private String name;

    @OneToMany
    private List<Player> players = new ArrayList<>();

    public Team(String name) {
        super();
        this.name = name;
    }

    public Team() {
        super();
    }



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
                ", players=" + players +
                "} " + super.toString();
    }
}
