package model.register;

import model.register.cards.Card;
import model.register.cards.CardFactory;
import model.register.deck.CardDeck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class HandCards {

    private CardDeck deck = new CardDeck();
    private final List<Card> register = new ArrayList<>(7);

    public List<Card> getCards() { return register; }
    public void drawCards()
    {
        for(int i = 0; i < 7; i++)
        {
            if (register.size() >= 7) break;
            else register.add(deck.drawCard());
        }
    }
    public void setCards(List<Card> cards)
    {
        register.clear();
        register.addAll(cards);
    }

    public void loadHandCards(String[] cards){
        for(String card : cards){
            register.add(CardFactory.createCard(card));
        }

        System.out.println(Arrays.toString(cards));
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for(Card card : register){
            res.append(String.valueOf(card.getClass()).split("\\.")[3]).append(",");
        }
        if(res.isEmpty()) return res.toString();
        else return res.substring(0, res.length()-1);
    }
}
