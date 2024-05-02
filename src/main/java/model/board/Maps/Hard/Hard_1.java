package model.board.Maps.Hard;

import model.board.Maps.MapTranslator;
import model.board.Maps.Maps;
import model.board.components.Orientation;
import model.board.tile.Tile;

import java.io.File;

public class Hard_1 extends Maps {
    public Hard_1(Orientation orientation) { super(orientation); }
    @Override
    public Tile[][] generateMap() {
        return MapTranslator.csvToMap("src/main/resources/Maps/Hard_1.csv");
    }
}