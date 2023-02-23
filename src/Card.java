import javax.swing.ImageIcon;
import java.util.Hashtable;

public class Card {
    private final Suit suit;
    private final FaceName faceName;
    private final ImageIcon image;
//    private final Hashtable<Card, Integer> deck = makeDeck();

    public enum Suit {
        Coins, Sticks, Cups, Swords
    }

    // Added a comment
    public enum FaceName {
        Ace, Two, Three, Four, Five, Six, Seven, Jack, Horse, King
    }

    public Card(Suit suit, FaceName faceName, String imagePath) {
        this.suit = suit;
        this.faceName = faceName;
        this.image = new ImageIcon(imagePath);
    }

//    public Hashtable makeDeck() {
//        Hashtable<Card, Integer> deck = new Hashtable<>();
//
//
//
//        return deck;
//    }

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
