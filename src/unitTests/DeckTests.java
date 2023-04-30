package unitTests;

import gameStructure.Deck;
import gameStructure.Card;
import gameStructure.Hand;
import java.util.List;

/**
 * This class will test the methods within Deck.java.
 *
 * @author abbybrown
 * @see gameStructure.Deck
 */
public class DeckTests {
    public static void main(String[] args) {
        new DeckTests();
    }
    private int passed = 0;
    private int failed = 0;
    private int count = 0;

    /**
     * Constructor will instantiate the testing methods below. The program will count
     * all the failed/passed cases and print these cases to the user.
     */
    public DeckTests() {
        // call methods
        testDeck();
        testDealTopCard();
        testDealCards();

        // tell user how many cases passed or failed
        if (failed > 0) {
            System.err.println(failed + " CASE(S) FAILED");
        } else {
            System.out.println("ALL CASES PASSED (" + passed + " CASES)");
        }
    }

    /**
     * TestDeck will test the shuffle order of the deck to ensure that shuffle is truly random.
     * It will test looking at the first/top card, removing the top card, and ensure that
     * the size of the deck is what it should be. Counts passed/failed cases.
     */
    public void testDeck() {
        // create a new deck
        Deck deckA = new Deck();
        Deck deckB = new Deck();

        System.out.println("Testing Deck Shuffle Method...");
        // create a shuffled list of cards
        List<Card> listDeckA = deckA.getDeck();
        List<Card> listDeckB = deckB.getDeck();

        // test that the size of the deck is actually correct
        System.out.println("Testing Size...");
        if (listDeckA.size() == 40) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed correctSize");
            failed++;
        }

        // check that the two deck sizes are equal
        if (listDeckA.size() == listDeckB.size()) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed compareSize");
            failed++;
        }

        // count the number of repeated cards shuffled in the same place
        for (int i = 0; i < listDeckA.size(); i++) {
            if (listDeckA.get(i).equals(listDeckB.get(i))) {
                count++;
            }
        }

        System.out.println("Testing Shuffle Order...");
        // if there are more than ten cards shuffled in the same spot, test failed
        if (count >= 10) {
            System.err.println("   failed Shuffle: " + count + " cards are in the same place");
            failed++;
        } else {
            System.out.println("   pass: " + count + " cards in same spot");
            passed++;
        }

        // test that the top card found is actually the top card
        System.out.println("Testing getTopCard, lookTopCard...");
        if (deckA.lookTopCard() == deckA.getTopCard()) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed getTopCard/lookTopCard");
            failed++;
        }

        // test the the top card peeked at is actually the top card
        if (deckB.lookTopCard() == deckB.getTopCard()) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed getTopCard/lookTopCard");
            failed++;
        }

        // test that the two top cards are not equal
        if (deckA.lookTopCard() != deckB.lookTopCard()) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed lookTopCard");
            failed++;
        }

        // test that the two top cards are not equal
        if (deckA.getTopCard() != deckB.getTopCard()) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed getTopCard");
            failed++;
        }
    }

    /**
     * Testing only the DealTopCard method. This method will test to ensure that
     * the first card being dealt is actually the first card in the deck.
     */
    public void testDealTopCard() {
        // create two decks and a hand
        Deck deckA = new Deck();
        Deck deckB = new Deck();
        Hand hand = new Hand();

        System.out.println("Testing dealTopCard...");

        // Save the top card in a variable
        Card topCardA = deckA.lookTopCard();

        // deal the top card (removes it from deck)
        deckA.drawTopCard(hand);

        // check that the played card is the true top card
        if (hand.getPlayedCard() == topCardA) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed dealTopCard");
            failed++;
        }
        hand.clear();

        // save the top card from deck be and check that the played card is the true top card
        Card topCardB = deckB.lookTopCard();
        deckB.drawTopCard(hand);
        if (hand.getPlayedCard() == topCardB) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed dealTopCard");
            failed++;
        }

        hand.clear();

        // check that the new top of the deck does not equal the played card
        deckA.drawTopCard(hand);

        if (hand.getPlayedCard() != deckA.lookTopCard()) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed dealTopCard");
            failed++;
        }
    }

    /**
     * This testDealCards method will only look at the dealing order of the cards.
     * The order needs extensive testing to ensure that there is accurate dealing of cards
     * to both the CPU hand and the player hand.
     */
    public void testDealCards() {
        System.out.println("Testing dealCards");

        // create a new deck and two hands
        Deck deck = new Deck();
        Hand handA = new Hand();
        Hand handB = new Hand();

        // save the cards that are about to be dealt
        Card a = deck.getDeck().get(0);
        Card b = deck.getDeck().get(1);
        Card c = deck.getDeck().get(2);
        Card d = deck.getDeck().get(3);
        Card e = deck.getDeck().get(4);
        Card f = deck.getDeck().get(5);
        Card g = deck.getDeck().get(6);

        Card trump = deck.dealCards(handA, handB);
        System.out.println("Testing Trump Card");
        // This will ensure that the trump card is actually removed from the deck
        if (trump != deck.lookTopCard()) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed testingTrumpCard");
            failed++;
        }

        // check that the correct card was set as trump
        if (trump == g) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed testingTrumpCard");
            failed++;
        }

        System.out.println("Testing Correct Dealing Order");
        // test that the correct card was dealt
        if (handA.getHand().get(0) == a) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed 'a' card dealt");
            failed++;
        }

        if (handA.getHand().get(1) == c) {
            // test that the correct card was dealt
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed 'c' card dealt");
            failed++;
        }

        if (handA.getHand().get(2) == e) {
            // test that the correct card was dealt
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed 'e' card dealt");
            failed++;
        }

        if (handB.getHand().get(0) == b) {
            // test that the correct card was dealt
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed 'b' card dealt");
            failed++;
        }

        if (handB.getHand().get(1) == d) {
            // test that the correct card was dealt
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed 'd' card dealt");
            failed++;
        }

        if (handB.getHand().get(2) == f) {
            // test that the correct card was dealt
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed 'f' card dealt");
            failed++;
        }
    }
}
