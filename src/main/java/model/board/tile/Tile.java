package model.board.tile;

import model.board.GameBoard;
import model.board.robot.Robot;
import model.board.components.Position;

public class Tile {

    protected Position position;
    protected Robot robot = null;
    protected TileImageFile imageFile;
    protected String imageFilePath = "/Images/tiles/tile_tex.png";

    public Tile(Position position){
        this.position = position;
    }

    public void setRobot(Robot robot) {
        this.robot = robot;
    }

    public Robot getRobot() {
        return robot;
    }

    public void callEffect(){};

    public void callImmediateEffect(){};

    public model.board.components.Position getPosition() {
        return position;
    }

    public boolean hasRobot() {
        return !(robot==null);
    }

    public String getImageFilePath() { return imageFilePath; }
}
