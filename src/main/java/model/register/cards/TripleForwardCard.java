package model.register.cards;


import model.board.robot.Robot;

public class TripleForwardCard implements Card {

    public String getImageFilePath()
    {
        return "Images/Cards/3ForwardCard.png";
    }
    public void execute(Robot r) {
        boolean b = true;
        for(int i=0; i<3 && b; i++) // take three steps unless you die before
            b=r.takeStep(1);
    }
}
