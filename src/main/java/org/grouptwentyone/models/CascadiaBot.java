package org.grouptwentyone.models;

import java.util.ArrayList;

public class CascadiaBot extends Player{

    public CascadiaBot(String userName) {
        super(userName);
    }

    @Override
    public boolean playTurn() {
        System.out.println("Do bot stuff");

        ArrayList<Tile> placeableTileOptionsList = this.getPlayerBoardObject().getPlaceableTileOptionList();

//        System.out.println(placeableTileOptionsList);


        // Will never return false as the bot will never want to quit the game ... hopefully
        return true;
    }
}
