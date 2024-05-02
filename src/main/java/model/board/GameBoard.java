package model.board;

import model.board.Maps.MapsE;
import model.board.tile.*;
import model.board.components.Grid;
import model.board.components.Orientation;
import model.board.components.Position;

import java.util.*;

public class GameBoard {

    private static GameBoard instance;
    private Grid grid;
    private List<Position> freeTeleportationTiles;
    private MapsE mapDifficulty;

    private GameBoard(){
        grid = new Grid();
    }

    public static GameBoard getInstance(){
        if (instance ==null){
            instance = new GameBoard();
        }
        return instance;
    }

    public Grid getBoard(){
        return grid;
    }

    public Position getNextPosition(Position fromPosition, Orientation towardsOrientation) {
        System.out.println("Trying to figure out what the next position from " + fromPosition + " towards " + towardsOrientation +" should be:");
        Position nextPos = calculateNextPosition(fromPosition, towardsOrientation);
        if (canMoveTo(nextPos, towardsOrientation)){
            return nextPos;
        } else {
            return fromPosition;
        }
    }

    private Position calculateNextPosition(Position position, Orientation orientation) {
        Position nextPos = null;
        switch (orientation){
            case NORTH -> nextPos = new Position(position.getX(), position.getY()+1);
            case EAST -> nextPos = new Position(position.getX()+1, position.getY());
            case WEST -> nextPos = new Position(position.getX()-1, position.getY());
            case SOUTH -> nextPos = new Position(position.getX(), position.getY()-1);
        }
        return nextPos;
    }

    public boolean canMoveTo(Position nextPosition, Orientation towardsOrientation) {
        System.out.println(nextPosition);
        if (grid.getTileAt(nextPosition)==null){ // out of the board, move is allowed (consequence death)
            System.out.println("You are moving out of the board!");
            return true;
        }
        if(!(grid.getTileAt(nextPosition).hasRobot() || grid.getTileAt(nextPosition) instanceof Wall)){
            System.out.println("The tile at " +nextPosition +" is ok!");
            return true;
        } else if (grid.getTileAt(nextPosition).hasRobot()) {
            System.out.println("The tile at " +nextPosition +" is occupied, keep trying");
            return canMoveTo(calculateNextPosition(nextPosition, towardsOrientation), towardsOrientation);
        }
        return false;
    }

    public void setGameBoard(Tile[][] gameBoard) {
        freeTeleportationTiles = new ArrayList<>();
        this.grid.setMatrix(gameBoard);

        for (Tile[] line : gameBoard){
            for (Tile tile : line){
                if (tile instanceof TeleportationTile){
                    freeTeleportationTiles.add(tile.getPosition());
                }
            }
        }
    }

    public void loadBombs(List<Bomb> bombs){
        for(Bomb bomb : bombs) {
            grid.setBomb(bomb);
        }
    }

    public boolean isPositionOnBoard(Position pos){
        return (pos.getX()>=0 && pos.getX()< getInstance().getBoard().getX() && pos.getY()>=0 && pos.getY()< getInstance().getBoard().getY());
    }

    public List<Position> getFreeTeleportationTiles() {
        return freeTeleportationTiles;
    }

    public List<Tile> getRobotTiles() {
        List<Tile> robotTiles = new ArrayList<Tile>();

        Tile[][] board = instance.getBoard().getMatrix();
        for(Tile[] row: board)
        {
            for(Tile tile : row)
            {
                if (tile.hasRobot())
                {
                    robotTiles.add(tile);
                }
            }
        }
        return robotTiles;
    }

    public <T> List<T> getTypeTiles(Class<T> c) {
        List<T> tiles = new ArrayList<>();

        Tile[][] board = instance.getBoard().getMatrix();
        for(Tile[] row: board)
        {
            for(Tile tile : row)
            {
                if (c.isInstance(tile))
                {
                    tiles.add(c.cast(tile));
                }
            }
        }
        return tiles;
    }

    public List<Tile> getNeighbours(Position position) {

        List<Tile> robotTiles = new ArrayList<>();
        int[] offset = {-1, 0, 1};
        for (int xOffset : offset) {
            for (int yOffset : offset) {
                Tile tile = GameBoard.getInstance().getBoard().getTileAt(position.getX() + xOffset, position.getY() + yOffset);
                if (tile != null && tile.hasRobot()) {
                    robotTiles.add(tile);
                }
            }


        }
        return robotTiles;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        System.out.println(grid.getMatrix()==null);
        for (Tile[] tiles : grid.getMatrix()){
            for (Tile t : tiles){
                if (t.hasRobot()) {
                    stringBuilder.append(t.getPosition().toString() + " has robot:" + t.getRobot().getName() + " ");
                } else {
                    stringBuilder.append(t.getPosition().toString() + " has no robot ");
                }
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public void setMapDifficulty(MapsE mapsE) {
        mapDifficulty = mapsE;
    }

    public MapsE getMapDifficulty() {
        return mapDifficulty;
    }
}
