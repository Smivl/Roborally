package model.register.cards;


import model.board.robot.Robot;

public class ForwardCard implements Card {

    public String getImageFilePath()
    {
        return "Images/Cards/ForwardCard.png";
    }
    @Override
    public void execute(Robot r) {
        r.takeStep(1);
    }
}
