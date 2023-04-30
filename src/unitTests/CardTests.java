package unitTests;

import gameStructure.Card;

import javax.swing.*;

/**
 * This class will thoroughly test the methods within Card.java. Testing all the public methods. Program
 * will print the number of passed/failed cases.
 *
 * @author abbybrown
 * @see gameStructure.Card
 */

public class CardTests {

    /**
     * Main method will run all of our CardTests.
     */
    public static void main(String[] args) {
        new CardTests();
    }

    private Card cardOne = new Card(Card.Suit.Coins, Card.FaceName.Ace, "test.png");
    private Card cardTwo = new Card(Card.Suit.Sticks, Card.FaceName.Three, "test2.png");
    //    cardThree is a duplicate of cardOne to see how the program behaves with identical card types
    private Card cardThree = new Card(Card.Suit.Coins, Card.FaceName.Ace, "test.png");
    private ImageIcon testIcon = new ImageIcon("test.png");
    private ImageIcon testIconTwo = new ImageIcon("test2.png");
    private int passed = 0;
    private int failed = 0;

    /**
     * Constructor that implements CardTest methods.
     * The constructor will print the number of passed and failed cases.
     */
    public CardTests() {
        // call our card test methods
        testDifferentCards(cardOne, cardTwo);
        testDuplicateCards(cardOne, cardThree);

        // let the user know if all cases passed or not
        if (failed > 0) {
            System.err.println(failed + " CASE(S) FAILED");
        } else {
            System.out.println("ALL CASES PASSED (" + passed + " CASES)");
        }
    }

    /**
     * This method will test two different cards against each other.
     *
     * @param cardA a card used for testing against a card
     * @param cardB a different card from cardA used for testing
     */
    public void testDifferentCards(Card cardA, Card cardB) {
        System.out.println("Testing High Level Cards (getStrength)...");
        // check that the test cards actually have the correct strength (based on face value)
        if (cardA.getStrength() == 9 && cardB.getStrength() == 8) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed getStrength");
            failed++;
        }

        System.out.println("Testing High Level Cards (getWorth)...");
        // check that the card worth (point value) is correct
        if (cardA.getWorth() == 11 && cardB.getWorth() == 10) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed getWorth");
            failed++;
        }

        System.out.println("Testing High Level Cards (getFaceName)...");
        // check that the correct FaceName was applied to both cards
        if (cardA.getFaceName() == Card.FaceName.Ace && cardB.getFaceName() == Card.FaceName.Three) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed getFaceName");
            failed++;
        }

        System.out.println("Testing High Level Cards (getSuit)...");
        // test that the correct suit was assigned to each test card
        if (cardA.getSuit() == Card.Suit.Coins && cardB.getSuit() == Card.Suit.Sticks) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed getSuit");
            failed++;
        }

        // test that the correct image was truly assigned (with test images)
        System.out.println("Testing Get Image Path (getImage)...");
        if (cardA.getImage().getImage().equals(testIcon.getImage())) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed getImage");
            failed++;
        }

        // test that the image icons don't equal each other
        if (!cardA.getImage().getImage().equals(testIconTwo.getImage())) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed getImage");
            failed++;
        }

        System.out.println("Testing To String (toString)...");
        // test that the to String method returns the correct string name
        if (cardA.toString().equals("Ace of Coins")) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed toString");
            failed++;
        }

        // test it for the other card as well
        if (cardB.toString().equals("Three of Sticks")) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed toString");
            failed++;
        }
    }

    /**
     * This method will test cards that are identical to each other (meaning that they
     * are equivalent to two different decks that are identical physical cards).
     *
     * @param cardA a card that is equivalent to cardB
     * @param cardB a card that is equivalent to cardA
     */
    public void testDuplicateCards(Card cardA, Card cardB) {
        System.out.println("Testing Duplicate Cards (getStrength)...");
        // test two identical cards' get strength (faceName value)
        if (cardA.getStrength() == 9 && cardB.getStrength() == 9) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed getStrength");
            failed++;
        }

        System.out.println("Testing Duplicated Cards (getWorth)...");
        // test that the worth is the same (card point value)
        if (cardA.getWorth() == 11 && cardB.getWorth() == 11) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed getWorth");
            failed++;
        }

        System.out.println("Testing Duplicate Cards (getFaceName)...");
        // check that the faceNames are the same
        if (cardA.getFaceName() == Card.FaceName.Ace && cardB.getFaceName() == Card.FaceName.Ace) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed getFaceName");
            failed++;
        }

        System.out.println("Testing Duplicate Cards (getSuit)...");
        // check that the card suits match
        if (cardA.getSuit() == Card.Suit.Coins && cardB.getSuit() == Card.Suit.Coins) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed getSuit");
            failed++;
        }

        System.out.println("Testing Duplicate Image Path (getImage)...");
        // check that the images are the same
        if (cardA.getImage().getImage().equals(testIcon.getImage())) {
            if (cardB.getImage().getImage().equals(testIcon.getImage())) {
                System.out.println("   pass");
                passed++;
            }
        } else {
            System.err.println("   failed getImage");
            failed++;
        }

        // check that the image doesn't equal the wrong image
        if (!cardA.getImage().getImage().equals(testIconTwo.getImage())) {
            if (!cardB.getImage().getImage().equals(testIconTwo.getImage())) {
                System.out.println("   pass");
                passed++;
            }
        } else {
            System.err.println("   failed getImage");
            failed++;
        }

        System.out.println("Testing Duplicate To String (toString)...");
        // check that the to string is correc
        if (cardA.toString().equals("Ace of Coins")) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed toString");
            failed++;
        }

        if (cardB.toString().equals("Ace of Coins")) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed toString");
            failed++;
        }
    }
}