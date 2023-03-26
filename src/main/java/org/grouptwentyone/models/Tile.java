/*
 * Cascadia
 * 21: Group 21
 * Student name:            GitHub ID:
 * Yasith Atthanayake       YazAtth
 * Colm Ó hAonghusa         C0hAongha
 * Dominykas Jakubauskas    dominicjk
 */

package org.grouptwentyone.models;

public class Tile {
    private static int tileIdCounter = 0;
    final private int tileId;
    HabitatTile habitatTile;
    HexCoordinate hexCoordinate;
    boolean isActive; //this will determine whether coordinates get displayed
    boolean isIncludedInScoring = true;
    int tileOrientation; // Defaults to 0 and is between 0-5 inclusive (so 6 states).

    public Tile (HexCoordinate hexCoordinate) {
        this.habitatTile = new HabitatTile();
        this.hexCoordinate = hexCoordinate;
        this.isActive = false; //changes to true once a habitat tile is placed on tile
        this.tileOrientation = 0;
        this.tileId = tileIdCounter++;
    }


    public Tile (HabitatTile habitatTile, HexCoordinate hexCoordinate) {
        this.habitatTile = habitatTile;
        this.hexCoordinate = hexCoordinate;
        this.tileOrientation = 0;
        this.isActive = true;
        this.tileId = tileIdCounter++;
    }

    //constructor for debugging
    public Tile (HexCoordinate hexCoordinate, boolean debug) {
        this.habitatTile = new HabitatTile(debug);
        this.hexCoordinate = hexCoordinate;
        this.isActive = false; //changes to true once the tile is adjacent to a tile that has a habitat tile
        this.tileOrientation = 0;
        this.tileId = tileIdCounter++;
    }

    public HabitatTile getHabitatTile() {
        return this.habitatTile;
    }

    public HexCoordinate getHexCoordinate() {
        return this.hexCoordinate;
    }

    public boolean isActive() {
        return this.isActive;
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
        return this.tileOrientation;
    }

    public String toString() {
        if (this.habitatTile.isNull()) {
            return String.format("Empty Tile at %s", this.hexCoordinate);
        } else {
            return String.format("Habitat Tile at %s", this.hexCoordinate);
        }
    }

    //rotate tile by custom number
    public void rotateTile(int numRotations) {this.tileOrientation = (this.tileOrientation + numRotations) % 6;}

    public void setIncludedInScoring(boolean includedInScoring) {
        this.isIncludedInScoring = includedInScoring;
    }

    public int getTileId() {
        return this.tileId;
    }
}
