package view.player;

import io.cucumber.java.af.En;
import io.cucumber.java.en.But;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.observers.PlayerObserver;
import model.register.CardQueue;
import model.register.cards.Card;
import view.board.robot.RobotView;
import view.observers.RobotViewObserver;
import view.player.register.CardQueueView;
import view.player.register.HandCardsView;
import view.player.register.card.CardView;

import java.util.List;

public class PlayerView implements PlayerObserver, RobotViewObserver {

    private BorderPane root;
    private String player_name;

    private Stage stage;

    private HandCardsView handCardsView;
    private CardQueueView cardQueueView;

    private final Button hideCards;
    private Button endTurn;
    private Runnable nextTurn;

    private boolean showingCards = false;
    private boolean pickingCards = false;


    public PlayerView(Stage stage, String player_name, Runnable nextTurn)
    {
        this.stage = stage;
        this.nextTurn = nextTurn;

        root = new BorderPane();
        cardQueueView = new CardQueueView(stage);
        handCardsView = new HandCardsView(stage, cardQueueView);
        cardQueueView.setHandsCardView(handCardsView);

        this.player_name = player_name;

        Label nameLabel = new Label(this.player_name);
        nameLabel.getStyleClass().add("player-name-label");

        endTurn = new Button("End turn");
        hideCards = new Button("hide cards");

        root.setLeft(endTurn);
        BorderPane.setAlignment(endTurn, Pos.BOTTOM_CENTER);

        endTurn.setOnAction(event -> {
            pickingCards = false;
            this.nextTurn.run();
        });

        hideCards.setOnAction(event -> {
            if(showingCards){
                showingCards = false;
                root.setBottom(null);
                hideCards.setText("Show cards");
            } else {
                showingCards = true;
                root.setBottom(handCardsView);
                hideCards.setText("Hide cards");
            }
        });

        root.setRight(hideCards);
        BorderPane.setAlignment(hideCards, Pos.BOTTOM_CENTER);
        root.setTop(nameLabel);
        BorderPane.setAlignment(nameLabel, Pos.CENTER_LEFT);
    }
    @Override
    public void onRobotViewDeath(RobotView robotView){}
    @Override
    public void onAnimationDone(){
        endTurn.setDisable(false);
    }
    @Override
    public void onAnimationStart(){
        endTurn.setDisable(true);
    }
    @Override
    public void onPickingCards(List<Card> cards)
    {
        pickingCards = true;
        if (cards != null) handCardsView.setCards(cards);

        if (!hideCards.isVisible()) hideCards.setVisible(true);
        if (!endTurn.isVisible()) endTurn.setVisible(true);

        root.setBottom(handCardsView);
        showingCards = true;
        hideCards.setText("Hide cards");
        root.setCenter(cardQueueView);

        BorderPane.setAlignment(cardQueueView, javafx.geometry.Pos.CENTER);
        BorderPane.setMargin(cardQueueView, new Insets(0));
    }

    @Override
    public void onComputerPickingCards()
    {
        pickingCards = false;
        root.setBottom(null);
        Label l = new Label(player_name + " selecting cards");
        l.getStyleClass().add("CPU-selecting-cards");
        root.setCenter(l);

        endTurn.setVisible(false);
        hideCards.setVisible(false);
    }

    @Override
    public void onExecutingCard(Card card) {
        pickingCards = false;
        root.setBottom(null);
        root.setCenter(null);

        if (hideCards.isVisible()) hideCards.setVisible(false);
        if (!endTurn.isVisible()) endTurn.setVisible(true);

        cardQueueView.removeCardFromQueue(card);

        Rectangle c = new Rectangle();
        c.setFill(new ImagePattern(new Image(card.getImageFilePath())));
        c.setHeight(210);
        c.setWidth(150);

        root.setBottom(c);

    }

    public void setPickingCards(boolean b) {
        pickingCards = b;
    }
    public boolean getPickingCards() { return pickingCards; }

    public HandCardsView getHandCardsView() { return handCardsView; }
    public CardQueueView getCardQueueView() { return cardQueueView; }
    public BorderPane getRoot() { return root; }

}
