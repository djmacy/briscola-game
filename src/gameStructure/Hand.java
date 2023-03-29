package gameStructure;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    List<Card> hand;

    public Hand() {
        this.hand = new ArrayList<Card>();
    }

    public List<Card> getHand() {
        return hand;
    }

    public Card playFirstCard (Discard discard) {
        Card firstCard = hand.remove(0);
        discard.getDiscard().add(firstCard);
        return firstCard;
    }

    public Card playSecondCard (Discard discard) {
        Card secondCard = hand.remove(1);
        discard.getDiscard().add(secondCard);
        return secondCard;
    }

    public Card playThirdCard (Discard discard) {
        Card thirdCard = hand.remove(2);
        discard.getDiscard().add(thirdCard);
        return thirdCard;
    }

    /**
     * This method ended up being modified to utilize Java's clear method. It will clear
     * all the cards within a hand.
     */
    public void clear() {
        hand.clear();
    }

    public Card getPlayedCard() {
        if (hand.size() > 0) {
            Card topCard = hand.remove(0);
            return topCard;
        } else {
            return null;
        }
    }

    public void dealTrumpSuitCard (Card trumpSuitCard){
        hand.add(trumpSuitCard);
    }
}
