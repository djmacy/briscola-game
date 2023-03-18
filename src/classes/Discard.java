package classes;

import java.util.ArrayList;
import java.util.List;

public class Discard {

    List<Card> discard;

    public Discard() {
        this.discard = new ArrayList<Card>();
    }

    public List<Card> getDiscard() {
        return discard;
    }

    public Card viewCardPlayed() {
        Card cardPlayed = discard.get(0);
        return cardPlayed;
    }

    public Card.Suit getSuit(Card card) {
        return card.getSuit();
    }

    public void cardsWon(Pile pile) {
        for (Card cardsPlayed : discard) {
            pile.getPile().add(cardsPlayed);
        }
        discard.clear();
    }

}
