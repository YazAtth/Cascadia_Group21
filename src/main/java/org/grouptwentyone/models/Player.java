/*
 * Cascadia
 * 21: Group 21
 * Student name:            GitHub ID:
 * Yasith Atthanayake       YazAtth
 * Colm Ó hAonghusa         C0hAongha
 * Dominykas Jakubauskas    dominicjk
 */

package org.grouptwentyone.models;

import org.grouptwentyone.controllers.CullingController;
import org.grouptwentyone.controllers.NatureTokenController;
import org.grouptwentyone.controllers.WildlifeTokensController;
import org.grouptwentyone.models.Exceptions.*;
import org.grouptwentyone.views.BoardView;
import org.grouptwentyone.views.GameUiView;
import org.grouptwentyone.views.SelectionOptionsView;
import org.grouptwentyone.views.UserInputView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import static org.grouptwentyone.StartGame.selectedTiles;
import static org.grouptwentyone.StartGame.selectedTokens;

public class Player implements Comparable<Player>{

    private static int playerIdCounter = 0;

    private int playerId;
    private final String userName;
//    int Score = 0;
    PlayerBoard playerBoardObject = new PlayerBoard();

    public void setPlayerIdFromCounterAndIncrement() {
        this.playerId = playerIdCounter;
        playerIdCounter++;
    }

    public Player(String userName) {
        setPlayerIdFromCounterAndIncrement();
        this.userName = userName;
        this.getPlayerBoardObject().setupPlayerBoard();
    }

    public int compareTo(@NotNull Player p) {
            return Integer.compare(this.getScore(), p.getScore());
    }


    public String toString() {
        return String.format("\nPlayer ID: %d\nUsername: %s\nScore: %d\n\n", getPlayerId(), getUserName(), getScore());
    }

    public int getPlayerId() {
        return this.playerId;
    }

    public String getUserName() {
        return this.userName;
    }

    public int getScore() {
        return this.playerBoardObject.getScore();
    }

    public PlayerBoard getPlayerBoardObject() {
        return this.playerBoardObject;
    }

    public void spendNatureToken() {
        this.playerBoardObject.spendNatureToken();
    }

    public int getNumOfNatureTokens() {
        return this.playerBoardObject.getNumOfNatureTokens();
    }


    public boolean playTurn() {
        String coordinateDelim = ", |,";

        GameUiView.printPageBorder();
        GameUiView.printPlayerHeader(this);
        System.out.println(BoardView.displayTiles(this.getPlayerBoardObject()));

        GameUiView.printPageBorder();

        System.out.println(SelectionOptionsView.displaySelectedHabitatTiles(selectedTiles));
        System.out.println(SelectionOptionsView.displaySelectedWildlifeTokens(selectedTokens));
        System.out.println("      (1)            (2)            (3)            (4)      \n");

        //check for cull before user turn
        CullingController.checkForCull();


        //nature token options
        boolean tileSelected = false;
        if (this.getNumOfNatureTokens() > 0) {
            boolean spendToken = UserInputView.getUserConfirmation("spend a nature token");
            if (spendToken) {
                tileSelected = NatureTokenController.natureTokenSelectOption(this);
                this.spendNatureToken();
            }
        }


        //select tile/token pair if not already selected through spending a nature token
        if (!tileSelected) {
            SelectionOptionsView.selectTileAndToken(this);
        }

        GameUiView.printLargeSpace();

        //place tile
        GameUiView.printPageBorder();
        GameUiView.printPlayerHeader(this);
        System.out.println(BoardView.displayTiles(this.getPlayerBoardObject()));
        GameUiView.printPageBorder();

        //display selected tile by adding the players selected tile to a temp arraylist and passing that to displaySelectedTiles
        System.out.printf("\n%s",
                SelectionOptionsView.displaySelectedHabitatTiles(new ArrayList<>(Collections.singletonList(this.getPlayerBoardObject().getSelectedTile()))));
        GameUiView.printPageBorder();

        System.out.print("Please enter the coordinates for where you would like to place the tile at in the format 'x, y'\n> ");

        do {
            String [] tilePlacedCoordinates = UserInputView.askUserForInput().split(coordinateDelim);

            try {
                int tileXCoordinate = Integer.parseInt(tilePlacedCoordinates[0]);
                int tileYCoordinate = Integer.parseInt(tilePlacedCoordinates[1]);
                HexCoordinate newTileHexCoordinate = new HexCoordinate(tileXCoordinate, tileYCoordinate);

                this.getPlayerBoardObject().addNewTile(newTileHexCoordinate);
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
        GameUiView.printPlayerHeader(this);
        System.out.println(BoardView.displayTiles(this.getPlayerBoardObject()));
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
                        this.getPlayerBoardObject().getRecentlyPlacedTile().rotateTile(numRotations);

                        UserInputView.setIsPreviousInputInvalid(false);
                    } catch (NumberFormatException ex) {
                        UserInputView.setPreviousInputDisallowedMessage(String.format("%sInvalid number entered! Try again.%s\n> ",
                                GameUiView.RED_BOLD, GameUiView.RESET_COLOUR));
                    }
                } while (UserInputView.isIsPreviousInputInvalid());

                GameUiView.printPageBorder();
                GameUiView.printPlayerHeader(this);
                System.out.println(BoardView.displayTiles(this.getPlayerBoardObject()));
                GameUiView.printPageBorder();

                finishedRotation = !UserInputView.getUserConfirmation("continue rotating the tile you just placed");
            }
        }

        GameUiView.printLargeSpace();


        //place token
        GameUiView.printPageBorder();
        GameUiView.printPlayerHeader(this);
        System.out.println(BoardView.displayTiles(this.getPlayerBoardObject()));
        GameUiView.printPageBorder();

        boolean placeToken = false;

        //check if it's possible to place selected token and then give the player the option of placing it
        if (this.getPlayerBoardObject().canPlaceToken()) {
            //display selected token by adding the players selected token to a new arraylist that's passed to displaySelectedTokens
            System.out.printf("\n%s",
                    SelectionOptionsView.displaySelectedWildlifeTokens(new ArrayList<>(Collections.singletonList(this.getPlayerBoardObject().getSelectedToken()))));
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

                    this.getPlayerBoardObject().addNewToken(newTokenHexCoordinate);
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
            GameUiView.printPlayerHeader(this);
            System.out.println(BoardView.displayTiles(this.getPlayerBoardObject()));
            GameUiView.printPageBorder();
        } else {
            //return token
            WildlifeTokensController.wildlifeTokenBag.add(this.getPlayerBoardObject().getSelectedToken());

            //reset selectedToken
            this.getPlayerBoardObject().getSelectedToken().setWildlifeTokenType(WildlifeToken.WildlifeTokenType.EMPTY);
            System.out.println("Token returned to token bag");
        }

        //quit game option (proceeds to scoring from current game state
        System.out.print("Press enter to continue to next player's turn, or type quit to go to display score and quit\n> ");
        Scanner sc = new Scanner(System.in);
        String userInput = sc.nextLine().trim();

        GameUiView.printLargeSpace();

        if (userInput.equals("quit") || userInput.equals("exit")) {
            return false;
        }
        return true;
    }
}
