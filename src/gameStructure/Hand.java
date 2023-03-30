/**
 * Creates a list of cards that will represent the hand for each player. The functionality of this class will include
 * playing any of the three cards that can be played per round.
 * @author David
 */
package gameStructure;
import java.util.ArrayList;
import java.util.List;

public class Hand {
    List<Card> hand;

    /**
     * Creates the Hand object. It creates a list of cards.
     */
    public Hand() {
        this.hand = new ArrayList<Card>();
    }

    /**
     * Returns a list of cards which reppresents the deck.
     *
     * @return hand
     */
    public List<Card> getHand() {
        return hand;
    }

    /**
     * Plays the first card in the hand.
     *
     * @param discard is where the card will be sent. Each player will have their own discard to evaluate which card wins
     * @return firstCard
     */
    public Card playFirstCard (Discard discard) {
        Card firstCard = hand.remove(0);
        discard.getDiscard().add(firstCard);
        return firstCard;
    }

    /**
     * Plays the second card in the hand.
     *
     * @param discard is where the card will be sent. Each player will have their own discard to evaluate which card wins
     * @return secondCard
     */
    public Card playSecondCard (Discard discard) {
        Card secondCard = hand.remove(1);
        discard.getDiscard().add(secondCard);
        return secondCard;
    }

    /**
     * Plays the third card in the hand.
     *
     * @param discard is where the card will be sent. Each player will have their own discard to evaluate which card wins
     * @return thirdCard
     */
    public Card playThirdCard (Discard discard) {
        Card thirdCard = hand.remove(2);
        discard.getDiscard().add(thirdCard);
        return thirdCard;
    }

    /**
     * Removes all the cards in a hand.
     */
    public void clear() {
        for (Card elem : this.hand) {
            this.hand.remove(elem);
        }
    }

    /**
     * Method for testing purposes that looks at the 0 index of the hand to compare with other 0 index of other hands.
     *
     * @return firstCard
     */
    public Card getPlayedCard() {
        if (hand.size() > 0) {
            Card topCard = hand.remove(0);
            return topCard;
        } else {
            return null;
        }
    }

    /**
     * Deals the trump suit card of the game and adds it to a hand. This is implemented towards the end of the game when
     * there is only 1 card left to draw from the deck. The second player to draw will take the trumpSuitCard since there
     * are no more cards to draw from.
     *
     * @param trumpSuitCard is the card that is the trumpSuitCard of the game. This is the last card dealt before the endgame.
     */
    public void dealTrumpSuitCard (Card trumpSuitCard){
        hand.add(trumpSuitCard);
    }
}
