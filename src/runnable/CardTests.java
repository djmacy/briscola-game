
public class CardTests {

    public static Card cardOne = new Card(Card.Suit.Coins, Card.FaceName.Ace, "txt");
    public static Card cardTwo = new Card(Card.Suit.Sticks, Card.FaceName.Three, "txt");

    public static void main(String[] args) {
        testHighestCards(cardOne, cardTwo);
    }

    public static void testHighestCards(Card card, Card other) {
        System.out.println("Testing High Level Cards (getStrength)...");
        if (card.getStrength() == 9 && other.getStrength() == 8) {
            System.out.println("   pass");
        } else {
            System.err.println("   failed getStrength");
        }



    }
}