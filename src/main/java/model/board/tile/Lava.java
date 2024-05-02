package model.board.tile;

import model.board.components.Position;

public class Lava extends Tile{

    public Lava(Position position) {
        super(position);
        imageFilePath = "/Images/tiles/Lava_4_walls.png";
    }

    @Override
    public void callEffect() {
        robot.die();
    }

    @Override
    public void callImmediateEffect() {
        robot.die();
    }
}
