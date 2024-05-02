package view.menus;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import view.elements.CustomButton;

import java.io.File;

public class InitialMenu extends StandardMenu {

    private Stage stage;
    private StandardMenu nextMenu;
    private StandardMenu loadingMenu;

    public InitialMenu(Stage stage, MenuContainer menuContainer, StandardMenu parent) {
        super(stage,menuContainer, parent );
        nextMenu = new NewGameMenu(stage, this);
        loadingMenu = new LoadGameMenu(stage,this);

        this.stage = stage;
        setTop(null);

        titleLabel.setVisible(false);
        titleLabel.setManaged(false);
        ImageView imageView = new ImageView("/Images/Texts/RoboRally_title.png");
        imageView.setPreserveRatio(true);
        imageView.fitWidthProperty().bind(stage.widthProperty().multiply(0.40));
        StackPane pane = new StackPane(imageView);
        BorderPane.setAlignment(pane, Pos.BOTTOM_CENTER);

        CustomButton newGameButton = new CustomButton("New Game", this);
        CustomButton loadGameButton = new CustomButton("Load Game", this);
        CustomButton exitButton = new CustomButton("Exit", this);


        buttonBox.getChildren().addAll(pane, newGameButton, loadGameButton, exitButton);

        newGameButton.setOnAction(event -> {
            // Code for starting a new game
            goToMenu(nextMenu);
        });

        loadGameButton.setOnAction(event -> {
            // Code for loading a game
            goToMenu(loadingMenu);
            //System.out.println(" Loading game...");
        });

        exitButton.setOnAction(event -> System.exit(0));

    }

    public static void logout(Stage stage) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("You're about to log out");
        alert.setContentText("Are you sure?");

        ButtonType buttonType = alert.showAndWait().get();
        if ( buttonType == ButtonType.OK) {
            System.out.println("Logged out");

            System.exit(0);
        }
    }

}
