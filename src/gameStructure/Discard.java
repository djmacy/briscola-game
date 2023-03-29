package gameStructure;

import java.util.ArrayList;
import java.util.List;

public class Discard {

    List<Card> discard;

    public Discard() {
        this.discard = new ArrayList<Card>();
    }

    /**
     *
     *
     * @return
     */
    public List<Card> getDiscard() {
        return discard;
    }

    /**
     *
     * @return
     */
    public Card viewCardPlayed() {
        Card cardPlayed = discard.get(0);
        return cardPlayed;
    }

    /**
     *
     * @param card
     * @return
     */
    public Card.Suit getSuit(Card card) {
        return card.getSuit();
    }

    /**
     *
     * @param pile
     */
    public void cardsWon(Pile pile) {
        for (Card cardsPlayed : discard) {
            pile.getPile().add(cardsPlayed);
        }
        discard.clear();
    }

}
