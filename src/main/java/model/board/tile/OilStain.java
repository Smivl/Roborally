package model.board.tile;

import model.board.GameBoard;
import model.board.components.Orientation;
import model.board.components.Position;

public class OilStain extends Tile{
    public OilStain(Position position) {
        super(position);
        imageFilePath = "/Images/tiles/oilstain.png";
    }

    @Override
    public void callEffect() {
        Orientation robotOrientation = robot.getOrientation();
        Orientation randomOrientation = Orientation.getRandomOrientation();
        if (robotOrientation==randomOrientation){
            callEffect();
        } else {
            robot.setOrientation(randomOrientation);
        }
    }
}
