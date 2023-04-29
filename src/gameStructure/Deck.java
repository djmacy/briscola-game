package gameStructure;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Creates a collection of cards that will represent the deck for the card game Briscola. Each deck contains 40 cards,
 * 10 cards from each suit. The deck is shuffled everytime it's created so it does not have to be done during the game.
 * The functionality for this class will include dealing and drawing cards from the deck to the players hands.
 * @author David
 */

public class Deck {
    private final List<Card> deck;

    /**
     * Creates the deck object. It creates a deck with 10 unique cards from each of the four suits and then shuffles the deck.
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
     * Returns a list of cards which represents the deck.
     *
     * @return list of cards
     */
    public List<Card> getDeck() {
        return this.deck;
    }

    /**
     * Returns the top card on the deck without removing it.
     *
     * @return top card of deck
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
     * Returns the top card on the deck while removing.
     *
     * @return the top card
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
     * @return the top card
     */
    public Card drawTopCard(Hand hand) {
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
     * @return the trumpSuitCard after cards have been dealt
     */
    public Card dealCards(Hand hand1, Hand hand2) {
            Card cardOne = deck.remove(0);
            Card cardTwo = deck.remove(0);
            Card cardThree = deck.remove(0);
            Card cardFour = deck.remove(0);
            Card cardFive = deck.remove(0);
            Card cardSix = deck.remove(0);
            Card trumpSuit = deck.remove(0);
            hand1.getHand().add(cardOne);
            hand1.getHand().add(cardThree);
            hand1.getHand().add(cardFive);
            hand2.getHand().add(cardTwo);
            hand2.getHand().add(cardFour);
            hand2.getHand().add(cardSix);
            return trumpSuit;

    }

}
