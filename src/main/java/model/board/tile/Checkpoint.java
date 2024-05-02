package model.board.tile;

import model.board.components.Position;
import model.board.robot.Robot;

public class Checkpoint extends SpawnableTile{

    public Checkpoint(Position position) {
        super(position);
        imageFilePath = "/Images/tiles/Respawn.png";
    }
    @Override
    public void callEffect() {
        robot.setCheckpoint(this);
    }

    @Override
    public void callImmediateEffect() {
        switchCheckpoints();
    }
    public void switchCheckpoints() {
        Robot r = getRobot();
        if (getAttachedRobot() == null) {
            r.getCheckpoint().setAttachedRobot(null);
            r.setCheckpoint(this);
        } else {
            getAttachedRobot().setCheckpoint(r.getCheckpoint());
            r.setCheckpoint(this);
        }
    }


    }
