package gameStructure;

import java.security.SecureRandom;

public class DeckOfCards {
    public static final String[] FACES = {
            "Ace", "Two", "Three", "Four", "Five",
            "Six", "Seven", "Jack", "Horse", "King"
    };
    public static final String[] SUITS = {
            "Cups", "Sticks", "Coins", "Swords"
    };

    private static final int NUMBER_OF_CARDS = SUITS.length * FACES.length;
    private static final SecureRandom randomNumbers = new SecureRandom();

    // instance variables
    private final BriscolaCard[] deck;
    private int currentCard;

    //default constructor
    public DeckOfCards() {
        deck = new BriscolaCard[40];
        for (int i = 0; i < deck.length; i++) {
            deck[i] = new BriscolaCard(FACES[i % 10], SUITS[i / 10]);
        }
        currentCard = 0;
    }

    public int cardsInDeck() {
        return deck.length;
    }

    public BriscolaCard dealCard() {
        if (currentCard >= deck.length)
            return null;

        return deck[currentCard++];
    }

    public void shuffle() {
        for (int index = 0; index < deck.length; index++) {
            final int swapIndex = randomNumbers.nextInt(NUMBER_OF_CARDS);
            BriscolaCard swap = deck[index];
            deck[index] = deck[swapIndex];
            deck[swapIndex] = swap;
        }
        currentCard = 0;
    }
}
