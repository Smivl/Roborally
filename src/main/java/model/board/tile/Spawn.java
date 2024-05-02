package model.board.tile;

import model.board.components.Position;

public class Spawn extends SpawnableTile{
    public boolean is_spawn = true;
    public Spawn(Position position) {
        super(position);
        imageFilePath = "/Images/tiles/start_tile_deprecated.png";
    }
}
