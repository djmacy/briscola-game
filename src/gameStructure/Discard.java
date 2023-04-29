package gameStructure;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates a list of cards that will represent the discard for each player. This is used to compare the two cards played
 * by each player and evaluate who wins the round. Once a winner is determined cards are sent to the winner's pile.
 * @author David
 */

public class Discard {

    private List<Card> discard;

    public Discard() {
        this.discard = new ArrayList<Card>();
    }

    /**
     * Returns a list of cards which represents the discard. Each player has their own discard and will have their
     * cards evaluated to see who wins. At the end of every round a check is preformed and the cards are sent to the
     * player pile who won.
     *
     * @return list of cards
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

    /**
     * Returns the card played in the discard.
     *
     * @return cardPlayed
     */
    public Card viewCardPlayed() {
        Card cardPlayed = discard.get(0);
        return cardPlayed;
    }


    public void addCard(Card card) {
        discard.add(card);
    }

    public void removeCard(Card card) {
        discard.remove(card);
    }

}
