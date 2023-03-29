/**
 * @David & Abby
 *
 * This is the Card class which defines the card objects for the
 * rest of the program. The cards objects all have a Suit, Facename,
 * and an ImageIcon associated with them. The suits and facenames
 * are made with enumerations, where the facenames have different
 * values associated with them. Specifically the strength of the
 * facename and the worth of the facename.
 */

package gameStructure;
import javax.swing.ImageIcon;

public class Card {
    private final Suit suit;
    private final FaceName faceName;
    private final ImageIcon image;

    /**
     * Creates the suits for the cards.
     *
     * @return Suit (Coins, Sticks, Cups, Swords)
     */
    public enum Suit {
        Coins, Sticks, Cups, Swords
    }

    /**
     * Creates the facenames for the cards. Each facename has a
     * worth and strength associated with it. The first digit represents
     * the worth, while the second is the strength.
     *
     * @return FaceName (Ace, Two, Three, Four, Five, Six, Seven, Jack, Horse, King).
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
         * Constructor for the FaceName enum. Allows us to create a getCardWorth or getCardStrength method for cards.
         *
         * @param cardWorth
         * @param cardStrength
         */

        FaceName(int cardWorth, int cardStrength) {
            this.cardWorth = cardWorth;
            this.cardStrength = cardStrength;
        }

        /**
         * Returns the worth of a given FaceName.
         *
         * @return the cardWorth of the FaceName
         */
        public int getWorth() {
            return cardWorth;
        }

        /**
         * Returns the strength of a given FaceName.
         *
         * @return the cardStrength of the FaceName
         */
        public int getStrength() {
            return cardStrength;
        }
    }

    /**
     * Constructor for the card.
     *
     * @param suit
     * @param faceName
     * @param imagePath
     */
    public Card(Suit suit, FaceName faceName, String imagePath) {
        this.suit = suit;
        this.faceName = faceName;
        this.image = new ImageIcon(imagePath);
    }

    /**
     * Returns the suit of a card.
     *
     * @return suit of card
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Returns the faceName of a card.
     *
     * @return faceName of card
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
     * @return the faceName and suit of a card
     */
    public String toString() {
        return this.faceName + " of " + this.suit;
    }
}
