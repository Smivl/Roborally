package model.register.cards;

import model.board.robot.Robot;

public class DoubleForwardCard implements Card {

    public String getImageFilePath()
    {
        return "Images/Cards/2ForwardCard.png";
    }
    public void execute(Robot r) {
        boolean b = true;
        for(int i=0; i<2 && b; i++) // take two steps unless you die before
            b=r.takeStep(1);
    }
}
