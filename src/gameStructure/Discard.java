package gameStructure;

import java.util.ArrayList;
import java.util.List;

public class Discard {

    List<Card> discard;

    public Discard() {
        this.discard = new ArrayList<Card>();
    }

    /**
     * Returns the discard object. Each player has their own discard and will have their cards evaluated to see who wins.
     * At the end of every round a check is preformed and the cards are sent to the player pile who won.
     *
     * @return discard
     */
    public List<Card> getDiscard() {
        return discard;
    }

    /**
     * Adds the card in the discard to the player pile that won and clears the card in the discard object.
     *
     * @param pile for the player that won
     */
    public void cardsWon(Pile pile) {
        for (Card cardsPlayed : discard) {
            pile.getPile().add(cardsPlayed);
        }
        discard.clear();
    }

}
