import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private final List<Card> cards;

    public Deck() {
        this.cards = new ArrayList<>();
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.FaceName faceName : Card.FaceName.values()) {
                String imagePath = "src/images/" + faceName.name().toLowerCase() + "_of_" + suit.name().toLowerCase() + ".png";
                Card card = new Card(suit, faceName, imagePath);
                this.cards.add(card);
            }
        }
        Collections.shuffle(this.cards);
    }

    public List<Card> getCards() {
        return this.cards;
    }

    public Card getTopCard() {
        Card topCard = cards.get(0);
        cards.remove(0);
        return topCard;
    }

}
