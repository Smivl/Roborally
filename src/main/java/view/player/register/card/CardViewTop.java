package view.player.register.card;

import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.register.cards.Card;

public class CardViewTop extends Rectangle {

    Card card;
    public CardViewTop(Card card)
    {
        super();
        this.card = card;

        this.setFill(new ImagePattern(new Image(card.getImageFilePath())));

        setOnMouseEntered(event ->{
            //setStroke(Color.BLACK);
            //setStrokeWidth(2.0);
            setCursor(Cursor.HAND);
        });

        setOnMouseExited(event -> {
            //setStroke(null);
            setCursor(Cursor.DEFAULT);
        });

    }

    public Card getCard(){ return card; }
}
