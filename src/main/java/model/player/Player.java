package model.player;

import com.google.gson.Gson;
import model.board.robot.Robot;
import model.observers.PlayerObserver;
import model.register.CardQueue;
import model.register.HandCards;
import model.register.cards.Card;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class Player {

    public final String player_name;

    private Robot robot;
    protected boolean isComputer;
    protected CardQueue cardQueue = new CardQueue();
    protected HandCards handCards = new HandCards();
    protected Set<PlayerObserver> observers =new HashSet<>();
    public Player(String name) {
        player_name = name;
    }

    public abstract void onUpdate();
    protected void activateCard() {
        Card card = cardQueue.getNextCard();
        card.execute(robot);
    }

    public void queueCard(Card card)
    {
        cardQueue.queueCard(card);
    }
    public void addObserver(PlayerObserver observer){
        observers.add(observer);
    }

    public void loadCardData(String[] hand, String[] queue){
        handCards.loadHandCards(hand);
        cardQueue.loadCardQueue(queue);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Player)
        {
            return Objects.equals(this.player_name, ((Player) obj).player_name);
        }
        return false;
    }

    @Override
    public String toString() {
        return isComputer + ";"
                + player_name + ";"
                + handCards + ";"
                + cardQueue + ";"
                + robot;
    }

    public void setRobot(Robot robot) {
        this.robot = robot;
    }
    public Robot getRobot() {
        return robot;
    }
    public HandCards getHandCards() {
        return handCards;
    }
    public CardQueue getCardQueue(){return cardQueue;}
    public int getQueueSize() {
        return cardQueue.getQueueSize();
    }

}
