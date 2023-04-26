package gameStructure;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates a list of cards that will represent the pile where all cards won will end up. Once the game is finished all
 * cards values are counted up to see who scores more points. More than 60 points guarantees a win while 60 is a draw.
 * @author David
 */

public class Pile {
    private final List<Card> pile;

    /**
     * Creates the pile object. It creates a list of cards.
     */
    public Pile() {
        this.pile = new ArrayList<>();
    }

    /**
     * Returns a list of cards which represents the pile used to keep track of all the cards won by each player.
     *
     * @return list of cards
     */
    public List<Card> getPile() {
        return pile;
    }

    /**
     * Clears the cards in the pile.
     */
    public void startOver() {
        pile.clear();
    }

    /**
     * Loops through all the cards in a pile and gets the total points earned by a player.
     *
     * @return pile points
     */
    public int getPoints() {
        int points = 0;
        if (pile.size() == 0) {
            points = 0;
        } else {
            for (Card card : pile) {
                points += card.getWorth();
            }
        }
        return points;
    }

    public void addCard(Card card) {
        pile.add(card);
    }
}
