package gameStructure;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import java.io.IOException;

public class BriscolaCards extends Sprite {
    private static BufferedImage cardImage;

    private final String face;
    private final String suit;
    private final int value;

    private Rectangle boundingBox;
    private boolean selected;

    public BriscolaCards(String cardFace, String cardSuit) {
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

        if (cardImage == null) {
            final String resource = "/images/sprite.png";
            try {
                cardImage = ImageIO.read(this.getClass().getResource(resource));
                setWidth(cardImage.getWidth() / 14);
                setHeight(cardImage.getHeight() / 4);
            } catch (IllegalArgumentException e) {
                System.out.println("Image '" + resource + "' not found.");
            } catch (IOException e) {
                System.out.println("Image '" + resource + "' not found.");
            }
        }
        boundingBox = new Rectangle(getX(), getY(), getWidth(), getHeight());
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

    public boolean contains(Point p) {
        return boundingBox.contains(p);
    }

    public void moveTo(int x, int y) {
        setX(x);
        setY(y);
        updateBoundingBox();
    }

    public void moveBy(int deltaX, int deltaY) {
        moveTo(getX() + deltaX, getY() + deltaY);
    }

    private void updateBoundingBox() {
        boundingBox.setBounds(getX(),  getY(), getWidth(), getHeight());
    }

    public int compareTo(BriscolaCards c) {
        if (c == null)
            return 1;
        return this.value - c.getValue();
    }

    @Override
    public void draw(Graphics g) {
        if (cardImage != null) {
            int srcHeight = cardImage.getHeight() / 4;
            int srcWidth = cardImage.getWidth() / 14;

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

            int col = (value - 1);

            g.drawImage(cardImage, getX(), getY(), getX() + getWidth(), getY() + getHeight(),
                    col * srcWidth, row * srcHeight, col * srcWidth + srcWidth, row * srcHeight + srcHeight, null);
        }
    }

    public String toString() {
        return face + " of " + suit;
    }
}
