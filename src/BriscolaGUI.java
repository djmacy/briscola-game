import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

// A comment
public class BriscolaGUI extends JFrame {
    private JFrame frame;
    private JButton dealButton;

    private JButton player1Card1Button;
    private JButton player1Card2Button;
    private JButton player1Card3Button;
    private Deck deck;
    private Hand hand1;
    private Hand hand2;
    private final int gameHeight = 800;
    private final int gameWidth = 1300;
    private int scaledWidth = 125;
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
        dealButton = new JButton("Deal Cards");
        dealButton.setBounds(((gameWidth / 2) / 2) - 150 / 2, 700, 150, 50);
        contentPane.add(dealButton);

        //Create card images/buttons
        Icon scaledIconPlayer1Card1 = scaleImage(deck.lookTopCard());
        player1Card1Button = new JButton(scaledIconPlayer1Card1);
        player1Card1Button.setBounds(750,500,scaledWidth,250);
        contentPane.add(player1Card1Button);

        Icon scaledIconPlayer1Card2 = scaleImage(deck.lookTopCard());
        player1Card2Button = new JButton(scaledIconPlayer1Card2);
        player1Card2Button.setBounds(755 + scaledWidth, 500, scaledWidth, 250);
        contentPane.add(player1Card2Button);

        Icon scaledIconPlayer1Card3 = scaleImage(deck.lookTopCard());
        player1Card3Button  = new JButton(scaledIconPlayer1Card3);
        player1Card3Button.setBounds(760 + scaledWidth + scaledWidth, 500, scaledWidth, 250);
        contentPane.add(player1Card3Button);
        

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
        backOfCardPic.setBounds( 100, gameHeight / 2 - scaledHeight / 2, scaledWidth,scaledHeight);
        contentPane.add(backOfCardPic);

        JLabel cpuCard1 = new JLabel(scaledIcon);
        cpuCard1.setBounds(750, 10, scaledWidth, scaledHeight);
        contentPane.add(cpuCard1);

        JLabel cpuCard2 = new JLabel(scaledIcon);
        cpuCard2.setBounds(755 + scaledWidth, 10, scaledWidth, scaledHeight);
        contentPane.add(cpuCard2);

        JLabel cpuCard3 = new JLabel(scaledIcon);
        cpuCard3.setBounds(760 + scaledWidth + scaledWidth, 10, scaledWidth, scaledHeight);
        contentPane.add(cpuCard3);

        dealButton.addActionListener(e -> {
            if (deck.getDeck().size() == 40) {
                Card trumpSuitCard = deck.dealCards(hand1, hand2);
                Card playerCard1 = hand1.getHand().get(0);
                Card playerCard2 = hand1.getHand().get(1);
                Card playerCard3 = hand1.getHand().get(2);
                player1Card1Button.setVisible(true);
                player1Card2Button.setVisible(true);
                player1Card3Button.setVisible(true);


                topCardPic.setIcon(new ImageIcon(scaleImage(trumpSuitCard).getImage()));
                topCardPic.setBounds(105 + scaledWidth, gameHeight / 2 - scaledHeight / 2, scaledWidth, scaledHeight);
                contentPane.add(topCardPic);
                System.out.println(deck.getDeck().size());
            } else {

                dealButton.setEnabled(false);
                dealButton.setVisible(false);
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
