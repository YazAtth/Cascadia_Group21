
# Table of Contents
1. [Introduction](#introduction)
2. [Project](#project)
   1. [controllers](#controllers)
   2. [models](#Models)
      1. [RandomNumberGenerator](#RandomNumberGenerator)
   3. [views](#Views)



    
# Introduction
....


# Project

## Controllers

### PlayerController
 - Handles all the players in the game.
 - Keeps a list of all the players.

#### cycleToNextPlayer()
```java
    public Player cycleToNextPlayer() {
```
- Returns a player at index `n` from the ArrayList `playerList`.
- When the method is called again: it returns the player at index `n+1`.
- When the method is called after returning the player at the end of the ArrayList: it cycles back to the player at the start of the ArrayList and returns that player.
#### 

### RandomNumberGenerator


#### getRandomNumberInRange()
``` java
    public static int getRandomNumberInRange(int minInclusive, int maxInclusive)
```
- Takes in two non-decreasing integers and outputs a random number between them inclusive of the two integers passed in.
- Throws an `illegalArgumentException` if `minInclusive` is greater than `maxInclusive`.


## Models

## Views




