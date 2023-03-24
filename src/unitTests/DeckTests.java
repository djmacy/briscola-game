package unitTests;

import gameStructure.Deck;
import gameStructure.Card;
import gameStructure.Hand;

import java.util.List;

public class DeckTests {
    public static void main(String[] args) {
        new DeckTests();
    }
    private int passed = 0;
    private int failed = 0;
    private int count = 0;

    public DeckTests() {
        testDeck();
        testDealTopCard();
        testDealCards();

        if (failed > 0) {
            System.err.println(failed + " CASE(S) FAILED");
        } else {
            System.out.println("ALL CASES PASSED (" + passed + " CASES)");
        }
    }

    public void testDeck() {
        Deck deckA = new Deck();
        Deck deckB = new Deck();

        System.out.println("Testing Deck Shuffle Method...");
        List<Card> listDeckA = deckA.getDeck();
        List<Card> listDeckB = deckB.getDeck();

        System.out.println("Testing Size...");
        if (listDeckA.size() == Deck.DECK_SIZE) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed correctSize");
            failed++;
        }

        if (listDeckA.size() == listDeckB.size()) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed compareSize");
            failed++;
        }

        for (int i = 0; i < listDeckA.size(); i++) {
            if (listDeckA.get(i).equals(listDeckB.get(i))) {
                count++;
            }
        }

        System.out.println("Testing Shuffle Order...");
        if (count >= 10) {
            System.err.println("   failed Shuffle: " + count + " cards are in the same place");
            failed++;
        } else {
            System.out.println("   pass: " + count + " cards in same spot");
            passed++;
        }

        System.out.println("Testing getTopCard, lookTopCard...");
        if (deckA.lookTopCard() == deckA.getTopCard()) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed getTopCard/lookTopCard");
            failed++;
        }

        if (deckB.lookTopCard() == deckB.getTopCard()) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed getTopCard/lookTopCard");
            failed++;
        }

        if (deckA.lookTopCard() != deckB.lookTopCard()) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed lookTopCard");
            failed++;
        }

        if (deckA.getTopCard() != deckB.getTopCard()) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed getTopCard");
            failed++;
        }
    }

    public void testDealTopCard() {
        Deck deckA = new Deck();
        Deck deckB = new Deck();
        Hand hand = new Hand();

        System.out.println("Testing dealTopCard...");

        // Save the top card in a variable
        Card topCardA = deckA.lookTopCard();
        // deal the top card (removes it from deck)
        deckA.dealTopCard(hand);

        if (hand.getPlayedCard() == topCardA) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed dealTopCard");
            failed++;
        }
        hand.clear();

        Card topCardB = deckB.lookTopCard();
        deckB.dealTopCard(hand);
        if (hand.getPlayedCard() == topCardB) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed dealTopCard");
            failed++;
        }

        hand.clear();
        deckA.dealTopCard(hand);
        if (hand.getPlayedCard() != deckA.lookTopCard()) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed dealTopCard");
            failed++;
        }
    }

    public void testDealCards() {
        System.out.println("Testing dealCards");
        Deck deck = new Deck();
        Hand handA = new Hand();
        Hand handB = new Hand();

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

        if (trump == g) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed testingTrumpCard");
            failed++;
        }

        System.out.println("Testing Correct Dealing Order");
        if (handA.getHand().get(0) == a) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed 'a' card dealt");
            failed++;
        }

        if (handA.getHand().get(1) == c) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed 'c' card dealt");
            failed++;
        }

        if (handA.getHand().get(2) == e) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed 'e' card dealt");
            failed++;
        }

        if (handB.getHand().get(0) == b) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed 'b' card dealt");
            failed++;
        }

        if (handB.getHand().get(1) == d) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed 'd' card dealt");
            failed++;
        }

        if (handB.getHand().get(2) == f) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed 'f' card dealt");
            failed++;
        }
    }
}
