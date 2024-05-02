package model.register.cards;

public class CardFactory {
    public static Card createCard(String cardtype) {
        if(cardtype.equals("BackwardCard")) {
            return new BackwardCard();
        }
        if(cardtype.equals("DoubleForwardCard")){
            return new DoubleForwardCard();
        }
        if(cardtype.equals("ForwardCard")){
            return new ForwardCard();
        }
        if(cardtype.equals("TripleForwardCard")){
            return new TripleForwardCard();
        }
        if(cardtype.equals("Turn180")){
            return new Turn180();
        }

        if(cardtype.equals("TurnLeft")){
            return new TurnLeft();
        }
        if(cardtype.equals("TurnRight")){
            return new TurnRight();
        }

    return  null;
    }

}
