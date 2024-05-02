package model.board.robot;

import javafx.geometry.Pos;
import model.board.components.Grid;
import model.observers.RobotObserverAlerts;
import model.board.GameBoard;
import model.board.components.Orientation;
import model.board.components.Position;
import model.board.tile.SpawnableTile;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import model.observers.RobotObserver;

import javax.swing.text.StyledEditorKit;

public class Robot {

    private String playerName;
    private int health = 5;
    private SpawnableTile checkpoint;
    private Position position;
    private String name;
    private Orientation orientation = Orientation.NORTH;
    private Orientation defaultOrientation = Orientation.NORTH;
    private Set<RobotObserver> observers = new HashSet<>();

    private boolean canMove = true;

    public Robot(RobotName robotName, Position pos){
        name = robotName.getName();
        position = pos;
        GameBoard.getInstance().getBoard().getTileAt(pos).setRobot(this);
    }

    /////////// HEALTH / KILL FUNCTION //////////
    public Robot setHealth(int health){ // Also works as constructor
        this.health = Math.max(Math.min(health,10), 0); // 0<=health<=10
        alertObservers(RobotObserverAlerts.HEALTH);
        if (this.health==0) { // health is 0, die and respawn
            alertObservers(RobotObserverAlerts.DEAD);
            respawn();
            alertObservers(RobotObserverAlerts.RESPAWN);
        }
        return this;
    }

    public void die(){
        setHealth(0);
    }

    private void respawn() {
        canMove = false;
        setOrientation(defaultOrientation); // set default orientation NORTH
        if (checkpoint.hasRobot() && checkpoint.getRobot()!=this) checkpoint.getRobot().die();
        forceMoveToPosition(checkpoint.getPosition(),orientation); // sets the position to the checkpoint
        setHealth(5); // resets the health
    }

    ////////// MOVING FUNCTIONS //////////

    private void forceMoveToPosition(Position nextPosition,Orientation orientation) { // Moves robot to position without any validity check
        Grid board = GameBoard.getInstance().getBoard();

        if (!GameBoard.getInstance().isPositionOnBoard(nextPosition)){
            board.getTileAt(position).setRobot(null);
            die();
            return;
        }
        if (board.getTileAt(nextPosition).hasRobot()){
            Robot robotInFront = board.getTileAt(nextPosition).getRobot();
            Position newNextPosition = GameBoard.getInstance().getNextPosition(nextPosition,orientation);
            robotInFront.forceMoveToPosition(newNextPosition,orientation);
        }
        board.getTileAt(this.position).setRobot(null); // tells the tile that it's moving
        this.position = nextPosition;
        board.getTileAt(this.position).setRobot(this); // tells the new tile that a robot is on it
        alertObservers(RobotObserverAlerts.POSITION);
        board.getTileAt(position).callImmediateEffect();
    }

    public void teleport(){
        GameBoard.getInstance().getBoard().getTileAt(this.position).setRobot(null);
        this.position = GameBoard.getInstance().getFreeTeleportationTiles().getFirst();
        GameBoard.getInstance().getBoard().getTileAt(this.position).setRobot(this);
        alertObservers(RobotObserverAlerts.POSITION);
    }

    /**
     * Moves a robot to the desired position while pushing other robots away in case there are any.
     *
     * @param position The position to move the robot to.
     * @param orientation The direction in which the robot is moving.
     * @return FALSE if the robot died while moving, TRUE otherwise.
     */
    public boolean moveToPosition(Position position, Orientation orientation) { // tries to move a robot to a position
        canMove = true;
        if (this.position==position) {
            return true;
        }
        if (GameBoard.getInstance().canMoveTo(position, orientation)) { // checks if it can move (walls or players)
            //Position nextPos = GameBoard.getInstance().getNextPosition(position, orientation);
            forceMoveToPosition(position, orientation);
        }
        return canMove;
    }


    /**
     *
     * @param i 1 if the step is forwards, two if the step is backwards
     * @return FALSE if robot died while taking the step, TRUE otherwise
     */
    public boolean takeStep(int i){ // returns false if the robot dies when taking the step
        boolean b = false;// takes input 1 or -1: tries to take a step forward or backwards
        if (i==1)
            b = moveToPosition(GameBoard.getInstance().getNextPosition(position,orientation), orientation);
        else if (i==-1)
            b = moveToPosition(GameBoard.getInstance().getNextPosition(position,Orientation.opposite(orientation)), Orientation.opposite(orientation));
        return b;
    }

    ///////////// BUILDER PATTERN //////////

    public Robot setCheckpoint(SpawnableTile checkpoint) {
        checkpoint.setAttachedRobot(this);
        this.checkpoint = checkpoint;
        return this;
    }

    public Robot setOrientation(Orientation orientation) {
        this.orientation = orientation;
        alertObservers(RobotObserverAlerts.ORIENTATION);
        return this;
    }

    public void setDefaultOrientation(Orientation orientation){
        defaultOrientation = orientation;
    }

    //////////////// OBSERVER PATTERN ALERT FUNCTION /////////////
    public void alertObservers(RobotObserverAlerts alert){
        switch (alert){
            case POSITION -> {
                for(RobotObserver o: observers) o.updateRobotPosition(this);
           }
            case HEALTH -> {
                for(RobotObserver o: observers) o.updateRobotHealth(this);
            }
            case DEAD -> {
                for(RobotObserver o: observers) o.updateRobotDead(this);
            }
            case RESPAWN -> {
                for(RobotObserver o: observers) o.updateRobotRespawn(this);
            }
            case ORIENTATION -> {
               for(RobotObserver o: observers) o.updateRobotOrientation(this);
            }
       }
    }

    ////////////////// GETTERS //////////////

    public void setPlayerName(String name) {
        playerName = name;
    }
    public String getPlayerName() {
        return playerName;
    }

    public int getHealth() { return health; }

    public Orientation getOrientation() {
        return orientation;
    }

    public String getName() {
        return name;
    }

    public Position getPosition() {
        return position;
    }

    public SpawnableTile getCheckpoint() {
        return checkpoint;
    }

    public void addObserver(RobotObserver o){
        observers.add(o);
    }

    @Override
    public String toString() {
        return name + ";"
                + position + ";"
                + orientation.toString() + ";"
                + health + ";"
                + checkpoint.getPosition() + ";";
    }
}
