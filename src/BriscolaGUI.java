import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
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
    private Discard discard;
    private Pile pile1;
    private Pile pile2;
    private final int gameHeight = 800;
    private final int gameWidth = 1300;
    private int scaledWidth = 125;
    private int scaledHeight;
    private Card playerCard1;
    private Card playerCard2;
    private Card playerCard3;
    private Card player2Card1;
    private Card player2Card2;
    private Card player2Card3;
    private ImageIcon scaledIcon;
    private ImageIcon backOfCard;
    private JLabel cpuCard1;
    private JLabel cpuCard2;
    private JLabel cpuCard3;
    private int whoWentFirst;


    public BriscolaGUI() {

        deck = new Deck();
        hand1 = new Hand();
        hand2 = new Hand();
        discard = new Discard();
        pile1 = new Pile();
        pile2 = new Pile();

        player1Card1Button = new JButton();
        player1Card2Button = new JButton();
        player1Card3Button = new JButton();

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
        /*
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

         */

        //creating  and adding the cpu cards to the screen
        JLabel topCardPic = new JLabel();
        contentPane.add(topCardPic);
        JLabel cpuCard1 = new JLabel();
        contentPane.add(cpuCard1);
        JLabel cpuCard2 = new JLabel();
        contentPane.add(cpuCard2);
        JLabel cpuCard3 = new JLabel();
        contentPane.add(cpuCard3);

        //Add where we put the played cards
        JLabel player1PlayedCard = new JLabel();
        contentPane.add(player1PlayedCard);
        JLabel player2PlayedCard = new JLabel();
        contentPane.add(player2PlayedCard);

        //making back of card image
        backOfCard = new ImageIcon("src/images/backOfCard.png");
        scaledHeight = (int) ((double) scaledWidth / backOfCard.getIconWidth() * backOfCard.getIconHeight());
        Image scaledImage = backOfCard.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        scaledIcon = new ImageIcon(scaledImage);

        //add the back of card image to the screen
        JLabel backOfCardPic = new JLabel(scaledIcon);
        backOfCardPic.setBounds( 100, gameHeight / 2 - scaledHeight / 2, scaledWidth,scaledHeight);
        contentPane.add(backOfCardPic);

        dealButton.addActionListener(e -> {
            if (deck.getDeck().size() == 40) {
                Card trumpSuitCard = deck.dealCards(hand1, hand2);
                playerCard1 = hand1.getHand().get(0);
                playerCard2 = hand1.getHand().get(1);
                playerCard3 = hand1.getHand().get(2);
                player2Card1 = hand2.getHand().get(0);
                player2Card2 = hand2.getHand().get(1);
                player2Card3 = hand2.getHand().get(2);

                Icon scaledIconPlayerCard1 = scaleImage(playerCard1);
                Icon scaledIconPlayerCard2 = scaleImage(playerCard2);
                Icon scaledIconPlayerCard3 = scaleImage(playerCard3);

                /*
                player1Card1Button = new JButton();
                player1Card2Button = new JButton();
                player1Card3Button = new JButton();
                */
                player1Card1Button.setBounds(750,500,scaledWidth,250);
                contentPane.add(player1Card1Button);
                player1Card2Button.setBounds(755 + scaledWidth, 500, scaledWidth, 250);
                contentPane.add(player1Card2Button);
                player1Card3Button.setBounds(760 + scaledWidth + scaledWidth, 500, scaledWidth, 250);
                contentPane.add(player1Card3Button);

                player1Card1Button.setVisible(true);
                player1Card2Button.setVisible(true);
                player1Card3Button.setVisible(true);

                player1Card1Button.setIcon(scaledIconPlayerCard1);
                player1Card2Button.setIcon(scaledIconPlayerCard2);
                player1Card3Button.setIcon(scaledIconPlayerCard3);

                scaledHeight = (int) ((double) scaledWidth / backOfCard.getIconWidth() * backOfCard.getIconHeight());

                cpuCard1.setIcon(scaledIcon);
                cpuCard1.setBounds(750, 20, scaledWidth, scaledHeight);
                contentPane.add(cpuCard1);

                cpuCard2.setIcon(scaledIcon);
                cpuCard2.setBounds(755 + scaledWidth, 20, scaledWidth, scaledHeight);
                contentPane.add(cpuCard2);

                cpuCard3.setIcon(scaledIcon);
                cpuCard3.setBounds(760 + scaledWidth + scaledWidth, 20, scaledWidth, scaledHeight);
                contentPane.add(cpuCard3);

                topCardPic.setIcon(new ImageIcon(scaleImage(trumpSuitCard).getImage()));
                topCardPic.setBounds(105 + scaledWidth, gameHeight / 2 - scaledHeight / 2, scaledWidth, scaledHeight);
                contentPane.add(topCardPic);

                System.out.println(deck.getDeck().size());
                System.out.println("Player 1: " + hand1.getHand());
                System.out.println("Player 2: " + hand2.getHand());

                dealButton.setEnabled(false);
                dealButton.setVisible(false);
            }
        });

        player1Card1Button.addActionListener(e -> {

            Icon scaledIconPlayerCard1 = scaleImage(playerCard1);
            player1PlayedCard.setBounds(400,scaledHeight/2 + 150,scaledWidth,scaledHeight);
            contentPane.add(player1PlayedCard);
            player1PlayedCard.setIcon(scaledIconPlayerCard1);

            //method for selecting card for now just pick first
            Card player2Card = hand2.getHand().get(0);

            Icon scaledIconPlayer2Card = scaleImage(player2Card);
            player2PlayedCard.setBounds(405 + scaledWidth, scaledHeight / 2 + 150, scaledWidth, scaledHeight);
            contentPane.add(player2PlayedCard);
            player2PlayedCard.setIcon(scaledIconPlayer2Card);

            hand1.playFirstCard(discard);
            hand2.playFirstCard(discard);

            player1Card1Button.setVisible(false);
            player1Card1Button.setEnabled(false);
            cpuCard1.setVisible(false);

            player1PlayedCard.setVisible(true);
            player2PlayedCard.setVisible(true);

            System.out.println("Discard Pile: " + discard.getDiscard());
            int whoWon = checkWhoWins(playerCard1, playerCard2);

            if (whoWon == 0) {
                System.out.println("You Won This Round");

                Timer timer = new Timer(5000, event -> {

                    player1PlayedCard.setVisible(false);
                    player2PlayedCard.setVisible(false);

                    Card card1 = deck.dealTopCard(hand1);
                    Card card2 = deck.dealTopCard(hand2);
                    playerCard1 = hand1.getHand().get(0);
                    playerCard2 = hand1.getHand().get(1);
                    playerCard3 = hand1.getHand().get(2);
                    System.out.println("Your new hand" + hand1.getHand());

                    Icon scaledIconCard1 = scaleImage(card1);
                    player1Card1Button.setIcon(scaledIconCard1);
                    player1Card1Button.setVisible(true);
                    player1Card1Button.setEnabled(true);
                    cpuCard1.setVisible(true);
                    cpuCard1.setIcon(scaledIcon);
                    deck.dealTopCard(hand1);
                    deck.dealTopCard(hand2);

                });
                timer.setRepeats(false);
                timer.start();

            } else {
                System.out.println("You Lost This Round");
                deck.dealTopCard(hand2);
                deck.dealTopCard(hand1);
            }


            player1Card1Button.setIcon(scaleImage(playerCard1));
            player1Card2Button.setIcon(scaleImage(playerCard2));
            player1Card3Button.setIcon(scaleImage(playerCard3));


        });

        //
        player1Card2Button.addActionListener(e -> {

        });

        player1Card3Button.addActionListener(e -> {

        });

        // Set the frame to be visible
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new BriscolaGUI();
    }

    private ImageIcon scaleImage(Card topCard) {
        ImageIcon originalImage = new ImageIcon(topCard.getImage().getImage());
        // Scale the image to a smaller size
        scaledHeight = (int) ((double) scaledWidth / originalImage.getIconWidth() * originalImage.getIconHeight());
        Image scaledImage = originalImage.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        return scaledIcon;
    }

    //method for checking who wins a round. We will make card one, player ones card and card 2 player two's card
    private int checkWhoWins(Card card1, Card card2) {

        if (card1.getSuit().equals(card2.getSuit())) {
            if (card1.getStrength() > card2.getStrength()) {
                return 1;
            } else {
                return 2;
            }
        } else {
            if (whoWentFirst == 0) {
                
            }
        }

        return 0;
    }

    private int whoStartsGame() {
        return (int) (int) Math.random();
    }

    private int randomCardPicker() {
        return 0;
    }
}
