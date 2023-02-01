package org.grouptwentyone.models;

public class HexCoordinate {

    private int X;
    private int Y;

    public HexCoordinate(int X, int Y) {
        if (X < 0 || Y < 0) throw new IllegalArgumentException("co-ordinate cannot be negative");
        this.X = X;
        this.Y = Y;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public String toString() {
        return String.format("(%d, %d)", getX(), getY());
    }
}
