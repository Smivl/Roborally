package view.elements;

import javafx.scene.control.Slider;


public class CustomSlider extends Slider {

    public CustomSlider(int min, int max, int current) {
        super(min,max,current);
        setShowTickLabels(true);
        setShowTickMarks(false);
        setMajorTickUnit(1);
        setMinorTickCount(0);
        setSnapToTicks(true);
    }

    public int read(){
        return (int) getValue();
    }
}