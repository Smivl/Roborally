package model.board.tile;

import model.board.components.Orientation;
import model.board.components.Position;

public class RotatingGear extends Tile{

    private boolean isClockwise;

    public RotatingGear(Position position) {
        super(position);
        imageFilePath = "/Images/tiles/RotatingGear.png";
    }

    public RotatingGear setClockwise(boolean clockwise) {
        isClockwise = clockwise;
        return this;
    }

    public boolean getClockwise()
    {
        return isClockwise;
    }

    @Override
    public void callEffect() {
        if (isClockwise){
            robot.setOrientation(Orientation.next(robot.getOrientation()));
        } else {
            robot.setOrientation(Orientation.previous(robot.getOrientation()));
        }
    }
}
