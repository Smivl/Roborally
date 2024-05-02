package view.player.register;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.register.cards.Card;
import view.player.register.card.CardView;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.zip.ZipEntry;


public class CardQueueView extends HBox {

    private final Stage stage;
    private HandCardsView handCardsView;

    private List<CardView> cardViews = new ArrayList<>();

    public CardQueueView(Stage stage)
    {
        super(25);
        this.stage = stage;

        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(10));

    }
    public void addCardToQueue(CardView card)
    {
        this.getChildren().add(card);
        cardViews.add(card);

        card.setOnMouseClicked(event -> {
                this.getChildren().remove(card);
                handCardsView.getChildren().add(card);
                handCardsView.setCardBindings(card);
        });
    }

    public void removeCardFromQueue(Card card)
    {
        CardView cardView = null;
        for(CardView cV : cardViews) {
            if(cV.getCard().equals(card)) {
                this.getChildren().remove(cV);
                cardView = cV;
            }
        }
        cardViews.remove(cardView);
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

    public void setHandsCardView(HandCardsView handCardsView) {
        this.handCardsView=handCardsView;
    }
}
