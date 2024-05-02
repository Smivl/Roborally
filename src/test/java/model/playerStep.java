package model;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.board.components.Orientation;
import model.board.components.Position;
import model.board.robot.Robot;
import model.board.robot.RobotName;
import model.player.ComputerPlayer;
import model.player.Player;
import model.player.RealPlayer;
import model.register.cards.CardFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class playerStep {
    Player player;
    @And("A real player called {string}")
    public void aRealPlayerCalled(String name) {
        player = new RealPlayer(name);
    }

    @And("Player has card {string} in the queue")
    public void playerHasCardInTheQueue(String cardType) {
        player.queueCard(CardFactory.createCard(cardType));
    }

    @And("Player has robot in position {int} {int} pointing {string}")
    public void playerHasRobotInPositionPointing(int x, int y, String orientation) {
        player.setRobot(new Robot(RobotName.ASTRAL_ENFORCER, new Position(x,y)).setOrientation(Orientation.valueOf(orientation)));
    }

    @When("Player executes card")
    public void playerExecutesCard() {
        player.onUpdate();
    }


    @Then("Player's robot is in position {int} {int} pointing {string}")
    public void playerSRobotIsInPositionPointing(int x, int y, String orientation) {
        assertEquals(player.getRobot().getPosition(), new Position(x,y));
        assertEquals(player.getRobot().getOrientation(), Orientation.valueOf(orientation));
    }

    @Then("Player's queue size is {int}")
    public void playerSQueueSizeIs(int n) {
        assertEquals(player.getQueueSize(), n);
    }

    @And("A computer player called {string}")
    public void aComputerPlayerCalled(String name) {
        player = new ComputerPlayer(name);
    }
}
