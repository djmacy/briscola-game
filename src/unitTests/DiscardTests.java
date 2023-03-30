package unitTests;

/**
 * @file DiscardTests.java
 * @author abbybrown
 * @date 03/29/23
 *
 *      This package thoroughly tests the Discard.java file.
 *      Methods include testDiscard(), which will test cardsWon(),
 *      removeCard(), addCard(), viewCardPlayed(), getDiscard().
 *      Number of cases passed/failed will appear in terminal.
 */

import gameStructure.Card;
import gameStructure.Discard;
import gameStructure.Pile;

/**
 * Our public class containing all the unit tests for the
 * Discard.java file.
 */
public class DiscardTests {

    /**
     * Main method; calls an instance of the class.
     */
    public static void main(String[] args) {
        new DiscardTests();
    }
    // These are private counters that keep track of the passed/failed cases
    private int passed = 0;
    private int failed = 0;

    /**
     * Our DiscardTests constructor calls the testDiscard method.
     * It will print the number of passed/failed cases to the user.
     */
    public DiscardTests() {
        testDiscard();

        if (failed > 0) {
            System.err.println(failed + " CASE(S) FAILED");
        } else {
            System.out.println("ALL CASES PASSED (" + passed + " CASES)");
        }
    }

    /**
     * Contains all of the main tests for the Discard.java file.
     * This method tests getDiscard(), viewCardPlayed(), cardsWon(),
     * addCard(), removeCard().
     */
    public void testDiscard() {
        // create an instance of the pile/discard
        Pile pile = new Pile();
        Discard discard = new Discard();

        // create some card instances to add to discard
        Card cardA = new Card(Card.Suit.Coins, Card.FaceName.Ace, "test.png");
        Card cardB = new Card(Card.Suit.Swords, Card.FaceName.Four, "test.png");

        // add the cards to the discard pile
        discard.addCard(cardA);
        discard.addCard(cardB);

        // test the addCard method
        System.out.println("Testing addCard...");
        // the size of the discard should be 2
        if (discard.getDiscard().size() == 2) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed addCard");
            failed++;
        }

        // test that the correct cards were added
        if (discard.getDiscard().contains(cardA) && discard.getDiscard().contains(cardB)) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed addCard");
            failed++;
        }

        // test the viewCardPlayed method
        System.out.println("Testing viewCardPlayed...");
        if (discard.viewCardPlayed() == cardA) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed viewCardPlayed");
            failed++;
        }

        // remove the first card and try again
        discard.removeCard(cardA);
        if (discard.viewCardPlayed() == cardB) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed viewCardPlayed");
            failed++;
        }

        // test that the discard pile gets cleared
        System.out.println("Testing cardsWon");
        discard.cardsWon(pile);
        if (discard.getDiscard().size() == 0) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed cardsWon");
            failed++;
        }

        // test that the pile now has a card
        if (pile.getPile().size() == 1) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed cardsWon");
            failed++;
        }

        // test that the right card was added
        if (pile.getPile().contains(cardB)) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed cardsWon");
            failed++;
        }
    }
}
