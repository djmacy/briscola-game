package gameStructure;

import java.awt.Point;
import java.awt.image.BufferedImage;

public class BriscolaCard extends Sprite {
    private final String face;
    private final String suit;
    private final int value;
    private boolean selected;

    public BriscolaCard(String cardFace, String cardSuit) {
        super();
        this.face = cardFace;
        this.suit = cardSuit;
        this.setName(cardFace + " of " + cardSuit);
        this.selected = false;

        switch (cardFace) {
            case "Ace":
                value = 1;
                break;

            case "King":
                value = 13;
                break;

            case "Horse":
                value = 12;
                break;

            case "Jack":
                value = 11;
                break;

            case "Seven":
                value = 7;
                break;

            case "Six":
                value = 6;
                break;

            case "Five":
                value = 5;
                break;

            case "Four":
                value = 4;
                break;

            case "Three":
                value = 3;
                break;

            case "Two":
                value = 2;
                break;

            default:
                value = 0;
                break;
        }

        loadImage("/images/sprite.png");
        BufferedImage img = getImage();
        setHeight(img.getHeight() / 4);
        setWidth(img.getWidth() / 14);

        int row;

        switch (suit) {
            case "Coins":
                row = 0;
                break;

            case "Sticks":
                row = 1;
                break;

            case "Cups":
                row = 2;
                break;

            case "Swords":
                row = 3;
                break;

            default:
                row = 4;
        }

        int col = value - 1;
        setXOffset(col * getWidth() - 2);
        setYOffset(row * getHeight());
    }

    public int getValue() {
        return value;
    }

    public void clicked(Point p) {
        System.out.println(getName() + " was clicked at coordinates (" + p.getX() + ", " + p.getY() + ").");
    }

    public void select() {
        selected = true;
    }

    public void deselect() {
        selected = false;
    }

    public boolean isSelected() {
        return selected;
    }

    public int compareTo(BriscolaCard c) {
        if (c == null)
            return 1;
        return this.value - c.getValue();
    }

    public String toString() {
        return face + " of " + suit;
    }
}
