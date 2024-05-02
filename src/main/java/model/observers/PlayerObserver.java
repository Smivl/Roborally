package model.observers;

import model.register.cards.Card;

import java.util.List;

public interface PlayerObserver {

    void onPickingCards(List<Card> cards);
    void onComputerPickingCards();
    void onExecutingCard(Card card);
}
