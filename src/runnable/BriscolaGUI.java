package runnable;

import gameStructure.*;

import java.awt.*;

import javax.swing.*;

// A comment
public class BriscolaGUI extends JFrame {

    public static void main(String[] args) {
        new BriscolaGUI();
    }

    private boolean playerLost;
    private JFrame frame;
    private JButton dealButton;
    private JButton player1Card1Button;
    private JButton player1Card2Button;
    private JButton player1Card3Button;
    private JButton nextRoundButton;
    private Deck deck;
    private Hand hand1;
    private Hand hand2;
    private Discard discard1;
    private Discard discard2;
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
    private JLabel backOfCardPic;

    private JLabel cpuCard1;
    private JLabel cpuCard2;
    private JLabel cpuCard3;
    private JLabel messageLabel;
    private JLabel topCardPic;

    private int whoWon;
    private Card trumpSuitCard;

    public BriscolaGUI() {

        deck = new Deck();
        hand1 = new Hand();
        hand2 = new Hand();
        discard1 = new Discard();
        discard2 = new Discard();
        pile1 = new Pile();
        pile2 = new Pile();
        whoStartsGame();

        player1Card1Button = new JButton();
        player1Card2Button = new JButton();
        player1Card3Button = new JButton();

        frame = new JFrame("Briscola");
        frame.setSize(gameWidth, gameHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //CREATING CONTENT PANE
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(null);
        contentPane.setBackground(Color.red);

        //create draw button
        dealButton = new JButton("Deal Cards");
        dealButton.setBounds(((gameWidth / 2) / 2) - 150 / 2, 700, 150, 50);
        contentPane.add(dealButton);

        //create next round button
        nextRoundButton = new JButton("Next Round");
        nextRoundButton.setBounds(gameWidth/2 - 200, 555, 150, 50);
        contentPane.add(nextRoundButton);
        nextRoundButton.setVisible(false);

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

        //add message on who won after every round
        JLabel messageLabel = new JLabel("");
        messageLabel.setBounds(gameWidth/2 - 150, 200, 150, 50);
        contentPane.add(messageLabel);

        dealButton.addActionListener(e -> {
            if (deck.getDeck().size() == 40) {
                trumpSuitCard = deck.dealCards(hand1, hand2);
                playerCard1 = hand1.getHand().get(0);
                playerCard2 = hand1.getHand().get(1);
                playerCard3 = hand1.getHand().get(2);
                player2Card1 = hand2.getHand().get(0);
                player2Card2 = hand2.getHand().get(1);
                player2Card3 = hand2.getHand().get(2);

                Icon scaledIconPlayerCard1 = scaleImage(playerCard1);
                Icon scaledIconPlayerCard2 = scaleImage(playerCard2);
                Icon scaledIconPlayerCard3 = scaleImage(playerCard3);

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
                System.out.println(trumpSuitCard.toString());
            }
        });

        nextRoundButton.addActionListener(e -> {
            messageLabel.setVisible(false);

            if (deck.getDeck().size() > 1) {
                checkWhoWins(discard1.getDiscard().get(0), discard2.getDiscard().get(0));

                if (whoWon == 1) {
                    discard1.cardsWon(pile1);
                    discard2.cardsWon(pile1);

                    player1PlayedCard.setVisible(false);
                    player2PlayedCard.setVisible(false);

                    Card card1 = deck.dealTopCard(hand1);
                    Card card2 = deck.dealTopCard(hand2);
                    playerCard1 = hand1.getHand().get(0);
                    playerCard2 = hand1.getHand().get(1);
                    playerCard3 = hand1.getHand().get(2);
                    System.out.println("Your new hand" + hand1.getHand());

                    Icon scaledIconCard1 = scaleImage(playerCard1);
                    Icon scaledIconCard2 = scaleImage(playerCard2);
                    Icon scaledIconCard3 = scaleImage(playerCard3);

                    player1Card1Button.setIcon(scaledIconCard1);
                    player1Card1Button.setVisible(true);
                    player1Card1Button.setEnabled(true);

                    player1Card2Button.setIcon(scaledIconCard2);
                    player1Card2Button.setVisible(true);
                    player1Card2Button.setEnabled(true);

                    player1Card3Button.setIcon(scaledIconCard3);
                    player1Card3Button.setVisible(true);
                    player1Card3Button.setEnabled(true);

                    cpuCard1.setVisible(true);
                    cpuCard1.setIcon(scaledIcon);

                    nextRoundButton.setVisible(false);
                    nextRoundButton.setEnabled(false);

                } else if (whoWon == 2) {
                    discard1.cardsWon(pile2);
                    discard2.cardsWon(pile2);

                    player1PlayedCard.setVisible(false);
                    player2PlayedCard.setVisible(false);

                    deck.dealTopCard(hand2);
                    deck.dealTopCard(hand1);

                    playerCard1 = hand1.getHand().get(0);
                    playerCard2 = hand1.getHand().get(1);
                    playerCard3 = hand1.getHand().get(2);

                    System.out.println("Your new hand" + hand1.getHand());

                    Icon scaledIconCard1 = scaleImage(playerCard1);
                    Icon scaledIconCard2 = scaleImage(playerCard2);
                    Icon scaledIconCard3 = scaleImage(playerCard3);

                    player1Card1Button.setIcon(scaledIconCard1);
                    player1Card1Button.setVisible(true);
                    player1Card1Button.setEnabled(true);

                    player1Card2Button.setIcon(scaledIconCard2);
                    player1Card2Button.setVisible(true);
                    player1Card2Button.setEnabled(true);

                    player1Card3Button.setIcon(scaledIconCard3);
                    player1Card3Button.setVisible(true);
                    player1Card3Button.setEnabled(true);

                    cpuCard1.setVisible(true);
                    cpuCard1.setIcon(scaledIcon);

                    nextRoundButton.setVisible(false);
                    nextRoundButton.setEnabled(false);

                    Card player2Card = hand2.getHand().get(0);

                    Icon scaledIconPlayer2Card = scaleImage(player2Card);
                    player2PlayedCard.setIcon(scaledIconPlayer2Card);

                    //method for selecting card for cpu
                    hand2.playFirstCard(discard2);

                    cpuCard1.setVisible(false);
                    player2PlayedCard.setVisible(true);


                }
            } else if (deck.getDeck().size() == 1) {

                checkWhoWins(discard1.getDiscard().get(0), discard2.getDiscard().get(0));
                topCardPic.setVisible(false);
                backOfCardPic.setVisible(false);

                if (whoWon == 1) {
                    discard1.cardsWon(pile1);
                    discard2.cardsWon(pile1);

                    player1PlayedCard.setVisible(false);
                    player2PlayedCard.setVisible(false);

                    deck.dealTopCard(hand1);
                    hand2.dealTrumpSuitCard(trumpSuitCard);
                    playerCard1 = hand1.getHand().get(0);
                    playerCard2 = hand1.getHand().get(1);
                    playerCard3 = hand1.getHand().get(2);
                    System.out.println("Your new hand: " + hand1.getHand());

                    Icon scaledIconCard1 = scaleImage(playerCard1);
                    Icon scaledIconCard2 = scaleImage(playerCard2);
                    Icon scaledIconCard3 = scaleImage(playerCard3);

                    player1Card1Button.setIcon(scaledIconCard1);
                    player1Card1Button.setVisible(true);
                    player1Card1Button.setEnabled(true);

                    player1Card2Button.setIcon(scaledIconCard2);
                    player1Card2Button.setVisible(true);
                    player1Card2Button.setEnabled(true);

                    player1Card3Button.setIcon(scaledIconCard3);
                    player1Card3Button.setVisible(true);
                    player1Card3Button.setEnabled(true);

                    cpuCard1.setVisible(true);
                    cpuCard1.setIcon(scaledIcon);

                    nextRoundButton.setVisible(false);
                    nextRoundButton.setEnabled(false);

                } else if (whoWon == 2) {
                    discard1.cardsWon(pile2);
                    discard2.cardsWon(pile2);

                    player1PlayedCard.setVisible(false);
                    player2PlayedCard.setVisible(false);

                    deck.dealTopCard(hand2);
                    hand1.dealTrumpSuitCard(trumpSuitCard);

                    playerCard1 = hand1.getHand().get(0);
                    playerCard2 = hand1.getHand().get(1);
                    playerCard3 = hand1.getHand().get(2);

                    System.out.println("Your new hand: " + hand1.getHand());

                    Icon scaledIconCard1 = scaleImage(playerCard1);
                    Icon scaledIconCard2 = scaleImage(playerCard2);
                    Icon scaledIconCard3 = scaleImage(playerCard3);

                    player1Card1Button.setIcon(scaledIconCard1);
                    player1Card1Button.setVisible(true);
                    player1Card1Button.setEnabled(true);

                    player1Card2Button.setIcon(scaledIconCard2);
                    player1Card2Button.setVisible(true);
                    player1Card2Button.setEnabled(true);

                    player1Card3Button.setIcon(scaledIconCard3);
                    player1Card3Button.setVisible(true);
                    player1Card3Button.setEnabled(true);

                    cpuCard1.setVisible(true);
                    cpuCard1.setIcon(scaledIcon);

                    nextRoundButton.setVisible(false);
                    nextRoundButton.setEnabled(false);

                    Card player2Card = hand2.getHand().get(0);

                    Icon scaledIconPlayer2Card = scaleImage(player2Card);
                    player2PlayedCard.setIcon(scaledIconPlayer2Card);

                    //method for selecting card for cpu
                    hand2.playFirstCard(discard2);

                    cpuCard1.setVisible(false);
                    player2PlayedCard.setVisible(true);
                }

            } else if (deck.getDeck().size() == 0 && hand1.getHand().size() == 2){

                checkWhoWins(discard1.getDiscard().get(0), discard2.getDiscard().get(0));

                if (whoWon == 1) {

                    discard1.cardsWon(pile1);
                    discard2.cardsWon(pile1);

                    player1PlayedCard.setVisible(false);
                    player2PlayedCard.setVisible(false);


                    playerCard1 = hand1.getHand().get(0);
                    playerCard2 = hand1.getHand().get(1);
                    System.out.println("Your new hand: " + hand1.getHand());

                    Icon scaledIconCard1 = scaleImage(playerCard1);
                    Icon scaledIconCard2 = scaleImage(playerCard2);

                    player1Card1Button.setIcon(scaledIconCard1);
                    player1Card1Button.setVisible(true);
                    player1Card1Button.setEnabled(true);

                    player1Card2Button.setIcon(scaledIconCard2);
                    player1Card2Button.setVisible(true);
                    player1Card2Button.setEnabled(true);

                    player1Card3Button.setVisible(false);
                    player1Card3Button.setEnabled(false);

                    cpuCard1.setVisible(true);
                    cpuCard1.setIcon(scaledIcon);

                    cpuCard3.setVisible(false);

                    nextRoundButton.setVisible(false);
                    nextRoundButton.setEnabled(false);

                } else if (whoWon == 2) {

                    discard1.cardsWon(pile2);
                    discard2.cardsWon(pile2);

                    player1PlayedCard.setVisible(false);
                    player2PlayedCard.setVisible(false);

                    playerCard1 = hand1.getHand().get(0);
                    playerCard2 = hand1.getHand().get(1);

                    System.out.println("Your new hand: " + hand1.getHand());

                    Icon scaledIconCard1 = scaleImage(playerCard1);
                    Icon scaledIconCard2 = scaleImage(playerCard2);

                    player1Card1Button.setIcon(scaledIconCard1);
                    player1Card1Button.setVisible(true);
                    player1Card1Button.setEnabled(true);

                    player1Card2Button.setIcon(scaledIconCard2);
                    player1Card2Button.setVisible(true);
                    player1Card2Button.setEnabled(true);

                    player1Card3Button.setVisible(false);
                    player1Card3Button.setEnabled(false);

                    cpuCard1.setVisible(true);
                    cpuCard1.setIcon(scaledIcon);

                    nextRoundButton.setVisible(false);
                    nextRoundButton.setEnabled(false);

                    Card player2Card = hand2.getHand().get(0);

                    Icon scaledIconPlayer2Card = scaleImage(player2Card);
                    player2PlayedCard.setIcon(scaledIconPlayer2Card);

                    //method for selecting card for cpu
                    hand2.playFirstCard(discard2);

                    cpuCard1.setVisible(false);
                    cpuCard3.setVisible(false);
                    player2PlayedCard.setVisible(true);
                }
            } else if (deck.getDeck().size() == 0 && hand1.getHand().size() == 1){

                checkWhoWins(discard1.getDiscard().get(0), discard2.getDiscard().get(0));

                if (whoWon == 1) {

                    discard1.cardsWon(pile1);
                    discard2.cardsWon(pile1);

                    player1PlayedCard.setVisible(false);
                    player2PlayedCard.setVisible(false);


                    playerCard1 = hand1.getHand().get(0);
                    System.out.println("Your new hand: " + hand1.getHand());

                    Icon scaledIconCard1 = scaleImage(playerCard1);

                    player1Card1Button.setIcon(scaledIconCard1);
                    player1Card1Button.setVisible(true);
                    player1Card1Button.setEnabled(true);

                    player1Card2Button.setVisible(false);
                    player1Card2Button.setEnabled(false);

                    player1Card3Button.setVisible(false);
                    player1Card3Button.setEnabled(false);

                    cpuCard1.setVisible(true);
                    cpuCard1.setIcon(scaledIcon);

                    cpuCard2.setVisible(false);
                    cpuCard3.setVisible(false);

                    nextRoundButton.setVisible(false);
                    nextRoundButton.setEnabled(false);
                    nextRoundButton.setText("Check Score");

                } else if (whoWon == 2) {

                    discard1.cardsWon(pile2);
                    discard2.cardsWon(pile2);

                    player1PlayedCard.setVisible(false);
                    player2PlayedCard.setVisible(false);

                    playerCard1 = hand1.getHand().get(0);

                    System.out.println("Your new hand: " + hand1.getHand());

                    Icon scaledIconCard1 = scaleImage(playerCard1);

                    player1Card1Button.setIcon(scaledIconCard1);
                    player1Card1Button.setVisible(true);
                    player1Card1Button.setEnabled(true);

                    player1Card2Button.setVisible(false);
                    player1Card2Button.setEnabled(false);

                    player1Card3Button.setVisible(false);
                    player1Card3Button.setEnabled(false);

                    cpuCard1.setVisible(true);
                    cpuCard1.setIcon(scaledIcon);

                    nextRoundButton.setVisible(false);
                    nextRoundButton.setEnabled(false);

                    Card player2Card = hand2.getHand().get(0);

                    Icon scaledIconPlayer2Card = scaleImage(player2Card);
                    player2PlayedCard.setIcon(scaledIconPlayer2Card);

                    //method for selecting card for cpu
                    hand2.playFirstCard(discard2);

                    cpuCard1.setVisible(false);
                    cpuCard2.setVisible(false);
                    cpuCard3.setVisible(false);
                    player2PlayedCard.setVisible(true);
                    nextRoundButton.setText("Check Score");
                }
            } else if (hand1.getHand().size() == 0 && hand2.getHand().size() == 0 && discard1.getDiscard().size() == 1) {
                checkWhoWins(discard1.getDiscard().get(0), discard2.getDiscard().get(0));
                if (whoWon == 1) {
                    discard1.cardsWon(pile1);
                    discard2.cardsWon(pile1);
                    player1PlayedCard.setVisible(false);
                    player2PlayedCard.setVisible(false);
                    if (pile1.getPoints() > 60) {
                        messageLabel.setText("You won with " + pile1.getPoints() + " points");
                    } else if (pile2.getPoints() > 60) {
                        messageLabel.setText("You lost with " + pile1.getPoints() + " points");
                    } else {
                        messageLabel.setText("You tied with " + pile1.getPoints() + " points");
                    }
                    messageLabel.setVisible(true);
                    nextRoundButton.setText("New Game");
                } else if (whoWon == 2) {
                    discard2.cardsWon(pile2);
                    discard2.cardsWon(pile2);
                    player1PlayedCard.setVisible(false);
                    player2PlayedCard.setVisible(false);
                    if (pile1.getPoints() > 60) {
                        messageLabel.setText("You won with " + pile1.getPoints() + " points");
                    } else if (pile2.getPoints() > 60) {
                        messageLabel.setText("You lost with " + pile1.getPoints() + " points");
                    } else {
                        messageLabel.setText("You tied with " + pile1.getPoints() + " points");
                    }
                    messageLabel.setVisible(true);
                    nextRoundButton.setText("New Game");
                }

            } else if (hand1.getHand().size() == 0 && hand2.getHand().size() == 0 && pile1.getPile().size() + pile2.getPile().size() == 40){
                System.out.println("pile size: " + pile1.getPile().size());
                resetGame();
            } else {

            }
        });

        player1Card1Button.addActionListener(e -> {

            if (whoWon == 1) {

                Icon scaledIconPlayerCard1 = scaleImage(playerCard1);
                player1PlayedCard.setBounds(400, scaledHeight / 2 + 150, scaledWidth, scaledHeight);
                contentPane.add(player1PlayedCard);
                player1PlayedCard.setIcon(scaledIconPlayerCard1);

                //method for selecting card for now just pick first
                Card player2Card = hand2.getHand().get(0);

                Icon scaledIconPlayer2Card = scaleImage(player2Card);
                player2PlayedCard.setBounds(405 + scaledWidth, scaledHeight / 2 + 150, scaledWidth, scaledHeight);
                contentPane.add(player2PlayedCard);
                player2PlayedCard.setIcon(scaledIconPlayer2Card);

                hand1.playFirstCard(discard1);
                hand2.playFirstCard(discard2);

                player1Card1Button.setVisible(false);
                player1Card1Button.setEnabled(false);
                cpuCard1.setVisible(false);

                player1PlayedCard.setVisible(true);
                player2PlayedCard.setVisible(true);

                player1Card2Button.setEnabled(false);
                Icon scaledIconPlayerCard2 = scaleImage(playerCard2);
                player1Card2Button.setDisabledIcon(scaledIconPlayerCard2);

                player1Card3Button.setEnabled(false);
                Icon scaledIconPlayerCard3 = scaleImage(playerCard3);
                player1Card3Button.setDisabledIcon(scaledIconPlayerCard3);

                System.out.println("Discard Pile: " + discard1.getDiscard() + " " + discard2.getDiscard());

                nextRoundButton.setEnabled(true);
                nextRoundButton.setVisible(true);

                checkWhoWins(discard1.getDiscard().get(0), discard2.getDiscard().get(0));

                if (whoWon == 1) {
                    messageLabel.setText("You Won");
                    messageLabel.setVisible(true);
                } else if (whoWon == 2) {
                    messageLabel.setText("You Lost");
                    messageLabel.setVisible(true);
                }
            } else if (whoWon == 2) {
                Icon scaledIconPlayerCard1 = scaleImage(playerCard1);
                player1PlayedCard.setBounds(400, scaledHeight / 2 + 150, scaledWidth, scaledHeight);
                contentPane.add(player1PlayedCard);
                player1PlayedCard.setIcon(scaledIconPlayerCard1);

                hand1.playFirstCard(discard1);

                player1Card1Button.setVisible(false);
                player1Card1Button.setEnabled(false);

                player1PlayedCard.setVisible(true);

                player1Card2Button.setEnabled(false);
                Icon scaledIconPlayerCard2 = scaleImage(playerCard2);
                player1Card2Button.setDisabledIcon(scaledIconPlayerCard2);

                player1Card3Button.setEnabled(false);
                Icon scaledIconPlayerCard3 = scaleImage(playerCard3);
                player1Card3Button.setDisabledIcon(scaledIconPlayerCard3);


                nextRoundButton.setEnabled(true);
                nextRoundButton.setVisible(true);

                checkWhoWins(discard1.getDiscard().get(0), discard2.getDiscard().get(0));

                if (whoWon == 1) {
                    messageLabel.setText("You Won");
                    messageLabel.setVisible(true);
                } else if (whoWon == 2) {
                    messageLabel.setText("You Lost");
                    messageLabel.setVisible(true);
                }

            }

        });

        //
        player1Card2Button.addActionListener(e -> {
            if (whoWon == 1) {

                Icon scaledIconPlayerCard2 = scaleImage(playerCard2);
                player1PlayedCard.setBounds(400, scaledHeight / 2 + 150, scaledWidth, scaledHeight);
                contentPane.add(player1PlayedCard);
                player1PlayedCard.setIcon(scaledIconPlayerCard2);

                //method for selecting card for now just pick first
                Card player2Card = hand2.getHand().get(0);

                Icon scaledIconPlayer2Card = scaleImage(player2Card);
                player2PlayedCard.setBounds(405 + scaledWidth, scaledHeight / 2 + 150, scaledWidth, scaledHeight);
                contentPane.add(player2PlayedCard);
                player2PlayedCard.setIcon(scaledIconPlayer2Card);

                hand1.playSecondCard(discard1);

                //method for selecting cpu card
                hand2.playFirstCard(discard2);

                player1Card2Button.setVisible(false);
                player1Card2Button.setEnabled(false);

                //card selected for cpu
                cpuCard1.setVisible(false);

                player1PlayedCard.setVisible(true);
                player2PlayedCard.setVisible(true);

                player1Card1Button.setEnabled(false);
                Icon scaledIconPlayerCard1 = scaleImage(playerCard1);
                player1Card1Button.setDisabledIcon(scaledIconPlayerCard1);

                player1Card3Button.setEnabled(false);
                Icon scaledIconPlayerCard3 = scaleImage(playerCard3);
                player1Card3Button.setDisabledIcon(scaledIconPlayerCard3);

                System.out.println("Discard Pile: " + discard1.getDiscard() + " " + discard2.getDiscard());

                nextRoundButton.setEnabled(true);
                nextRoundButton.setVisible(true);

                checkWhoWins(discard1.getDiscard().get(0), discard2.getDiscard().get(0));

                if (whoWon == 1) {
                    messageLabel.setText("You Won");
                    messageLabel.setVisible(true);
                } else if (whoWon == 2) {
                    messageLabel.setText("You Lost");
                    messageLabel.setVisible(true);
                }
            } else if (whoWon == 2) {
                Icon scaledIconPlayerCard2 = scaleImage(playerCard2);
                player1PlayedCard.setBounds(400, scaledHeight / 2 + 150, scaledWidth, scaledHeight);
                contentPane.add(player1PlayedCard);
                player1PlayedCard.setIcon(scaledIconPlayerCard2);

                hand1.playSecondCard(discard1);

                player1Card2Button.setVisible(false);
                player1Card2Button.setEnabled(false);

                player1PlayedCard.setVisible(true);

                player1Card1Button.setEnabled(false);
                Icon scaledIconPlayerCard1 = scaleImage(playerCard1);
                player1Card1Button.setDisabledIcon(scaledIconPlayerCard1);

                player1Card3Button.setEnabled(false);
                Icon scaledIconPlayerCard3 = scaleImage(playerCard3);
                player1Card3Button.setDisabledIcon(scaledIconPlayerCard3);

                nextRoundButton.setEnabled(true);
                nextRoundButton.setVisible(true);

                checkWhoWins(discard1.getDiscard().get(0), discard2.getDiscard().get(0));

                if (whoWon == 1) {
                    messageLabel.setText("You Won");
                    messageLabel.setVisible(true);
                } else if (whoWon == 2) {
                    messageLabel.setText("You Lost");
                    messageLabel.setVisible(true);
                }
            }
        });

        player1Card3Button.addActionListener(e -> {
            if (whoWon == 1) {

                Icon scaledIconPlayerCard3 = scaleImage(playerCard3);
                player1PlayedCard.setBounds(400, scaledHeight / 2 + 150, scaledWidth, scaledHeight);
                contentPane.add(player1PlayedCard);
                player1PlayedCard.setIcon(scaledIconPlayerCard3);

                //method for selecting card for now just pick first
                Card player2Card = hand2.getHand().get(0);

                Icon scaledIconPlayer2Card = scaleImage(player2Card);
                player2PlayedCard.setBounds(405 + scaledWidth, scaledHeight / 2 + 150, scaledWidth, scaledHeight);
                contentPane.add(player2PlayedCard);
                player2PlayedCard.setIcon(scaledIconPlayer2Card);

                hand1.playThirdCard(discard1);

                //method for selecting cpu card
                hand2.playFirstCard(discard2);

                player1Card3Button.setVisible(false);
                player1Card3Button.setEnabled(false);

                //card selected for cpu
                cpuCard1.setVisible(false);

                player1PlayedCard.setVisible(true);
                player2PlayedCard.setVisible(true);

                player1Card1Button.setEnabled(false);
                Icon scaledIconPlayerCard1 = scaleImage(playerCard1);
                player1Card1Button.setDisabledIcon(scaledIconPlayerCard1);

                player1Card2Button.setEnabled(false);
                Icon scaledIconPlayerCard2 = scaleImage(playerCard2);
                player1Card2Button.setDisabledIcon(scaledIconPlayerCard2);

                System.out.println("Discard Pile: " + discard1.getDiscard() + " " + discard2.getDiscard());

                nextRoundButton.setEnabled(true);
                nextRoundButton.setVisible(true);

                checkWhoWins(discard1.getDiscard().get(0), discard2.getDiscard().get(0));

                if (whoWon == 1) {
                    messageLabel.setText("You Won");
                    messageLabel.setVisible(true);
                } else if (whoWon == 2) {
                    messageLabel.setText("You Lost");
                    messageLabel.setVisible(true);
                }
            } else if (whoWon == 2) {
                Icon scaledIconPlayerCard3 = scaleImage(playerCard3);
                player1PlayedCard.setBounds(400, scaledHeight / 2 + 150, scaledWidth, scaledHeight);
                contentPane.add(player1PlayedCard);
                player1PlayedCard.setIcon(scaledIconPlayerCard3);

                hand1.playThirdCard(discard1);

                player1Card3Button.setVisible(false);
                player1Card3Button.setEnabled(false);

                player1PlayedCard.setVisible(true);

                player1Card1Button.setEnabled(false);
                Icon scaledIconPlayerCard1 = scaleImage(playerCard1);
                player1Card1Button.setDisabledIcon(scaledIconPlayerCard1);

                player1Card2Button.setEnabled(false);
                Icon scaledIconPlayerCard2 = scaleImage(playerCard2);
                player1Card2Button.setDisabledIcon(scaledIconPlayerCard2);

                nextRoundButton.setEnabled(true);
                nextRoundButton.setVisible(true);

                checkWhoWins(discard1.getDiscard().get(0), discard2.getDiscard().get(0));

                if (whoWon == 1) {
                    messageLabel.setText("You Won");
                    messageLabel.setVisible(true);
                } else if (whoWon == 2) {
                    messageLabel.setText("You Lost");
                    messageLabel.setVisible(true);
                }
            }
        });

        // Set the frame to be visible
        frame.setVisible(true);
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
    //returns 1 if player one wins and returns 2 if player two wins
    private void checkWhoWins(Card card1, Card card2) {

        if (card1.getSuit().equals(card2.getSuit())) {
            if (card1.getStrength() > card2.getStrength()) {
                whoWon = 1;
            } else {
                whoWon =  2;
            }
        } else if (card1.getSuit().equals(trumpSuitCard.getSuit()) && !card2.getSuit().equals(trumpSuitCard.getSuit())) {
            whoWon = 1;
        } else if (!card1.getSuit().equals(trumpSuitCard.getSuit()) && card2.getSuit().equals(trumpSuitCard.getSuit())) {
            whoWon = 2;
        } else {
            if (whoWon == 1) {
                if (!card1.getSuit().equals(card2.getSuit())) {
                    whoWon = 1;
                }
            } else {
                whoWon = 2;
            }
        }

    }

    private void whoStartsGame() {
        whoWon = (int) Math.random() + 1;
    }

    private int randomCardPicker() {
        return 0;
    }

    private void resetGame() {
        pile1.startOver();
        pile2.startOver();
        deck = new Deck();
        dealButton.setEnabled(true);
        dealButton.setVisible(true);

    }
}
