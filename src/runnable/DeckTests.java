package runnable;

import classes.Deck;
import classes.Card;

import java.util.List;

public class DeckTests {
    public static void main(String[] args) {
        new DeckTests();
    }

    private Deck deckA = new Deck();
    private Deck deckB = new Deck();
    private int passed = 0;
    private int failed = 0;
    private int count = 0;

    public DeckTests() {
        testDeck(deckA, deckB);

        if (failed > 0) {
            System.err.println(failed + " CASE(S) FAILED");
        } else {
            System.out.println("ALL CASES PASSED (" + passed + " CASES)");
        }
    }

    public void testDeck(Deck deckA, Deck deckB) {
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
    }

}
