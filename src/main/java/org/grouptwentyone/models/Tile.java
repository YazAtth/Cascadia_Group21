package org.grouptwentyone.models;

public class Tile {
    HabitatTile habitatTile;
    HexCoordinate hexCoordinate;

    boolean isActive; //this will determine whether coordinates get displayed

    public Tile (HexCoordinate hexCoordinate) {
        this.habitatTile = null; //need to switch to empty habitat tile
        this.hexCoordinate = hexCoordinate;
        isActive = false; //changes to true once the tile is adjacent to a tile that has a habitat tile
    }

    //constructor for debugging
    public Tile (HabitatTile habitatTile, HexCoordinate hexCoordinate) {
        this.habitatTile = habitatTile;
        this.hexCoordinate = hexCoordinate;
        this.isActive = true;
    }

    public HabitatTile getHabitatTile() {
        return habitatTile;
    }

    public HexCoordinate getHexCoordinate() {
        return hexCoordinate;
    }

    public boolean isActive() {
        return isActive;
    }
}
