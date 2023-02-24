import javax.swing.ImageIcon;

public class Card {
    private final Suit suit;
    private final FaceName faceName;
    private final ImageIcon image;

    public enum Suit {
        Coins,
        Sticks,
        Cups,
        Swords
    }

    public enum FaceName {
        Ace(11, 9),
        Two(0, 0),
        Three(10, 8),
        Four(0, 1),
        Five(0, 2),
        Six(0, 3),
        Seven(0, 4),
        Jack(2, 5),
        Horse(3, 6),
        King(4,7);

        private int cardWorth;

        private int CardStrength;

        FaceName(int cardWorth, int CardStrength) {
            this.cardWorth = cardWorth;
            this.CardStrength = CardStrength;

        }

        public int getCardStrength() {
            return CardStrength;
        }

        public int getCardWorth() {
            return cardWorth;
        }
    }


    public Card(Suit suit, FaceName faceName, String imagePath) {
        this.suit = suit;
        this.faceName = faceName;
        this.image = new ImageIcon(imagePath);
    }

    public Suit getSuit() {
        return suit;
    }

    public FaceName getFaceName() {
        return faceName;
    }

    public ImageIcon getImage() {
        return image;
    }

    public String toString() {
        return this.faceName + " of " + this.suit;
    }
}
