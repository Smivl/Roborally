package view.menus;

import io.cucumber.java.bs.A;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import view.elements.CustomButton;
import view.elements.CustomSlider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MultiplayerMenu extends StandardMenu{
    private StandardMenu usernameMenus;

    public MultiplayerMenu( Stage stage, StandardMenu parent){
        super (stage, parent);
        usernameMenus = new UsernameMenu(stage, this);

        titleLabel.setText("Multiplayer");
        titleLabel.getStyleClass().add("subtitle-label");

        Label label = new Label("Choose number of players:");
        label.getStyleClass().add("inner-label");
        CustomSlider slider = new CustomSlider(2,6, 2);
        CustomSlider sliderComputer = new CustomSlider(0,4,0);
        CustomButton nextButton = new CustomButton("Next", this);
        CustomButton backButton = new CustomButton("Back", this );

        buttonBox.getChildren().addAll(label,slider, sliderComputer, nextButton, backButton);
        slider.valueProperty().addListener(event ->{
            sliderComputer.setMax(6-slider.read());
        });

        nextButton.setOnAction(event -> {
            menuContainer.getGameController().setNComputerPlayers(sliderComputer.read());
            goToMenu(((UsernameMenu) usernameMenus).accessWithPlayers(slider.read()));
        });
        backButton.setOnAction(event -> goToMenu(parent));
    }
}
