package view.menus;

import controller.GameController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import view.elements.CustomButton;

import java.io.File;

public class WinMenu extends BorderPane {
    private Stage stage;
    private CustomButton backToInitialMenu;
    private CustomButton exitButton;
    private Image im = new Image("/Images/winnerBackground.png");

    public WinMenu(Stage stage, String winnerText){

        this.stage = stage;
        setBackground(
                new Background(
                        new BackgroundImage(
                                im,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                new BackgroundSize(
                                        BackgroundSize.AUTO,
                                        BackgroundSize.AUTO,
                                        false,
                                        false,
                                        false,
                                        true))));

        Rectangle pic = new Rectangle();
        pic.setFill(new ImagePattern(new Image("/Images/winnerTrophy.png")));

        Label titleLabel = new Label(winnerText);
        titleLabel.getStyleClass().add("subtitle-label");
        backToInitialMenu = new CustomButton("Back to Start Menu", this);
        exitButton = new CustomButton("Exit Game", this);

        titleLabel.setPadding(new Insets(200, 0, 0, 0));
        setTop(titleLabel);

        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(backToInitialMenu,exitButton);
        vBox.setAlignment(Pos.CENTER);
        titleLabel.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        setCenter(vBox);
        setBottom(pic);

        backToInitialMenu.setOnMouseClicked(mouseEvent -> {
            GameController gameController = new GameController(stage);
            stage.getScene().setRoot(new MenuContainer(stage, gameController));
        });
        exitButton.setOnMouseClicked(mouseEvent -> {
            InitialMenu.logout(stage);
        });
    }
}
