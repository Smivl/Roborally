package view.elements;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;


public class TextInput extends HBox {

    private Label label = new Label();
    private TextField input = new TextField();

    public TextInput(String labelText, String promptText){

        label.setText(labelText);
        label.getStyleClass().add("textInput-label");
        input.setPromptText(promptText);
        input.getStyleClass().add("textInput-text");

        getChildren().addAll(label, input);

        setAlignment(Pos.CENTER);
    }

    public String getInput(){
        return input.getText();
    }

    public TextField getTextField() { return input;}

}
