package com.teamA.data.team;

import com.teamA.customExceptions.PersistenceFailure;
import com.teamA.data.player.Player;

import java.util.List;

public interface TeamService {

    Team createTeam(String name) throws PersistenceFailure;
    boolean setPlayers(List <Player> players);
    Team loadTeam (String id) throws PersistenceFailure;
}
