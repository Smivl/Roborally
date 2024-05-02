package view.menus;

import controller.GameController;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.board.GameBoard;
import model.board.Maps.MapsE;
import view.elements.CustomButton;

import java.io.File;
import java.util.List;

public class MapSelectionMenu extends StandardMenu {
    private String difficulty;
    private CustomButton map1Button;
    private CustomButton map2Button;
    private CustomButton map3Button;
    public MapSelectionMenu(Stage stage, StandardMenu parent) {
        super(stage, parent);

        titleLabel.setText("Map Selection");
        titleLabel.getStyleClass().add("subtitle-label");

        map1Button = new CustomButton("", this);
        map2Button = new CustomButton("", this);
        map3Button = new CustomButton("",this);

        CustomButton backButton = new CustomButton("Back", this);
        buttonBox.getChildren().addAll(map1Button,map2Button,map3Button, backButton);


        map1Button.setOnAction(event -> {
            GameBoard.getInstance().setGameBoard(MapsE.valueOf(difficulty+"_1").getMap().generateMap());
            GameBoard.getInstance().setMapDifficulty(MapsE.valueOf(difficulty+"_1"));
            menuContainer.getGameController().startGame();
        });
        map2Button.setOnAction(event ->{
            GameBoard.getInstance().setGameBoard(MapsE.valueOf(difficulty+"_2").getMap().generateMap());
            GameBoard.getInstance().setMapDifficulty(MapsE.valueOf(difficulty+"_2"));
            menuContainer.getGameController().startGame();
        });
        map3Button.setOnAction(event ->{
            GameBoard.getInstance().setGameBoard(MapsE.valueOf(difficulty+"_3").getMap().generateMap());
            GameBoard.getInstance().setMapDifficulty(MapsE.valueOf(difficulty+"_3"));
            menuContainer.getGameController().startGame();
        });
        backButton.setOnAction(event -> goToMenu(parent));
        //backButton.setOnAction(event -> stage.getScene().setRoot(new MapDifficultyMenu(stage,background1.getLayoutX(),players)));
    }

    public StandardMenu setDifficulty(String difficulty) {
        this.difficulty = difficulty;
        map1Button.setText(difficulty+"-1");
        map2Button.setText(difficulty+"-2");
        map3Button.setText(difficulty+"-3");
        return this;
    }
}
