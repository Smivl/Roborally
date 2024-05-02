package model.register.cards;


import model.board.robot.Robot;
import model.board.components.Orientation;

public class TurnLeft implements Card {
    public String getImageFilePath()
    {
        return "Images/Cards/TurnLeftCard.png";
    }
    @Override
    public void execute(Robot r) { r.setOrientation(Orientation.previous(r.getOrientation())); }
}
