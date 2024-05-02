package view.board.robot;

import javafx.animation.*;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import java.io.File;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.board.components.Orientation;
import view.board.tiles.BombView;
import view.observers.RobotViewObserver;


import java.io.File;
import java.util.*;


public class RobotView extends StackPane {

    public final String name;

    private StackPane parent;
    private final Stage stage;

    private Rectangle robot;
    private Label playerName;

    private int current_angle;

    private static final int FRAME_COUNT = 28;
    private static final int FRAME_WIDTH = 222; // Width of each frame in pixels
    private static final int FRAME_HEIGHT = 222; // Height of each frame in pixels
    private static double SCALE_FACTOR = 1.4;
    private int current_frame = 0;

    private final List<RobotViewObserver> observers = new ArrayList<>();
    private final ImageView explosionSpriteSheet;
    private final Deque<Runnable> anim_queue = new ArrayDeque<Runnable>();
    private boolean playing_anim = false;

    private final Media bombExplosionSound = new Media(new File("src/main/resources/Sounds/bombExplosion.mp3").toURI().toString());
    private final Media robotExplosionSound = new Media(new File("src/main/resources/Sounds/robotExplosion.wav").toURI().toString());
    private final Media robotMoveSound = new Media(new File("src/main/resources/Sounds/robotMove.wav").toURI().toString());

    // RUNNABLE ANIMATIONS WITH PARAMETERS
    class RotationAnim implements Runnable {
        Orientation orientation;
        RotationAnim(Orientation o) { orientation = o; }
        public void run() {
            rotateTo(orientation);
        }
    }
    class MoveAnim implements Runnable {
        StackPane tile;

        MoveAnim(StackPane t) { tile = t; }
        public void run() {
            moveTo(tile);
        }
    }
    class SpawnAnim implements Runnable {
        StackPane tile;

        SpawnAnim(StackPane t) { tile = t; }
        public void run() {
            spawn(tile);
        }
    }

    public RobotView(Stage stage, String name)
    {
        super();
        this.stage = stage;
        this.name = name;

        robot = new Rectangle();

        current_angle = 0;

        explosionSpriteSheet = new ImageView(new Image("Images/explosion_2048x2048.png"));
        robot.setFill(new ImagePattern(new Image("Images/robot.png")));

        this.getChildren().add(robot);
    }

    // ANIMATIONS
    public void explode()
    {
        if(!playing_anim) {

            // Notify observers about animation starting
            for(RobotViewObserver o : observers) o.onAnimationStart();
            MediaPlayer mediaPlayer;
            if(parent instanceof BombView) {
                SCALE_FACTOR = 2;
                mediaPlayer = new MediaPlayer(bombExplosionSound);
            }
            else {
                SCALE_FACTOR = 1.4;
                mediaPlayer = new MediaPlayer(robotExplosionSound);
            }

            // Makes explosion bigger than tiles
            explosionSpriteSheet.setFitWidth(FRAME_WIDTH * SCALE_FACTOR);
            explosionSpriteSheet.setFitHeight(FRAME_HEIGHT * SCALE_FACTOR);

            explosionSpriteSheet.setViewport(new javafx.geometry.Rectangle2D(0, 0, 222, 222));

            // Sets explosion right on top of the robot
            explosionSpriteSheet.setTranslateX(stage.isFullScreen() ?
                    (parent.getLayoutX() - (stage.getWidth() / 2)) + (parent.getWidth() / 2) :
                    (parent.getLayoutX() - ((stage.getWidth() - 16) / 2)) + (parent.getWidth() / 2)
            );
            explosionSpriteSheet.setTranslateY(stage.isFullScreen() ?
                    (parent.getLayoutY() - (stage.getHeight() / 2)) + (parent.getHeight() / 2) :
                    (parent.getLayoutY() - ((stage.getHeight() - 32) / 2)) + (parent.getHeight() / 2)
            );

            if (stage.getScene().getRoot() instanceof StackPane stackPane)
                stackPane.getChildren().add(explosionSpriteSheet);

            current_frame = 0;

            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(0.04), event -> {
                        double nextFrameX = ((current_frame % 2) == 0) ? 0 : 222;
                        double nextFrameY = Math.floor(current_frame / 2.0) * 222;

                        current_frame += 1;
                        explosionSpriteSheet.setViewport(new javafx.geometry.Rectangle2D(nextFrameX, nextFrameY, 222, 222));
                        if (current_frame == 4) {
                            if(parent instanceof BombView bombView) bombView.explode();
                            parent.getChildren().remove(this);
                            for(RobotViewObserver o : observers) o.onRobotViewDeath(this);
                        }

                    })
            );

            timeline.setCycleCount(FRAME_COUNT);
            timeline.play();
            mediaPlayer.play();
            playing_anim = true;

