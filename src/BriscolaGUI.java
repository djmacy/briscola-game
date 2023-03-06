import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

// A comment
public class BriscolaGUI extends JFrame {
    private JFrame frame;
    private JPanel panel;
    private JButton topCardButton;
    private JButton player1Card1Button;
    private JButton player1Card2Button;
    private JButton player1Card3Button;
    private JLabel topCardLabel;
    private Deck deck;
    private Hand hand1;
    private Hand hand2;
    private final int gameHeight = 800;
    private final int gameWidth = 1300;
    private int scaledWidth = 175;
    private int scaledHeight;
    private Card topCard;


    public BriscolaGUI() {

        deck = new Deck();
        hand1 = new Hand();
        hand2 = new Hand();
        System.out.println("Size of Deck: " + deck.getDeck().size());

        frame = new JFrame("Briscola");
        frame.setSize(gameWidth, gameHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //CREATING CONTENT PANE
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(null);
        contentPane.setBackground(Color.red);

        //create draw button NOT COMPLETE
        topCardButton = new JButton("Show Top Card");
        topCardButton.setBounds(gameWidth / 2 - 150 / 2, 700, 150, 50);
        contentPane.add(topCardButton);

        //Create card images/buttons
        player1Card1Button = new JButton("card1");
        player1Card1Button.setBounds(650,370,scaledWidth,325);
        contentPane.add(player1Card1Button);

        player1Card2Button = new JButton("card2");
        player1Card2Button.setBounds(650 + scaledWidth, 370, scaledWidth, 325);
        contentPane.add(player1Card2Button);
        

        //use the method below to scale the image
        JLabel topCardPic = new JLabel();
        contentPane.add(topCardPic);

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
            if (deck.lookTopCard() != null) {
                topCard = deck.getTopCard();
                topCardPic.setIcon(new ImageIcon(scaleImage(topCard).getImage()));
                topCardPic.setBounds(275, 150, scaledWidth, scaledHeight);
                contentPane.add(topCardPic);
                System.out.println(deck.getDeck().size());
            } else {
                topCardButton.setEnabled(false);
                topCardButton.setVisible(false);
            }
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
