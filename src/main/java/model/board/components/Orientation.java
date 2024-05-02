package model.board.components;

import java.util.Random;
public enum Orientation {
    NORTH(0), EAST(1), SOUTH(2), WEST(3);

    private final int direction;

    Orientation(int direction){
        this.direction = direction;
    }

    public static Orientation next(Orientation direction){
        return Orientation.values()[(direction.direction+1)%4];
    }

    public static Orientation previous(Orientation direction){
        return Orientation.values()[((direction.direction+3)%4)];
    }

    public static Orientation opposite(Orientation direction){
        return Orientation.values()[(direction.direction+2)%4];
    }
    public static Orientation getDirection(Orientation direction){
        return direction;
    }

    public static Orientation getRandomOrientation(){
        Random random= new Random();
        return Orientation.values()[random.nextInt(Orientation.values().length)];
    }


}
