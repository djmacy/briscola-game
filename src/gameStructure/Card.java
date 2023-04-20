package gameStructure;
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;

/**
 * Creates a card object that will have a unique suit and faceName associated with it based on the Italian card game
 * Briscola. Each card can be a Coin, Stick, Sword, or Cup with faceNames 2-7, jack, horse, king, and ace. Each faceName
 * will have a worth and strength associated with the card. Worth is used to count up points at the end of the game
 * whereas strength decides which card wins when the cards are played during the game.
 * @author David & Abby
 */

public class Card extends Sprite {
    private static final String SPRITE_SHEET_IMAGE = "/images/sprite.png";
    private final Suit suit;
    private final FaceName faceName;

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
        King(4, 7),
        Back(0, 0);

        private final int cardWorth;
        private final int cardStrength;

        /**
         * Constructor for the FaceName enum.
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
         * @return the worth of the FaceName
         */
        public int getWorth() {
            return cardWorth;
        }

        /**
         * Returns the strength of a given FaceName.
         *
         * @return the strength of the FaceName
         */
        public int getStrength() {
            return cardStrength;
        }
    }

    public Card(Suit suit, FaceName faceName) {
        this.suit = suit;
        this.faceName = faceName;
        loadImage(SPRITE_SHEET_IMAGE);
        initialize();
    }

    /**
     * Constructor for the card.
     *
     * @param suit the associated  {@link Suit suit} of the card
     * @param faceName
     */
    public Card(Suit suit, FaceName faceName, BufferedImage img) {
        this.suit = suit;
        this.faceName = faceName;
        setImage(img);
        initialize();
    }

    private void initialize() {
        int col;

        switch (faceName) {
            case Ace:
                col = 0;
                break;

            case King:
                col = 12;
                break;

            case Horse:
                col = 11;
                break;

            case Jack:
                col = 10;
                break;

            case Seven:
                col = 6;
                break;

            case Six:
                col = 5;
                break;

            case Five:
                col = 4;
                break;

            case Four:
                col = 3;
                break;

            case Three:
                col = 2;
                break;

            case Two:
                col = 1;
                break;

            case Back:
            default:
                col = 13;
                break;
        }

        int row;

        switch (suit) {
            case Coins:
                row = 0;
                break;

            case Sticks:
                row = 1;
                break;

            case Cups:
                row = 2;
                break;

            case Swords:
            default:
                row = 3;
                break;
        }
        setHeight(getImage().getHeight() / 4);
        setWidth(getImage().getWidth() / 14);
        setXOffset(col * getWidth());
        setYOffset(row * getHeight());
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
     * A toString method for cards.
     *
     * @return unique string description of a card
     */
    public String toString() {
        return this.faceName + " of " + this.suit;
    }

    public ImageIcon getImage(Card card) {
        return new ImageIcon(card.getImage());
    }
}
