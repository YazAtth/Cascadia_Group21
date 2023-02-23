package org.grouptwentyone.models;

public class Tile {
    HabitatTile habitatTile;
    HexCoordinate hexCoordinate;
    boolean isActive; //this will determine whether coordinates get displayed
    int tileOrientation; // Defaults to 0 and is between 0-5 inclusive (so 6 states).

    public Tile (HexCoordinate hexCoordinate) {
        this.habitatTile = null; //need to switch to empty habitat tile
        this.hexCoordinate = hexCoordinate;
        isActive = false; //changes to true once the tile is adjacent to a tile that has a habitat tile
        this.tileOrientation = 0;
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

    public boolean isAdjacentToTile(Tile o) {

        return (this.habitatTile != null && o.habitatTile != null) &&
                this.getHexCoordinate().isAdjacentToHexCoordinate(o.getHexCoordinate());

    }

    public int getTileOrientation() {
        return tileOrientation;
    }

    public String toString() {
        if (this.habitatTile == null) {
            return String.format("Empty Tile at %s", this.hexCoordinate);
        } else {
            return String.format("Habitat Tile at %s", this.hexCoordinate);
        }
    }
    public void rotateTile() {
        this.tileOrientation = (this.tileOrientation + 1) % 6;
    }
}
