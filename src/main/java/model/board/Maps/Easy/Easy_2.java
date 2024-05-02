package model.board.Maps.Easy;

import model.board.Maps.MapTranslator;
import model.board.Maps.Maps;
import model.board.components.Orientation;
import model.board.components.Position;
import model.board.tile.*;

public class Easy_2 extends Maps {
    public Easy_2(Orientation orientation) { super(orientation); }
    @Override
    public Tile[][] generateMap(){
            return MapTranslator.csvToMap("src/main/resources/Maps/Easy_2.csv");
        }
}
