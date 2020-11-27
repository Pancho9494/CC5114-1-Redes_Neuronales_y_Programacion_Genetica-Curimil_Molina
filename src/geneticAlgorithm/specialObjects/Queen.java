package geneticAlgorithm.specialObjects;

import java.util.ArrayList;

/**
 * This class represents a Queen piece
 * Basically a glorified tuple
 */
public class Queen {
    private int x;
    private int y;

    public Queen(int posX, int posY){
        this.x = posX;
        this.y = posY;
    }

    /**
     * The X coordinate of the Queen
     * @return int of the coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * The Y coordinate of the Queen
     * @return int of the coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Changes the position of a Queen by changing its coordinates
     * @param newX the new X coordinate
     * @param newY the new Y coordinate
     */
    public void newCoordinates(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }
}
