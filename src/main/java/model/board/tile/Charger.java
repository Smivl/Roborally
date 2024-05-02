package model.board.tile;

import model.board.components.Position;

public class Charger extends Tile{

    public Charger(Position position) {
        super(position);
        imageFilePath = "/Images/tiles/Charge.png";
    }

    @Override
    public void callEffect() {
        robot.setHealth(robot.getHealth()+5);
    }

    @Override
    public void callImmediateEffect() {
        robot.setHealth(robot.getHealth()+1);
    }
}
