package com.teamA.data.match;

import com.teamA.data.AbstractEntity;
import com.teamA.data.team.Team;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDate;

@Entity(name = "match")
public class Match extends AbstractEntity {

    @OneToOne
    private Team firstTeam;

    @OneToOne
    private Team secondTeam;

    private int firstTeamScore;
    private int secondTeamScore;

    @Temporal(TemporalType.DATE)
    private LocalDate date;

    private String location;

    Match(Team firstTeam, Team secondTeam, String location) {
        super();
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        this.location = location;
    }

    Match(Team firstTeam, Team secondTeam, int firstTeamScore,
          int secondTeamScore, LocalDate date, String location) {
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        this.firstTeamScore = firstTeamScore;
        this.secondTeamScore = secondTeamScore;
        this.date = date;
        this.location = location;
    }

    protected Match() {
        super();
    }

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
