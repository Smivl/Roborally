package view.board.tiles;

import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.board.tile.Bomb;

public class BombView extends StackPane {
    private boolean exploded = false;
    private static final Image explodedImage = new Image("/Images/tiles/Bomb_crater.png");;

    public BombView(boolean hasExploded){
        exploded = hasExploded;
    }

    public void explode(){
        if(this.getChildren().getFirst() instanceof Rectangle rectangle){
            if(!exploded){
                rectangle.setFill(new ImagePattern(explodedImage));
                exploded = true;
            }
        }
    }
}
