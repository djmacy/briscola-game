package gameStructure;

import java.util.ArrayList;
import java.util.List;

public class Pile {
    List<Card> pile;

    /**
     * Pile constructor method.
     */
    public Pile() {
        this.pile = new ArrayList<Card>();
    }

    /**
     * Returns the pile object used to keep track of all the cards won by each player.
     *
     * @return pile object
     */
    public List<Card> getPile() {
        return pile;
    }

    /**
     * clears the pile object of all the cards
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
}
