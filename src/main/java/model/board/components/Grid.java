package model.board.components;

import model.board.tile.Bomb;
import model.board.tile.Tile;

public class Grid {

    private Tile[][] matrix;
    private int sizeX;
    private int sizeY;

    public Tile getTileAt(Position position) {
        try {
            // Adjust array indexing
            return matrix[sizeY - position.getY() - 1][position.getX()];
        } catch (ArrayIndexOutOfBoundsException e) {
            // Handle out-of-bounds exception more gracefully
            System.out.println("catch");
            return null;
        }
    }

    public void setBomb(Bomb tile) {
        Position position = tile.getPosition();
        matrix[sizeY - position.getY() - 1][position.getX()] = tile;
    }

    public Tile getTileAt(int x, int y) {
        return this.getTileAt(new Position(x, y));
    }

    public void setMatrix(Tile[][] board) {
        this.matrix = board;
        this.sizeX = board[0].length;
        this.sizeY = board.length;
    }

    public int getX() {
        return sizeX;
    }

    public int getY() {
        return sizeY;
    }

    public Tile[][] getMatrix() {
        return matrix;
    }

    public void setPositionTile(Position position, Tile tile) {
        matrix[sizeY-position.getY()-1][position.getX()]=tile;
    }
}

