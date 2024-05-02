package view.player.register.card;

import javafx.scene.Cursor;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import model.register.cards.Card;

public class CardView extends StackPane {

    Rectangle background = new Rectangle();
    CardViewTop cardViewTop;

    public CardView(CardViewTop cardViewTop){
        this.cardViewTop = cardViewTop;
        background.setFill(Paint.valueOf("white"));
        background.setVisible(false);
        cardViewTop.setOnMouseEntered(mouseEvent -> {
            background.setVisible(true);
            cursorProperty().set(Cursor.HAND);
        });
        cardViewTop.setOnMouseExited(mouseEvent -> {
            background.setVisible(false);
            cursorProperty().set(Cursor.DEFAULT);
        });
        background.widthProperty().bind(cardViewTop.widthProperty().multiply(1.05));
        background.heightProperty().bind(cardViewTop.heightProperty().multiply(1.05));
        getChildren().addAll(background, cardViewTop);
    }

    public Card getCard(){
        return cardViewTop.getCard();
    }
}
