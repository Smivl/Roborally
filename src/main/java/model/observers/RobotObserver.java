package model.observers;

import model.board.robot.Robot;

public interface RobotObserver {

    void updateRobotPosition(Robot robot);

    void updateRobotOrientation(Robot robot);

    void updateRobotDead(Robot robot);

    void updateRobotRespawn(Robot robot);

    void updateRobotHealth(Robot robot);
}
