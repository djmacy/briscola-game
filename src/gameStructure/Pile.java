package gameStructure;

import java.util.ArrayList;
import java.util.List;

public class Pile {
    List<Card> pile;

    public Pile() {
        this.pile = new ArrayList<Card>();
    }

    public List<Card> getPile() {
        return pile;
    }

    public void startOver() {
        pile.clear();
    }

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
