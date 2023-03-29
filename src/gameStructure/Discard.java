package gameStructure;

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

    public void cardsWon(Pile pile) {
        for (Card cardsPlayed : discard) {
            pile.getPile().add(cardsPlayed);
        }
        discard.clear();
    }

    public void addCard(Card card) {
        discard.add(card);
    }

    public void removeCard(Card card) {
        discard.remove(card);
    }

}
