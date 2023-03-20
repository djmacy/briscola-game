package runnable;

import classes.Card;

import javax.swing.*;

public class CardTests {

    public static void main(String[] args) {
        new CardTests();
    }

    public Card cardOne = new Card(Card.Suit.Coins, Card.FaceName.Ace, "test.png");
    public Card cardTwo = new Card(Card.Suit.Sticks, Card.FaceName.Three, "test2.png");
    //    cardThree is a duplicate of cardOne to see how the program behaves with identical card types
    public Card cardThree = new Card(Card.Suit.Coins, Card.FaceName.Ace, "test.png");
    private ImageIcon testIcon = new ImageIcon("test.png");
    private int passed = 0;
    private int failed = 0;

    public CardTests() {
        testHighestCards(cardOne, cardTwo, cardThree);
        if (failed > 0) {
            System.err.println(failed + " CASE(S) FAILED");
        } else {
            System.out.println("ALL CASES PASSED (" + passed + " CASES)");
        }
    }

    public void testHighestCards(Card cardA, Card cardB, Card cardC) {
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
            System.out.println(cardA.getImage());
            failed++;
        }

        System.out.println("Testing To String (toString)...");
    }
}