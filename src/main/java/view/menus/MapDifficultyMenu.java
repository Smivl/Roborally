package view.menus;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import view.elements.CustomButton;

import java.io.File;
import java.util.List;

public class MapDifficultyMenu extends StandardMenu{
    private List<String> playerNames;
    private MapSelectionMenu nextMenu;

    public MapDifficultyMenu(Stage stage, StandardMenu parent) {
        super(stage, parent);
        nextMenu = new MapSelectionMenu(stage, this);

        titleLabel.setText("Choose Map Difficulty");
        titleLabel.getStyleClass().add("subtitle-label");

        CustomButton EasyButton = new CustomButton("EASY", this);
        CustomButton MediumButton = new CustomButton("MEDIUM", this );
        CustomButton HardButton = new CustomButton("HARD", this );
        CustomButton backButton = new CustomButton("BACK",this);


        buttonBox.getChildren().addAll(EasyButton, MediumButton, HardButton,backButton);

        EasyButton.setOnAction(event -> {
            goToMenu(nextMenu.setDifficulty("EASY"));
        });
        MediumButton.setOnAction(event -> {
            goToMenu(nextMenu.setDifficulty("MEDIUM"));
        });
        HardButton.setOnAction(event -> {
            goToMenu(nextMenu.setDifficulty("HARD"));
        });
        backButton.setOnAction(event -> {
            goToMenu(parent);
        });
    }
    public MapDifficultyMenu setPlayers(List<String> playerNames){
        this.playerNames = playerNames;
        return this;
    }
}
