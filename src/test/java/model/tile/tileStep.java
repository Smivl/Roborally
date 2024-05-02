package model.tile;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.board.GameBoard;
import model.board.robot.Robot;
import model.board.robot.RobotName;
import model.board.components.Orientation;
import model.board.components.Position;
import model.board.tile.*;
import model.register.cards.Card;
import model.register.cards.CardFactory;

import static org.junit.jupiter.api.Assertions.*;

public class tileStep {
    Robot robot;
    @And("A robot at position {int} {int} pointing {string}")
    public void aRobotAtPositionPositionXPositionYPointing(int posX, int posY, String orientation ) {
        robot = new Robot(RobotName.ASTRAL_ENFORCER, new Position(posX, posY));
        robot.setOrientation(Orientation.valueOf(orientation));
    }
    @Then("Robot is at position {int} {int}")
    public void robotIsAtPositionPositionFinalXPositionFinalY(int posX, int posY) {
        assertEquals(robot.getPosition(), new Position(posX, posY));
    }

    @Given("An initialized board of dimensions: {int} {int}")
    public void anInitializedBoardOfDimensions(int x, int y) {
        Tile[][] board = new Tile[y][x];
        for (int i=0; i<y; i++){
            for (int j=0; j<x; j++){
                System.out.println(i + " " + j + " --> " + j + " " + (y-i-1));
                board[i][j] = new Tile(new Position(j, y-i-1));
            }
        }
        GameBoard.getInstance().setGameBoard(board);
        System.out.println(board==null);
        System.out.println(GameBoard.getInstance().getBoard().getMatrix()==null);
        System.out.println(GameBoard.getInstance().toString());
    }

    @Given("A robot has checkpoint in {int} {int}")
    public void aRobotHAsCheckpointIn(int x, int y){
        Tile checkpoint = GameBoard.getInstance().getBoard().getTileAt(x,y);
        robot.setCheckpoint((SpawnableTile) checkpoint);
        assertEquals(robot.getCheckpoint().getPosition(), new Position(x, y));
    }

    @And("A robot has {int} health")
    public void aRobotHasHealthHealth(int health) {
        robot.setHealth(health);
    }

    @And("Robot health equals {int}")
    public void robotHealthEqualsFinalHealth(int health) {
        assertEquals(robot.getHealth(),health, "robot's: " + robot.getHealth() + "expected: " + health);
    }

    @And("Tile on position {int} {int} is a {string}")
    public void tileOnPositionPositionXPositionYIsA(int x, int y, String tileType) {
        Tile tile = TileFactory.create(tileType, new Position(x,y));
        GameBoard.getInstance().getBoard().setPositionTile(new Position(x,y), tile);
    }

    @When("The effect of tile at position {int} {int} is called")
    public void theEffectOfTileAtPositionPositionXPositionYIsCalled(int x, int y) {
        GameBoard.getInstance().getBoard().getTileAt(x,y).callEffect();
    }

    @And("The belt at position {int} {int} is pointing {string} with power {int}")
    public void theBeltAtPositionPositionXPositionYIsPointingWithPowerPower(int x, int y, String orientation, int power) {
        ((Belt) GameBoard.getInstance().getBoard().getTileAt(x,y)).setPower(power).setOrientation(Orientation.valueOf(orientation));
    }

    @When("The effect of tile at the robot's position is called")
    public void theEffectOfTileAtTheRobotSPositionIsCalled() {
        GameBoard.getInstance().getBoard().getTileAt(robot.getPosition()).callEffect();
    }

    @When("The card {string} is executed on the robot")
    public void theCardIsExecutedOnTheRobot(String cardType) {
        Card card = CardFactory.createCard(cardType);
        card.execute(robot);
    }

    @And("One of many robots at position {int} {int} pointing {string} named {string}")
    public void oneOfManyRobotsAtPositionRobotPositionXRobotPositionYPointingNamed(int x, int y, String orientation, String name) {
        Robot r = new Robot(RobotName.valueOf(name), new Position(x, y));
        r.setOrientation(Orientation.valueOf(orientation));
    }

