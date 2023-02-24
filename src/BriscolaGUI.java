import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class BriscolaGUI extends JFrame {
    private JFrame frame;
    private JPanel panel;
    private JButton topCardButton;
    private JLabel topCardLabel;
    private Deck deck;
    private final int gameHeight = 1000;
    private final int gameWidth = 1000;
    private int scaledWidth = 200;
    private int scaledHeight;
    private Card topCard;

    public BriscolaGUI() {

        deck = new Deck();
        topCard = deck.getTopCard();

        frame = new JFrame("Briscola");
        frame.setSize(gameWidth, gameHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(null);
        contentPane.setBackground(Color.red);

        topCardButton = new JButton("Show Top Card");
        topCardButton.setBounds(gameWidth / 2 - 150 / 2, 700, 150, 50);
        contentPane.add(topCardButton);

        //use the method below to scale the image
        JLabel topCardPic = new JLabel();

        //making back of card image
        ImageIcon backOfCard = new ImageIcon("src/images/backOfCard.png");
        scaledHeight = (int) ((double) scaledWidth / backOfCard.getIconWidth() * backOfCard.getIconHeight());
        Image scaledImage = backOfCard.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        //add the back of card image to the screen
        JLabel backOfCardPic = new JLabel(scaledIcon);
        backOfCardPic.setBounds(100,150, scaledWidth,scaledHeight);
        contentPane.add(backOfCardPic);

        topCardButton.addActionListener(e -> {
            topCardPic.setIcon(new ImageIcon(scaleImage(topCard).getImage()));
            topCardPic.setBounds(300, 150, scaledWidth, scaledHeight);
            contentPane.add(topCardPic);
            topCard = deck.getTopCard();


            System.out.println(deck.getDeck().size());
        });
        // Set the frame to be visible
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new BriscolaGUI();
    }

    public ImageIcon scaleImage(Card topCard) {
        ImageIcon originalImage = new ImageIcon(topCard.getImage().getImage());
        // Scale the image to a smaller size
        scaledHeight = (int) ((double) scaledWidth / originalImage.getIconWidth() * originalImage.getIconHeight());
        Image scaledImage = originalImage.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        return scaledIcon;
    }

}
