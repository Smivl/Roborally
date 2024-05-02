package model.register;

import model.register.cards.Card;
import model.register.cards.CardFactory;

import java.util.*;

public class CardQueue {
    private final Queue<Card> program_queue = new ArrayDeque<>();

    public boolean queueCard(Card card) {
        if (getQueueSize() < 5) {
            program_queue.add(card);
            return true;
        } else return false;
    }
    public Card getNextCard() {
        return program_queue.poll();
    }

    public Card lookAtNextCard() { return program_queue.peek();}
    public int getQueueSize() {
        return program_queue.size();
    }

    public void loadCardQueue(String[] cards){

        for(String card : cards){
            if(card.isBlank()) continue;
            program_queue.add(CardFactory.createCard(card));
        }

        System.out.println(Arrays.toString(cards));
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for(Card card : program_queue){
            res.append(String.valueOf(card.getClass()).split("\\.")[3]).append(",");
        }
        if(res.isEmpty()) return res.toString();
        else return res.substring(0, res.length()-1);
    }
}
