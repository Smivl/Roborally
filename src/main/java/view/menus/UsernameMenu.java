package view.menus;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import view.elements.CustomButton;
import view.elements.TextInput;

import java.io.File;
import java.util.*;

public class UsernameMenu extends StandardMenu {

    private StandardMenu nextMenu;
    private List<TextInput> textInputs = new ArrayList<>();
    private Button nextButton;
    private Label hintLabel;
    private int nPlayers;
    public UsernameMenu(Stage stage, StandardMenu parent){

        super(stage, parent);
        this.stage = stage;
        nextMenu = new MapDifficultyMenu(stage, this);
        hintLabel = new Label("All usernames must \n be different!");
        hintLabel.getStyleClass().add("hint-label");
        hintLabel.setVisible(false);

        titleLabel.setText("Multiplayer");
        titleLabel.getStyleClass().add("subtitle-label");

        Label label = new Label("Insert usernames:");
        label.getStyleClass().add("inner-label");
        buttonBox.getChildren().add(label);
        bottomPane.setLeft(hintLabel);

        for (int i=0;i<6;i+=2){
            textInputs.add(new TextInput("P" + (i + 1) + ":", "Player " + (i + 1) + "..."));
            textInputs.add( new TextInput("P"+ (i+2)+ ":", "Player " + (i+2) +"..."));
            HBox hBox = new HBox(textInputs.get(i),textInputs.get(i+1));
            hBox.setAlignment(Pos.CENTER);
            buttonBox.getChildren().add(hBox);
        }

        nextButton = new CustomButton("Next", this);
        nextButton.setDisable(true);
        CustomButton backButton = new CustomButton("Back", this );

        buttonBox.getChildren().addAll(nextButton,backButton);

        nextButton.setOnAction(event -> {
            menuContainer.getGameController().setPlayerNames(textInputs.subList(0,nPlayers).stream().map(TextInput::getInput).toList());
            goToMenu(nextMenu);
        });
        backButton.setOnAction(event -> {
            goToMenu(parent);
        });
    }

    private void updateNextState(List<TextInput> textInputs, Button button, Label hintLabel){
        List<String> usernames = textInputs.stream().map(TextInput::getInput).toList();
        System.out.println(usernames.stream().toList());
        if(usernamesAreFilled(usernames) && usernamesAreUnique(usernames)) {
            button.setDisable(false);
            hintLabel.setVisible(false);
        } else if (usernamesAreFilled(usernames)) {
            button.setDisable(true);
            hintLabel.setVisible(true);
        } else {
            button.setDisable(true);
            hintLabel.setVisible(false);
        }
    }

    private boolean usernamesAreFilled(List<String> usernames){
        boolean b1 = true;

        for (String name: usernames){
            b1 = b1 && !(name.isEmpty());
        }
        return b1;
    }

    private boolean usernamesAreUnique(List<String> usernames){
        boolean b2 = true;
        Set<String> uniqueNames = new HashSet<>();
        for (String name : usernames){
            if(!uniqueNames.add(name)){
                b2=false;
            }
        }
        return b2;
    }

    public StandardMenu accessWithPlayers(int nPlayers){
        this.nPlayers = nPlayers;
        for (TextInput textInput : textInputs){
            textInput.setVisible(true);
            textInput.setManaged(true);
        }
        for (int i=5;i>=nPlayers; i--){
            textInputs.get(i).setVisible(false);
            textInputs.get(i).setManaged(false);
        }
        for (TextInput textInput : textInputs.subList(0,nPlayers)){
            textInput.getTextField().setText("");
            textInput.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
                updateNextState(textInputs.subList(0,nPlayers), nextButton, hintLabel);
            });
        }
        return this;
    }
}
