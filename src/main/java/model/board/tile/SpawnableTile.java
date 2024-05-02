package model.board.tile;

import model.board.components.Position;
import model.board.robot.Robot;

public class SpawnableTile extends Tile{

    protected Robot attachedRobot;
    public SpawnableTile(Position position) {
        super(position);
    }

    public SpawnableTile setAttachedRobot(Robot attachedRobot) {
        this.attachedRobot=attachedRobot;
        return this;
    }

    public Robot getAttachedRobot() {
        return attachedRobot;
    }
}
