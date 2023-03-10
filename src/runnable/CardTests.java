package runnable;

import classes.Card;

public class CardTests {

    public static Card cardOne = new Card(Card.Suit.Coins, Card.FaceName.Ace, "txt");
    public static Card cardTwo = new Card(Card.Suit.Sticks, Card.FaceName.Three, "txt");

    public static void main(String[] args) {
        testHighestCards(cardOne, cardTwo);
    }

    public static void testHighestCards(Card cardA, Card cardB) {
        System.out.println("Testing High Level Cards (getStrength)...");
        if (cardA.getStrength() == 9 && cardB.getStrength() == 8) {

            System.out.println("Testing High Level Cards (getWorth)...");
            if (cardA.getWorth() == 11 && cardB.getWorth() == 10) {
                System.out.println("   pass");
            } else {
                System.err.println("   failed getWorth");
            }

            System.out.println("Testing High Level Cards (getFaceName)...");
            if (cardA.getFaceName() == Card.FaceName.Ace && cardB.getFaceName() == Card.FaceName.Three) {
                System.out.println("   pass");
            } else {
                System.err.println("   failed getFaceName");
            }

            System.out.println("Testing High Level Cards (getSuit)...");
            if (cardA.getSuit() == Card.Suit.Coins && cardB.getSuit() == Card.Suit.Sticks) {
                System.out.println("   pass");
            } else {
                System.err.println("   failed getSuit");
            }
        }
    }
}