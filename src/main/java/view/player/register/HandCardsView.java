package view.player.register;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.register.cards.Card;
import view.player.register.card.CardView;
import view.player.register.card.CardViewTop;

import java.util.ArrayList;
import java.util.List;

public class HandCardsView extends HBox {

    private final Stage stage;
    private CardQueueView cardQueueView;

    private double xOffset;
    private double yOffset;

    private boolean isDragging = false;

    public HandCardsView(Stage stage, CardQueueView cardQueueView) {
        super(25);
        this.stage = stage;
        this.cardQueueView = cardQueueView;

        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(30,0,20,0));
        this.setBackground(new Background(new BackgroundFill(Color.rgb(50,50,50,0.5), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public void setCards(List<Card> cards)
    {

        for (Card card : cards)
        {
            if(cardExists(card)) continue;

            CardViewTop ct = new CardViewTop(card);
            CardView c = new CardView(ct);

            ct.widthProperty().bind((Bindings.min(
                    stage.heightProperty().multiply(16/9f),
                    stage.widthProperty()
            ).divide(1920)).multiply(150));

            ct.heightProperty().bind((Bindings.min(
                    stage.widthProperty().divide(16/9f),
                    stage.heightProperty()
            ).divide(1080)).multiply(210));

            setCardBindings(c);

            this.getChildren().add(c);
        }
    }

    public List<Card> getCards()
    {
        List<Card> cardList = new ArrayList<Card>();
        for(Node node : this.getChildren())
        {
            if(node instanceof CardView card) cardList.add(card.getCard());
        }
        return cardList;
    }

    public void setCardBindings(CardView card)
    {

        card.setOnMousePressed((MouseEvent event) -> {
            // Record the current mouse position
            xOffset = event.getSceneX() - card.getTranslateX();
            yOffset = event.getSceneY() - card.getTranslateY();

            isDragging = false;
        });

        card.setOnMouseDragged((MouseEvent event) -> {
            // Calculate new position based on mouse movement
            double newX = event.getSceneX() - xOffset;
            double newY = event.getSceneY() - yOffset;

            // Update the position of the rectangle
            card.setTranslateX(newX);
            card.setTranslateY(newY);

            isDragging = true;

        });

        card.setOnMouseReleased((MouseEvent event) -> {
            // Reset the rectangle's position to its original position
            if(isDragging){
                card.setTranslateX(0);
                card.setTranslateY(0);
            }
        });


        card.setOnMouseClicked(event -> {
            if(!isDragging){
                this.getChildren().remove(card);
                cardQueueView.addCardToQueue(card);
            }
        });
    }

    private boolean cardExists(Card card)
    {
        for(Node node : this.getChildren())
        {
            if(node instanceof CardView cardView) {
                if(cardView.getCard().equals(card)) return true;
            }
        }
        return false;
    }
}
