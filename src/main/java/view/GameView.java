package view;

import controller.GameController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.util.Duration;
import model.board.robot.Robot;
import model.observers.PlayerObserver;
import model.observers.RobotObserver;
import model.observers.WinObserver;
import model.register.cards.Card;
import view.board.robot.RobotView;
import view.elements.HealthBar;
import view.menus.PauseMenu;
import view.menus.WinMenu;
import view.observers.RobotViewObserver;
import view.player.PlayerView;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class GameView implements RobotViewObserver, RobotObserver, PlayerObserver, WinObserver {

    private final StackPane root = new StackPane();
    private final BorderPane gameUILayer = new BorderPane();
    private final PauseMenu pauseMenu = new PauseMenu();
    private final HashMap<String, HealthBar> healthBars = new HashMap<>();
    private final VBox healthBox = new VBox(15);
    private final Stage stage;

    private final Label boardUpdatingLabel = new Label("Board is updating...");
    private final Label timerLabel = new Label();
    private Timeline timer;
    private int timerDuration;
    private int timeSeconds;

    private boolean gameEnded;
    private final MediaPlayer gameBackgroundMusic = new MediaPlayer(
                                                        new Media(
                                                            new File("src/main/resources/Sounds/Automation.mp3").toURI().toString()
                                                        )
                                                    );

    private Runnable gameLoop;
    private Runnable onEndOfTurn;

    public GameView(Stage stage, GameController controller)
    {
        gameEnded = false;
        this.stage = stage;

        Image im = new Image("/Images/background3.jpg");
        root.setBackground(
                new Background(
                        new BackgroundImage(
                                im,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                new BackgroundSize(
                                        BackgroundSize.AUTO,
                                        BackgroundSize.AUTO,
                                        false,
                                        false,
                                        false,
                                        true))));


        root.getChildren().add(gameUILayer);

        pauseMenu.setStage(stage);
        pauseMenu.init(controller);

        root.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.P)){
                timer.pause();
                if (!root.getChildren().contains(pauseMenu))
                    root.getChildren().add(pauseMenu);
                else {
                    pauseMenu.setVisible(true);
                    pauseMenu.setManaged(true);
                }
            }
        });
        gameUILayer.setRight(healthBox);
        healthBox.setPadding(new Insets(20, 15,0,0));
        healthBox.setAlignment(Pos.TOP_RIGHT);
        BorderPane.setAlignment(healthBox, Pos.CENTER_RIGHT);
    }

    public void addTimer(int duration, Runnable gameLoop, Runnable onEndOfTurn)
    {
        this.timerDuration = duration;

        timerLabel.setText("Time: " + timerDuration);
        timerLabel.setFont(new Font(50));
        timerLabel.setPadding(new Insets(10, 10,0,0));
        timerLabel.getStyleClass().add("timer-label");
        gameUILayer.setTop(timerLabel);
        BorderPane.setAlignment(timerLabel, Pos.TOP_RIGHT);

        this.gameLoop = gameLoop;
        this.onEndOfTurn = onEndOfTurn;
    }
    public void startTimer()
    {
        timeSeconds = timerDuration;
        timerLabel.setText("Time: " + timeSeconds);


        // Create a Timeline to update the timer label every second
        timer = new Timeline(
                new KeyFrame(Duration.seconds(1),
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                timeSeconds--;
                                timerLabel.setText("Time: " + timeSeconds);

                                if (timeSeconds <= 0) {
                                    stopTimer();
                                }
                            }
                        }
                )
        );
        pauseMenu.setTimer(timer);
        timer.setCycleCount(Animation.INDEFINITE); // Repeat indefinitely
        timer.play(); // Start the timer
    }
    public void setTimerDuration(int duration)
    {
        timerDuration = duration;
    }
    public void stopTimer()
    {
        if(!gameEnded) {
            root.getChildren().remove(boardUpdatingLabel);
            timer.stop();
            onEndOfTurn.run();

            timerLabel.setVisible(true);

            gameLoop.run();

        }
    }
    public void addHealthBar(String playerName, Robot robot)
    {

        HealthBar healthBar = new HealthBar(playerName, robot.getHealth());
        healthBox.getChildren().add(healthBar);
        healthBars.put(robot.getName(), healthBar);

        gameBackgroundMusic.stop();
        gameUILayer.setRight(healthBox);
        healthBox.setPadding(new Insets(20, 15,0,0));
        healthBox.setAlignment(Pos.TOP_RIGHT);
        BorderPane.setAlignment(healthBox, Pos.CENTER_RIGHT);
    }

    public void showBoardUpdate() {

        boardUpdatingLabel.getStyleClass().add("CPU-selecting-cards");
        root.getChildren().add(boardUpdatingLabel);

    }

    public void onGameStart(){
        gameBackgroundMusic.setVolume(0.1);
        gameBackgroundMusic.setCycleCount(-1);
        gameBackgroundMusic.play();
    }

    @Override
    public void endGame(Robot robot){
        root.getChildren().remove(boardUpdatingLabel);
        gameBackgroundMusic.stop();
        gameEnded = true;
        timer.stop();
        root.getChildren().clear();
        stage.getScene().setRoot(new WinMenu(stage, robot.getPlayerName() + " is the winner!"));
    }
    @Override
    public void onPickingCards(List<Card> cards){
        timerLabel.setVisible(true);
    }
    @Override
    public void onComputerPickingCards(){
        timerLabel.setVisible(true);
    }
    @Override
    public void onExecutingCard(Card card){
        timerLabel.setVisible(true);
    }
    @Override
    public void onRobotViewDeath(RobotView robotView){
        healthBars.get(robotView.name).respawnHealthAnim();;
    }
    @Override
    public void updateRobotHealth(Robot robot){
        healthBars.get(robot.getName()).setHealth(robot.getHealth());
    }

    // ADDING AND HIDING NODES IN GAME VIEW
    public void addNodeToBack(Node node)
    {
        root.getChildren().addFirst(node);
    }
    public void addNodeToFront(Node node)
    {
        root.getChildren().addLast(node);
    }
    public void showPlayerView(PlayerView playerView)
    {
        root.getChildren().add(playerView.getRoot());
    }
    public void hidePlayerView(PlayerView playerView)
    {
        root.getChildren().remove(playerView.getRoot());
        playerView.setPickingCards(false);
    }

    // GETTERS

    public StackPane getRoot() { return root; }

    // UNUSED EVENTS
    @Override
    public void onAnimationDone(){
    }
    @Override
    public void onAnimationStart(){
    }
    @Override
    public void updateRobotPosition(Robot robot){}
    @Override
    public void updateRobotOrientation(Robot robot){}
    @Override
    public void updateRobotDead(Robot robot){}
    @Override
    public void updateRobotRespawn(Robot robot){}

}
