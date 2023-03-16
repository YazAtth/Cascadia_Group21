package org.grouptwentyone.models;

public class Tile {
    HabitatTile habitatTile;
    HexCoordinate hexCoordinate;
    boolean isActive; //this will determine whether coordinates get displayed
    int tileOrientation; // Defaults to 0 and is between 0-5 inclusive (so 6 states).

    public Tile (HexCoordinate hexCoordinate) {
        this.habitatTile = new HabitatTile();
        this.hexCoordinate = hexCoordinate;
        this.isActive = false; //changes to true once a habitat tile is placed on tile
        this.tileOrientation = 0;
    }

    //constructor for debugging
    public Tile (HabitatTile habitatTile, HexCoordinate hexCoordinate) {
        this.habitatTile = habitatTile;
        this.hexCoordinate = hexCoordinate;
        this.tileOrientation = 0;
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

    // This requires both the host tile and the argument tile to be non-empty
    public boolean isAdjacentToTile(Tile o) {

        return (!this.habitatTile.isNull() && !o.habitatTile.isNull()) &&
                this.getHexCoordinate().isAdjacentToHexCoordinate(o.getHexCoordinate());

    }

    // This requires only the argument tile to be non-empty. The host tile can be an empty tile.
    public boolean isEmptyTileAdjacentToTile(Tile o) {
        return (!o.habitatTile.isNull()) && this.getHexCoordinate().isAdjacentToHexCoordinate(o.getHexCoordinate());
    }

    public int getTileOrientation() {
        return tileOrientation;
    }

    public String toString() {
        if (this.habitatTile.isNull()) {
            return String.format("Empty Tile at %s", this.hexCoordinate);
        } else {
            return String.format("Habitat Tile at %s", this.hexCoordinate);
        }
    }
    public void rotateTile() {
        this.tileOrientation = (this.tileOrientation + 1) % 6;
    }

    //rotate tile by custom number
    public void rotateTile(int numRotations) {this.tileOrientation = (this.tileOrientation + numRotations) % 6;}
}
