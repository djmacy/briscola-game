package unitTests;

import gameStructure.Card;
import gameStructure.Discard;
import gameStructure.Hand;

/**
 * This file thoroughly tests the methods within Hand.java.
 *
 * @author abbybrown
 * @see gameStructure.Hand
 */

public class HandTests {

    /**
     * Main method which calls the testing methods.
     */
    public static void main(String[] args) {
        new HandTests();
    }

    private int passed = 0;
    private int failed = 0;

    /**
     * Our HandTests constructor calls the testHand method.
     * It will print the number of passed/failed cases to the user.
     */
    public HandTests() {
        // call the testHand method
        testHand();
        testThirdCard();
        testSecondCard();

        // print the number of failed/passed cases for the user to see
        if (failed > 0) {
            System.err.println(failed + " CASE(S) FAILED");
        } else {
            System.out.println("ALL CASES PASSED (" + passed + " CASES)");
        }
    }

    /**
     * The testHand method will test various methods within the Hand.java file.
     *
     * @see gameStructure.Hand
     */
    public void testHand() {
        // create instances of three cards to put into the hand, and trump card
        Card cardA = new Card(Card.Suit.Coins, Card.FaceName.Ace, "test.png");
        Card cardB = new Card(Card.Suit.Swords, Card.FaceName.Four, "test.png");
        Card cardC = new Card(Card.Suit.Sticks, Card.FaceName.Horse, "test2.png");

        // create an instance of hand so the cards have a hand to go to
        Hand hand = new Hand();

        hand.getHand().add(cardA);
        hand.getHand().add(cardB);
        hand.getHand().add(cardC);

        // test if the cards were added to hand properly
        System.out.println("Testing adding cards to Hand");
        if (hand.getHand().size() == 3) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed adding cards");
            failed++;
        }

        // test that the correct cards were actually added and are in the hand
        if (hand.getHand().contains(cardA) && hand.getHand().contains(cardB) && hand.getHand().contains(cardC)) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed adding cards");
            failed++;
        }

        // test that the played card is returning the first card in hand
        System.out.println("Testing getPlayedCard...");
        Card firstCard = hand.getPlayedCard();
        if (firstCard == cardA) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed getPlayedCard");
            failed++;
        }

        // test that cardA was removed from hand
        if (hand.getHand().size() == 2) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed getPlayedCard");
            failed++;
        }

        if (!hand.getHand().contains(cardA)) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed getPlayedCard");
            failed++;
        }

        hand.clear();
        System.out.println("Testing clear...");
        if (hand.getHand().size() == 0) {
            System.out.println("   pass");
            passed++;
        } else {
            System.out.println("   failed getPlayedCard");
            failed++;
            System.out.println(hand.getHand().size());
        }
    }

    /**
     * The testHand method will test specifically the playThirdCard method.
     *
     * @see gameStructure.Hand
     */
    public void testThirdCard() {
        Card cardA = new Card(Card.Suit.Coins, Card.FaceName.Ace, "test.png");
        Card cardB = new Card(Card.Suit.Swords, Card.FaceName.Four, "test.png");
        Card cardC = new Card(Card.Suit.Sticks, Card.FaceName.Horse, "test2.png");

        // create an instance of discard so the cards in hand have a container to go to
        Discard discard = new Discard();
        // create an instance of hand so the cards have a hand to go to
        Hand hand = new Hand();

        hand.getHand().add(cardA);
        hand.getHand().add(cardB);
        hand.getHand().add(cardC);

        System.out.println("Testing playThirdCard...");
        Card third = hand.playThirdCard(discard);
        if (third == cardC) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed playThirdCard");
            failed++;
        }

        if (hand.getHand().size() == 2) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed playThirdCard");
            failed++;
        }

        if (discard.getDiscard().size() == 1) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed playThirdCard");
            failed++;
        }
    }

    /**
     * The testHand method will test specifically the playSecondCard method.
     *
     * @see gameStructure.Hand
     */
    public void testSecondCard() {
        Card cardA = new Card(Card.Suit.Coins, Card.FaceName.Ace, "test.png");
        Card cardB = new Card(Card.Suit.Swords, Card.FaceName.Four, "test.png");
        Card cardC = new Card(Card.Suit.Sticks, Card.FaceName.Horse, "test2.png");

        // create an instance of discard so the cards in hand have a container to go to
        Discard discard = new Discard();
        // create an instance of hand so the cards have a hand to go to
        Hand hand = new Hand();

        hand.getHand().add(cardA);
        hand.getHand().add(cardB);
        hand.getHand().add(cardC);

        System.out.println("Testing playSecondCard...");
        Card second = hand.playSecondCard(discard);
        if (second == cardB) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed playThirdCard");
            failed++;
        }

        if (hand.getHand().size() == 2) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed playThirdCard");
            failed++;
        }

        if (discard.getDiscard().size() == 1) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed playThirdCard");
            failed++;
        }
    }
}
