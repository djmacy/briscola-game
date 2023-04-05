
package gameGUI;

import gameStructure.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Random;
import javax.swing.*;

// A comment
public class BriscolaGUI extends JFrame {

    public static void main(String[] args) {
        new BriscolaGUI();
    }

    private JFrame gameFrame;
    private JFrame menuFrame;
    private Container contentPane;
    private JButton dealButton;
    private JButton player1Card1Button;
    private JButton player1Card2Button;
    private JButton player1Card3Button;
    private JButton nextRoundButton;
    private JButton newGameButton;
    private JButton startButton;
    private ButtonGroup bg;
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
    private Card playedCard;
    private ImageIcon scaledIcon;
    private ImageIcon backOfCard;
    private JLabel backOfCardPic;

    private JLabel cpuCard1;
    private JLabel cpuCard2;
    private JLabel cpuCard3;
    private JLabel player1PlayedCard;
    private JLabel player2PlayedCard;
    private JLabel messageLabel;
    private JLabel topCardPic;
    private JLabel backgroundImage;
    private JLabel userPointsLabel;
    private JLabel cpuPointsLabel;
    private JLabel trumpSuitLabel;
    private JLabel deckSizeLabel;
    private int whoWon;
    private int rand;
    private int cardChosen;
    private Card trumpSuitCard;
    private Boolean easyMode;

