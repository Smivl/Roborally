package view.menus;

import controller.GameController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MenuContainer extends StackPane {

    private Stage stage;
    private GameController gameController;
    protected ImageView background1 = new ImageView("/Images/background1.jpg");
    protected ImageView background2 = new ImageView("/Images/background2.jpg");
    private final double SCROLL_SPEED = 0.5;

    public MenuContainer(Stage stage, GameController gameController){
        this.stage = stage;
        this.gameController = gameController;
        BorderPane backgroundLevel = getBackgroundLevel();
        getChildren().addAll(backgroundLevel, new InitialMenu(stage, this, null));
    }

    private BorderPane getBackgroundLevel() {
        background1.setPreserveRatio(true);
        background1.setFitWidth(stage.getWidth());
        background2.setPreserveRatio(true);
        background2.setFitWidth(stage.getWidth());
        stage.widthProperty().addListener((observable, oldValue, newValue) -> {
            double WIDTH = newValue.doubleValue();
            if (WIDTH>=stage.getHeight()){
                background1.setFitWidth(WIDTH);
                background2.setFitWidth(WIDTH);
            }
            if(background1.getLayoutX()<=0){
                background2.setLayoutX(background1.getLayoutX()+WIDTH);
            } else {
                background1.setLayoutX(background2.getLayoutX()+WIDTH);
            }
        });
        stage.heightProperty().addListener((observable, oldValue, newValue) -> {
            double HEIGHT = newValue.doubleValue();
            if (HEIGHT>=stage.getWidth()){
                background1.setFitHeight(HEIGHT);
                background2.setFitHeight(HEIGHT);
            }
        });

        background1.setLayoutX(0);
        background2.setLayoutX(background1.getFitWidth());
        Timeline timeline = getTimeline(SCROLL_SPEED);
        timeline.play();

        BorderPane backgroundLevel = new BorderPane();

        backgroundLevel.getChildren().addAll(background1,background2);
        return backgroundLevel;
    }

    private Timeline getTimeline(double SCROLL_SPEED) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis((double) 1000 / 60), event -> {
            // Update the positions of the background images
            background1.setLayoutX(background1.getLayoutX() - SCROLL_SPEED);
            background2.setLayoutX(background2.getLayoutX() - SCROLL_SPEED);

            // If the first image moves completely off the screen, move it to the right of the second image
            if (background1.getLayoutX() + background1.getFitWidth() <= 0) {
                background1.setLayoutX(background2.getLayoutX() + background1.getFitWidth());
            }

            // If the second image moves completely off the screen, move it to the right of the first image
            if (background2.getLayoutX() + background2.getFitWidth() <= 0) {
                background2.setLayoutX(background1.getLayoutX() + background2.getFitWidth());
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        return timeline;
    }

    public GameController getGameController() {
        return gameController;
    }
}
