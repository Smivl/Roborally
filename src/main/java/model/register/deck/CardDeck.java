package model.register.deck;

import model.register.cards.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardDeck {
    private List<Card> deck = new ArrayList<>();

    public List<Card> getcards() {
        return deck;
    }

    public CardDeck()
    {
        generateDeck();
        shuffleDeck();
    }

    public Card drawCard()
    {
        Card res =  deck.removeFirst();
        if (deck.isEmpty()) {
            generateDeck();
            shuffleDeck();
        }
        return res;
    }

    private void generateDeck()
    {
        // Game generates 6 of ea
        for(int i = 0; i < 6; i++)
        {
            deck.add(new ForwardCard());
            deck.add(new TurnRight());
            deck.add(new DoubleForwardCard());
            deck.add(new BackwardCard());
            deck.add(new TripleForwardCard());
            deck.add(new TurnLeft());
            deck.add(new Turn180());
        }
    }

    private void shuffleDeck()
    {
        Collections.shuffle(deck);
    }
}
