package model.board.Maps;

import model.board.components.Orientation;
import model.board.tile.Tile;

public abstract class Maps {
    private final Orientation defaultOrientation;
    public Maps(Orientation orientation) { defaultOrientation = orientation; }
    public Orientation getDefaultOrientation() { return defaultOrientation; }
    public abstract Tile[][] generateMap();
}
