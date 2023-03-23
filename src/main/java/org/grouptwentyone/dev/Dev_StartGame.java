package org.grouptwentyone.dev;

import org.grouptwentyone.StartGame;
import org.grouptwentyone.controllers.*;
import org.grouptwentyone.models.*;
import org.grouptwentyone.models.Exceptions.*;
import org.grouptwentyone.views.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Dev_StartGame {
    public static boolean tilesRemain = true;

    public static void start() {

        int numOfPlayers = 1;
        ArrayList<Player> playerList = new ArrayList<>();
        playerList.add(new Player("TON"));

        //remove habitat tiles depending on number of players
        int tilesToRemove = (((numOfPlayers-4)*-1)*20)+2;
        HabitatTilesController.habitatTilesBag.subList(0, tilesToRemove).clear();


        PlayerController playerController = new PlayerController(playerList);
        //playerController.shufflePlayerList();
        //GameSetupView.displayPlayerOrder(playerList);

        Player activePlayer = playerController.getFirstPlayer();
        String coordinateDelim = ",|, ";

        while (tilesRemain) {

            GameUiView.printPageBorder();
            System.out.printf("Player Score: %d\n",activePlayer.getPlayerBoardObject().getScore());

            GameUiView.printPlayerHeader(activePlayer);
            System.out.println(BoardView.displayTiles(activePlayer.getPlayerBoardObject()));

            GameUiView.printPageBorder();

            System.out.println(SelectionOptionsView.displaySelectedHabitatTiles(StartGame.selectedTiles));
            System.out.println(SelectionOptionsView.displaySelectedWildlifeTokens(StartGame.selectedTokens));
            System.out.println("      (1)            (2)            (3)            (4)      \n");

            //check for cull before user turn
            CullingController.checkForCull();


            //nature token options
            boolean tileSelected = false;
            if (activePlayer.getNumOfNatureTokens() > 0) {
                boolean spendToken = UserInputView.getUserConfirmation("spend a nature token");
                if (spendToken) {
                    tileSelected = NatureTokenController.natureTokenSelectOption(activePlayer);
                    activePlayer.spendNatureToken();
                }
            }


            //select tile/token pair if not already selected through spending a nature token
            if (!tileSelected) {
                SelectionOptionsView.selectTileAndToken(activePlayer);
            }

            GameUiView.printLargeSpace();

            //place tile
            GameUiView.printPageBorder();
            GameUiView.printPlayerHeader(activePlayer);
            System.out.println(BoardView.displayTiles(activePlayer.getPlayerBoardObject()));
            GameUiView.printPageBorder();

            //display selected tile by adding the players selected tile to a temp arraylist and passing that to displaySelectedTiles
            System.out.printf("\n%s",
                    SelectionOptionsView.displaySelectedHabitatTiles(new ArrayList<>(Collections.singletonList(activePlayer.getPlayerBoardObject().getSelectedTile()))));
            GameUiView.printPageBorder();

            System.out.print("Please enter the coordinates for where you would like to place the tile at in the format 'x, y'\n> ");

            do {
                String [] tilePlacedCoordinates = UserInputView.askUserForInput().split(coordinateDelim);

                try {
                    int tileXCoordinate = Integer.parseInt(tilePlacedCoordinates[0]);
                    int tileYCoordinate = Integer.parseInt(tilePlacedCoordinates[1]);
                    HexCoordinate newTileHexCoordinate = new HexCoordinate(tileXCoordinate, tileYCoordinate);

                    activePlayer.getPlayerBoardObject().addNewTile(newTileHexCoordinate);
                    UserInputView.setIsPreviousInputInvalid(false);
                } catch (ArrayIndexOutOfBoundsException ex) {
                    UserInputView.setPreviousInputDisallowedMessage(String.format("%sInvalid input, please enter coordinates in the format x,y%s\n> ",
                            GameUiView.RED_BOLD, GameUiView.RESET_COLOUR));
                } catch (NumberFormatException ex) {
                    UserInputView.setPreviousInputDisallowedMessage(String.format("%sInvalid input, please enter coordinates in the format x,y%s \n> ",
                            GameUiView.RED_BOLD, GameUiView.RESET_COLOUR));
                } catch (TilePlacedAtOccupiedPositionException ex) {
                    UserInputView.setPreviousInputDisallowedMessage(String.format("%sTile Already Exists at that position! Try again.%s\n> ",
                            GameUiView.RED_BOLD, GameUiView.RESET_COLOUR));
                } catch (TileNotPlacedAdjacentlyException ex) {
                    UserInputView.setPreviousInputDisallowedMessage(String.format("%sTile cannot be placed in a position that is not adjacent to an existing tile. Try again.%s\n> ",
                            GameUiView.RED_BOLD, GameUiView.RESET_COLOUR));
                }
            } while (UserInputView.isIsPreviousInputInvalid());

            GameUiView.printLargeSpace();

            GameUiView.printPageBorder();
            GameUiView.printPlayerHeader(activePlayer);
            System.out.println(BoardView.displayTiles(activePlayer.getPlayerBoardObject()));
            GameUiView.printPageBorder();

            //rotate tile
            boolean rotateTile = UserInputView.getUserConfirmation("rotate the tile you just placed");
            if (rotateTile) {
                boolean finishedRotation = false;
                while (!finishedRotation) {
                    System.out.print("Please enter the number of times you would like to rotate the tile clockwise\n> ");

                    do {
                        try {
                            int numRotations = Integer.parseInt(UserInputView.askUserForInput());
                            activePlayer.getPlayerBoardObject().getRecentlyPlacedTile().rotateTile(numRotations);

                            UserInputView.setIsPreviousInputInvalid(false);
                        } catch (NumberFormatException ex) {
                            UserInputView.setPreviousInputDisallowedMessage(String.format("%sInvalid number entered! Try again.%s\n> ",
                                    GameUiView.RED_BOLD, GameUiView.RESET_COLOUR));
                        }
                    } while (UserInputView.isIsPreviousInputInvalid());

                    GameUiView.printPageBorder();
                    GameUiView.printPlayerHeader(activePlayer);
                    System.out.println(BoardView.displayTiles(activePlayer.getPlayerBoardObject()));
                    GameUiView.printPageBorder();

                    finishedRotation = !UserInputView.getUserConfirmation("continue rotating the tile you just placed");
                }
            }

            GameUiView.printLargeSpace();


            //place token
            GameUiView.printPageBorder();
            GameUiView.printPlayerHeader(activePlayer);
            System.out.println(BoardView.displayTiles(activePlayer.getPlayerBoardObject()));
            GameUiView.printPageBorder();

            boolean placeToken = false;

            //check if it's possible to place selected token and then give the player the option of placing it
            if (activePlayer.getPlayerBoardObject().canPlaceToken()) {
                //display selected token by adding the players selected token to a new arraylist that's passed to displaySelectedTokens
                System.out.printf("\n%s",
                        SelectionOptionsView.displaySelectedWildlifeTokens(new ArrayList<>(Collections.singletonList(activePlayer.getPlayerBoardObject().getSelectedToken()))));
                GameUiView.printPageBorder();

                placeToken = UserInputView.getUserConfirmation("place a token");
            } else {
                System.out.print("Token cannot be placed on your board, therefore ");
            }

            //false by default unless user chooses to when given option
            if (placeToken) {
                System.out.print("Please enter the coordinates for where you would like to place the token at in the format 'x, y'\n> ");
                do {
                    String tokenPlacedCoordinates = UserInputView.askUserForInput();
                    try {
                        int tokenXCoordinate = Integer.parseInt(tokenPlacedCoordinates.split(coordinateDelim)[0]);
                        int tokenYCoordinate = Integer.parseInt(tokenPlacedCoordinates.split(coordinateDelim)[1]);
                        HexCoordinate newTokenHexCoordinate = new HexCoordinate(tokenXCoordinate, tokenYCoordinate);

                        activePlayer.getPlayerBoardObject().addNewToken(newTokenHexCoordinate);
                        UserInputView.setIsPreviousInputInvalid(false);
                    } catch (ArrayIndexOutOfBoundsException ex) {
                        UserInputView.setPreviousInputDisallowedMessage(String.format("%sInvalid input, please enter coordinates in the format x,y%s\n> ",
                                GameUiView.RED_BOLD, GameUiView.RESET_COLOUR));
                    } catch (NumberFormatException ex) {
                        UserInputView.setPreviousInputDisallowedMessage(String.format("%sInvalid input, please enter coordinates in the format x,y %s\n> ",
                                GameUiView.RED_BOLD, GameUiView.RESET_COLOUR));
                    } catch (TokenPlacedAtOccupiedPositionException ex) {
                        UserInputView.setPreviousInputDisallowedMessage(String.format("%sTried to place Wildlife Token on an already occupied Habitat Tile%s\n> ",
                                GameUiView.RED_BOLD, GameUiView.RESET_COLOUR));
                    } catch (TokenPlacedAtEmptyPositionException ex) {
                        UserInputView.setPreviousInputDisallowedMessage(String.format("%sTried to place Wildlife Token where there is no Habitat Tile%s\n> ",
                                GameUiView.RED_BOLD, GameUiView.RESET_COLOUR));
                    } catch (TokenPlacedAtIllegalTileException ex) {
                        UserInputView.setPreviousInputDisallowedMessage(String.format("%sThis type of Wildlife Token Type cannot be placed on this Habitat Tile%s\n> ",
                                GameUiView.RED_BOLD, GameUiView.RESET_COLOUR));
                    }
                } while (UserInputView.isIsPreviousInputInvalid());
                GameUiView.printPageBorder();
                GameUiView.printPlayerHeader(activePlayer);
                System.out.println(BoardView.displayTiles(activePlayer.getPlayerBoardObject()));
                GameUiView.printPageBorder();
            } else {
                //return token
                WildlifeTokensController.wildlifeTokenBag.add(activePlayer.getPlayerBoardObject().getSelectedToken());

                //reset selectedToken
                activePlayer.getPlayerBoardObject().getSelectedToken().setWildlifeTokenType(WildlifeToken.WildlifeTokenType.EMPTY);
                System.out.println("Token returned to token bag");
            }

            //quit game option (proceeds to scoring from current game state
            System.out.print("Press enter to continue to next player's turn, or type quit to go to display score and quit\n> ");
            Scanner sc = new Scanner(System.in);
            String userInput = sc.nextLine().trim();
            if (userInput.equals("quit") || userInput.equals("exit")) {
                break;
            }

            //next player
            System.out.println("Moving to next player");
            activePlayer = playerController.cycleToNextPlayer();
            GameUiView.printLargeSpace();
        }
        System.out.println("No tiles remain so play is finished, calculating player score...");

        playerController.tallyUpAllScores();
        ScoreDisplayView.displayScorePage(playerController);

//        WildlifeToken.WildlifeTokenType scoreToken = ScoringCards.getScoreCardsList().get(0).getTokenType();
//        switch (scoreToken) {
//            case BEAR:
//                switch (ScoringCards.getScoreCardsList().get(0).getScoreType()) {
//                    case A: //call scoreBearTypeA()
//                        break;
//                    case B: //call scoreBearTypeB()
//                        break;
//                }
//        }

        //end program
        UserTerminationController.endProgram();
        GameUiView.printLargeSpace();
    }

    public static void main(String[] args) {
        start();
    }
}
