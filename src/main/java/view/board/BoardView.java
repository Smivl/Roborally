package view.board;

import io.cucumber.java.be.I;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import model.board.components.Grid;
import model.board.components.Orientation;
import model.board.components.Position;
import model.board.robot.Robot;
import model.board.robot.RobotName;
import model.board.tile.*;
import model.observers.RobotObserver;
import view.board.robot.RobotView;
import view.board.tiles.BombView;

import java.util.HashMap;


public class BoardView implements RobotObserver {

    private GridPane root;
    private Stage stage;
    private int boardSizeX;
    private int boardSizeY;


    private HashMap<String, RobotView> robots = new HashMap<>();

    public BoardView(Stage stage) {
        this.stage = stage;
        root = new GridPane();
        root.setAlignment(Pos.CENTER);
    }

    public void setBoard(Grid grid)
    {

        boardSizeX = grid.getX();
        boardSizeY = grid.getY();

        for (int i = 0; i < grid.getX(); i++) {
            for (int j = 0; j < grid.getY(); j++)
            {
                Tile tile = grid.getTileAt(i,j);

                Rectangle square = new Rectangle();
                StackPane baseTile;

                if(tile instanceof Bomb b){
                    baseTile = new BombView(b.getBombExploded());
                } else baseTile = new StackPane();

                square.widthProperty().bind(Bindings.min(stage.heightProperty().subtract(32), stage.widthProperty()).divide(boardSizeX).subtract(5));
                square.heightProperty().bind(Bindings.min(stage.heightProperty().subtract(32), stage.widthProperty()).divide(boardSizeY).subtract(5));


                if(tile instanceof Belt belt) {
                    ImageView imgV;
                    if(belt.getPower() == 1) imgV = new ImageView(new Image(belt.getImageFilePath()));
                    else imgV = new ImageView(new Image("/Images/tiles/belt_tex_2.png"));
                    Orientation o = belt.getOrientation();
                    imgV.setRotate(o.ordinal() * 90);
                    SnapshotParameters params = new SnapshotParameters();
                    Image img = imgV.snapshot(params,null);

                    square.setFill(new ImagePattern(img));
                }
                else if (tile instanceof Lava lava) {
                    Position lavaPosition = lava.getPosition();
                    Tile left = grid.getTileAt(lavaPosition.getX()-1, lavaPosition.getY());
                    Tile right = grid.getTileAt(lavaPosition.getX()+1, lavaPosition.getY());
                    Tile above = grid.getTileAt(lavaPosition.getX(), lavaPosition.getY()+1);
                    Tile below = grid.getTileAt(lavaPosition.getX(), lavaPosition.getY()-1);
                    int walls = 4;

                    boolean lava_left = false;
                    boolean lava_right = false;
                    boolean lava_top = false;
                    boolean lava_bot = false;

                    if(left instanceof Lava) {
                        walls--;
                        lava_left = true;
                    }
                    if(right instanceof Lava) {
                        walls--;
                        lava_right = true;
                    }
                    if(above instanceof Lava) {
                        walls--;
                        lava_top = true;
                    }
                    if(below instanceof Lava) {
                        walls--;
                        lava_bot = true;
                    }


                    if (walls == 0) square.setFill(new ImagePattern(new Image("/Images/tiles/Lava_no_walls.png")));
                    else if (walls == 1) {
                        Image img = new Image("/Images/tiles/Lava_1_wall.png");
                        if(!lava_left) square.setFill(new ImagePattern(img));
                        if(!lava_right) {
                            ImageView imgV = new ImageView(img);
                            imgV.setRotate(180);
                            square.setFill(new ImagePattern(imgV.snapshot(new SnapshotParameters(),null)));
                        }
                        if(!lava_top) {
                            ImageView imgV = new ImageView(img);
                            imgV.setRotate(90);
                            square.setFill(new ImagePattern(imgV.snapshot(new SnapshotParameters(),null)));
                        }
                        if(!lava_bot) {
                            ImageView imgV = new ImageView(img);
                            imgV.setRotate(-90);
                            square.setFill(new ImagePattern(imgV.snapshot(new SnapshotParameters(),null)));
                        }
                    }
                    else if (walls == 2) {
                        Image img = new Image("/Images/tiles/Lava_2_walls.png");
                        if(!lava_left) {
                            if(!lava_bot) square.setFill(new ImagePattern(img));
                            else if (!lava_right) {
                                ImageView imgV = new ImageView(new Image("/Images/tiles/Lava_2_walls_opposite.png"));
                                imgV.setRotate(90);
                                square.setFill(new ImagePattern(imgV.snapshot(new SnapshotParameters(),null)));
                            }
                            else {
                                ImageView imgV = new ImageView(img);
                                imgV.setRotate(90);
                                square.setFill(new ImagePattern(imgV.snapshot(new SnapshotParameters(),null)));
                            }
                        } else if(!lava_right) {
                            ImageView imgV = new ImageView(img);
                            if(!lava_bot){
                                imgV.setRotate(270);
                            } else {
                                imgV.setRotate(180);
                            }
                            square.setFill(new ImagePattern(imgV.snapshot(new SnapshotParameters(),null)));
                        } else {
                            square.setFill(new ImagePattern(new Image("/Images/tiles/Lava_2_walls_opposite.png")));
                        }
                    }
                    else if (walls == 3) {
                        Image img = new Image("/Images/tiles/Lava_3_walls.png");
                        if(lava_top) square.setFill(new ImagePattern(img));
                        if(lava_bot) {
                            ImageView imgV = new ImageView(img);
                            imgV.setRotate(180);
                            square.setFill(new ImagePattern(imgV.snapshot(new SnapshotParameters(),null)));
                        }
                        if(lava_right) {
                            ImageView imgV = new ImageView(img);
                            imgV.setRotate(90);
                            square.setFill(new ImagePattern(imgV.snapshot(new SnapshotParameters(),null)));
                        }
                        if(lava_left) {
                            ImageView imgV = new ImageView(img);
                            imgV.setRotate(-90);
                            square.setFill(new ImagePattern(imgV.snapshot(new SnapshotParameters(),null)));
                        }
                    }
                    else if (walls == 4) square.setFill(new ImagePattern(new Image("/Images/tiles/Lava_4_walls.png")));

                }
                else if (tile instanceof RotatingGear gear) {
                    if(gear.getClockwise()) {
                        square.setFill(new ImagePattern(new Image(tile.getImageFilePath())));
                    } else {
                        square.setFill(new ImagePattern(new Image("/Images/tiles/RotatingGearCounterClockwise.png")));
                    }
                }
                else if (tile instanceof Bomb bomb){
                    if (bomb.getBombExploded()) square.setFill(new ImagePattern(new Image("/Images/tiles/Bomb_crater.png")));
                    else square.setFill(new ImagePattern(new Image(tile.getImageFilePath())));
                }
                else {
                    square.setFill(new ImagePattern(new Image(tile.getImageFilePath())));
                }

                baseTile.getChildren().add(square);

                root.add(baseTile,i ,grid.getY()-j-1);
            }
        }
    }

