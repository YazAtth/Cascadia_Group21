package org.grouptwentyone;

import org.grouptwentyone.controllers.GameController;
import org.grouptwentyone.controllers.HabitatTilesController;
import org.grouptwentyone.controllers.PlayerController;
import org.grouptwentyone.controllers.UserTerminationController;
import org.grouptwentyone.models.*;
import org.grouptwentyone.views.*;

import java.util.ArrayList;

public class StartGame {

    public static void start() {


        int numOfPlayers = GameSetupView.getNumberOfPlayersFromUser();
        ArrayList<Player> playerList = GameSetupView.getPlayerNamesFromUser(numOfPlayers);

        PlayerController playerController = new PlayerController(playerList);
        playerController.shufflePlayerList();
        GameSetupView.displayPlayerOrder(playerList);

        Player activePlayer = playerController.getFirstPlayer();

        ArrayList<HabitatTile> selectedTiles = SelectionOptionsView.getFourHabitatTiles();

        ArrayList<WildlifeToken> selectedTokens = SelectionOptionsView.getFourWildlifeTokens();

        while (true) {

            GameUiView.printPageBorder();

            System.out.printf("%s⏺ %s ⏺\n\n%s", GameUiView.WHITE_BOLD_BRIGHT, activePlayer.getUserName(), GameUiView.RESET_COLOUR);
            System.out.println(TestBoardView.displayTiles(activePlayer.getPlayerBoard()));

            GameUiView.printPageBorder();

            System.out.println(SelectionOptionsView.displaySelectedHabitatTiles(selectedTiles));
            System.out.println(SelectionOptionsView.displaySelectedWiildlifeTokens(selectedTokens));

            GameUiView.printPageBorder();

            String userInput = GameView.askUserForInput();
            GameController.UserAction userAction = GameController.getUserActionFromInput(userInput);
            ArrayList<String> userCommandArguments = GameController.getUserActionCommandArguments(userInput);

            switch (userAction) {
                case HELP:
                    GameView.showHelpPage();
                    break;
                case EXIT:
                    UserTerminationController.endProgram();
                    GameUiView.printLargeSpace();
                    break;
                case DEV__PRINT_ACTIVE_PLAYER:
                    System.out.println(activePlayer);
                    break;
                case NEXT_PLAYER:
                    System.out.println("Moved to next player");
                    activePlayer = playerController.cycleToNextPlayer();
                    GameUiView.printLargeSpace();
                    break;
                case ROTATE_TILE_CLOCKWISE:
                    System.out.println("SAMPLE TEXT: Rotated tile");
                    break;
                case PLACE_TILE_AND_TOKEN:
                    String tilePlacedCoordinates = userCommandArguments.get(0);
                    int xCoordinate = Integer.parseInt(tilePlacedCoordinates.split(",")[0]);
                    int yCoordinate = Integer.parseInt(tilePlacedCoordinates.split(",")[1]);
                    HexCoordinate newTileHexCoordinate = new HexCoordinate(xCoordinate, yCoordinate);
                    activePlayer.addNewTile(newTileHexCoordinate);
                    break;
                case INVALID_COMMAND:
                    GameView.setIsPreviousInputInvalid(true);
                    GameView.setPreviousInvalidInput(userInput);
                    GameUiView.printLargeSpace();
            }



        }


    }
}
