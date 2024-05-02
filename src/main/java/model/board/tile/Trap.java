package model.board.tile;

import model.board.components.Position;

public class Trap extends Tile{


    public Trap(Position position) {
        super(position);
        imageFilePath = "/Images/tiles/trapdoor_tex.png";
    }

    @Override
    public void callEffect() {
        robot.setHealth(robot.getHealth()-3);
    }
}
