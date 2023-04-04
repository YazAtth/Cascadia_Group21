package org.grouptwentyone.models;

public class CascadiaBot extends Player{

    public CascadiaBot(String userName) {
        super(userName);
    }

    @Override
    public boolean playTurn() {
        System.out.println("Do bot stuff");

        // Will never return false as the bot will never want to quit the game ... hopefully
        return true;
    }
}
