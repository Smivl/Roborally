package view.elements;

import javafx.scene.control.ScrollBar;

import javafx.geometry.Orientation;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

//Still working on this
public class CustomScroller extends ScrollBar{
    public CustomScroller(int setMin, int setMax, int setValue) {
        super();

        setOrientation(Orientation.VERTICAL);
        setUnitIncrement(2);
        StackPane root = new StackPane();





    }
    public int read() {
        return 0;
    }

}
