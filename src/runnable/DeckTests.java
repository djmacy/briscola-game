package runnable;

import classes.Deck;

public class DeckTests {
    public static void main(String[] args) {

    }

    private Deck deck = new Deck();
    private int passed = 0;
    private int failed = 0;

    public DeckTests() {
        testSingleDeck(deck);

        if (failed > 0) {
            System.err.println(failed + " CASE(S) FAILED");
        } else {
            System.out.println("ALL CASES PASSED (" + passed + " CASES)");
        }
    }

    public void testSingleDeck(Deck deckA) {

    }

}
