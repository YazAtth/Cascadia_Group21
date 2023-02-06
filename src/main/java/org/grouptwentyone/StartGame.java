package org.grouptwentyone;

import com.sun.jdi.InterfaceType;
import org.grouptwentyone.controllers.GameController;
import org.grouptwentyone.controllers.PlayerController;
import org.grouptwentyone.controllers.UserTerminationController;
import org.grouptwentyone.models.Player;
import org.grouptwentyone.views.GameSetupView;
import org.grouptwentyone.views.GameUiView;
import org.grouptwentyone.views.GameView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;

public class StartGame {


    public static void start() {


        int numOfPlayers = GameSetupView.getNumberOfPlayersFromUser();
        ArrayList<Player> playerList = GameSetupView.getPlayerNamesFromUser(numOfPlayers);

        PlayerController playerController = new PlayerController(playerList);
        Player activePlayer = playerController.getFirstPlayer();


        while (true) {

            System.out.println(GameUiView.PAGE_BORDER_TOP);

            System.out.println("VIEW OF HABITAT GOES HERE");
            System.out.println("VIEW OF CARD OPTIONS GOES HERE\n");

            System.out.println(GameUiView.PAGE_BORDER_TOP);

            String userInput = GameView.askUserForInput();
            GameController.UserAction userAction = GameController.getUserActionFromInput(userInput);

            switch (userAction) {
                case HELP:
                    GameView.showHelpPage();
                    System.out.println(GameUiView.LARGE_SPACE);
                    break;
                case EXIT:
                    UserTerminationController.endProgram();
                    System.out.println(GameUiView.LARGE_SPACE);
                    break;
                case DEV__PRINT_ACTIVE_PLAYER:
                    System.out.println(activePlayer);
                    break;
                case DEV__NEXT_PLAYER:
                    System.out.println("Moved to next player");
                    activePlayer = playerController.cycleToNextPlayer();
                    break;
                case INVALID_COMMAND:
                    GameView.setIsPreviousInputInvalid(true);
                    GameView.setPreviousInvalidInput(userInput);
                    System.out.println(GameUiView.LARGE_SPACE);
            }



        }


    }
}