    public void createRobot(Robot robot)
    {
        RobotView robotView = new RobotView(stage, robot.getName());
        robotView.setRotation(robot.getOrientation());
        robots.put(robot.getName(), robotView);
    }
    public void addRobot(Robot robot)
    {
        Position pos = robot.getPosition();
        Node tile = getNodeFromPosition(pos);

        if(tile instanceof StackPane stackTile)
        {
            /*
            if(robot.getCheckpoint() instanceof Spawn spawn){
                if(getNodeFromPosition(spawn.getPosition()) instanceof StackPane s){
                    setSpawnTile(s, robot.getName());
                }
            }
             */
            RobotView robotView = robots.get(robot.getName());
            StackPane parent = robotView.getParentTile();
            if(parent != null) parent.getChildren().remove(robotView);

            stackTile.getChildren().addLast(robotView);
            robotView.setParentTile(stackTile);
        }

    }

    @Override
    public void updateRobotPosition(Robot robot) {
        Position pos = robot.getPosition();
        Node tile = getNodeFromPosition(pos);

        if(tile instanceof StackPane stackTile) {
            RobotView robotView = robots.get(robot.getName());
            robotView.moveTo(stackTile);
        }
    }
    @Override
    public void updateRobotOrientation(Robot robot) {

        RobotView robotView = robots.get(robot.getName());
        robotView.rotateTo(robot.getOrientation());
    }
    @Override
    public void updateRobotDead(Robot robot) {

        RobotView robotView = robots.get(robot.getName());
        robotView.explode();
        Position pos = robot.getCheckpoint().getPosition();
        Node tile = getNodeFromPosition(pos);

        if(tile instanceof StackPane stackTile) {
            robotView.spawn(stackTile);
        }
    }
    @Override
    public void updateRobotRespawn(Robot robot) {
        //RobotView robotView = robots.get(robot.getName());

        //Position pos = robot.getPosition();

    }
    @Override
    public void updateRobotHealth(Robot robot) {
        //RobotView robotView = robots.get(robot.getName());
        //robotView.spawn();
    }

    public GridPane getRoot() { return root; }
    public HashMap<String, RobotView> getRobots() { return robots; }

    private Node getNodeFromPosition(Position position)
    {
        int x = position.getX();
        int y = position.getY();

        for (Node node : root.getChildren()) {

            if (GridPane.getColumnIndex(node) == x && GridPane.getRowIndex(node) == boardSizeY-y-1) {
                return node;
            }
        }
        return null;
    }

    private void setSpawnTile(StackPane tile, String name)
    {
        if(tile.getChildren().getFirst() instanceof Rectangle rectangle)
        switch (RobotName.valueOf(name.toUpperCase().replace(' ', '_'))) {
            case RobotName.TITANUS_PRIME ->
                    rectangle.setFill(new ImagePattern(new Image("/Images/tiles/p1_start_tile.png")));
            case RobotName.ECHO_VANGUARD ->
                    rectangle.setFill(new ImagePattern(new Image("/Images/tiles/p2_start_tile.png")));
            case RobotName.NOVA_SENTINEL ->
                    rectangle.setFill(new ImagePattern(new Image("/Images/tiles/p3_start_tile.png")));
            case RobotName.ZENITH_AUTOMATON ->
                    rectangle.setFill(new ImagePattern(new Image("/Images/tiles/p4_start_tile.png")));
            case RobotName.PHOENIX_STRYKER ->
                    rectangle.setFill(new ImagePattern(new Image("/Images/tiles/p5_start_tile.png")));
            case RobotName.ASTRAL_ENFORCER ->
                    rectangle.setFill(new ImagePattern(new Image("/Images/tiles/p6_start_tile.png")));
            default -> rectangle.setFill(new ImagePattern(new Image("/Images/tiles/start_tile_deprecated.png")));
        }
    }
}
