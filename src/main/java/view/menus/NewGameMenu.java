package view.menus;

import javafx.scene.control.Label;
import javafx.stage.Stage;
import view.elements.CustomButton;


public class NewGameMenu extends StandardMenu {
    private StandardMenu singlePlayerMenu;
    private StandardMenu multiPlayerMenu;
    private StandardMenu rumbleMenu;

    public NewGameMenu(Stage stage, StandardMenu parent){

        super(stage, parent);
        singlePlayerMenu = new SinglePlayerMenu(stage, this);
        multiPlayerMenu = new MultiplayerMenu(stage, this);
        rumbleMenu = new RumbleMenu(stage, this);

        titleLabel.setText("New Game");
        titleLabel.getStyleClass().add("subtitle-label");


        Label label = new Label("Choose game mode");
        label.getStyleClass().add("inner-label");
        CustomButton singlePlayerButton = new CustomButton("Single Player", this);
        CustomButton multiPlayerButton = new CustomButton("Multi-Player", this);
        CustomButton backButton = new CustomButton("Back", this);

        buttonBox.getChildren().addAll(label,singlePlayerButton, multiPlayerButton, backButton);
        singlePlayerButton.setOnAction(event -> {
            // Code for starting a new game
            goToMenu(singlePlayerMenu);
        });

        multiPlayerButton.setOnAction(event -> {
            // Code for loading a game
            goToMenu(multiPlayerMenu);
        });

        backButton.setOnAction(event -> goToMenu(parent));
}

}
