package org.grouptwentyone.models;

public class HexCoordinate {

    private int X;
    private int Y;

    public HexCoordinate(int X, int Y) {
//        if (X < 0 || Y < 0) throw new IllegalArgumentException("co-ordinate cannot be negative");
        this.X = X;
        this.Y = Y;
    }

    public boolean isAdjacentToHexCoordinate(HexCoordinate o) {

        // For a given vertical line: half are on the left side of the line and half are on the right
        boolean isLeftSideOnSameVerticalLine = this.getX() % 2 == 1;

        if (isLeftSideOnSameVerticalLine) {
            return (this.getY() == o.getY() && (this.getX() == o.getX()-1 || this.getX() == o.getX()+1)) ||
                    (this.getY() == o.getY()+1 && (this.getX() == o.getX()-1 || this.getX() == o.getX() || this.getX() == o.getX()+1)) ||
                    (this.getY() == o.getY()-1 && this.getX() == o.getX());
        } else {
            return (this.getY() == o.getY() && (this.getX() == o.getX()-1 || this.getX() == o.getX()+1)) ||
                    (this.getY() == o.getY()-1 && (this.getX() == o.getX()-1 || this.getX() == o.getX() || this.getX() == o.getX()+1)) ||
                    (this.getY() == o.getY()+1 && this.getX() == o.getX());
        }
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public void setX(int x) {
        X = x;
    }

    public void setY(int y) {
        Y = y;
    }

    //removed space after comma for use in playerBoard printing
    public String toString() {
        return String.format("(%d,%d)", getX(), getY());
    }

    public boolean equals(HexCoordinate o) {
        return this.getX() == o.getX() && this.getY() == o.getY();
    }
}
