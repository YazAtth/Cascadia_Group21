package org.grouptwentyone.models;

public class HexCoordinate {

    private int X;
    private int Y;

    public HexCoordinate(int X, int Y) {
        if (X < 0 || Y < 0) throw new IllegalArgumentException("co-ordinate cannot be negative");
        this.X = X;
        this.Y = Y;
    }

    public boolean isAdjacentToHexCoordinate(HexCoordinate o) {

        boolean isHigherOnSameLine = this.getY() % 2 == 0;
        if (isHigherOnSameLine) {
//            System.out.println(o.getX()-1);
            return (this.getX() == o.getX() && (this.getY() == o.getY()-1 || this.getY() == o.getY()+1)) ||
                    (this.getX() == o.getX()+1 && (this.getY() == o.getY()-1 || this.getY() == o.getY() || this.getY() == o.getY()+1)) ||
                    (this.getX() == o.getX()-1 && this.getY() == o.getY());
        } else {
            return (this.getX() == o.getX() && (this.getY() == o.getY()-1 || this.getY() == o.getY()+1)) ||
                    (this.getX() == o.getX()-1 && (this.getY() == o.getY()-1 || this.getY() == o.getY() || this.getY() == o.getY()+1)) ||
                    (this.getX() == o.getX()+1 && this.getY() == o.getY());
        }
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

    public boolean equals(HexCoordinate o) {
        return this.getX() == o.getX() && this.getY() == o.getY();
    }
}
