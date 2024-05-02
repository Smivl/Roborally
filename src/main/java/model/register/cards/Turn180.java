package model.register.cards;


import model.board.robot.Robot;
import model.board.components.Orientation;

public class Turn180 implements Card {

    public String getImageFilePath()
    {
        return "Images/Cards/180TurnCard.png";
    }
    @Override
    public void execute(Robot r) {
        r.setOrientation(Orientation.opposite(r.getOrientation()));
    } //r.turn(-1)
}
