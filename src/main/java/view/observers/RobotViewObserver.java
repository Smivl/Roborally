package view.observers;

import model.board.robot.Robot;
import view.board.robot.RobotView;

public interface RobotViewObserver {
    void onRobotViewDeath(RobotView robotView);

    void onAnimationStart();
    void onAnimationDone();
}
