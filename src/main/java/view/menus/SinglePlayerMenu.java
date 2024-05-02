package view.menus;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import view.elements.CustomButton;
import view.elements.CustomSlider;
import view.elements.TextInput;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class SinglePlayerMenu extends StandardMenu{
    private Stage stage;
    private StandardMenu nextMenu;
    public SinglePlayerMenu(Stage stage, StandardMenu parent){
        super (stage, parent);
        nextMenu = new MapDifficultyMenu(stage, this);
        System.out.println("hello");

        titleLabel.setText("Single Player");
        titleLabel.getStyleClass().add(("subtitle-label"));

        Label label = new Label("Insert username and number of computer players:");
        label.getStyleClass().add("inner-label");
        TextInput input = new TextInput("Username: ", "Type here...");
        CustomSlider slider = new CustomSlider(1,5,1);
        CustomButton nextButton = new CustomButton("Next", this);
        nextButton.setDisable(true);
        CustomButton backButton = new CustomButton("Back", this);
        input.getTextField().textProperty().addListener(event -> {
            if (input.getInput().isEmpty()){
                nextButton.setDisable(true);
            } else {
                nextButton.setDisable(false);
            }
        });

        buttonBox.getChildren().addAll(label, input, slider, nextButton,backButton);
        backButton.setOnAction(event -> {
            goToMenu(parent);
        });
        nextButton.setOnAction(event -> {
            menuContainer.getGameController().setPlayerNames(List.of(input.getInput()));
            menuContainer.getGameController().setNComputerPlayers(slider.read());
            goToMenu(nextMenu);
        });
    }
}
