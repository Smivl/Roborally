package model.register.cards;


import model.board.robot.Robot;
import model.board.components.Orientation;

public class TurnRight implements Card {

    public String getImageFilePath()
    {
        return "Images/Cards/TurnRightCard.png";
    }
    @Override
    public void execute(Robot r) { r.setOrientation(Orientation.next(r.getOrientation())); }
}
