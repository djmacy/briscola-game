/**
 * @author David
 * This is the deck class which creates the deck objects and the methods needed to play the game. Among the methods are the deal and dealTopCard Methods.
 * These are used for getting the card objects into a deck where we can put the cards into hands.
 */

package gameStructure;

import java.util.*;

public class Deck {
    public static final int DECK_SIZE = 40;
    private final List<Card> deck;

    /**
     * Creates the deck object. It loops through each suit faceName and image file for each card to put into the deck
     */
    public Deck() {
        this.deck = new ArrayList<>();
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.FaceName faceName : Card.FaceName.values()) {
                String imagePath = "src/images/" + faceName.name().toLowerCase() + "_of_" + suit.name().toLowerCase() + ".png";
                Card card = new Card(suit, faceName, imagePath);
                this.deck.add(card);
            }
        }
        Collections.shuffle(this.deck);
    }

    /**
     * Returns the deck object.
     *
     * @return deck
     */
    public List<Card> getDeck() {
        return this.deck;
    }

    /**
     * Returns the top card on the deck without removing it.
     *
     * @return the 0 index of the deck
     */
    public Card lookTopCard() {
        if (deck.size() > 0) {
            Card card = deck.get(0);
            return card;
        } else {
            return null;
        }
    }

    /**
     * Returns the top card on the deck while removing it as well.
     *
     * @return the 0 index of the deck
     */
    public Card getTopCard() {
        if (deck.size() > 0) {
            Card topCard = deck.remove(0);
            return topCard;
        } else {
            return null;
        }
    }

    /**
     * Returns the top card while removing it and assigning the card to a hand. Used to draw after every round.
     *
     * @param hand is the hand where the card will go when called
     * @return the 0 index of the deck
     */
    public Card dealTopCard(Hand hand) {
        Card topCard = deck.remove(0);
        hand.getHand().add(topCard);
        return topCard;
    }

    /**
     * Returns the trump suit card which will be the last card pulled from the deck after the cards have been dealt.
     * Each player will get three cards.
     *
     * @param hand1 is the hand associated with player 1
     * @param hand2 is the hand associated with player 2
     * @param whoStarts will determine which order the cards are dealt
     * @return the trumpSuitCard after cards have been dealt
     */
    public Card dealCards(Hand hand1, Hand hand2, int whoStarts) {
        if (whoStarts == 1) {
            Card cardOne = deck.remove(0);
            Card cardTwo = deck.remove(0);
            Card cardThree = deck.remove(0);
            Card cardFour = deck.remove(0);
            Card cardFive = deck.remove(0);
            Card cardSix = deck.remove(0);
            Card trumpSuit = deck.remove(0);
            hand1.hand.add(cardOne);
            hand1.hand.add(cardThree);
            hand1.hand.add(cardFive);
            hand2.hand.add(cardTwo);
            hand2.hand.add(cardFour);
            hand2.hand.add(cardSix);
            return trumpSuit;
        } else {
            Card cardOne = deck.remove(0);
            Card cardTwo = deck.remove(0);
            Card cardThree = deck.remove(0);
            Card cardFour = deck.remove(0);
            Card cardFive = deck.remove(0);
            Card cardSix = deck.remove(0);
            Card trumpSuit = deck.remove(0);
            hand2.hand.add(cardOne);
            hand2.hand.add(cardThree);
            hand2.hand.add(cardFive);
            hand1.hand.add(cardTwo);
            hand1.hand.add(cardFour);
            hand1.hand.add(cardSix);
            return trumpSuit;
        }
    }

}
