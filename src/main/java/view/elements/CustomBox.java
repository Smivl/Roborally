package view.elements;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Insets;
import javafx.scene.text.Text;


//Still working on this
public class CustomBox extends Pane {
    private Label label = new Label();

        public CustomBox(double setX, double setY, double setWidth, double setHeight, String LabelText) {
            {
            }

            Rectangle rectangle = new Rectangle(setX, setY, setWidth, setHeight);

            rectangle.setFill(Color.LIGHTGRAY);
            rectangle.setArcHeight(30);
            rectangle.setArcWidth(30);

            Text text = new Text(LabelText);
            text.setFill(Color.DARKGRAY);
            label.setText(LabelText);
            label.getStyleClass().add("loading-label");

            VBox vbox = new VBox();
            vbox.setPadding(new Insets(50));
            vbox.setSpacing(50);
            vbox.getChildren().addAll(text);
            this.getChildren().addAll(rectangle, vbox);


        }

        public int read() {
            return 0;
        }
    }




