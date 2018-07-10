package com.teamA;

import com.teamA.data.match.Match;
import com.teamA.data.match.MatchService;
import com.teamA.data.match.MatchServiceImpl;
import com.teamA.data.team.Team;
import com.teamA.data.team.TeamService;
import com.teamA.data.team.TeamServiceImpl;
import com.teamA.customExceptions.PersistenceFailure;
import com.teamA.data.player.Player;
import com.teamA.data.player.PlayerService;
import com.teamA.data.player.PlayerServiceImpl;
import com.teamA.servletSupplier.Supplier;

public class RootImpl implements Root {

    public static Root getInstance() {
        return new RootImpl();
    }

    private RootImpl() {}

    @Override
    public void run() {

        makeFunWithPlayer();
        makeFunWithMatch();
        makeFunWithTeam();

    }


    private void makeFunWithPlayer() {

        PlayerService playerService = Supplier.deliverPlayerService(PlayerServiceImpl.class);
        Player loaded;
        try {


            loaded = playerService.loadPlayer("2");

            System.out.println("his age: " + playerService.getAge("2"));
            System.out.println(loaded);
            playerService.changeFirstName("2", "Mario has new name");
            playerService.changeLastName("2", "Youghurt it's new lastName");
            System.out.println(playerService.getFullName("2"));
            System.out.println(loaded);



        } catch (PersistenceFailure persistenceFailure) {
            persistenceFailure.printStackTrace();
        }
    }


    private void makeFunWithTeam() {

        TeamService teamService = Supplier.deliverTeamService(TeamServiceImpl.class);

        System.out.println(teamService.registerPlayer("4", "3") );
    }

    private void makeFunWithMatch() {

        MatchService matchService = Supplier.deliverMatchService(MatchServiceImpl.class);
        TeamService teamService = Supplier.deliverTeamService(TeamServiceImpl.class);

        try {

            Team team1 = teamService.createTeam("Albany");

            Team team2 = teamService.createTeam("Norway");

            Match match = matchService.createMatch(team1, team2,"Berlin");


            System.out.println("Created match: " + match);

            matchService.changeLocation(match, "Hamburg");
            matchService.registerScore(match, "0", "2");

            System.out.println(match);
        } catch (PersistenceFailure persistenceFailure) {
            persistenceFailure.printStackTrace();
        }

    }
}
