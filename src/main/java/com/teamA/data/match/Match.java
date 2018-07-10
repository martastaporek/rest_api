package com.teamA.data.match;

import com.teamA.data.AbstractEntity;
import com.teamA.data.team.Team;

import java.time.LocalDate;

public class Match extends AbstractEntity {

    private Team firstTeam;
    private Team secondTeam;
    private int firstTeamScore;
    private int secondTeamScore;
    private LocalDate date;
    private String location;


    public Team getFirstTeam() {
        return firstTeam;
    }

    public void setFirstTeam(Team firstTeam) {
        this.firstTeam = firstTeam;
    }

    public Team getSecondTeam() {
        return secondTeam;
    }

    public void setSecondTeam(Team secondTeam) {
        this.secondTeam = secondTeam;
    }

    public int getFirstTeamScore() {
        return firstTeamScore;
    }

    public void setFirstTeamScore(int firstTeamScore) {
        this.firstTeamScore = firstTeamScore;
    }

    public int getSecondTeamScore() {
        return secondTeamScore;
    }

    public void setSecondTeamScore(int secondTeamScore) {
        this.secondTeamScore = secondTeamScore;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Match{" +
                "firstTeam=" + firstTeam +
                ", secondTeam=" + secondTeam +
                ", firstTeamScore=" + firstTeamScore +
                ", secondTeamScore=" + secondTeamScore +
                ", date=" + date +
                ", location='" + location + '\'' +
                "} " + super.toString();
    }
}
