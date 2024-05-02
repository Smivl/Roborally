package model.player;

import model.board.robot.Robot;
import model.observers.PlayerObserver;
import model.register.cards.Card;

import java.util.List;

public class ComputerPlayer extends Player{
    public ComputerPlayer(String name) {
        super(name);
        isComputer = true;
    }

    @Override
    public void onUpdate() {

        int queueSize = cardQueue.getQueueSize();
        if (queueSize > 0){
            for (PlayerObserver o : observers){
                o.onExecutingCard(cardQueue.lookAtNextCard());
            }
            super.activateCard();
        } else {
            for (PlayerObserver o : observers){
                o.onComputerPickingCards();
            }
            handCards.drawCards();
            List<Card> cards = handCards.getCards();
            for(Card card : cards)
            {
                if(!cardQueue.queueCard(card)) break;
            }
        }
    }
}
