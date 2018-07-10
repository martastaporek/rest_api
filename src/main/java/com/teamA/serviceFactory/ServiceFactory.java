package com.teamA.serviceFactory;

import com.teamA.data.match.MatchService;
import com.teamA.data.player.PlayerService;
import com.teamA.data.team.TeamService;

public interface ServiceFactory {

    <T extends PlayerService> PlayerService createPlayerService(Class<T> type);
    <T extends MatchService> MatchService createMatchService(Class<T> type);
    <T extends TeamService> TeamService createTeamService(Class<T> type);
}
