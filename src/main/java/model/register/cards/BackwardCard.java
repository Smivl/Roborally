package model.register.cards;


import model.board.robot.Robot;

public class BackwardCard implements Card {

    public String getImageFilePath()
    {
        return "Images/Cards/BackwardCard.png";
    }

    @Override
    public void execute(Robot r) {
        r.takeStep(-1);
    }
}
