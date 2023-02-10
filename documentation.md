
# Table of Contents
1. [Introduction](#introduction)
2. [Methods](#Methods)
   1. [cycleToNextPlayer()](#cycletonextplayer--)
   2. [getRandomNumberInRange()](#getrandomnumberinrange--)
   3. [getUserActionFromInput()](#getUserActionFromInput--)
   4. [getNumberOfPlayersFromUser()](#getnumberofplayersfromuser)
3. [Classes](#classes)
   1. [PlayerController](#playercontroller)



    
# Introduction
....

# Methods

### getNumberOfPlayersFromUser()
*views.GameSetupView.getNumberOfPlayersFromUser()*
```java
    public static int getNumberOfPlayersFromUser()
```
- Returns an integer representing the number of players. This number of players should be between 2 and 4 inclusive


### cycleToNextPlayer()
*controllers.PlayerController.cycleToNextPlayer()*
```java
    public Player cycleToNextPlayer() {
```
- Returns a player at index `n` from the ArrayList `playerList`.
- When the method is called again: it returns the player at index `n+1`.
- When the method is called after returning the player at the end of the ArrayList: it cycles back to the player at the start of the ArrayList and returns that player.



### getRandomNumberInRange()
*models.RandomNumberGenerator.getRandomNumberInRange()*
``` java
    public static int getRandomNumberInRange(int minInclusive, int maxInclusive)
```
- Takes in two non-decreasing integers and outputs a random number between them inclusive of the two integers passed in.


### getUserActionFromInput()
*controllers.GameController.getUserActionFromInput()*
```java
   public static UserAction getUserActionFromInput(String userInput)
```
- Takes in a user input and returns an enum that represents the user action to be performed.
- If an invalid input is received, an 'INVALID_COMMAND' enum is returned.

### askUserForInput()
*views.GameView.askUserForInput()*
```java
public static String askUserForInput()
```
- GUI presented to user to get command from user
- 

# Classes

## PlayerController

- Contains an ArrayList of Player objects so that it can keep track of all of the players in the game.



