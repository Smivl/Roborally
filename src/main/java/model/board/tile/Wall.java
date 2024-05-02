package model.board.tile;

import model.board.components.Position;

public class Wall extends Tile{
    public Wall(Position position) {
        super(position);
        imageFilePath = "/Images/tiles/Wall.png";
    }
}
