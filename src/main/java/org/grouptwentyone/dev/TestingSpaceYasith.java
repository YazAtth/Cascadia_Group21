package org.grouptwentyone.dev;

import org.grouptwentyone.models.CascadiaBot;
import org.grouptwentyone.models.Player;
import org.grouptwentyone.models.PlayerManager;

import java.util.ArrayList;
import java.util.List;

public class TestingSpaceYasith {

    public static void testingCascadiaBot1() {

        ArrayList<Player> playerList = new ArrayList<>(List.of(
                new Player("Colm"),
                new CascadiaBot("CascadiaBot"),
                new Player("Dom"),
                new Player("Yasith")));

        PlayerManager playerManager = new PlayerManager(playerList);
        Player activePlayer = playerManager.getFirstPlayer();

        while (true) {

            if (!activePlayer.playTurn()) {
                break;
            }

            activePlayer = playerManager.cycleToNextPlayer();
        }
    }

    public static void main(String[] args) {
        testingCascadiaBot1();
    }
}
