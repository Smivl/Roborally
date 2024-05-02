package model.board.components;

public class Position {

    private int x;
    private int y;

    public Position(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Position)
        {
            return (x == ((Position) obj).x) && (y == ((Position) obj).y);
        } else
        {
            return false;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return x + "," + y;
    }
}
