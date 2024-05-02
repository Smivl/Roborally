package model.board.Maps.Hard;

import model.board.Maps.MapTranslator;
import model.board.Maps.Maps;
import model.board.components.Orientation;
import model.board.tile.Tile;

import java.io.File;

public class Hard_3 extends Maps {
    public Hard_3(Orientation orientation) { super(orientation); }
    @Override
    public Tile[][] generateMap() {
        return MapTranslator.csvToMap("src/main/resources/Maps/Hard_3.csv");
    }
}