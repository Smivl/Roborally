package model.board.tile;

import model.board.components.Position;
import model.observers.WinObserver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Win extends Tile{
    private List<WinObserver> gameObservers = new ArrayList<>();

    public Win(Position position) {
        super(position);
        imageFilePath = "/Images/tiles/Win.png";
    }

    @Override
    public void callEffect() {
        for (WinObserver winObserver: gameObservers){
            winObserver.endGame(robot);
        }
    }

    public void addObserver(WinObserver o) {
        gameObservers.add(o);
    }
}
