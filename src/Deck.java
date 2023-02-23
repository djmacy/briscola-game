import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private final List<Card> deck;

    public Deck() {
        this.deck = new ArrayList<>();
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.FaceName faceName : Card.FaceName.values()) {
                String imagePath = "src/images/" + faceName.name().toLowerCase() + "_of_" + suit.name().toLowerCase() + ".png";
                Card card = new Card(suit, faceName, imagePath);
                this.deck.add(card);
            }
        }
        Collections.shuffle(this.deck);
    }

    public List<Card> getDeck() {
        return this.deck;
    }

    public Card getTopCard() {
        Card topCard = deck.remove(0);
        return topCard;
    }

    public void dealTopCard(Hand hand) {
        Card topCard = deck.remove(0);
        hand.getHand().add(topCard);
    }

}
