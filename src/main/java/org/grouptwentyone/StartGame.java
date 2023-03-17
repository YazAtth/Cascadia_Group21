package org.grouptwentyone;

import org.grouptwentyone.controllers.*;
import org.grouptwentyone.models.*;
import org.grouptwentyone.models.Exceptions.*;
import org.grouptwentyone.views.*;

import java.util.ArrayList;
import java.util.Collections;

public class StartGame {
    public static ArrayList<HabitatTile> selectedTiles = SelectionOptionsView.getFourHabitatTiles();

    public static ArrayList<WildlifeToken> selectedTokens = SelectionOptionsView.getFourWildlifeTokens();

    public static boolean tilesRemain = true;

    public static void start() {

        int numOfPlayers = GameSetupView.getNumberOfPlayersFromUser();
        ArrayList<Player> playerList = GameSetupView.getPlayerNamesFromUser(numOfPlayers);

        //remove habitat tiles depending on number of players
        int tilesToRemove = (((numOfPlayers-4)*-1)*20)+2;
        if (tilesToRemove > 0)
            HabitatTilesController.habitatTilesBag.subList(0, tilesToRemove).clear();

        PlayerController playerController = new PlayerController(playerList);
        playerController.shufflePlayerList();
        GameSetupView.displayPlayerOrder(playerList);

        Player activePlayer = playerController.getFirstPlayer();

        String coordinateDelim = ", |,";

        while (tilesRemain) {

            GameUiView.printPageBorder();

            System.out.printf("%s⏺ %s ⏺\n\n%s", GameUiView.WHITE_BOLD_BRIGHT, activePlayer.getUserName(), GameUiView.RESET_COLOUR);
            System.out.println(BoardView.displayTiles(activePlayer.getPlayerBoardObject()));

            GameUiView.printPageBorder();

            System.out.println(SelectionOptionsView.displaySelectedHabitatTiles(selectedTiles));
            System.out.println(SelectionOptionsView.displaySelectedWildlifeTokens(selectedTokens));
            System.out.println("      (1)            (2)            (3)            (4)      \n");

            //GameUiView.printPageBorder();

            //check for cull before user turn
            CullingController.checkForCull();


            //nature token options
            boolean tileSelected = false;
            if (activePlayer.getNumOfNatureTokens() > 0) {
                boolean spendToken = GameView.getUserConfirmation("spend a nature token");
                if (spendToken) {
                    tileSelected = NatureTokenController.natureTokenSelectOption(activePlayer);
                    activePlayer.spendNatureToken();
                }
            }


            //select tile/token pair if not already selected through spending a nature token
            if (!tileSelected)
                SelectionOptionsView.selectTileAndToken(activePlayer);

            GameUiView.printLargeSpace();

            //place tile
            GameUiView.printPageBorder();
            System.out.printf("%s⏺ %s ⏺\n\n%s", GameUiView.WHITE_BOLD_BRIGHT, activePlayer.getUserName(), GameUiView.RESET_COLOUR);
            System.out.println(BoardView.displayTiles(activePlayer.getPlayerBoardObject()));
            GameUiView.printPageBorder();

            //display selected tile by adding the players selected tile to a temp arraylist and passing that to displaySelectedTiles
            System.out.printf("\n%s",
                    SelectionOptionsView.displaySelectedHabitatTiles(new ArrayList<>(Collections.singletonList(activePlayer.getPlayerBoardObject().getSelectedTile()))));
            GameUiView.printPageBorder();

            System.out.print("Please enter the coordinates for where you would like to place the tile at in the format 'x, y'\n> ");

            do {
                String [] tilePlacedCoordinates = GameView.askUserForInput().split(coordinateDelim);

                try {
                    int tileXCoordinate = Integer.parseInt(tilePlacedCoordinates[0]);
                    int tileYCoordinate = Integer.parseInt(tilePlacedCoordinates[1]);
                    HexCoordinate newTileHexCoordinate = new HexCoordinate(tileXCoordinate, tileYCoordinate);

                    activePlayer.getPlayerBoardObject().addNewTile(newTileHexCoordinate);
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
            System.out.println(BoardView.displayTiles(activePlayer.getPlayerBoardObject()));
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
                            activePlayer.getPlayerBoardObject().getRecentlyPlacedTile().rotateTile(numRotations);

                            GameView.setIsPreviousInputInvalid(false);
                        } catch (NumberFormatException ex) {
                            GameView.setPreviousInputDisallowedMessage(String.format("%sInvalid number entered! Try again.%s\n> ",
                                    GameUiView.RED_BOLD, GameUiView.RESET_COLOUR));
                        }
                    } while (GameView.isIsPreviousInputInvalid());

                    GameUiView.printPageBorder();
                    System.out.printf("%s⏺ %s ⏺\n\n%s", GameUiView.WHITE_BOLD_BRIGHT, activePlayer.getUserName(), GameUiView.RESET_COLOUR);
                    System.out.println(BoardView.displayTiles(activePlayer.getPlayerBoardObject()));
                    GameUiView.printPageBorder();

                    finishedRotation = !GameView.getUserConfirmation("continue rotating the tile you just placed");
                }
            }

