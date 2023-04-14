package unitTests;

import gameStructure.Card;
import gameStructure.Pile;
import java.util.ArrayList;
import java.util.List;

/**
 * This method tests the methods within Pile.java. The program will print the number of passed/failed cases.
 *
 * @author abbybrown
 * @see gameStructure.Pile
 */

public class PileTests {

    /**
     * Main function will instantiate our class.
     */
    public static void main(String[] args) {
        new PileTests();
    }
    private int passed = 0;
    private int failed = 0;

    /**
     * The Constructor will implement the methods within PileTests.java. The Constructor
     * will print the number of passed/failed cases.
     */
    public PileTests() {
        testPile();

        if (failed > 0) {
            System.err.println(failed + " CASE(S) FAILED");
        } else {
            System.out.println("ALL CASES PASSED (" + passed + " CASES)");
        }
    }

    /**
     * The testPile method will test all the methods in Pile.java.
     *
     * @see gameStructure.Pile
     */
    public void testPile() {
        // instantiate the cards
        Card cardA = new Card(Card.Suit.Coins, Card.FaceName.Ace);
        Card cardB = new Card(Card.Suit.Swords, Card.FaceName.Four);
        Card cardC = new Card(Card.Suit.Sticks, Card.FaceName.Horse);
        Card cardD = new Card(Card.Suit.Cups, Card.FaceName.Three);
        Card cardE = new Card(Card.Suit.Coins, Card.FaceName.Six);
        Card cardF = new Card(Card.Suit.Cups, Card.FaceName.King);

        // create a pile and array to compare pile to array
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

        // check that card in arr and pile are the same
        for (int i = 0; i < pile.getPile().size(); i++) {
            if (pile.getPile().get(i) == testArr.get(i)) {
                count++;
            }
        }

        // all 6 cards should match
        if (count == 6) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed addCard");
            failed++;
        }

        // check that the size is accurate
        if (pile.getPile().size() == 6) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed getSize");
            failed++;
        }

        System.out.println("Testing getPoints...");
        int points = pile.getPoints();

        // the point values should add up to 28
        if (points == 28) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed getPoints");
            failed++;
        }

        System.out.println("Testing startOver...");
        pile.startOver();

        // start over should delete the cards in pile
        if (pile.getPile().size() == 0) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed startOver");
            failed++;
        }

        // pile points should be zero
        if (pile.getPoints() == 0) {
            System.out.println("   pass");
            passed++;
        } else {
            System.err.println("   failed getPoints");
            failed++;
        }
    }
}
