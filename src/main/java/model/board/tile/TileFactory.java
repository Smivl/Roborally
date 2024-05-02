package model.board.tile;

import model.board.components.Position;

public class TileFactory {

    public static Tile create(String tile, Position position) {
        if (tile.equals("tile")) {
            return new Tile(position);
        }
        if (tile.equals("belt")) {
            return new Belt(position);
        }
        if (tile.equals("charger")) {
            return new Charger(position);
        }
        if (tile.equals("checkpoint")) {
            return new Checkpoint(position);
        }
        if (tile.equals("laser")) {
            return new Laser(position);
        }
        if (tile.equals("rotating gear")){
            return new RotatingGear(position).setClockwise(true);
        }
        if (tile.equals("spawn")) {
            return new Spawn(position);
        }
        if (tile.equals("trap")) {
            return new Trap(position);
        }
        if (tile.equals("wall")) {
            return new Wall(position);
        }
        if (tile.equals("lava")) {
            return new Lava(position);
        }
        if (tile.equals("win")) {
            return new Win(position);
        }
        if (tile.equals("teleportation")){
            return new TeleportationTile(position);
        }
        if (tile.equals("oilstain")){
            return new OilStain(position);
        }
        if (tile.equals("bomb")){
            return new Bomb(position);
        }
        System.out.println("Couldn't find '" + tile + "'!");
        return null;
    }
}