    @When("The card {string} is executed on the robot at position {int} {int}")
    public void theCardIsExecutedOnTheRobotAtPositionRobotPositionXRobotPositionY(String cardType, int x, int y) {
        Card card = CardFactory.createCard(cardType);
        card.execute(GameBoard.getInstance().getBoard().getTileAt(x,y).getRobot());
    }
    @Then("Tile at position {int} {int} has robot named {string}")
    public void tileAtPositionFirstRobotFinalPositionXFirstRobotFinalPositionYHasRobotNamed(int x, int y, String name) {
        assertEquals(GameBoard.getInstance().getBoard().getTileAt(x,y).getRobot().getName(), RobotName.valueOf(name).getName());

    }

    @Then("Robot is at position {int} {int} and pointing {string}")
    public void RobotIsAtPositionAndPointing(int x, int y, String orientation){
        assertEquals(Orientation.valueOf(orientation),robot.getOrientation());
        assertEquals(robot.getPosition(),new Position(x,y));
    }

    @Then("Assert robot named {string} has checkpoint in position {int} {int}")
    public void assertRobotNamedHasCheckpointInPosition(String arg0, int x, int y) {
        assertEquals(RobotName.valueOf(arg0).getName(), ((SpawnableTile) GameBoard.getInstance().getBoard().getTileAt(x,y)).getAttachedRobot().getName());
    }

    @And("Assert robot in position {int} {int} has checkpoint in position {int} {int}")
    public void assertRobotInPositionHasCheckpointInPosition(int x1, int y1, int x2, int y2) {
        assertEquals(GameBoard.getInstance().getBoard().getTileAt(x1,y1).getRobot().getCheckpoint().getPosition(),new Position(x2,y2));
    }

    @And("Robot in position {int} {int} has checkpoint in position {int} {int}")
    public void robotInPositionHasCheckpointInPosition(int x1, int y1, int x2, int y2) {
        GameBoard.getInstance().getBoard().getTileAt(x1,y1).getRobot().setCheckpoint((SpawnableTile) GameBoard.getInstance().getBoard().getTileAt(x2,y2));
    }



    @Given("A {string} tile at position {int} {int}")
    public void aTileAtPosition(String arg0, int arg1, int arg2) {


    }
    @And("Robot's checkpoint is at {int} {int}")
    public void robotSCheckpointIsAt(int arg0, int arg1) {
        Tile checkpoint = TileFactory.create("checkpoint",new Position(arg0,arg1));
        robot.setCheckpoint((Checkpoint) checkpoint);
    }

    @Then("Assert robot named {string} has position {int} {int}")
    public void assertRobotNamedHasPosition(String arg0, int x, int y) {
        assertEquals(RobotName.valueOf(arg0).getName(), ((SpawnableTile) GameBoard.getInstance().getBoard().getTileAt(x,y)).getRobot().getName());
    }


    @And("Robot in position {int} {int} has {int} health")
    public void robotInPositionHasHealth(int arg0, int arg1, int arg2) {
        assertEquals(GameBoard.getInstance().getBoard().getTileAt(arg0,arg1).getRobot().setHealth(arg2).getHealth(),arg2);
    }

    @And("Robot in position {int} {int} equals {int} health")
    public void robotInPositionEqualsHealth(int arg0, int arg1, int arg2) {
        assertEquals(GameBoard.getInstance().getBoard().getTileAt(arg0,arg1).getRobot().getHealth(),arg2);
    }

    @Then("printposition of robot at position {string}")
    public void printpositionOfRobotAtPosition(String arg0) {

        Tile[][] board = GameBoard.getInstance().getBoard().getMatrix();
        for(Tile[] row: board)
        {
            for(Tile tile : row)
            {
                if (tile.hasRobot()){
                    tile.getRobot().getPosition();
                    System.out.println("X-------------"+tile.getRobot().getPosition().getX()+" Y--------------------"+tile.getPosition().getY());

                }
            }
        }
    }




    @Then("Robot is at position {int} {int} and not pointing {string}")
    public void robotIsAtPositionAndNotPointing(int arg0, int arg1, String arg2) {
        assertFalse(GameBoard.getInstance().getBoard().getTileAt(arg0,arg1).getRobot().getOrientation()==Orientation.valueOf(arg2));

    }


}