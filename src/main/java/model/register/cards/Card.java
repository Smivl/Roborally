package model.register.cards;


import model.board.robot.Robot;

public interface Card {
    void execute(Robot r);

    String getImageFilePath();
}
