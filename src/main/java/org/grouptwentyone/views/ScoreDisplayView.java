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
        GameUiView.printPageBorder();
        System.out.printf(
                "    _                     _             \n" +
                "   | |                   (_)            \n" +
                "    \\ \\   ____ ___   ____ _ ____   ____ \n" +
                "     \\ \\ / ___) _ \\ / ___) |  _ \\ / _  |\n" +
                " _____) | (__| |_| | |   | | | | ( ( | |\n" +
                "(______/ \\____)___/|_|   |_|_| |_|\\_|| |\n" +
                "                                 (_____|\n" +
                "                                        \n");

        ArrayList<Player> playerListInOrder = playerManager.getPlayerList();

        // Sorts the list of players from first to last based off their score.
        Collections.sort(playerListInOrder);
        Collections.reverse(playerListInOrder);
        GameUiView.printThinBorder();

        for (int i=0; i<playerListInOrder.size(); i++) {
            System.out.printf("%s %d) %s%s%s %s              " + GameUiView.YELLOW_BOLD_BRIGHT +
                            "%d points " + GameUiView.RESET_COLOUR + "\n",
                    positionColours.getOrDefault(i+1, GameUiView.RESET_COLOUR),
                    i+1,
                    GameUiView.RESET_COLOUR,
                    GameUiView.BLUE_UNDERLINED,
                    playerListInOrder.get(i).getUserName(),
                    GameUiView.RESET_COLOUR,
                    playerListInOrder.get(i).getScore());
                    GameUiView.printThinBorder();
        }


        System.out.print("\n");

        GameUiView.printPageBorder();
    }
}
