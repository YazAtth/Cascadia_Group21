package org.grouptwentyone;

import org.grouptwentyone.controllers.*;
import org.grouptwentyone.models.*;
import org.grouptwentyone.models.Exceptions.*;
import org.grouptwentyone.views.*;

import java.util.ArrayList;

public class StartGame {
    public static ArrayList<HabitatTile> selectedTiles = SelectionOptionsView.getFourHabitatTiles();

    public static ArrayList<WildlifeToken> selectedTokens = SelectionOptionsView.getFourWildlifeTokens();

    public static boolean tilesRemain = true;

    public static void start() {

        int numOfPlayers = GameSetupView.getNumberOfPlayersFromUser();
        ArrayList<Player> playerList = GameSetupView.getPlayerNamesFromUser(numOfPlayers);

        PlayerController playerController = new PlayerController(playerList);
        playerController.shufflePlayerList();
        GameSetupView.displayPlayerOrder(playerList);

        Player activePlayer = playerController.getFirstPlayer();

        String coordinateDelim = ",|, ";

        while (tilesRemain) {

            GameUiView.printPageBorder();

            System.out.printf("%s⏺ %s ⏺\n\n%s", GameUiView.WHITE_BOLD_BRIGHT, activePlayer.getUserName(), GameUiView.RESET_COLOUR);
            System.out.println(BoardView.displayTiles(activePlayer.getPlayerBoard()));

            GameUiView.printPageBorder();

            System.out.println(SelectionOptionsView.displaySelectedHabitatTiles(selectedTiles));
            System.out.println(SelectionOptionsView.displaySelectedWildlifeTokens(selectedTokens));
            System.out.println("      (1)            (2)            (3)            (4)      \n");

            //GameUiView.printPageBorder();

            //check for cull before user turn
            CullingController.checkForCull();


            //nature token options will go here



            //select tile/token pair
            SelectionOptionsView.selectTileAndToken(activePlayer);

            GameUiView.printLargeSpace();

            //place tile
            GameUiView.printPageBorder();
            System.out.printf("%s⏺ %s ⏺\n\n%s", GameUiView.WHITE_BOLD_BRIGHT, activePlayer.getUserName(), GameUiView.RESET_COLOUR);
            System.out.println(BoardView.displayTiles(activePlayer.getPlayerBoard()));
            GameUiView.printPageBorder();

            //display selected tile below


            System.out.print("Please enter the coordinates for where you would like to place the tile at in the format 'x, y'\n> ");

            do {
                String [] tilePlacedCoordinates = GameView.askUserForInput().split(coordinateDelim);

                try {
                    int tileXCoordinate = Integer.parseInt(tilePlacedCoordinates[0]);
                    int tileYCoordinate = Integer.parseInt(tilePlacedCoordinates[1]);
                    HexCoordinate newTileHexCoordinate = new HexCoordinate(tileXCoordinate, tileYCoordinate);

                    activePlayer.addNewTile(newTileHexCoordinate);
                    GameView.setIsPreviousInputInvalid(false);
                } catch (ArrayIndexOutOfBoundsException ex) {
                    GameView.setPreviousInputDisallowedMessage(String.format("%sInvalid input, please enter coordinates in the format x,y%s\n> ",
                            GameUiView.RED_BOLD, GameUiView.RESET_COLOUR));
                } catch (NumberFormatException ex) {
                    GameView.setPreviousInputDisallowedMessage(String.format("%sInvalid input, please enter coordinates in the format x,y%s\n> ",
                            GameUiView.RED_BOLD, GameUiView.RESET_COLOUR));
                } catch (TilePlacedAtOccupiedPositionException ex) {
                    GameView.setPreviousInputDisallowedMessage(String.format("%sTile Already Exists at that position! Try again.%s\n> ",
                            GameUiView.RED_BOLD, GameUiView.RESET_COLOUR));
                } catch (TileNotPlacedAdjacentlyException ex) {
                    GameView.setPreviousInputDisallowedMessage(String.format("%sTile cannot be placed in a position that is not adjacent to an existing tile. Try again.%s\n> ",
                            GameUiView.RED_BOLD, GameUiView.RESET_COLOUR));
                }
            } while (GameView.isIsPreviousInputInvalid());

            GameUiView.printLargeSpace();

            GameUiView.printPageBorder();
            System.out.printf("%s⏺ %s ⏺\n\n%s", GameUiView.WHITE_BOLD_BRIGHT, activePlayer.getUserName(), GameUiView.RESET_COLOUR);
            System.out.println(BoardView.displayTiles(activePlayer.getPlayerBoard()));
            GameUiView.printPageBorder();

            //rotate tile
            boolean rotateTile = GameView.getUserConfirmation("rotate the tile you just placed");
            if (rotateTile) {
                boolean finishedRotation = false;
                while (!finishedRotation) {
                    System.out.print("Please enter the number of times you would like to rotate the tile clockwise\n> ");

                    do {
                        try {
                            int numRotations = Integer.parseInt(GameView.askUserForInput());
                            activePlayer.getRecentlyPlacedTile().rotateTile(numRotations);

                            GameView.setIsPreviousInputInvalid(false);
                        } catch (NumberFormatException ex) {
                            GameView.setPreviousInputDisallowedMessage(String.format("%sInvalid number entered! Try again.%s\n> ",
                                    GameUiView.RED_BOLD, GameUiView.RESET_COLOUR));
                        }
                    } while (GameView.isIsPreviousInputInvalid());

                    GameUiView.printPageBorder();
                    System.out.printf("%s⏺ %s ⏺\n\n%s", GameUiView.WHITE_BOLD_BRIGHT, activePlayer.getUserName(), GameUiView.RESET_COLOUR);
                    System.out.println(BoardView.displayTiles(activePlayer.getPlayerBoard()));
                    GameUiView.printPageBorder();

                    finishedRotation = !GameView.getUserConfirmation("continue rotating the tile you just placed");
                }
            }

            GameUiView.printLargeSpace();


            //place token
            GameUiView.printPageBorder();
            System.out.printf("%s⏺ %s ⏺\n\n%s", GameUiView.WHITE_BOLD_BRIGHT, activePlayer.getUserName(), GameUiView.RESET_COLOUR);
            System.out.println(BoardView.displayTiles(activePlayer.getPlayerBoard()));
            GameUiView.printPageBorder();

            //display selected token below

            boolean placeToken = GameView.getUserConfirmation("place a token");

            if (placeToken) {
                System.out.print("Please enter the coordinates for where you would like to place the token at in the format 'x, y'\n> ");
                do {
                    String tokenPlacedCoordinates = GameView.askUserForInput();
                    try {
                        int tokenXCoordinate = Integer.parseInt(tokenPlacedCoordinates.split(coordinateDelim)[0]);
                        int tokenYCoordinate = Integer.parseInt(tokenPlacedCoordinates.split(coordinateDelim)[1]);
                        HexCoordinate newTokenHexCoordinate = new HexCoordinate(tokenXCoordinate, tokenYCoordinate);

                        activePlayer.addNewToken(newTokenHexCoordinate);
                        GameView.setIsPreviousInputInvalid(false);
                    } catch (ArrayIndexOutOfBoundsException ex) {
                        GameView.setPreviousInputDisallowedMessage(String.format("%sInvalid input, please enter coordinates in the format x,y%s\n> ",
                                GameUiView.RED_BOLD, GameUiView.RESET_COLOUR));
                    } catch (NumberFormatException ex) {
                        GameView.setPreviousInputDisallowedMessage(String.format("%sInvalid input, please enter coordinates in the format x,y%s\n> ",
                                GameUiView.RED_BOLD, GameUiView.RESET_COLOUR));
                    } catch (TokenPlacedAtOccupiedPositionException ex) {
                        GameView.setPreviousInputDisallowedMessage(String.format("%sTried to place Wildlife Token on an already occupied Habitat Tile%s\n> ",
                                GameUiView.RED_BOLD, GameUiView.RESET_COLOUR));
                    } catch (TokenPlacedAtEmptyPositionException ex) {
                        GameView.setPreviousInputDisallowedMessage(String.format("%sTried to place Wildlife Token where there is no Habitat Tile%s\n> ",
                                GameUiView.RED_BOLD, GameUiView.RESET_COLOUR));
                    } catch (TokenPlacedAtIllegalTileException ex) {
                        GameView.setPreviousInputDisallowedMessage(String.format("%sThis type of Wildlife Token Type cannot be placed on this Habitat Tile%s\n> ",
                                GameUiView.RED_BOLD, GameUiView.RESET_COLOUR));
                    }
                } while (GameView.isIsPreviousInputInvalid());
            } else {
                //return token
                WildlifeTokensController.wildlifeTokenBag.add(activePlayer.getSelectedToken());
                //reset selectedToken
                activePlayer.getSelectedToken().setWildlifeTokenType(WildlifeToken.WildlifeTokenType.EMPTY);
                System.out.println("Token returned to token bag");
            }

            //quit game option
            if (GameView.getUserConfirmation("quit the game")) UserTerminationController.endProgram();

            //next player
            System.out.println("Moving to next player");
            activePlayer = playerController.cycleToNextPlayer();
            GameUiView.printLargeSpace();
        }

        //end program
        UserTerminationController.endProgram();
        GameUiView.printLargeSpace();
    }

    public static void main(String[] args) {
        start();
    }
}
