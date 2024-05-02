package controller.board;

import model.board.GameBoard;
import model.board.tile.Tile;
import view.board.BoardView;

import java.util.List;

public class BoardController {

    private GameBoard gameBoard;
    private BoardView boardView;

    public BoardController(GameBoard model, BoardView view)
    {
        gameBoard = model;
        boardView = view;
    }

    public void onUpdate()
    {
        List<Tile> robotTiles = gameBoard.getRobotTiles();
        for(Tile tile : robotTiles) tile.callEffect();
    }

    public BoardView getView() { return boardView; }
}
