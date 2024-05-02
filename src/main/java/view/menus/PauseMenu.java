package view.menus;

import controller.GameController;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import view.elements.CustomButton;

import java.io.File;
import java.sql.Time;

public class PauseMenu extends BorderPane {
    private Timeline timer;
    private Stage stage;
    private CustomButton resumeButton;
    private CustomButton saveButton;
    private CustomButton exitButton;

    public void init(GameController controller){
        Label titleLabel = new Label("PAUSE");
        titleLabel.getStyleClass().add("subtitle-label");
        resumeButton = new CustomButton("Resume", this);
        saveButton = new CustomButton("Save and Quit", this);
        exitButton = new CustomButton("Quit", this);
        titleLabel.setAlignment(Pos.BOTTOM_CENTER);

        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(titleLabel,resumeButton,saveButton,exitButton);
        vBox.setAlignment(Pos.CENTER);
        setTop(titleLabel);
        setCenter(vBox);
        BorderPane.setAlignment(titleLabel,Pos.BOTTOM_CENTER);

        getStyleClass().add("pause-menu");



        resumeButton.setOnMouseClicked(mouseEvent -> {
            this.setVisible(false);
            this.setManaged(false);
            timer.play();
        });
        saveButton.setOnMouseClicked(mouseEvent -> {
            controller.saveGameState();
            System.exit(0);
        });
        exitButton.setOnMouseClicked(mouseEvent -> {
            System.exit(0);
        });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setTimer(Timeline timer) {
        this.timer = timer;
    }
}
