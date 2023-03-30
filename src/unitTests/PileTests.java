package unitTests;

import gameStructure.Card;
import gameStructure.Pile;

import java.util.ArrayList;
import java.util.List;

public class PileTests {
    public static void main(String[] args) {
        new PileTests();
    }
    private int passed = 0;
    private int failed = 0;

    public PileTests() {
        testPile();

        if (failed > 0) {
            System.err.println(failed + " CASE(S) FAILED");
        } else {
            System.out.println("ALL CASES PASSED (" + passed + " CASES)");
        }
    }

    public void testPile() {
        Card cardA = new Card(Card.Suit.Coins, Card.FaceName.Ace, "test.png");
        Card cardB = new Card(Card.Suit.Swords, Card.FaceName.Four, "test.png");
        Card cardC = new Card(Card.Suit.Sticks, Card.FaceName.Horse, "test2.png");
        Card cardD = new Card(Card.Suit.Cups, Card.FaceName.Three, "test2.png");
        Card cardE = new Card(Card.Suit.Coins, Card.FaceName.Six, "test.png");
        Card cardF = new Card(Card.Suit.Cups, Card.FaceName.King, "test2.png");

        System.out.println("Testing addCard...");
        Pile pile = new Pile();
        List<Card> testArr = new ArrayList<>();
        int count = 0;

        pile.addCard(cardA);
        pile.addCard(cardB);
        pile.addCard(cardC);
        pile.addCard(cardD);
        pile.addCard(cardE);
        pile.addCard(cardF);

        testArr.add(cardA);
        testArr.add(cardB);
        testArr.add(cardC);
        testArr.add(cardD);
        testArr.add(cardE);
        testArr.add(cardF);

        for (int i = 0; i < pile.getPile().size(); i++) {
            if (pile.getPile().get(i) == testArr.get(i)) {
                count++;
            }
        }

        if (count == 6) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed addCard");
            failed++;
        }

        if (pile.getPile().size() == 6) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed getSize");
            failed++;
        }

        System.out.println("Testing getPoints...");
        int points = pile.getPoints();

        if (points == 28) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed getPoints");
            failed++;
        }

        System.out.println("Testing startOver...");
        pile.startOver();

        if (pile.getPile().size() == 0) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed startOver");
            failed++;
        }

        if (pile.getPoints() == 0) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed getPoints");
            failed++;
        }
    }
}
