package gameStructure;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import java.io.IOException;

/**
 * The Sprite class is the most generic class of drawable objects. We
 * will use this as a parent class for all of our drawable/movable
 * objects in the future.
 *
 * Created:     3/19/2020
 * Modified:    4/5/2023
 * @author      Ted Wendt/Nate Williams/Abby Brown
 */

public class Sprite {
    // All we need to know about this is where it is and what color it is.
    private String name;
    private int xCoord;
    private int yCoord;
    private int width;
    private int height;
    private BufferedImage img;
    private Color currentColor;

    /**
     * Default constructor.  Set position to (1,1), the width and height to 10, and the color to BLACK.
     */
    public Sprite() {
        this(1, 1, 10, 10, "UnnamedSprite", Color.BLACK, null);
    }

    /**
     * Constructor.  Set the Sprite location to (x,y), name to "Unnamed Sprite", and default color to BLUE
     * @param x
     * @param y
     */
    public Sprite(int x, int y) {
        this(x, y, 10, 10, "Unnamed Sprite", Color.BLUE, null);
    }

    /**
     * Constructor.  Set the Sprite location to (x,y), name to specified value, and color to RED.
     * @param x
     * @param y
     * @param name
     */
    public Sprite(int x, int y, String name) {
        this(x, y, 10, 10, name, Color.RED, null);
    }

    /**
     * Standard Constructor.  Set the Sprite location to (x,y) with width w and height h, name to specified value, and color to c.
     * @param x
     * @param y
     * @param w
     * @param h
     * @param name
     * @param c
     * @param imageName
     */
    public Sprite(int x, int y, int w, int h, String name, Color c, String imageName) {
        this.name = name;
        this.xCoord = x;
        this.yCoord = y;
        this.height = h;
        this.width = w;
        this.loadImage(imageName);
        currentColor = c;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return xCoord;
    }

    public void setX(int x) {
        this.xCoord = x;
    }

    public int getY() {
        return yCoord;
    }

    public void setY(int y) {
        this.yCoord = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int w) {
        if (w <= 0)
            throw new IllegalArgumentException("Width must be a positive integer value.");
        this.width = w;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int h) {
        if (h <= 0)
            throw new IllegalArgumentException("Height must be a positive integer value.");
        this.height = h;
    }

    public Color getColor() {
        return currentColor;
    }

    public void setColor(Color c) {
        this.currentColor = c;
    }

    public BufferedImage getImage() {
        return img;
    }

    public void loadImage(String fileName) {
        if (fileName != null) {
            final String resource = "/data/" + fileName;
            try {
                img = ImageIO.read(this.getClass().getResource(resource));
            } catch (IOException e) {
                System.out.println("Image not found at '" + resource + "'");
            }
        }
    }

    public void moveRight() {
        xCoord++;
    }

    public void moveLeft() {
        xCoord--;
    }

    public void moveUp() {
        yCoord--;
    }

    public void moveDown() {
        yCoord++;
    }

    public void setCoords(int x, int y) {
        xCoord = x;
        yCoord = y;
    }

    /**
     * Ask the Sprite to report its current location as an array of integers.
-     * @return    an array of integers describing the Sprite's current location.
     */
    public int[] whereAreYou() {
        return new int[] {xCoord, yCoord};
    }

    public void draw(Graphics g) {
        g.setColor(currentColor);
        if (img == null)
            g.fillRect(xCoord, yCoord, width, height);
        else
            g.drawImage(img, xCoord, yCoord, width, height, null);
        g.drawString(name, xCoord - 10,  yCoord - 10);
    }
}
