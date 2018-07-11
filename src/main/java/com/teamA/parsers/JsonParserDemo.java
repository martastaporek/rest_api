package com.teamA.parsers;

import com.teamA.customExceptions.PersistenceFailure;
import com.teamA.data.player.Player;
import com.teamA.data.player.PlayerService;
import com.teamA.data.player.PlayerServiceImpl;
import com.teamA.supplier.Supplier;

public class JsonParserDemo {

    public static void main(String[] args) {
        JsonParser parser = Supplier.deliverJsonParser();

        PlayerService playerService = Supplier.deliverPlayerService(PlayerServiceImpl.class);

        try {
            Player player = playerService.loadPlayer("100");

            String playerAsString = parser.parse(player);

            System.out.println(playerAsString);

        } catch (PersistenceFailure persistenceFailure) {
            persistenceFailure.printStackTrace();
        }
    }
}