            timeline.setOnFinished(e -> {
                if (stage.getScene().getRoot() instanceof StackPane stackPane)
                    stackPane.getChildren().remove(explosionSpriteSheet);
                current_frame = 0;
                parent = null;

                playing_anim = false;
                playNextAnim();
            });
        } else anim_queue.add(this::explode);
    }
    public void spawn(StackPane tile)
    {
        if(!playing_anim) {
            tile.getChildren().add(this);
            setParentTile(tile);

            playing_anim = false;
            playNextAnim();

        }else anim_queue.add(new SpawnAnim(tile));

    }
    public void moveTo(StackPane tile)
    {
        if(!playing_anim) {
            if(!tile.equals(parent) && parent != null && !tile.equals(parent))
            {
                for(RobotViewObserver o : observers) o.onAnimationStart();

                double x = tile.getLayoutX() - parent.getLayoutX();
                double y = tile.getLayoutY() - parent.getLayoutY();

                parent.toFront();

                MediaPlayer mediaPlayer = new MediaPlayer(robotMoveSound);
                mediaPlayer.setVolume(0.05);

                TranslateTransition translateTransition = new TranslateTransition();
                translateTransition.setDuration(Duration.seconds(1.5));
                translateTransition.setToX(x);
                translateTransition.setToY(y);
                translateTransition.setNode(this);
                translateTransition.play();
                mediaPlayer.play();

                playing_anim = true;

                translateTransition.setOnFinished(e -> {

                    parent.getChildren().remove(this);

                    mediaPlayer.stop();

                    this.setTranslateX(0);
                    this.setTranslateY(0);

                    tile.getChildren().addLast(this);
                    setParentTile(tile);

                    playing_anim = false;
                    playNextAnim();

                });
            } else playNextAnim();
        }else anim_queue.add(new MoveAnim(tile));
    }
    public void rotateTo(Orientation orientation)
    {
        if(!playing_anim) {

            int shortestPath = calculateShortestAngle(current_angle, orientation.ordinal()*90);

            if(shortestPath == 0) {
                playNextAnim();
                return;
            }

            MediaPlayer mediaPlayer = new MediaPlayer(robotMoveSound);
            mediaPlayer.setVolume(0.05);
            for(RobotViewObserver o : observers) o.onAnimationStart();

            RotateTransition rotateTransition = new RotateTransition();
            rotateTransition.setDuration(Duration.seconds(1));

            rotateTransition.setFromAngle(current_angle);
            rotateTransition.setToAngle(current_angle + shortestPath);

            rotateTransition.setNode(robot);
            rotateTransition.play();

            mediaPlayer.play();

            playing_anim = true;

            rotateTransition.setOnFinished(e -> {
                mediaPlayer.stop();
                current_angle += shortestPath;
                playing_anim = false;
                playNextAnim();
            });
        } else anim_queue.add(new RotationAnim(orientation));
    }

    // OBSERVERS
    public void addObserver(RobotViewObserver robotViewObserver){
        observers.add(robotViewObserver);
    }

    // SETTERS
    public void setRotation(Orientation o) {
        current_angle = o.ordinal()*90;
        robot.setRotate(current_angle);
    }
    public void setPlayerName(String name){
        if(name.length() > 7) playerName = new Label(name.substring(0,6));
        else playerName = new Label(name);

        this.getChildren().addLast(playerName);
        playerName.setPadding(new Insets(1));
        playerName.getStyleClass().add("player-name");
    }
    public void setParentTile(StackPane parent) {
        this.parent = parent;
        robot.widthProperty().bind(parent.widthProperty().subtract(10));
        robot.heightProperty().bind(parent.heightProperty().subtract(10));
    }
    public StackPane getParentTile() { return parent; }

    // ROTATION HELPER FUNCTIONS
    private int wrapToRange(int number, int min, int max) {
        int range = max - min;
        int wrappedNumber = (number - min) % range;
        if (wrappedNumber < 0) {
            wrappedNumber += range;
        }
        return wrappedNumber + min;
    }
    private int calculateShortestAngle(int currentAngle, int targetAngle)
    {
        int currentDirection = wrapToRange(currentAngle, -180, 180);
        int targetDirection = wrapToRange(targetAngle, -180, 180);

        int angleDifference = targetDirection - currentDirection;

        angleDifference = wrapToRange(angleDifference, -180, 180);

        if (angleDifference > 180) {
            angleDifference -= 360;
        } else if (angleDifference < -180) {
            angleDifference += 360;
        }
        return angleDifference;
    }

    private void playNextAnim(){
        if (!anim_queue.isEmpty()) anim_queue.poll().run();
        else for(RobotViewObserver o : observers) o.onAnimationDone();
    }
}
