package org.grouptwentyone;

import org.grouptwentyone.controllers.*;
import org.grouptwentyone.models.*;
import org.grouptwentyone.models.Exceptions.*;
import org.grouptwentyone.views.*;

import java.util.ArrayList;

import static org.grouptwentyone.views.SelectionOptionsView.selectTileAndToken;

public class StartGame {
    public static ArrayList<HabitatTile> selectedTiles = SelectionOptionsView.getFourHabitatTiles();

    public static ArrayList<WildlifeToken> selectedTokens = SelectionOptionsView.getFourWildlifeTokens();


    public static void start() {


        int numOfPlayers = GameSetupView.getNumberOfPlayersFromUser();
        ArrayList<Player> playerList = GameSetupView.getPlayerNamesFromUser(numOfPlayers);

        PlayerController playerController = new PlayerController(playerList);
        playerController.shufflePlayerList();
        GameSetupView.displayPlayerOrder(playerList);

        Player activePlayer = playerController.getFirstPlayer();


        while (true) {

            GameUiView.printPageBorder();

            System.out.printf("%s⏺ %s ⏺\n\n%s", GameUiView.WHITE_BOLD_BRIGHT, activePlayer.getUserName(), GameUiView.RESET_COLOUR);
            System.out.println(TestBoardView.displayTiles(activePlayer.getPlayerBoard()));

            GameUiView.printPageBorder();

            System.out.println(SelectionOptionsView.displaySelectedHabitatTiles(selectedTiles));
            System.out.println(SelectionOptionsView.displaySelectedWiildlifeTokens(selectedTokens));
            System.out.println("      (1)            (2)            (3)            (4)      \n");

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
                case SELECT_TILE_AND_TOKEN:
                    selectTileAndToken(activePlayer);
                    break;
                case PLACE_TILE:
                    String tilePlacedCoordinates = userCommandArguments.get(0);
                    int tileXCoordinate = Integer.parseInt(tilePlacedCoordinates.split(",")[0]);
                    int tileYCoordinate = Integer.parseInt(tilePlacedCoordinates.split(",")[1]);
                    HexCoordinate newTileHexCoordinate = new HexCoordinate(tileXCoordinate, tileYCoordinate);

                    try {
                        activePlayer.addNewTile(newTileHexCoordinate);
                    } catch (TilePlacedAtOccupiedPositionException ex) {
                        GameView.setPreviousInputDisallowedMessage(String.format("%sTile Already Exists at that position! Try again.%s", GameUiView.RED_BOLD, GameUiView.RESET_COLOUR));
                    } catch (TileNotPlacedAdjacentlyException ex) {
                        GameView.setPreviousInputDisallowedMessage(String.format("%sTile cannot be placed in a position that is not adjacent to an existing tile. Try again.%s", GameUiView.RED_BOLD, GameUiView.RESET_COLOUR));
                    }
                    break;
                case PLACE_TOKEN:
                    String tokenPlacedCoordinates = userCommandArguments.get(0);
                    int tokenXCoordinate = Integer.parseInt(tokenPlacedCoordinates.split(",")[0]);
                    int tokenYCoordinate = Integer.parseInt(tokenPlacedCoordinates.split(",")[1]);
                    HexCoordinate newTokenHexCoordinate = new HexCoordinate(tokenXCoordinate, tokenYCoordinate);

                    try {
                        activePlayer.addNewToken(newTokenHexCoordinate);
                    } catch (TokenPlacedAtOccupiedPositionException ex) {
                        GameView.setPreviousInputDisallowedMessage("Tried to place Wildlife Token on an already occupied Habitat Tile");
                    } catch (TokenPlacedAtEmptyPositionException ex) {
                        GameView.setPreviousInputDisallowedMessage("Tried to place Wildlife Token where there is no Habitat Tile");
                    } catch (TokenPlacedAtIllegalTileException ex) {
                        GameView.setPreviousInputDisallowedMessage("This type of Wildlife Token Typw cannot be placed on this Habitat Tile");
                    }
                    break;
                case RETURN_TOKEN:
                    WildlifeTokensController.wildlifeTokenBag.add(activePlayer.getSelectedToken());
                    //reset selectedToken
                    activePlayer.getSelectedToken().setWildlifeTokenType(WildlifeToken.WildlifeTokenType.EMPTY);
                    break;
                case INVALID_COMMAND:
                    GameView.setIsPreviousInputInvalid(true);
                    GameView.setPreviousInvalidInput(userInput);
                    GameUiView.printLargeSpace();
            }



        }


    }
}
