package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.board.BoardController;
import controller.player.PlayerController;
import controller.save.FileOperations;
import javafx.stage.Stage;
import model.board.GameBoard;
import model.board.Maps.MapsE;
import model.board.components.Orientation;
import model.board.components.Position;
import model.board.robot.Robot;
import model.board.robot.RobotName;
import model.board.tile.*;
import model.player.ComputerPlayer;
import model.player.Player;
import model.player.RealPlayer;
import view.GameView;
import view.board.BoardView;
import view.board.robot.RobotView;
import view.player.PlayerView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;

public class GameController {

    private List<String> playerNames = new ArrayList<>();
    private int nComputerPlayers;
    private List<PlayerController> players= new ArrayList<>();
    private final GameView gameView;
    private BoardController boardController;

    private int currentPlayerIndex = 0;
    private boolean updateBoard = false;

    private final Stage stage;

    public GameController(Stage stage) {
        this.stage = stage;
        gameView = new GameView(stage, this);
    }

    // GAME STARTS
    public void startGame(){
        // Setup board
        BoardView boardView = new BoardView(stage);
        boardView.setBoard(GameBoard.getInstance().getBoard());
        boardController = new BoardController(GameBoard.getInstance(), boardView);

        // Create both real and computer players
        for(String playerName : playerNames){
            if(!playerExists(playerName)) createPlayer(playerName, false);
        }
        int repetitions = 0;
        for (int i=0; i<nComputerPlayers+repetitions;i++){
            String name = "CPU " + (i + 1);
            if (!(playerExists(name))){
                createPlayer(name, true);
            } else {
                repetitions++;
            }
        }

        // Link Win tile and GameView
        for(Win winTile : GameBoard.getInstance().getTypeTiles(Win.class)) winTile.addObserver(gameView);

        // Determine order of players
        Collections.shuffle(players);

        // Create robots for players
        List<Spawn> spawnTiles = GameBoard.getInstance().getTypeTiles(Spawn.class);
        Orientation defaultOrientation = GameBoard.getInstance().getMapDifficulty().getMap().getDefaultOrientation();
        for(int i = 0; i < players.size(); i++){

            Spawn spawnPoint = spawnTiles.get(i);

            Robot robot = new Robot(RobotName.values()[i], spawnPoint.getPosition());
            robot.setDefaultOrientation(defaultOrientation);
            robot.setOrientation(defaultOrientation);
            robot.addObserver(boardView);
            robot.addObserver(gameView);

            boardView.createRobot(robot);
            boardView.addRobot(robot);

            spawnPoint.setAttachedRobot(robot);
            robot.setCheckpoint(spawnPoint);

            PlayerController player = players.get(i);
            player.setPlayerRobot(robot);

            robot.setPlayerName(player.getPlayerName());
            gameView.addHealthBar(player.getPlayerName(), robot);
        }

        run();
    }
    public void loadGame(){

        // LOAD DATA FROM FILE
        FileOperations Fo = new FileOperations();
        Gson gson = new Gson();
        String[] data = Fo.readToStr("save_game.txt").split(";");

        // GET CURRENT PLAYER TURN AND MAP
        currentPlayerIndex = parseInt(data[data.length-1]);
        MapsE map = MapsE.valueOf(data[data.length-2]);

        // GENERATE MAP AND UPDATE IT WITH PREVIOUS MAP STATE
        GameBoard.getInstance().setGameBoard(map.getMap().generateMap());
        GameBoard.getInstance().setMapDifficulty(map);
        Type bombListType = new TypeToken<List<Bomb>>(){}.getType();
        List<Bomb> bombs = gson.fromJson(data[data.length-3], bombListType);
        for(Bomb bomb : bombs) System.out.println(bomb.getBombExploded());
        GameBoard.getInstance().loadBombs(bombs);

        // SETUP BOARD
        BoardView boardView = new BoardView(stage);
        boardView.setBoard(GameBoard.getInstance().getBoard());
        boardController = new BoardController(GameBoard.getInstance(), boardView);

        // Link Win tile and GameView
        for(Win winTile : GameBoard.getInstance().getTypeTiles(Win.class)) winTile.addObserver(gameView);

        // Create robots for players
        Orientation defaultOrientation = GameBoard.getInstance().getMapDifficulty().getMap().getDefaultOrientation();

        for(int i = 0; i < ((data.length-3)/9); i++){

            // Create player
            boolean isComputer = parseBoolean(data[i*9]);
            String playerName = data[(i * 9)+1];
            playerNames.add(playerName);
            createPlayer(playerName, isComputer);

            // Load hand cards
            String[] handCards = data[(i * 9)+2].split(",");
            String[] cardQueue = data[(i*9)+3].split(",");
            PlayerController player = findPlayer(playerName);
            player.loadCards(handCards, cardQueue);

            // Get orientation
            String name = data[(i*9)+4].toUpperCase().replace(' ', '_');
            RobotName robotName = RobotName.valueOf(name);

            // Get position
            String[] p = data[(i * 9) + 5].split(",");

            int x = parseInt(p[0]);
            int y = parseInt(p[1]);

            Position robotPosition = new Position(x, y);

            Orientation robotorientation = Orientation.valueOf(data[(i * 9)+6]);
            int health = parseInt(data[(i * 9)+7]);

            // Get spawn point
            String[] spawn = data[(i * 9)+8].split(",");
            Position spawnPosition = new Position(parseInt(spawn[0]), parseInt(spawn[1]));
            Tile tile = GameBoard.getInstance().getBoard().getTileAt(spawnPosition);

            Robot loadedRobot = new Robot(robotName, robotPosition);
            loadedRobot.setDefaultOrientation(defaultOrientation);
            loadedRobot.setOrientation(robotorientation);
            loadedRobot.setHealth(health);
            loadedRobot.setPlayerName(playerName);

            if(tile instanceof SpawnableTile sp){
                sp.setAttachedRobot(loadedRobot);
                loadedRobot.setCheckpoint(sp);
            } else{
                System.out.println(tile.getPosition());
            }

            loadedRobot.addObserver(boardView);
            loadedRobot.addObserver(gameView);

            boardView.createRobot(loadedRobot);
            boardView.addRobot(loadedRobot);

            players.get(i).setPlayerRobot(loadedRobot);
            gameView.addHealthBar(playerName, loadedRobot);
        }

       run();

    }

