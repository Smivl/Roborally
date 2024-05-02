package model.board.Maps.Easy;

import model.board.Maps.MapTranslator;
import model.board.Maps.Maps;
import model.board.components.Orientation;
import model.board.tile.Tile;

public class Easy_1 extends Maps {
    public Easy_1(Orientation orientation) { super(orientation); }

    @Override
    public Tile[][] generateMap() {
        return MapTranslator.csvToMap("src/main/resources/Maps/Easy_1.csv");
    }

}
