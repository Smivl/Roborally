package model.board.tile;

import model.board.GameBoard;
import model.board.components.Position;
import model.board.robot.Robot;

public class TeleportationTile extends Tile{

    public TeleportationTile(Position position) {
        super(position);

        imageFilePath = "/Images/tiles/TP.png";
    }

    @Override
    public void callImmediateEffect() {
        robot.teleport();
    }

    @Override
    public void setRobot(Robot robot) {
        super.setRobot(robot);
        if (robot!=null){
            GameBoard.getInstance().getFreeTeleportationTiles().remove(this.position);
        } else {
            GameBoard.getInstance().getFreeTeleportationTiles().add(this.position);
        }
    }
}