    // GAME LOGIC
    private void run() {
        // Link PlayerView and RobotView
        for(String name : getRobotNames()){
            RobotView robotView = boardController.getView().getRobots().get(name);
            if(robotView != null) {

                for(PlayerController player : players) {
                    if (player.getRobotName() == name) {
                        robotView.addObserver(player.getView());
                        robotView.setPlayerName(player.getPlayerName());
                    }
                }

                robotView.addObserver(gameView);
            }
        }

        // Initialize the game timer & the health bars
        gameView.addTimer(60, this::gameLoop, this::onEndOfTurn);

        // Display board and game
        gameView.addNodeToBack(boardController.getView().getRoot());
        stage.getScene().setRoot(gameView.getRoot());
        gameView.onGameStart();


        gameLoop();
    }
    private void gameLoop() {

        currentPlayerIndex %= players.size();

        if(updateBoard)
        {
            boardController.onUpdate();
            gameView.showBoardUpdate();
            gameView.setTimerDuration(3);

        } else {

            PlayerController player = players.get(currentPlayerIndex);

            player.onUpdate();
            gameView.showPlayerView(player.getView());
            if(!player.isComputer) gameView.setTimerDuration(60);
            else gameView.setTimerDuration(3);

        }

        //saveGameState();
        gameView.startTimer();
    }
    private void onEndOfTurn() {
        if(updateBoard) updateBoard = false;
        else {
            PlayerController player = players.get(currentPlayerIndex);
            player.onEndOfTurn();

            gameView.hidePlayerView(player.getView());

            // Update board on next turn if on the last player.
            if(currentPlayerIndex == players.size()-1) updateBoard = true;

            currentPlayerIndex++;
        }
    }

    // SAVING
    public void saveGameState(){
        Gson gson = new Gson();
        FileOperations Fo = new FileOperations();
        Fo.strToFile("save_game.txt", "", false);

        for (PlayerController player : players) {
            String player_str = player.toString();
            Fo.strToFile("save_game.txt", player_str, true);
        }

        String bombs = gson.toJson(GameBoard.getInstance().getTypeTiles(Bomb.class)) + ";";
        Fo.strToFile("save_game.txt", bombs, true);
        Fo.strToFile("save_game.txt", GameBoard.getInstance().getMapDifficulty().toString() + ";", true);

        // Check if current player is picking cards. If he is then return to his turn on load.
        if(players.get(currentPlayerIndex).getView().getPickingCards()) Fo.strToFile("save_game.txt", currentPlayerIndex + ";", true);
        else Fo.strToFile("save_game.txt", ++currentPlayerIndex + ";", true);
    }

    // PLAYER FUNCTIONALITIES
    private PlayerController findPlayer(String name) {
        for(PlayerController player : players)
        {
            if (Objects.equals(player.getPlayerName(), name)) return player;
        }
        return null;
    }
    private boolean playerExists(String name) {
        for(PlayerController player : players)
        {
            if (Objects.equals(player.getPlayerName(), name)) return true;
        }
        return false;
    }
    private void createPlayer(String name, boolean isComputer) {
        Player pModel = isComputer ? new ComputerPlayer(name) : new RealPlayer(name);
        PlayerView pView = new PlayerView(stage, name, gameView::stopTimer);

        pModel.addObserver(pView);
        pModel.addObserver(gameView);
        PlayerController player = new PlayerController(pModel, pView, isComputer);

        registerPlayer(player);
    }
    private void registerPlayer(PlayerController p) {
        if (!playerExists(p.getPlayerName())) {
            players.add(p);
        }
    }
    private List<String> getRobotNames() {
        List<String> names = new ArrayList<>();
        for(PlayerController playerController : players) names.add(playerController.getRobotName());
        return names;
    }

    // SETTERS AND GETTERS
    public void setPlayerNames(List<String> playerNames){
        this.playerNames = playerNames;
    }
    public List<String> getPlayerNames() {
        return playerNames;
    }
    public void setNComputerPlayers(int nComputerPlayers){
        this.nComputerPlayers = nComputerPlayers;
    }
}
