package model.board.Maps.Medium;

import model.board.Maps.MapTranslator;
import model.board.Maps.Maps;
import model.board.components.Orientation;
import model.board.tile.Tile;

import java.io.File;

public class Medium_3 extends Maps {
    public Medium_3(Orientation orientation) { super(orientation); }
    @Override
    public Tile[][] generateMap() {
        return MapTranslator.csvToMap("src/main/resources/Maps/Medium_3.csv");
    }
}