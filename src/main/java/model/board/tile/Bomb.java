package model.board.tile;

import model.board.GameBoard;
import model.board.components.Position;
import model.observers.RobotObserverAlerts;

import java.util.List;

public class Bomb extends Tile {

    boolean bomb_exploded = false;

    public Bomb(Position position){

        super(position);
        imageFilePath = "/Images/tiles/Bomb.png";;
    }
    @Override
    public void callImmediateEffect() {
        if (!bomb_exploded){
            List<Tile> neighbours = GameBoard.getInstance().getNeighbours(this.position);
            for (Tile neighbour : neighbours) {
                if (neighbour.getPosition().equals(this.getPosition())) {
                    robot.die();
                } else {
                    neighbour.getRobot().setHealth(neighbour.getRobot().getHealth() - 2);
                }
            }
            bomb_exploded = true;
            imageFilePath = "/Images/tiles/tile_tex.png";
        }
    }

    public boolean getBombExploded() { return bomb_exploded; }
}