            GameUiView.printLargeSpace();


            //place token
            GameUiView.printPageBorder();
            System.out.printf("%s⏺ %s ⏺\n\n%s", GameUiView.WHITE_BOLD_BRIGHT, activePlayer.getUserName(), GameUiView.RESET_COLOUR);
            System.out.println(BoardView.displayTiles(activePlayer.getPlayerBoardObject()));
            GameUiView.printPageBorder();

            boolean placeToken = false;

            //check if it's possible to place selected token and then give the player the option of placing it
            if (activePlayer.getPlayerBoardObject().canPlaceToken()) {
                //display selected token by adding the players selected token to a new arraylist that's passed to displaySelectedTokens
                System.out.printf("\n%s",
                        SelectionOptionsView.displaySelectedWildlifeTokens(new ArrayList<>(Collections.singletonList(activePlayer.getPlayerBoardObject().getSelectedToken()))));
                GameUiView.printPageBorder();


                placeToken = GameView.getUserConfirmation("place a token");
            } else {
                System.out.print("Token cannot be placed on your board, therefore ");
            }

            //false by default unless user chooses to when given option
            if (placeToken) {
                System.out.print("Please enter the coordinates for where you would like to place the token at in the format 'x, y'\n> ");
                do {
                    String tokenPlacedCoordinates = GameView.askUserForInput();
                    try {
                        int tokenXCoordinate = Integer.parseInt(tokenPlacedCoordinates.split(coordinateDelim)[0]);
                        int tokenYCoordinate = Integer.parseInt(tokenPlacedCoordinates.split(coordinateDelim)[1]);
                        HexCoordinate newTokenHexCoordinate = new HexCoordinate(tokenXCoordinate, tokenYCoordinate);

                        activePlayer.getPlayerBoardObject().addNewToken(newTokenHexCoordinate);
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
                GameUiView.printPageBorder();
                System.out.printf("%s⏺ %s ⏺\n\n%s", GameUiView.WHITE_BOLD_BRIGHT, activePlayer.getUserName(), GameUiView.RESET_COLOUR);
                System.out.println(BoardView.displayTiles(activePlayer.getPlayerBoardObject()));
                GameUiView.printPageBorder();
            } else {
                //return token
                WildlifeTokensController.wildlifeTokenBag.add(activePlayer.getPlayerBoardObject().getSelectedToken());

                //reset selectedToken
                activePlayer.getPlayerBoardObject().getSelectedToken().setWildlifeTokenType(WildlifeToken.WildlifeTokenType.EMPTY);
                System.out.println("Token returned to token bag");
            }

            //quit game option
            if (GameView.getUserConfirmation("quit the game")) UserTerminationController.endProgram();

            //next player
            System.out.println("Moving to next player");
            activePlayer = playerController.cycleToNextPlayer();
            GameUiView.printLargeSpace();
        }

        System.out.println("No tiles remain so play is finished, calculating player score...");

        //end program
        UserTerminationController.endProgram();
        GameUiView.printLargeSpace();
    }

    public static void main(String[] args) {
        start();
    }
}
