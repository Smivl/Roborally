package model.player;

import model.board.robot.Robot;
import model.observers.PlayerObserver;

public class RealPlayer extends Player{
    public RealPlayer(String name) {
        super(name);
        isComputer = false;
    }

    @Override
    public void onUpdate() // checks if the card queue is empty. If true, then it draws cards, if
    {                      // false, it activates tha card in the queue.
        int queueSize = cardQueue.getQueueSize();
        if (queueSize > 0){
            for (PlayerObserver o : observers){
                o.onExecutingCard(cardQueue.lookAtNextCard());
            }
            super.activateCard();
        } else {
            handCards.drawCards();
            for (PlayerObserver o : observers){
                o.onPickingCards(handCards.getCards());
            }
        }
    }
}
