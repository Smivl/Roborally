package model;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import model.board.GameBoard;
import model.board.Maps.MapsE;
import model.board.tile.*;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Map_initialization_step {
    GameBoard board = GameBoard.getInstance();
    @Given("The board is {string}")
    public void theBoardIs(String mapType) {
        board.setGameBoard(MapsE.valueOf(mapType).getMap().generateMap());
    }

    @Then("The tile in position {int} {int} is a {string}")
    public void theTileInPositionPosXPosYIsA(int x, int y,String tileType) {
        switch (tileType){
            case "belt" -> assertInstanceOf(Belt.class, board.getBoard().getTileAt(x, y));
            case "lava" -> assertInstanceOf(Lava.class, board.getBoard().getTileAt(x, y));
            case "charger" -> assertInstanceOf(Charger.class, board.getBoard().getTileAt(x, y));
            case "laser" -> assertInstanceOf(Laser.class, board.getBoard().getTileAt(x, y));
            case "win" -> assertInstanceOf(Win.class, board.getBoard().getTileAt(x, y));
            case "tile" -> assertInstanceOf(Tile.class, board.getBoard().getTileAt(x, y));
            case "wall" -> assertInstanceOf(Wall.class, board.getBoard().getTileAt(x, y));
            case "bomb" -> assertInstanceOf(Bomb.class, board.getBoard().getTileAt(x, y));
            case "teleportation" -> assertInstanceOf(TeleportationTile.class, board.getBoard().getTileAt(x, y));
            case "rotating_gear" -> assertInstanceOf(RotatingGear.class, board.getBoard().getTileAt(x, y));
        }
    }
}
