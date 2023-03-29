package unitTests;

/**
 * @file CardTests.java
 * @author abbybrown
 * @date 03/29/23
 *
 *      This class will thoroughly test the methods within Card.java. Methods tested include
 *      getSuit(), getFaceNAme(), getStrength(), getWorth(), getImage(), and toString(). Program
 *      will print the number of passed/failed cases.
 */

import gameStructure.Card;

import javax.swing.*;

public class CardTests {

    /**
     * Main method will instantiate our constructor, which will ultimately print the number
     * of passed/failed cases.
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
     * Constructor that implements the testHighestCards and testDuplicateCards methods.
     * The constructor will print the number of passed and failed cases.
     */
    public CardTests() {
        testHighestCards(cardOne, cardTwo);
        testDuplicateCards(cardOne, cardThree);

        if (failed > 0) {
            System.err.println(failed + " CASE(S) FAILED");
        } else {
            System.out.println("ALL CASES PASSED (" + passed + " CASES)");
        }
    }

    /**
     * This method will test two different cards against each other and use methods from Card.java.
     * Methods include getSuit(), getFaceNAme(), getStrength(), getWorth(), getImage(), and toString().
     *
     * @param cardA a card used for testing against
     * @param cardB a very different card from cardA used for testing
     */
    public void testHighestCards(Card cardA, Card cardB) {
        System.out.println("Testing High Level Cards (getStrength)...");
        if (cardA.getStrength() == 9 && cardB.getStrength() == 8) {
            System.out.println("   pass");
            passed++;
            System.out.println("Testing High Level Cards (getWorth)...");
            if (cardA.getWorth() == 11 && cardB.getWorth() == 10) {
                System.out.println("   pass");
                passed++;
            } else {
                System.err.println("   failed getWorth");
                failed++;
            }
        } else {
            System.err.println("   failed getStrength");
            failed++;
        }

        System.out.println("Testing High Level Cards (getFaceName)...");
        if (cardA.getFaceName() == Card.FaceName.Ace && cardB.getFaceName() == Card.FaceName.Three) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed getFaceName");
            failed++;
        }

        System.out.println("Testing High Level Cards (getSuit)...");
        if (cardA.getSuit() == Card.Suit.Coins && cardB.getSuit() == Card.Suit.Sticks) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed getSuit");
            failed++;
        }

        System.out.println("Testing Get Image Path (getImage)...");
        if (cardA.getImage().getImage().equals(testIcon.getImage())) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed getImage");
            failed++;
        }

        if (!cardA.getImage().getImage().equals(testIconTwo.getImage())) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed getImage");
            failed++;
        }

        System.out.println("Testing To String (toString)...");
        if (cardA.toString().equals("Ace of Coins")) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed toString");
            failed++;
        }

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
     * are equivalent to two identical physical cards). Methods tested include
     * getSuit(), getFaceNAme(), getStrength(), getWorth(), getImage(), and toString().
     *
     * @param cardA a card that is equal to cardB
     * @param cardB a card that is equal to cardA
     */
    public void testDuplicateCards(Card cardA, Card cardB) {
        System.out.println("Testing Duplicate Cards (getStrength)...");
        if (cardA.getStrength() == 9 && cardB.getStrength() == 9) {
            System.out.println("   pass");
            passed++;
            System.out.println("Testing Duplicated Cards (getWorth)...");
            if (cardA.getWorth() == 11 && cardB.getWorth() == 11) {
                System.out.println("   pass");
                passed++;
            } else {
                System.err.println("   failed getWorth");
                failed++;
            }
        } else {
            System.err.println("   failed getStrength");
            failed++;
        }

        System.out.println("Testing Duplicate Cards (getFaceName)...");
        if (cardA.getFaceName() == Card.FaceName.Ace && cardB.getFaceName() == Card.FaceName.Ace) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed getFaceName");
            failed++;
        }

        System.out.println("Testing Duplicate Cards (getSuit)...");
        if (cardA.getSuit() == Card.Suit.Coins && cardB.getSuit() == Card.Suit.Coins) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed getSuit");
            failed++;
        }

        System.out.println("Testing Duplicate Image Path (getImage)...");
        if (cardA.getImage().getImage().equals(testIcon.getImage())) {
            if (cardB.getImage().getImage().equals(testIcon.getImage())) {
                System.out.println("   pass");
                passed++;
            }
        } else {
            System.err.println("   failed getImage");
            failed++;
        }

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