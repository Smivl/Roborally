package view.elements;

import javafx.animation.ScaleTransition;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import view.menus.StandardMenu;

public class CustomButton extends Button {

    public CustomButton(String text, Pane menu) {
        super(text);

//        // Add mouse pressed event handler
//        setOnMousePressed(event -> {
//            // Create a scale transition for the button
//            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), this);
//            scaleTransition.setToX(0.9); // Scale down to 90% of the original size
//            scaleTransition.setToY(0.9);
//            scaleTransition.play();
//        });
//
//        // Add mouse released event handler
//        setOnMouseReleased(event -> {
//            // Create a scale transition for the button
//            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), this);
//            scaleTransition.setToX(1.0); // Scale up to the original size
//            scaleTransition.setToY(1.0);
//            scaleTransition.play(); // Play the transition
//        });

        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (menu instanceof StandardMenu)
                    ((StandardMenu) menu).showLoading(true);
            }
        });

    }
}
