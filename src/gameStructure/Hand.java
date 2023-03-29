/**
 * @author David
 * Creates the hand objects associated with each player. Each player has their own hand where they can choose what cards
 * to play. The methods include methods for playing each card into the discard piles and a method to get the trumpSuitCard
 * when appropriate.
 */
package gameStructure;
import java.util.ArrayList;
import java.util.List;

public class Hand {
    List<Card> hand;

    public Hand() {
        this.hand = new ArrayList<Card>();
    }

    /**
     * Returns the hand object as a list of cards.
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
     * @return the 0 index card of the hand
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
     * @return the 1 index card of the hand
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
     * @return the 2 index card of the hand
     */
    public Card playThirdCard (Discard discard) {
        Card thirdCard = hand.remove(2);
        discard.getDiscard().add(thirdCard);
        return thirdCard;
    }

    /**
     * Removes the cards in a hand.
     */
    public void clear() {
        for (Card elem : this.hand) {
            this.hand.remove(elem);
        }
    }

    /**
     * Method for testing purposes that looks at the 0 index of the hand to compare with other 0 index of other hands.
     *
     * @return 0 index card of the hand object
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
     * Deals the trump suit card of the game and adds it to a hand.
     *
     * @param trumpSuitCard is the card that is the trumpSuitCard of the game. This is the last card deat before the endgame.
     */
    public void dealTrumpSuitCard (Card trumpSuitCard){
        hand.add(trumpSuitCard);
    }
}
