package org.grouptwentyone.views;

import org.grouptwentyone.models.PlayerManager;
import org.grouptwentyone.models.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ScoreDisplayView {
    public static void displayScorePage(PlayerManager playerManager) {

        HashMap<Integer, String> positionColours = new HashMap<>();
        positionColours.put(1, GameUiView.YELLOW_BOLD_BRIGHT);
        positionColours.put(2, GameUiView.SILVER_BOLD);
        positionColours.put(3, GameUiView.BRONZE_BOLD);

        GameUiView.printPageBorder();
        System.out.printf("%s Scoring %s \n\n", GameUiView.targetCircle, GameUiView.targetCircle);

        ArrayList<Player> playerListInOrder = playerManager.getPlayerList();

        // Sorts the list of players from first to last based off their score.
        Collections.sort(playerListInOrder);
        Collections.reverse(playerListInOrder);


        for (int i=0; i<playerListInOrder.size(); i++) {
            System.out.printf("%s %d) %s %s (%d points) \n",
                    positionColours.getOrDefault(i+1, GameUiView.RESET_COLOUR),
                    i+1,
                    GameUiView.RESET_COLOUR,
                    playerListInOrder.get(i).getUserName(),
                    playerListInOrder.get(i).getScore());
        }

        System.out.print("\n");
        GameUiView.printPageBorder();
    }
}
