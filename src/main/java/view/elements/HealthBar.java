package view.elements;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.util.concurrent.atomic.AtomicBoolean;

public class HealthBar extends HBox {

    private static int MAX_HEALTH = 5;
    private static final Image fullHeart = new Image("/Images/heart.png");
    private static final Image emptyHeart = new Image("/Images/empty_heart.png");

    private Label playerNameLabel;
    private boolean respawnAnim;
    private Timeline timeline;

    private int currentHealth;
    public HealthBar(String playerName, int health) {
        super(10);
        setAlignment(Pos.CENTER_RIGHT);
        playerNameLabel = new Label(playerName + " : ");
        playerNameLabel.getStyleClass().add("robot-name");
        setHealth(health);
    }

    public void setHealth(int health)
    {
        if(!respawnAnim)
        {
            if (health == 0) {
                respawnAnim = true;
            } else{
                setHealthBar(health);
            }
        }

    }

    public void respawnHealthAnim()
    {
        currentHealth = 0;
        setHealthBar(0);
        Timeline delay = new Timeline(new KeyFrame(Duration.seconds(2)));
        delay.setCycleCount(1);
        delay.play();
        respawnAnim = true;

        timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.5), event -> {
                    currentHealth++;
                    setHealthBar(currentHealth);;
                })
        );

        timeline.setCycleCount(5);

        delay.setOnFinished(e -> {
            timeline.play();

            timeline.setOnFinished(e1 -> {
                respawnAnim = false;
            });
        });
    }

    private void setHealthBar(int health)
    {
        getChildren().clear();
        getChildren().add(playerNameLabel);
        for (int i = 0; i < MAX_HEALTH; i++) {
            if (i < health) {
                ImageView heart = new ImageView(fullHeart);
                heart.setFitHeight(25);
                heart.setFitWidth(25);
                this.getChildren().add(heart);
            } else {
                ImageView heart = new ImageView(emptyHeart);
                heart.setFitHeight(25);
                heart.setFitWidth(25);
                this.getChildren().add(heart);
            }
        }
    }


}