    public BriscolaGUI() {
        menuFrame = new JFrame("Main Menu");
        menuFrame.setSize(gameWidth,gameHeight);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setVisible(true);

        JPanel gamePanel = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image img = new ImageIcon("src/images/BriCenMenu.png").getImage();
                Dimension size = getSize();
                g.drawImage(img,0,0,size.width,size.height,null);
            }
        };
        menuFrame.setContentPane(gamePanel);

        Container contentPane = menuFrame.getContentPane();
        contentPane.setLayout(null);

        startButton = new JButton("Start Game");
        startButton.setBounds(gameWidth/2 - 50, gameHeight - 100, 100,50);
        startButton.addActionListener(e -> showGameWindow());
        contentPane.add(startButton);

        JRadioButton easyButton = new JRadioButton("Easy");
        easyButton.setBounds(gameWidth - 300, gameHeight - 100, 100, 50);
        contentPane.add(easyButton);
        easyButton.setOpaque(false);
        easyButton.setSelected(true);

        JRadioButton normalButton = new JRadioButton("Normal");
        normalButton.setBounds(gameWidth - 200, gameHeight - 100, 100, 50);
        contentPane.add(normalButton);
        normalButton.setOpaque(false);

        bg = new ButtonGroup();
        bg.add(easyButton);
        bg.add(normalButton);

        if (bg.getSelection() == easyButton) {
            easyMode = true;
        } else {
            easyMode = false;
        }

    }

    private void showGameWindow() {
        System.out.println(easyMode);
        startButton.setEnabled(false);
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

        gameFrame = new JFrame("Briscola");
        gameFrame.setSize(gameWidth, gameHeight);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //https://stackoverflow.com/questions/26698975/how-to-deal-with-public-void-paint-method-in-jframe
        JPanel gamePanel = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image img = new ImageIcon("src/images/backgroundImage.png").getImage();
                Dimension size = getSize();
                g.drawImage(img,0,0,size.width,size.height,null);
            }
        };
        gameFrame.setContentPane(gamePanel);

        //making content pane
        contentPane = gameFrame.getContentPane();
        contentPane.setLayout(null);

        //create deal button
        dealButton = new JButton("Deal Cards");
        dealButton.setBounds((gameWidth / 2) - 160, 570, 150, 50);
        contentPane.add(dealButton);

        //create next round button
        nextRoundButton = new JButton("Next Round");
        nextRoundButton.setBounds(gameWidth/2 - 160, 570, 150, 50);
        contentPane.add(nextRoundButton);
        nextRoundButton.setVisible(false);

        //create new game button
        newGameButton = new JButton("New Game");
        newGameButton.setBounds(gameWidth/2 - 160, 570, 150, 50);
        contentPane.add(newGameButton);
        newGameButton.setVisible(false);
        newGameButton.setEnabled(false);

        //creating  and adding the cpu cards to the screen
        JLabel topCardPic = new JLabel();
        contentPane.add(topCardPic);
        cpuCard1 = new JLabel();
        contentPane.add(cpuCard1);
        cpuCard2 = new JLabel();
        contentPane.add(cpuCard2);
        cpuCard3 = new JLabel();
        contentPane.add(cpuCard3);

        //Add where we put the played cards
        JLabel player1PlayedCard = new JLabel();
        player1PlayedCard.setBounds(500, scaledHeight / 2 - 150, scaledWidth, scaledHeight);
        contentPane.add(player1PlayedCard);
        player2PlayedCard = new JLabel();
        player2PlayedCard.setBounds(405 + scaledWidth, scaledHeight / 2 + 150, scaledWidth, scaledHeight);
        contentPane.add(player2PlayedCard);

        //making back of card image
        backOfCard = new ImageIcon("src/images/backOfCard.png");
        scaledHeight = (int) ((double) scaledWidth / backOfCard.getIconWidth() * backOfCard.getIconHeight());
        Image scaledImage = backOfCard.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        scaledIcon = new ImageIcon(scaledImage);

        //add the back of card image to the screen
        JLabel backOfCardPic = new JLabel(scaledIcon);
        backOfCardPic.setBounds( 50, gameHeight / 2 - scaledHeight / 2  - 40, scaledWidth,scaledHeight);
        contentPane.add(backOfCardPic);

        //add message on who won after every round
        JLabel messageLabel = new JLabel("Who Won: ");
        messageLabel.setBounds(gameWidth - 400, gameHeight - 100, 150, 50);
        contentPane.add(messageLabel);

        //add message for what card is being hovered
        JLabel infoLabel = new JLabel("Card: ");
        infoLabel.setBounds(gameWidth - 200, gameHeight - 100, 150, 50);
        contentPane.add(infoLabel);

        //add message for Player One points
        userPointsLabel = new JLabel("User Points: 0");
        userPointsLabel.setBounds(gameWidth - 600, gameHeight - 100, 150, 50);
        contentPane.add(userPointsLabel);

        //add message for player two points
        cpuPointsLabel = new JLabel("CPU Points: 0");
        cpuPointsLabel.setBounds(gameWidth - 800, gameHeight - 100, 150, 50);
        contentPane.add(cpuPointsLabel);

        //add message for Trump Suit
        trumpSuitLabel = new JLabel("TrumpSuit: ");
        trumpSuitLabel.setBounds(gameWidth - 1000, gameHeight - 100, 150, 50);
        contentPane.add(trumpSuitLabel);

        //add message for how many cards left in the deck
        deckSizeLabel = new JLabel("Cards Left: ");
        deckSizeLabel.setBounds(gameWidth - 1200, gameHeight - 100, 150, 50);
        contentPane.add(deckSizeLabel);


        menuFrame.setVisible(false);

        dealButton.addActionListener(e -> {
            if (deck.getDeck().size() == 40) {
                System.out.println("Who's starting: " + whoWon);
                if (whoWon == 1) {
                    trumpSuitCard = deck.dealCards(hand1, hand2, whoWon);
                    deckSizeLabel.setText("Cards Left: " + deck.getDeck().size());
                    trumpSuitLabel.setText("Trump Suit: " + trumpSuitCard.getSuit());
                    playerCard1 = hand1.getHand().get(0);
                    playerCard2 = hand1.getHand().get(1);
                    playerCard3 = hand1.getHand().get(2);
                    player2Card1 = hand2.getHand().get(0);
                    player2Card2 = hand2.getHand().get(1);
                    player2Card3 = hand2.getHand().get(2);

                    Icon scaledIconPlayerCard1 = scaleImage(playerCard1);
                    Icon scaledIconPlayerCard2 = scaleImage(playerCard2);
                    Icon scaledIconPlayerCard3 = scaleImage(playerCard3);

                    player1Card1Button.setBounds(840, 400, scaledWidth, 250);
                    contentPane.add(player1Card1Button);
                    player1Card2Button.setBounds(845 + scaledWidth, 400, scaledWidth, 250);
                    contentPane.add(player1Card2Button,1);
                    player1Card3Button.setBounds(850 + scaledWidth + scaledWidth, 400, scaledWidth, 250);
                    contentPane.add(player1Card3Button,1);

                    player1Card1Button.setVisible(true);
                    player1Card1Button.setEnabled(true);
                    player1Card2Button.setVisible(true);
                    player1Card2Button.setEnabled(true);
                    player1Card3Button.setVisible(true);
                    player1Card3Button.setEnabled(true);

                    player1Card1Button.setIcon(scaledIconPlayerCard1);
                    player1Card2Button.setIcon(scaledIconPlayerCard2);
                    player1Card3Button.setIcon(scaledIconPlayerCard3);

                    scaledHeight = (int) ((double) scaledWidth / backOfCard.getIconWidth() * backOfCard.getIconHeight());

                    cpuCard1.setIcon(scaledIcon);
                    cpuCard1.setBounds(840, 30, scaledWidth, scaledHeight);
                    contentPane.add(cpuCard1);

                    cpuCard2.setIcon(scaledIcon);
                    cpuCard2.setBounds(845 + scaledWidth, 30, scaledWidth, scaledHeight);
                    contentPane.add(cpuCard2);

                    cpuCard3.setIcon(scaledIcon);
                    cpuCard3.setBounds(850 + scaledWidth + scaledWidth, 30, scaledWidth, scaledHeight);
                    contentPane.add(cpuCard3);

                    cpuCard1.setVisible(true);
                    cpuCard2.setVisible(true);
                    cpuCard3.setVisible(true);


                    topCardPic.setIcon(new ImageIcon(scaleImage(trumpSuitCard).getImage()));
                    topCardPic.setBounds(105 + scaledWidth - 50, gameHeight / 2 - scaledHeight / 2 - 40, scaledWidth, scaledHeight);
                    contentPane.add(topCardPic);
                    topCardPic.setVisible(true);
                    backOfCardPic.setVisible(true);

                    System.out.println(deck.getDeck().size());
                    System.out.println("Player 1: " + hand1.getHand());
                    System.out.println("Player 2: " + hand2.getHand());

                    dealButton.setEnabled(false);
                    dealButton.setVisible(false);
                    System.out.println(trumpSuitCard.toString());
                } else {
                    trumpSuitCard = deck.dealCards(hand1, hand2, whoWon);
                    playerCard1 = hand1.getHand().get(0);
                    playerCard2 = hand1.getHand().get(1);
                    playerCard3 = hand1.getHand().get(2);
                    player2Card1 = hand2.getHand().get(0);
                    player2Card2 = hand2.getHand().get(1);
                    player2Card3 = hand2.getHand().get(2);

                    Icon scaledIconPlayerCard1 = scaleImage(playerCard1);
                    Icon scaledIconPlayerCard2 = scaleImage(playerCard2);
                    Icon scaledIconPlayerCard3 = scaleImage(playerCard3);

                    player1Card1Button.setBounds(750, 500, scaledWidth, 250);
                    contentPane.add(player1Card1Button);
                    player1Card2Button.setBounds(755 + scaledWidth, 500, scaledWidth, 250);
                    contentPane.add(player1Card2Button);
                    player1Card3Button.setBounds(760 + scaledWidth + scaledWidth, 500, scaledWidth, 250);
                    contentPane.add(player1Card3Button);

                    player1Card1Button.setVisible(true);
                    player1Card1Button.setEnabled(true);
                    player1Card2Button.setVisible(true);
                    player1Card2Button.setEnabled(true);
                    player1Card3Button.setVisible(true);
                    player1Card3Button.setEnabled(true);

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

                    cpuCard1.setVisible(true);
                    cpuCard2.setVisible(true);
                    cpuCard3.setVisible(true);

                    topCardPic.setIcon(new ImageIcon(scaleImage(trumpSuitCard).getImage()));
                    topCardPic.setBounds(105 + scaledWidth, gameHeight / 2 - scaledHeight / 2, scaledWidth, scaledHeight);
                    contentPane.add(topCardPic);
                    topCardPic.setVisible(true);
                    backOfCardPic.setVisible(true);

                    dealButton.setEnabled(false);
                    dealButton.setVisible(false);

                    Card player2Card = hand2.getHand().get(0);

                    Icon scaledIconPlayer2Card = scaleImage(player2Card);
                    player2PlayedCard.setIcon(scaledIconPlayer2Card);


                    System.out.println("First Card: " + hand2.getHand().get(0));
                    //method for selecting card for cpu
                    hand2.playFirstCard(discard2);

                    cpuCard1.setVisible(false);
                    player2PlayedCard.setVisible(true);
                    System.out.println("CPU: Card played: " + discard2.getDiscard());

                }
            }
        });

        newGameButton.addActionListener(e -> {
            resetGame();
            messageLabel.setText("Who Won: ");
            newGameButton.setVisible(false);
            newGameButton.setEnabled(false);
        });

        nextRoundButton.addActionListener(e -> {
            messageLabel.setVisible(false);

            System.out.println("Hand1: " + hand1.getHand());
            System.out.println("Hand2: " + hand2.getHand());

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
                    cpuCard2.setVisible(true);
                    cpuCard3.setVisible(true);

                    nextRoundButton.setVisible(false);
                    nextRoundButton.setEnabled(false);

                } else if (whoWon == 2) {
                    discard1.cardsWon(pile2);
                    discard2.cardsWon(pile2);

                    player1PlayedCard.setVisible(false);
                    player2PlayedCard.setVisible(true);

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
                    cpuCard2.setVisible(true);
                    cpuCard3.setVisible(true);

                    nextRoundButton.setVisible(false);
                    nextRoundButton.setEnabled(false);

                    if (easyMode) {
                        randomCardPicker();
                    } else {
                        hardModePicker(null);
                    }
                    setImagesForCPU(cardChosen);

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
                    cpuCard2.setVisible(true);
                    cpuCard3.setVisible(true);

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

                    if (easyMode) {
                        randomCardPicker();
                    } else {
                        hardModePicker(null);
                    }
                    setImagesForCPU(cardChosen);

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
                    cpuCard2.setVisible(true);

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
                    cpuCard2.setVisible(true);

                    nextRoundButton.setVisible(false);
                    nextRoundButton.setEnabled(false);

                    if (easyMode) {
                        randomCardPicker();
                    } else {
                        hardModePicker(null);
                    }
                    setImagesForCPU(cardChosen);

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

                    if (easyMode) {
                        randomCardPicker();
                    } else {
                        hardModePicker(null);
                    }
                    setImagesForCPU(cardChosen);

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
                    nextRoundButton.setVisible(false);
                    nextRoundButton.setVisible(false);
                    newGameButton.setVisible(true);
                    newGameButton.setEnabled(true);

                } else if (whoWon == 2) {
                    discard1.cardsWon(pile2);
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
                    nextRoundButton.setVisible(false);
                    nextRoundButton.setEnabled(false);
                    newGameButton.setVisible(true);
                    newGameButton.setEnabled(true);

                }
            }
            userPointsLabel.setText("User Points: " + pile1.getPoints());
            cpuPointsLabel.setText("CPU Points: " + pile2.getPoints());
            messageLabel.setText("Who Won: ");
            messageLabel.setVisible(true);
            deckSizeLabel.setText("Cards Left: " + deck.getDeck().size());
        });

        player1Card1Button.addActionListener(e -> {

            if (whoWon == 1) {
                Icon scaledIconPlayerCard1 = scaleImage(playerCard1);
                player1PlayedCard.setBounds(439, scaledHeight / 2 + 97, scaledWidth, scaledHeight);
                contentPane.add(player1PlayedCard);
                player1PlayedCard.setIcon(scaledIconPlayerCard1);

                hand1.playFirstCard(discard1);

                //method for selecting card for now just pick first
                System.out.println("Card Comparing: " + playerCard1);

                if (easyMode) {
                    randomCardPicker();
                } else {
                    hardModePicker(playerCard1);
                }
                setImagesForCPU(cardChosen);

                player1Card1Button.setVisible(false);
                player1Card1Button.setEnabled(false);

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
                    messageLabel.setText("Who Won: You Won");
                    messageLabel.setVisible(true);
                } else if (whoWon == 2) {
                    messageLabel.setText("Who Won: You Lost");
                    messageLabel.setVisible(true);
                }
            } else if (whoWon == 2) {
                Icon scaledIconPlayerCard1 = scaleImage(playerCard1);
                player1PlayedCard.setBounds(439, scaledHeight / 2 + 97, scaledWidth, scaledHeight);
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
                    messageLabel.setText("Who Won: You Won");
                    messageLabel.setVisible(true);
                } else if (whoWon == 2) {
                    messageLabel.setText("Who Won: You Lost");
                    messageLabel.setVisible(true);
                }
            }
        });

        //
        player1Card2Button.addActionListener(e -> {
            if (whoWon == 1) {
                Icon scaledIconPlayerCard2 = scaleImage(playerCard2);
                player1PlayedCard.setBounds(439, scaledHeight / 2 + 97, scaledWidth, scaledHeight);
                contentPane.add(player1PlayedCard);
                player1PlayedCard.setIcon(scaledIconPlayerCard2);

                hand1.playSecondCard(discard1);

                //method for selecting card for now just pick first
                System.out.println("Card Comparing: " + playerCard2);

                if (easyMode) {
                    randomCardPicker();
                } else {
                    hardModePicker(playerCard2);
                }
                setImagesForCPU(cardChosen);

                player1Card2Button.setVisible(false);
                player1Card2Button.setEnabled(false);

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
                    messageLabel.setText("Who Won: You Won");
                    messageLabel.setVisible(true);
                } else if (whoWon == 2) {
                    messageLabel.setText("Who Won: You Lost");
                    messageLabel.setVisible(true);
                }
            } else if (whoWon == 2) {
                playedCard = playerCard1;

                Icon scaledIconPlayerCard2 = scaleImage(playerCard2);
                player1PlayedCard.setBounds(439, scaledHeight / 2 + 97, scaledWidth, scaledHeight);
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
                    messageLabel.setText("Who Won: You Won");
                    messageLabel.setVisible(true);
                } else if (whoWon == 2) {
                    messageLabel.setText("Who Won: You Lost");
                    messageLabel.setVisible(true);
                }
            }
        });

        player1Card3Button.addActionListener(e -> {
            if (whoWon == 1) {
                Icon scaledIconPlayerCard3 = scaleImage(playerCard3);
                player1PlayedCard.setBounds(439, scaledHeight / 2 + 97, scaledWidth, scaledHeight);
                contentPane.add(player1PlayedCard);
                player1PlayedCard.setIcon(scaledIconPlayerCard3);

                hand1.playThirdCard(discard1);

                //method for selecting card for now just pick first
                System.out.println("Card Comparing: " + playerCard3);

                if (easyMode) {
                    randomCardPicker();
                } else {
                    hardModePicker(playerCard3);
                }

                setImagesForCPU(cardChosen);

                player1Card3Button.setVisible(false);
                player1Card3Button.setEnabled(false);

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
                    messageLabel.setText("Who Won: You Won");
                    messageLabel.setVisible(true);
                } else if (whoWon == 2) {
                    messageLabel.setText("Who Won: You Lost");
                    messageLabel.setVisible(true);
                }
            } else if (whoWon == 2) {
                playedCard = playerCard1;

                Icon scaledIconPlayerCard3 = scaleImage(playerCard3);
                player1PlayedCard.setBounds(439, scaledHeight / 2 + 97, scaledWidth, scaledHeight);
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
                    messageLabel.setText("Who Won: You Won");
                    messageLabel.setVisible(true);
                } else if (whoWon == 2) {
                    messageLabel.setText("Who Won: You Lost");
                    messageLabel.setVisible(true);
                }
            }
        });

        player1Card1Button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                infoLabel.setText(String.valueOf("Card: " + hand1.getHand().get(0)));
            }
            public void mouseExited(MouseEvent e) {
                infoLabel.setText("Card: ");
            }
        });

        player1Card2Button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (player1Card1Button.isVisible() == false) {
                    infoLabel.setText(String.valueOf("Card: " + hand1.getHand().get(0)));
                } else {
                    infoLabel.setText(String.valueOf("Card: " + hand1.getHand().get(1)));
                }
            }
            public void mouseExited(MouseEvent e) {
                infoLabel.setText("Card: ");
            }
        });


        player1Card3Button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (player1Card1Button.isVisible() == false || player1Card2Button.isVisible() == false) {
                    infoLabel.setText(String.valueOf("Card: " + hand1.getHand().get(1)));
                } else {
                    infoLabel.setText(String.valueOf("Card: " + hand1.getHand().get(2)));
                }
            }
            public void mouseExited(MouseEvent e) {
                infoLabel.setText("Card: ");
            }
        });

        // Set the frame to be visible
        gameFrame.setVisible(true);
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
        whoWon = 1;
        //Random random = new Random();
        //whoWon = random.nextInt(2) + 1;
    }

    private void randomCardPicker() {
        if (hand2.getHand().size() == 1) {
            cardChosen = 0;
        }
        if (hand2.getHand().size() == 2) {
            Random random = new Random();
            cardChosen = random.nextInt(2);

        }
        if (hand2.getHand().size() == 3) {
            Random random = new Random();
            cardChosen = random.nextInt(3);
        }
    }

    private void hardModePicker(Card player1Card) {
        if (player1Card == null) {
            cardChosen = lowestCardWorthIndex(hand2.getHand());
            System.out.println("Card Chosen: " + hand2.getHand().get(cardChosen));
        } else {
            if (player1Card.getWorth() > 3 && playerHasTrumpSuit(hand2.getHand()) && !player1Card.getSuit().equals(trumpSuitCard.getSuit())) {
                cardChosen = trumpSuitCardIndex(hand2.getHand());
                System.out.println("Card Chosen: " + hand2.getHand().get(cardChosen));

            } else if (player1Card.getSuit().equals(trumpSuitCard.getSuit())) {
                cardChosen = lowestCardWorthIndex(hand2.getHand());
                System.out.println("Card Chosen: " + hand2.getHand().get(cardChosen));

            } else if (player1Card.getSuit().equals(highestWorthCard(hand2.getHand()).getSuit()) && player1Card.getStrength() < highestWorthCard(hand2.getHand()).getStrength()) {
                cardChosen = highestCardWorthIndex(hand2.getHand());
                System.out.println("Card Chosen: " + hand2.getHand().get(cardChosen));

            } else {
                cardChosen = lowestCardWorthIndex(hand2.getHand());
                System.out.println("Card Chosen: " + hand2.getHand().get(cardChosen));

            }
        }
    }

    private void setImagesForCPU(int cardChosen) {

        if (cardChosen == 0) {
            Card player2Card = hand2.getHand().get(0);

            Icon scaledIconPlayer2Card = scaleImage(player2Card);
            player2PlayedCard.setBounds(455 + scaledWidth, scaledHeight / 2 + 97, scaledWidth, scaledHeight);
            contentPane.add(player2PlayedCard);
            player2PlayedCard.setIcon(scaledIconPlayer2Card);

            hand2.playFirstCard(discard2);

            player2PlayedCard.setVisible(true);
            cpuCard1.setVisible(false);

        } else if (cardChosen == 1) {
            Card player2Card = hand2.getHand().get(1);

            Icon scaledIconPlayer2Card = scaleImage(player2Card);
            player2PlayedCard.setBounds(445 + scaledWidth, scaledHeight / 2 + 97, scaledWidth, scaledHeight);
            contentPane.add(player2PlayedCard);
            player2PlayedCard.setIcon(scaledIconPlayer2Card);

            hand2.playSecondCard(discard2);

            player2PlayedCard.setVisible(true);
            cpuCard2.setVisible(false);

        } else {
            Card player2Card = hand2.getHand().get(2);

            Icon scaledIconPlayer2Card = scaleImage(player2Card);
            player2PlayedCard.setBounds(445 + scaledWidth, scaledHeight / 2 + 97, scaledWidth, scaledHeight);
            contentPane.add(player2PlayedCard);
            player2PlayedCard.setIcon(scaledIconPlayer2Card);

            hand2.playThirdCard(discard2);

            player2PlayedCard.setVisible(true);
            cpuCard3.setVisible(false);
        }
    }

    private int highestCardWorth(List<Card> hand) {
        int highestCardWorth = hand.get(0).getWorth();
        int highestCardIndex = 0;
        for (int i = 0; i < hand.size(); i++) {
            int worth = hand.get(i).getWorth();
            if (worth > highestCardWorth) {
                highestCardWorth = worth;
                highestCardIndex = i;
            }
        }
        return highestCardWorth;
    }

    private Card highestWorthCard(List<Card> hand) {
        Card highestWorthCard = hand.get(0);
        int highestCardWorth = 0;
        for (int i = 0; i < hand.size(); i++) {
            int worth = hand.get(i).getWorth();
            if (worth > highestCardWorth) {
                highestCardWorth = worth;
                highestWorthCard = hand.get(i);
            }
        }
        return highestWorthCard;
    }

    private int highestCardWorthIndex(List<Card> hand) {
        int highestCardWorth = hand.get(0).getWorth();
        int highestCardIndex = 0;
        for (int i = 0; i < hand.size(); i++) {
            int worth = hand.get(i).getWorth();
            if (worth > highestCardWorth) {
                highestCardWorth = worth;
                highestCardIndex = i;
            }
        }
        return highestCardIndex;
    }

    private int lowestCardWorthIndex(List<Card> hand) {
        int lowestCardWorth = hand.get(0).getWorth();
        int lowestCardIndex = 0;
        for (int i = 0; i < hand.size(); i++) {
            int worth = hand.get(i).getWorth();
            if (worth < lowestCardWorth) {
                lowestCardWorth = worth;
                lowestCardIndex = i;
            }
        }
        return lowestCardIndex;
    }
    private int lowestCardWorth(List<Card> hand) {
        int lowestCardWorth = hand.get(0).getWorth();
        int lowestCardIndex = 0;
        for (int i = 0; i < hand.size(); i++) {
            int worth = hand.get(i).getWorth();
            if (worth < lowestCardWorth) {
                lowestCardWorth = worth;
                lowestCardIndex = i;
            }
        }
        return lowestCardWorth;
    }

    private boolean playerHasTrumpSuit(List<Card> hand) {
        boolean hasTrump = false;
        for (Card card : hand) {
            if (card.getSuit().equals(trumpSuitCard.getSuit())) {
                hasTrump = true;
            }
        }
        return hasTrump;
    }

    private int trumpSuitCardIndex(List<Card> hand) {
        int trumpSuitCardIndex = 0;
        for (int i = 0; i < hand.size(); i++) {
            boolean isTrump = hand.get(i).getSuit().equals(trumpSuitCard.getSuit());
            if (isTrump) {
                trumpSuitCardIndex = i;
            }
        }
        return trumpSuitCardIndex;
    }
    private void resetGame() {
        pile1.startOver();
        pile2.startOver();
        deck = new Deck();
        dealButton.setEnabled(true);
        dealButton.setVisible(true);
        newGameButton.setVisible(false);
        newGameButton.setEnabled(false);
        nextRoundButton.setText("Next Round");
        userPointsLabel.setText("User Points: 0");
        cpuPointsLabel.setText("CPU Points: 0");
        trumpSuitLabel.setText("Trump Suit: ");
    }
}
