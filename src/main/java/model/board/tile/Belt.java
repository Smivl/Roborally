package model.board.tile;

import model.board.GameBoard;
import model.board.robot.Robot;
import model.board.components.Orientation;
import model.board.components.Position;

public class Belt extends Tile{

    private Orientation orientation = Orientation.NORTH;
    private int power = 1;

    public Belt(Position position){
        super(position);
        imageFilePath = "/Images/tiles/belt_tex.png";
    }

    @Override
    public void callEffect() {
        Robot r = robot;
        boolean robotAlive = true;
        for (int i =0; i<power && robotAlive; i++){
            robotAlive = r.moveToPosition(GameBoard.getInstance().getNextPosition(r.getPosition(),orientation), orientation);
        }
    }

    public Belt setOrientation(Orientation orientation) {
        this.orientation = orientation;
        return this;
    }

    public Orientation getOrientation()
    {
        return orientation;
    }

    public Belt setPower(int power){
        this.power = power;
        return this;
    }

    public int getPower(){
        return power;
    }
}
