package gameStructure;
import javax.swing.ImageIcon;

/**
 * Creates a card object that will have a unique suit and faceName associated with it based on the Italian card game
 * Briscola. Each card can be a Coin, Stick, Sword, or Cup with faceNames 2-7, jack, horse, king, and ace. Each faceName
 * will have a worth and strength associated with the card. Worth is used to count up points at the end of the game
 * whereas strength decides which card wins when the cards are played during the game.
 * @author David and Abby
 */

public class Card {
    private final Suit suit;
    private final FaceName faceName;
    private final ImageIcon image;

    /**
     * Describes the possible suits for the cards.
     */
    public enum Suit {
        Coins, Sticks, Cups, Swords
    }

    /**
     * Describes the possible facenames for the cards. Each facename has a
     * worth and strength associated with it. The first digit represents
     * the worth, while the second is the strength.
     */
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

        King(4, 7);

        private final int cardWorth;
        private final int cardStrength;

        /**
         * Constructor for the FaceName enum.
         *
         * @param cardWorth worth of the card
         * @param cardStrength strength of the card
         */
        FaceName(int cardWorth, int cardStrength) {
            this.cardWorth = cardWorth;
            this.cardStrength = cardStrength;
        }

        /**
         * Returns the worth of a given FaceName.
         *
         * @return the worth of the FaceName
         */
        private int getWorth() {
            return cardWorth;
        }

        /**
         * Returns the strength of a given FaceName.
         *
         * @return the strength of the FaceName
         */
        private int getStrength() {
            return cardStrength;
        }
    }

    /**
     * Constructor for the card.
     *
     * @param suit the associated  {@link Suit suit} of the card
     * @param faceName the associated {@link FaceName face name} of the card
     * @param imagePath the associated image of the card
     */
    public Card(Suit suit, FaceName faceName, String imagePath) {
        this.suit = suit;
        this.faceName = faceName;
        this.image = new ImageIcon(imagePath);
    }

    /**
     * Returns the suit of a card.
     *
     * @return {@link Suit suit} of card
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Returns the faceName of a card.
     *
     * @return {@link FaceName faceName} of card
     */
    public FaceName getFaceName() {
        return faceName;
    }

    /**
     * Returns the strength of a card.
     *
     * @return strength of a card
     */
    public int getStrength() {
        return faceName.getStrength();
    }

    /**
     * Returns the worth of a card.
     *
     * @return worth of a card.
     */
    public int getWorth() {
        return faceName.getWorth();
    }

    /**
     * Returns image of a card.
     *
     * @return image of card
     */
    public ImageIcon getImage() {
        return image;
    }

    /**
     * A toString method for cards.
     *
     * @return unique string description of a card
     */
    public String toString() {
        return this.faceName + " of " + this.suit;
    }
}
