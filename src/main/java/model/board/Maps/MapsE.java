package model.board.Maps;

import model.board.Maps.Easy.*;
import model.board.Maps.Hard.*;
import model.board.Maps.Hard.Hard_1;
import model.board.Maps.Medium.*;
import model.board.Maps.Medium.Medium_1;
import model.board.components.Orientation;

public enum MapsE {
    EASY_1(new Easy_1(Orientation.NORTH)), EASY_2(new Easy_2(Orientation.NORTH)), EASY_3(new Easy_3(Orientation.WEST)),
    MEDIUM_1(new Medium_1(Orientation.WEST)), MEDIUM_2(new Medium_2(Orientation.SOUTH)), MEDIUM_3(new Medium_3(Orientation.NORTH)),
    HARD_1(new Hard_1(Orientation.NORTH)), HARD_2(new Hard_2(Orientation.EAST)), HARD_3(new Hard_3(Orientation.SOUTH));


    private static MapsE[] mapsArray = MapsE.values(); // Array containing all maps
    private Maps map;

    private MapsE(Maps map){
        this.map = map;
    }

    public Maps getMap() {
        return map;
    }

}