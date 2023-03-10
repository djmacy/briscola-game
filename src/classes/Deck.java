import java.util.*;

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

    public Card lookTopCard() {
        if (deck.size() > 0) {
            Card card = deck.get(0);
            return card;
        } else {
            return null;
        }
    }

    public Card getTopCard() {
        if (deck.size() > 0) {
            Card topCard = deck.remove(0);
            return topCard;
        } else {
            return null;
        }
    }

    public void dealTopCard(Hand hand) {
        Card topCard = deck.remove(0);
        hand.getHand().add(topCard);
    }

    public Card dealCards(Hand hand1, Hand hand2) {
        Card cardOne = deck.remove(0);
        Card cardTwo = deck.remove(0);
        Card cardThree = deck.remove(0);
        Card cardFour = deck.remove(0);
        Card cardFive = deck.remove(0);
        Card cardSix = deck.remove(0);
        Card trumpSuit = deck.remove(0);
        hand1.hand.add(cardOne);
        hand1.hand.add(cardThree);
        hand1.hand.add(cardFive);
        hand2.hand.add(cardTwo);
        hand2.hand.add(cardFour);
        hand2.hand.add(cardSix);
        return trumpSuit;
    }

}
