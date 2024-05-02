package model.board.tile;

import model.board.components.Position;

public class Laser extends Tile{

    private boolean isHorizontal = false;

    public Laser(Position position) {
        super(position);
        imageFilePath = "/Images/tiles/Laser_horizontal.png";
    }

    public Laser setHorizontal(boolean horizontal) {
        isHorizontal = horizontal;
        if (!isHorizontal){
            imageFilePath="/Images/tiles/Laser_vertical.png";
        }
        return this;
    }

    @Override
    public void callEffect(){
        robot.die();
    }

    public void callImmediateEffect(){
        robot.setHealth(robot.getHealth()-3);
    }
}
