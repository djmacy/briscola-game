
package gameGUI;

import gameStructure.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Random;
import javax.swing.*;

/**
 * Controller class that creates the GUI and logic for the entire game of Briscola. This is going to be a 2P version
 * of the game where the user begins every game. This controller controls three windows, the gameFrame, menuFrame, and
 * the instructionsFrame. The menuFrame is where the user starts every time the application starts. In the menuFrame the
 * user can select which difficulty they would like to play on, visit the instructions frame, or to start the game.
 */

public class BriscolaGUI extends JFrame {
    /**
     * Main method that starts the Briscola game
     *
     * @param args BriscolaGUI
     */
    public static void main(String[] args) {
        new BriscolaGUI();
    }
    private JFrame gameFrame;
    private JFrame menuFrame;
    private JFrame instructionsFrame;
    private JFrame pointsFrame;
    private JFrame scenarioOne;
    private JFrame scenarioTwo;
    private JFrame scenarioThree;
    private Container contentPane;
    private JButton dealButton;
    private JButton player1Card1Button;
    private JButton player1Card2Button;
    private JButton player1Card3Button;
    private JButton nextRoundButton;
    private JButton newGameButton;
    private JButton instructionsButton;
    private JButton startButton;
    private JButton mainMenuButton;
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
    private final int scaledWidth = 125;
    private int scaledHeight;
    private Card playerCard1;
    private Card playerCard2;
    private Card playerCard3;
    private Card player2Card1;
    private Card player2Card2;
    private Card player2Card3;
    private Card playedCard;
    private Card player2Card;
    private Card trumpSuitCard;
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
    private JLabel wonOrLostLabel;
    private JLabel hints;
    private int whoWon = 1;
    private int rounds = 20;
    private int cardChosen;
    private Boolean easyMode = true;

    /**
     * This creates the main menu where the user can navigate to the game, select a difficulty, or see the instructions.
     */
    private BriscolaGUI() {
        //creating the main menu frame. This inlcudes a way for the user to choose a difficulty and look at instructions
        // on how to play
        menuFrame = new JFrame("Main Menu");
        menuFrame.setSize(gameWidth,gameHeight);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Method that I found for painting images for the background.
        //https://stackoverflow.com/questions/26698975/how-to-deal-with-public-void-paint-method-in-jframe
        JPanel menuPanel = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image img = new ImageIcon("src/images/briscola_center_menu.png").getImage();
                Dimension size = getSize();
                g.drawImage(img,0,0,size.width,size.height, null);
            }
        };
        menuFrame.setResizable(false);
        menuFrame.setContentPane(menuPanel);
        Container contentPane = menuFrame.getContentPane();
        contentPane.setLayout(null);
        //creating the start game button
        startButton = new JButton("Start Game");
        startButton.setBounds(gameWidth/2 - 60, gameHeight - 100, 110,50);
        startButton.addActionListener(e -> showGameWindow());
        contentPane.add(startButton);
        //exit the game button
        JButton quitGameButton = new JButton("Exit");
        quitGameButton.setBounds(gameWidth - 1125, 100, 100, 50);
        quitGameButton.setFocusPainted(false);
        quitGameButton.addActionListener(e -> exitGame());
        contentPane.add(quitGameButton);
        //display point label after a game has been played
        wonOrLostLabel = new JLabel("");
        wonOrLostLabel.setFont(new Font("SANS_SERIF", Font.BOLD, 22));
        wonOrLostLabel.setForeground(Color.decode("#545454"));
        wonOrLostLabel.setBounds(gameWidth/2 - 160, gameHeight - 780, 400, 100);
        contentPane.add(wonOrLostLabel);
        //creating the easymode radio button
        JRadioButton easyButton = new JRadioButton("Easy");
        easyButton.setBounds(gameWidth - 300, gameHeight - 100, 100, 50);
        contentPane.add(easyButton);
        easyButton.setOpaque(false);
        easyButton.setSelected(true);
        //easy mode is set by default to true since easy button is selected by default. If it gets pressed to normal mode
        //and then back to easyMode the boolean will switch back to true.
        easyButton.addActionListener(e -> {
            easyMode = true;
        });
        //creating the normalButton radio button
        JRadioButton normalButton = new JRadioButton("Normal");
        normalButton.setBounds(gameWidth - 200, gameHeight - 100, 100, 50);
        contentPane.add(normalButton);
        normalButton.setOpaque(false);
        //setting the boolean to false whenever normal mode is pressed
        normalButton.addActionListener(e -> {
            easyMode = false;
        });
        //adding the radio buttons to a button group that way only one can get selected at a time
        bg = new ButtonGroup();
        bg.add(easyButton);
        bg.add(normalButton);
        //creating the button for the instructions on how to play. Once pressed the user will open another window with
        //instructions
        instructionsButton = new JButton("How To Play");
        instructionsButton.setBounds(gameWidth - 1125, gameHeight - 100, 110, 50);
        instructionsButton.setFocusPainted(false);
        contentPane.add(instructionsButton);
        //This needs to be the last action done to the frame that way all containers are visible at the same time.
        menuFrame.setVisible(true);
        //This calls the method which opens the other window
        instructionsButton.addActionListener(e -> showInstructionsWindow());
    }

    /**
     * Sends the user to the game and generates the deck of cards with the buttons needed to play the game of Briscola.
     */
    private void showGameWindow() {
        //disable other buttons to prevent other frames from opening after startButton has been pressed
        startButton.setEnabled(false);
        instructionsButton.setEnabled(false);
        //objects that need to be created for the game to start
        deck = new Deck();
        hand1 = new Hand();
        hand2 = new Hand();
        discard1 = new Discard();
        discard2 = new Discard();
        pile1 = new Pile();
        pile2 = new Pile();
        //create the card buttons. Each button will get the image of the card it's associated with.
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
                Image img = new ImageIcon("src/images/background_image.png").getImage();
                Dimension size = getSize();
                g.drawImage(img,0,0,size.width,size.height,null);
            }
        };
        gameFrame.setResizable(false);
        gameFrame.setContentPane(gamePanel);
        contentPane = gameFrame.getContentPane();
        contentPane.setLayout(null);
        //create deal button to begin the game
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
        //create main menu button from game window
        mainMenuButton = new JButton("Main Menu");
        mainMenuButton.setBounds(125, 50, 100, 50);
        mainMenuButton.setFocusPainted(false);
        contentPane.add(mainMenuButton);
        mainMenuButton.setVisible(true);
        mainMenuButton.setEnabled(true);
        //creating and adding the cpu cards to the screen
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
        backOfCard = new ImageIcon("src/images/back_of_card.png");
        scaledHeight = (int) ((double) scaledWidth / backOfCard.getIconWidth() * backOfCard.getIconHeight());
        Image scaledImage = backOfCard.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        scaledIcon = new ImageIcon(scaledImage);
        //add the back of card image to the screen
        JLabel backOfCardPic = new JLabel(scaledIcon);
        backOfCardPic.setBounds( 50, gameHeight / 2 - scaledHeight / 2  - 40, scaledWidth,scaledHeight);
        contentPane.add(backOfCardPic);
        //add message on who won after every round
        messageLabel = new JLabel("");
        messageLabel.setFont(new Font("SANS_SERIF", Font.BOLD, 30));
        messageLabel.setForeground(Color.decode("#545454"));
        messageLabel.setBounds(gameWidth/ 2 - 150, 100, 150, 50);
        contentPane.add(messageLabel);
        messageLabel.setVisible(false);
        //add message for what card is being hovered
        JLabel player1Card1Label = new JLabel("");
        player1Card1Label.setBounds(gameWidth - 435, gameHeight - 165, 150, 50);
        contentPane.add(player1Card1Label);
        player1Card1Label.setVisible(true);
        //creating new labels for the hover first card
        JLabel player1Card2Label = new JLabel("");
        player1Card2Label.setBounds(gameWidth - 310, gameHeight - 165, 150, 50);
        contentPane.add(player1Card2Label);
        player1Card2Label.setVisible(true);
        //creating new labels for the hover third card
        JLabel player1Card3Label = new JLabel("");
        player1Card3Label.setBounds(gameWidth - 175, gameHeight - 165, 150, 50);
        contentPane.add(player1Card3Label);
        player1Card3Label.setVisible(true);
        //creting new labels for the player1 card played
        JLabel player1PlayedCardLabel = new JLabel("");
        player1PlayedCardLabel.setBounds(gameWidth - 835, gameHeight - 595, 150, 50);
        contentPane.add(player1PlayedCardLabel);
        player1PlayedCardLabel.setVisible(true);
        //creating new labels for the player2 card played
        JLabel player2PlayedCardLabel = new JLabel("");
        player2PlayedCardLabel.setBounds(gameWidth - 695, gameHeight - 595, 150, 50);
        contentPane.add(player2PlayedCardLabel);
        player2PlayedCardLabel.setVisible(true);
        //creating new labels for the trump suit card
        JLabel trumpSuitCardLabel = new JLabel("");
        trumpSuitCardLabel.setBounds(gameWidth - 1090, gameHeight - 330, 150, 50);
        contentPane.add(trumpSuitCardLabel);
        trumpSuitCardLabel.setVisible(true);
        //add message for Player One points
        userPointsLabel = new JLabel("User Points: 0");
        userPointsLabel.setBounds(gameWidth - 300, gameHeight - 100, 150, 50);
        contentPane.add(userPointsLabel);
        userPointsLabel.setVisible(false);
        //add message for player two points
        cpuPointsLabel = new JLabel("CPU Points: 0");
        cpuPointsLabel.setBounds(gameWidth - 600, gameHeight - 100, 150, 50);
        contentPane.add(cpuPointsLabel);
        cpuPointsLabel.setVisible(false);
        //add message for Trump Suit
        trumpSuitLabel = new JLabel("TrumpSuit: ");
        trumpSuitLabel.setBounds(gameWidth - 900, gameHeight - 100, 150, 50);
        contentPane.add(trumpSuitLabel);
        trumpSuitLabel.setVisible(false);
        //add message for how many cards left in the deck
        deckSizeLabel = new JLabel("Rounds: ");
        deckSizeLabel.setBounds(gameWidth - 1200, gameHeight - 100, 150, 50);
        contentPane.add(deckSizeLabel);
        deckSizeLabel.setVisible(false);
        //add message for hints
        hints = new JLabel("");
        hints.setBounds(gameWidth - 355, gameHeight - 475, 200, 50);
        contentPane.add(hints);
        menuFrame.setVisible(false);
        dealButton.addActionListener(e -> {
            //deal cards will give each player three cards and then we will assign the trump suit card for the game
            trumpSuitCard = deck.dealCards(hand1, hand2);
            deckSizeLabel.setText("Rounds Left: " + rounds);
            trumpSuitLabel.setText("Trump Suit: " + trumpSuitCard.getSuit());
            //assign the variables of each players cards in their hands. This will allows us to get the respective
            //images from each card object and any other information we need from them.
            playerCard1 = hand1.getHand().get(0);
            playerCard2 = hand1.getHand().get(1);
            playerCard3 = hand1.getHand().get(2);
            player2Card1 = hand2.getHand().get(0);
            player2Card2 = hand2.getHand().get(1);
            player2Card3 = hand2.getHand().get(2);
            //Use the scale methods to make sure all images are compressed and the same size
            Icon scaledIconPlayerCard1 = scaleImage(playerCard1);
            Icon scaledIconPlayerCard2 = scaleImage(playerCard2);
            Icon scaledIconPlayerCard3 = scaleImage(playerCard3);
            //setting the bounds for each player ones cards/buttons
            player1Card1Button.setBounds(840, 400, scaledWidth, 250);
            contentPane.add(player1Card1Button);
            player1Card2Button.setBounds(845 + scaledWidth, 400, scaledWidth, 250);
            contentPane.add(player1Card2Button,1);
            player1Card3Button.setBounds(850 + scaledWidth + scaledWidth, 400, scaledWidth, 250);
            contentPane.add(player1Card3Button,1);
            //making sure every button is enabled and visible
            player1Card1Button.setVisible(true);
            player1Card1Button.setEnabled(true);
            player1Card2Button.setVisible(true);
            player1Card2Button.setEnabled(true);
            player1Card3Button.setVisible(true);
            player1Card3Button.setEnabled(true);
            //adding the images to each button
            player1Card1Button.setIcon(scaledIconPlayerCard1);
            player1Card2Button.setIcon(scaledIconPlayerCard2);
            player1Card3Button.setIcon(scaledIconPlayerCard3);
            //variable that will be used to make sure each card is thhe same size
            scaledHeight = (int) ((double) scaledWidth / backOfCard.getIconWidth() * backOfCard.getIconHeight());
            //setting the bounds and back of card image for the cpu's cardds
            cpuCard1.setIcon(scaledIcon);
            cpuCard1.setBounds(840, 30, scaledWidth, scaledHeight);
            contentPane.add(cpuCard1);
            cpuCard2.setIcon(scaledIcon);
            cpuCard2.setBounds(845 + scaledWidth, 30, scaledWidth, scaledHeight);
            contentPane.add(cpuCard2);
            cpuCard3.setIcon(scaledIcon);
            cpuCard3.setBounds(850 + scaledWidth + scaledWidth, 30, scaledWidth, scaledHeight);
            contentPane.add(cpuCard3);
            //setting all the cpu's cards as visible
            cpuCard1.setVisible(true);
            cpuCard2.setVisible(true);
            cpuCard3.setVisible(true);
            //setting the deck, represented by a back of card picture and the trump suit card visible to the player
            topCardPic.setIcon(new ImageIcon(scaleImage(trumpSuitCard).getImage()));
            topCardPic.setBounds(105 + scaledWidth - 50, gameHeight / 2 - scaledHeight / 2 - 40, scaledWidth, scaledHeight);
            contentPane.add(topCardPic);
            topCardPic.setVisible(true);
            backOfCardPic.setVisible(true);
            //disabling the button so it can't be pressed twice
            dealButton.setEnabled(false);
            dealButton.setVisible(false);
            userPointsLabel.setVisible(true);
            cpuPointsLabel.setVisible(true);
            deckSizeLabel.setVisible(true);
            hints.setVisible(true);
            trumpSuitLabel.setVisible(true);
            //add hints if on easy mode.
            if (easyMode) {
                hints.setText("Try playing the card worth least");
            }
        });
        //button that is shown at the end of the game will reset the board
        newGameButton.addActionListener(e -> {
            resetGame();
            messageLabel.setText("");
            newGameButton.setVisible(false);
            newGameButton.setEnabled(false);
        });
        //button to go back to the main menu from the game window
        mainMenuButton.addActionListener(e -> {
            showMainMenuFrameFromGameFrame();
        });
        //button that does a majority of the heavy lifting. Most of the logic is found in the following code. This button
        //is activated when both players have played a card.
        nextRoundButton.addActionListener(e -> {
            messageLabel.setVisible(false);
            //this logic represents the majority of the game. This is before the end game starts.
            if (deck.getDeck().size() > 1) {
                //this method compares two cards and returns and reassigns the whoWon variable
                checkWhoWins(discard1.getDiscard().get(0), discard2.getDiscard().get(0));
                //if player 1 wins the following code is applied
                if (whoWon == 1) {
                    //send the cards to players ones pile
                    discard1.cardsWon(pile1);
                    discard2.cardsWon(pile1);
                    //make the played cards invisible
                    player1PlayedCard.setVisible(false);
                    player2PlayedCard.setVisible(false);
                    //deal the card to us first then CPU
                    deck.dealTopCard(hand1);
                    deck.dealTopCard(hand2);
                    //reset all the playercard variables
                    playerCard1 = hand1.getHand().get(0);
                    playerCard2 = hand1.getHand().get(1);
                    playerCard3 = hand1.getHand().get(2);
                    Icon scaledIconCard1 = scaleImage(playerCard1);
                    Icon scaledIconCard2 = scaleImage(playerCard2);
                    Icon scaledIconCard3 = scaleImage(playerCard3);
                    //enable all buttons again and assign the new images associated with the buttons
                    player1Card1Button.setIcon(scaledIconCard1);
                    player1Card1Button.setVisible(true);
                    player1Card1Button.setEnabled(true);
                    player1Card2Button.setIcon(scaledIconCard2);
                    player1Card2Button.setVisible(true);
                    player1Card2Button.setEnabled(true);
                    player1Card3Button.setIcon(scaledIconCard3);
                    player1Card3Button.setVisible(true);
                    player1Card3Button.setEnabled(true);
                    //make all back of cpu cards visible again
                    cpuCard1.setVisible(true);
                    cpuCard2.setVisible(true);
                    cpuCard3.setVisible(true);
                    //turn off the button until next time
                    nextRoundButton.setVisible(false);
                    nextRoundButton.setEnabled(false);
                    //add hints for easy mode
                    if (easyMode) {
                        hints.setText("Try playing the card worth least");
                    }
                //if the CPU won that round
                } else if (whoWon == 2) {
                    //send cards to CPU pile
                    discard1.cardsWon(pile2);
                    discard2.cardsWon(pile2);
                    //since the cpu won that round they will have to play a card first which is why we set their played card to visible.
                    player1PlayedCard.setVisible(false);
                    player2PlayedCard.setVisible(true);
                    //draw the cards this time with the CPU getting the top card of the deck
                    deck.dealTopCard(hand2);
                    deck.dealTopCard(hand1);
                    //reassign player card variables for the pictures
                    playerCard1 = hand1.getHand().get(0);
                    playerCard2 = hand1.getHand().get(1);
                    playerCard3 = hand1.getHand().get(2);
                    Icon scaledIconCard1 = scaleImage(playerCard1);
                    Icon scaledIconCard2 = scaleImage(playerCard2);
                    Icon scaledIconCard3 = scaleImage(playerCard3);
                    //reassign the button images with their respective card
                    player1Card1Button.setIcon(scaledIconCard1);
                    player1Card1Button.setVisible(true);
                    player1Card1Button.setEnabled(true);
                    player1Card2Button.setIcon(scaledIconCard2);
                    player1Card2Button.setVisible(true);
                    player1Card2Button.setEnabled(true);
                    player1Card3Button.setIcon(scaledIconCard3);
                    player1Card3Button.setVisible(true);
                    player1Card3Button.setEnabled(true);
                    //make all back of cpu cards visible again
                    cpuCard1.setVisible(true);
                    cpuCard2.setVisible(true);
                    cpuCard3.setVisible(true);
                    //disable the next round button until more cards have been played
                    nextRoundButton.setVisible(false);
                    nextRoundButton.setEnabled(false);
                    // add hints if on easy mode and use the randomCardPicker
                    if (easyMode) {
                        randomCardPicker();
                        player2Card = hand2.getHand().get(cardChosen);
                        hints();
                    //if not on easy mode we assume that we will be playing on normal mode. Since player two has to start the round player 1 card has to be
                    //null. This will make the cpu play its card worth the least.
                    } else {
                        hardModePicker(null);
                    }
                    //set the image for the CPU card played
                    setImagesForCPU(cardChosen);

                }
            //This logic indicates the round right before the end game. In this scenario one of the players will end up
            //drawing the trumpSuitCard that has been shown for the entire game as the last card drawn of the game.
            } else if (deck.getDeck().size() == 1) {
                //again checking to see who wins this round
                checkWhoWins(discard1.getDiscard().get(0), discard2.getDiscard().get(0));
                //making both the deck and trumpSuitCard invisible since these cards will end up being drawn
                topCardPic.setVisible(false);
                backOfCardPic.setVisible(false);
                //if player 1 just won the following logic will apply
                if (whoWon == 1) {
                    //cards will  be added to player ones pile
                    discard1.cardsWon(pile1);
                    discard2.cardsWon(pile1);
                    //setting both cards that were previously played to invisible
                    player1PlayedCard.setVisible(false);
                    player2PlayedCard.setVisible(false);
                    //deal the topcard to player 1 and give the trump suit card to player two
                    deck.dealTopCard(hand1);
                    hand2.dealTrumpSuitCard(trumpSuitCard);
                    //reassigning the variables of player ones cards
                    playerCard1 = hand1.getHand().get(0);
                    playerCard2 = hand1.getHand().get(1);
                    playerCard3 = hand1.getHand().get(2);
                    Icon scaledIconCard1 = scaleImage(playerCard1);
                    Icon scaledIconCard2 = scaleImage(playerCard2);
                    Icon scaledIconCard3 = scaleImage(playerCard3);
                    //reassigning the images for player 1's buttons
                    player1Card1Button.setIcon(scaledIconCard1);
                    player1Card1Button.setVisible(true);
                    player1Card1Button.setEnabled(true);
                    player1Card2Button.setIcon(scaledIconCard2);
                    player1Card2Button.setVisible(true);
                    player1Card2Button.setEnabled(true);
                    player1Card3Button.setIcon(scaledIconCard3);
                    player1Card3Button.setVisible(true);
                    player1Card3Button.setEnabled(true);
                    //making all of CPU's back of cards visible again
                    cpuCard1.setVisible(true);
                    cpuCard2.setVisible(true);
                    cpuCard3.setVisible(true);
                    //disabling next round button until the next time
                    nextRoundButton.setVisible(false);
                    nextRoundButton.setEnabled(false);
                    //add hints for easy mode
                    if (easyMode) {
                        hints.setText("Try playing the card worth least");
                    }
                //if player two won the round
                } else if (whoWon == 2) {
                    //give the cards to CPU's pile
                    discard1.cardsWon(pile2);
                    discard2.cardsWon(pile2);
                    player1PlayedCard.setVisible(false);
                    player2PlayedCard.setVisible(false);
                    //give the top card to the CPU and give the trumpSuitCard to the user
                    deck.dealTopCard(hand2);
                    hand1.dealTrumpSuitCard(trumpSuitCard);
                    //reassign the player card variables to get the proper cards after new cards drawn
                    playerCard1 = hand1.getHand().get(0);
                    playerCard2 = hand1.getHand().get(1);
                    playerCard3 = hand1.getHand().get(2);
                    Icon scaledIconCard1 = scaleImage(playerCard1);
                    Icon scaledIconCard2 = scaleImage(playerCard2);
                    Icon scaledIconCard3 = scaleImage(playerCard3);
                    //reassign the images to each button
                    player1Card1Button.setIcon(scaledIconCard1);
                    player1Card1Button.setVisible(true);
                    player1Card1Button.setEnabled(true);
                    player1Card2Button.setIcon(scaledIconCard2);
                    player1Card2Button.setVisible(true);
                    player1Card2Button.setEnabled(true);
                    player1Card3Button.setIcon(scaledIconCard3);
                    player1Card3Button.setVisible(true);
                    player1Card3Button.setEnabled(true);
                    //make the next round button disabled until the next time
                    nextRoundButton.setVisible(false);
                    nextRoundButton.setEnabled(false);
                    //if on easy mode randomly pick a card to play and give hints
                    if (easyMode) {
                        randomCardPicker();
                        player2Card = hand2.getHand().get(cardChosen);
                        hints();
                    //if not on easy mode use the logic for hard mode which is picking the card with least worth
                    } else {
                        hardModePicker(null);
                    }
                    //reassign the image for the player 2 played card
                    setImagesForCPU(cardChosen);
                    //only make two cards visible because the cpu will have to play a card
                    cpuCard1.setVisible(true);
                    cpuCard2.setVisible(true);
                    cpuCard3.setVisible(false);
                    //make it visible to user can see what card has been played
                    player2PlayedCard.setVisible(true);
                }
            //This is after the first round of the end game has been played
            } else if (deck.getDeck().size() == 0 && hand1.getHand().size() == 2){
                //check to see who won the previous round
                checkWhoWins(discard1.getDiscard().get(0), discard2.getDiscard().get(0));
                //if player 1 won the following logic applies
                if (whoWon == 1) {
                    //send the cards to player 1's pile
                    discard1.cardsWon(pile1);
                    discard2.cardsWon(pile1);
                    player1PlayedCard.setVisible(false);
                    player2PlayedCard.setVisible(false);
                    //reassign the card variables. This time we will only need to reassign the first two cards because
                    //cards are no longer drawn. This would mean that we would only have two cards left in our hand.
                    playerCard1 = hand1.getHand().get(0);
                    playerCard2 = hand1.getHand().get(1);
                    Icon scaledIconCard1 = scaleImage(playerCard1);
                    Icon scaledIconCard2 = scaleImage(playerCard2);
                    //reassign the icons for each button and disable the third button
                    player1Card1Button.setIcon(scaledIconCard1);
                    player1Card1Button.setVisible(true);
                    player1Card1Button.setEnabled(true);
                    player1Card2Button.setIcon(scaledIconCard2);
                    player1Card2Button.setVisible(true);
                    player1Card2Button.setEnabled(true);
                    player1Card3Button.setVisible(false);
                    player1Card3Button.setEnabled(false);
                    //show the cpu's two back of the cards and set the third to invisible because the cpu only has two cards
                    cpuCard1.setVisible(true);
                    cpuCard2.setVisible(true);
                    cpuCard3.setVisible(false);
                    //deactivate the next round button until next time
                    nextRoundButton.setVisible(false);
                    nextRoundButton.setEnabled(false);
                    //add hints for easy mode
                    if (easyMode) {
                        hints.setText("Try playing the card worth least");
                    }
                //the following logic only applies if CPU won the previous round
                } else if (whoWon == 2) {
                    //send the cards to player pile 2
                    discard1.cardsWon(pile2);
                    discard2.cardsWon(pile2);
                    player1PlayedCard.setVisible(false);
                    player2PlayedCard.setVisible(false);
                    //reassign the players new cards. Only will do this for first two cards because we stop drawing cards
                    playerCard1 = hand1.getHand().get(0);
                    playerCard2 = hand1.getHand().get(1);
                    Icon scaledIconCard1 = scaleImage(playerCard1);
                    Icon scaledIconCard2 = scaleImage(playerCard2);
                    //reset icon images for the first two buttons and deactivate the third card button
                    player1Card1Button.setIcon(scaledIconCard1);
                    player1Card1Button.setVisible(true);
                    player1Card1Button.setEnabled(true);
                    player1Card2Button.setIcon(scaledIconCard2);
                    player1Card2Button.setVisible(true);
                    player1Card2Button.setEnabled(true);
                    player1Card3Button.setVisible(false);
                    player1Card3Button.setEnabled(false);
                    //make only the first CPU back of card image visible because the CPU will have to play a card as well
                    //deactivate the next round button until the next time
                    nextRoundButton.setVisible(false);
                    nextRoundButton.setEnabled(false);
                    //if on easy mode randomly choose a card to play and leave a hint for the user
                    if (easyMode) {
                        randomCardPicker();
                        player2Card = hand2.getHand().get(cardChosen);
                        hints();
                        //have the CPU choose the card worth the least since it has to start and not lose points
                    } else {
                        hardModePicker(null);
                    }
                    //set the image for the card selected to be played
                    setImagesForCPU(cardChosen);
                    //since they won the previous round
                    cpuCard1.setVisible(true);
                    cpuCard2.setVisible(false);
                    cpuCard3.setVisible(false);
                    //make the card thar was just chosen to be visible for the user to see
                    player2PlayedCard.setVisible(true);
                }
            //if this is the last round of the end game
            } else if (deck.getDeck().size() == 0 && hand1.getHand().size() == 1){
                //check who wins to see who gets the last cards of the game
                checkWhoWins(discard1.getDiscard().get(0), discard2.getDiscard().get(0));
                //if player 1 won the previous round the following logic applies
                if (whoWon == 1) {
                    //give the cards to player 1
                    discard1.cardsWon(pile1);
                    discard2.cardsWon(pile1);
                    player1PlayedCard.setVisible(false);
                    player2PlayedCard.setVisible(false);
                    //only reassign the first card in hand because all other cards have been played
                    playerCard1 = hand1.getHand().get(0);
                    Icon scaledIconCard1 = scaleImage(playerCard1);
                    //deactivate all player card buttons except for first card
                    player1Card1Button.setIcon(scaledIconCard1);
                    player1Card1Button.setVisible(true);
                    player1Card1Button.setEnabled(true);
                    player1Card2Button.setVisible(false);
                    player1Card2Button.setEnabled(false);
                    player1Card3Button.setVisible(false);
                    player1Card3Button.setEnabled(false);
                    //set all cpu back of cards invisible except for the first one
                    cpuCard1.setVisible(true);
                    cpuCard2.setVisible(false);
                    cpuCard3.setVisible(false);
                    //deactivate nextRoundButton and set it up for the next time it gets called to check the score
                    nextRoundButton.setVisible(false);
                    nextRoundButton.setEnabled(false);
                    nextRoundButton.setText("Check Score");
                //if the CPU won the previous round
                } else if (whoWon == 2) {
                    //send the cards to player pile 2
                    discard1.cardsWon(pile2);
                    discard2.cardsWon(pile2);
                    player1PlayedCard.setVisible(false);
                    player2PlayedCard.setVisible(false);
                    //reassign first card that the user has since its the last card
                    playerCard1 = hand1.getHand().get(0);
                    Icon scaledIconCard1 = scaleImage(playerCard1);
                    //set the image for the first card button and deactivate the other card buttons
                    player1Card1Button.setIcon(scaledIconCard1);
                    player1Card1Button.setVisible(true);
                    player1Card1Button.setEnabled(true);
                    player1Card2Button.setVisible(false);
                    player1Card2Button.setEnabled(false);
                    player1Card3Button.setVisible(false);
                    player1Card3Button.setEnabled(false);
                    //set the first back of card invisible, so it represents the CPU playing its last card
                    cpuCard1.setVisible(false);
                    //deactivate the next round button and set up the text on the button to change for the next round
                    nextRoundButton.setVisible(false);
                    nextRoundButton.setEnabled(false);
                    nextRoundButton.setText("Check Score");
                    //if on easy mode randomly pick a card and provide a hint for the user
                    if (easyMode) {
                        randomCardPicker();
                        player2Card = hand2.getHand().get(cardChosen);
                        hints();
                        //the cpu will pick the card worth least in its hand
                    } else {
                        hardModePicker(null);
                    }
                    //reset the image for player two played card to the card that was just chosen
                    setImagesForCPU(cardChosen);
                    //make all cards in CPU's hand invisible because it has played its last card
                    cpuCard1.setVisible(false);
                    cpuCard2.setVisible(false);
                    cpuCard3.setVisible(false);
                    //make the card played visible so user can see what card has been played
                    player2PlayedCard.setVisible(true);
                }
            //After all cards have been the following logic will be applied
            } else if (hand1.getHand().size() == 0 && hand2.getHand().size() == 0 && discard1.getDiscard().size() == 1) {
                //need to see where the last cards will go
                checkWhoWins(discard1.getDiscard().get(0), discard2.getDiscard().get(0));
                if (whoWon == 1) {
                    //if the user won the last cards will be sent to him
                    discard1.cardsWon(pile1);
                    discard2.cardsWon(pile1);
                    player1PlayedCard.setVisible(false);
                    player2PlayedCard.setVisible(false);
                    //send user back to main menu to see final score
                    showMainMenuFrame();
                } else if (whoWon == 2) {
                    //if the CPU won send the cards to its pile
                    discard1.cardsWon(pile2);
                    discard2.cardsWon(pile2);
                    player1PlayedCard.setVisible(false);
                    player2PlayedCard.setVisible(false);
                    //send the user back to the main menu to see final score
                    showMainMenuFrame();
                }
            }
            //after proceeding to the next round update all labels at bottom of GUI
            userPointsLabel.setText("User Points: " + pile1.getPoints());
            cpuPointsLabel.setText("CPU Points: " + pile2.getPoints());
            messageLabel.setText("");
            deckSizeLabel.setText("Rounds Left: " + --rounds);
        });
        //setting up the action listener logic for the first card button
        player1Card1Button.addActionListener(e -> {
            //if you won the previous round the following logic applies
            if (whoWon == 1) {
                //get the image ready to be played and placed in the right location on the GUI
                Icon scaledIconPlayerCard1 = scaleImage(playerCard1);
                player1PlayedCard.setBounds(439, scaledHeight / 2 + 97, scaledWidth, scaledHeight);
                contentPane.add(player1PlayedCard);
                player1PlayedCard.setIcon(scaledIconPlayerCard1);
                //send the card to the discard pile for later comparison
                hand1.playFirstCard(discard1);
                //method for selecting card. Depending on what setting was chosen
                if (easyMode) {
                    randomCardPicker();
                    player2Card = hand2.getHand().get(cardChosen);
                    hints.setText("");
                } else {
                    hardModePicker(playerCard1);
                }
                setImagesForCPU(cardChosen);
                //set the first button to invisible/disabled, and disable the other buttons so multiple can't be pressed
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
                //allow for the next round button to be clickable again
                nextRoundButton.setEnabled(true);
                nextRoundButton.setVisible(true);
                //check who wins again to change the message on who won the round
                checkWhoWins(discard1.getDiscard().get(0), discard2.getDiscard().get(0));

                /////// add this to checkwhoWins method
                if (whoWon == 1) {
                    messageLabel.setText("You Won");
                    messageLabel.setVisible(true);
                } else if (whoWon == 2) {
                    messageLabel.setText("CPU Won");
                    messageLabel.setVisible(true);
                }
            //if the CPU won the previous round
            } else if (whoWon == 2) {
                //set the player one played card as the card from player card
                Icon scaledIconPlayerCard1 = scaleImage(playerCard1);
                player1PlayedCard.setBounds(439, scaledHeight / 2 + 97, scaledWidth, scaledHeight);
                contentPane.add(player1PlayedCard);
                player1PlayedCard.setIcon(scaledIconPlayerCard1);
                //send the first card to the discard pile and set the button associated with the card played as deactivated
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
                //enable the next round button
                nextRoundButton.setEnabled(true);
                nextRoundButton.setVisible(true);
                //update the who won message
                checkWhoWins(discard1.getDiscard().get(0), discard2.getDiscard().get(0));
                if (whoWon == 1) {
                    messageLabel.setText("You Won");
                    messageLabel.setVisible(true);
                } else if (whoWon == 2) {
                    messageLabel.setText("CPU Won");
                    messageLabel.setVisible(true);
                }
                hints.setText("");

            }
        });
        //action listener for if the user decides to play the second card
        player1Card2Button.addActionListener(e -> {
            //if the user won the previous round
            if (whoWon == 1) {
                //set the Icon for the player 1 played card as the second card in the hand
                Icon scaledIconPlayerCard2 = scaleImage(playerCard2);
                player1PlayedCard.setBounds(439, scaledHeight / 2 + 97, scaledWidth, scaledHeight);
                contentPane.add(player1PlayedCard);
                player1PlayedCard.setIcon(scaledIconPlayerCard2);
                //add card to discard
                hand1.playSecondCard(discard1);
                //method for selecting card for CPU
                if (easyMode) {
                    randomCardPicker();
                    player2Card = hand2.getHand().get(cardChosen);
                    hints.setText("");
                } else {
                    hardModePicker(playerCard2);
                }
                setImagesForCPU(cardChosen);
                //make the second button invisible and make the rest invisible. Set up the rest the buttons as well.
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
                //enable next round button
                nextRoundButton.setEnabled(true);
                nextRoundButton.setVisible(true);
                //update who won messages
                checkWhoWins(discard1.getDiscard().get(0), discard2.getDiscard().get(0));
                if (whoWon == 1) {
                    messageLabel.setText("You Won");
                    messageLabel.setVisible(true);
                } else if (whoWon == 2) {
                    messageLabel.setText("CPU Won");
                    messageLabel.setVisible(true);
                }
            //if CPU won
            } else if (whoWon == 2) {
                //set up the icon for the second card in the hand
                Icon scaledIconPlayerCard2 = scaleImage(playerCard2);
                player1PlayedCard.setBounds(439, scaledHeight / 2 + 97, scaledWidth, scaledHeight);
                contentPane.add(player1PlayedCard);
                player1PlayedCard.setIcon(scaledIconPlayerCard2);
                hand1.playSecondCard(discard1);
                //set the second button to invisible and disbled and set player 1 played card to visible
                player1Card2Button.setVisible(false);
                player1Card2Button.setEnabled(false);
                player1PlayedCard.setVisible(true);
                //disable all of the other buttons so they can't be pressed
                player1Card1Button.setEnabled(false);
                Icon scaledIconPlayerCard1 = scaleImage(playerCard1);
                player1Card1Button.setDisabledIcon(scaledIconPlayerCard1);
                player1Card3Button.setEnabled(false);
                Icon scaledIconPlayerCard3 = scaleImage(playerCard3);
                player1Card3Button.setDisabledIcon(scaledIconPlayerCard3);
                //make the next round button usable again
                nextRoundButton.setEnabled(true);
                nextRoundButton.setVisible(true);
                //update the who won message
                checkWhoWins(discard1.getDiscard().get(0), discard2.getDiscard().get(0));
                if (whoWon == 1) {
                    messageLabel.setText("You Won");
                    messageLabel.setVisible(true);
                } else if (whoWon == 2) {
                    messageLabel.setText("CPU Won");
                    messageLabel.setVisible(true);
                }
                hints.setText("");

            }
        });
        //set up the action listener for the third card in player 1's hand
        player1Card3Button.addActionListener(e -> {
            //if user won the first round
            if (whoWon == 1) {
                //set up the player 1 played card for the third card in the user's hand
                Icon scaledIconPlayerCard3 = scaleImage(playerCard3);
                player1PlayedCard.setBounds(439, scaledHeight / 2 + 97, scaledWidth, scaledHeight);
                contentPane.add(player1PlayedCard);
                player1PlayedCard.setIcon(scaledIconPlayerCard3);
                //send third card to the discard
                hand1.playThirdCard(discard1);
                //method for selecting card
                if (easyMode) {
                    randomCardPicker();
                    player2Card = hand2.getHand().get(cardChosen);
                    hints.setText("");
                } else {
                    hardModePicker(playerCard3);
                }
                setImagesForCPU(cardChosen);
                //deactivate the third button, allow the player 1 played card to be visible, and disable the first and second
                //button
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
                //make next round button enabled again
                nextRoundButton.setEnabled(true);
                nextRoundButton.setVisible(true);
                //update the message for whoWon
                checkWhoWins(discard1.getDiscard().get(0), discard2.getDiscard().get(0));
                if (whoWon == 1) {
                    messageLabel.setText("You Won");
                    messageLabel.setVisible(true);
                } else if (whoWon == 2) {
                    messageLabel.setText("CPU Won");
                    messageLabel.setVisible(true);
                }
            //for when the CPU won the previous round
            } else if (whoWon == 2) {
                //set up the card played to be the third card since its associated with this action listener
                Icon scaledIconPlayerCard3 = scaleImage(playerCard3);
                player1PlayedCard.setBounds(439, scaledHeight / 2 + 97, scaledWidth, scaledHeight);
                contentPane.add(player1PlayedCard);
                player1PlayedCard.setIcon(scaledIconPlayerCard3);
                //send the third card to the discard
                hand1.playThirdCard(discard1);
                //deactivate the third button
                player1Card3Button.setVisible(false);
                player1Card3Button.setEnabled(false);
                //set the player 1 played card to visit and disable the other buttons
                player1PlayedCard.setVisible(true);
                player1Card1Button.setEnabled(false);
                Icon scaledIconPlayerCard1 = scaleImage(playerCard1);
                player1Card1Button.setDisabledIcon(scaledIconPlayerCard1);
                player1Card2Button.setEnabled(false);
                Icon scaledIconPlayerCard2 = scaleImage(playerCard2);
                player1Card2Button.setDisabledIcon(scaledIconPlayerCard2);
                //enable the nextRoundButton
                nextRoundButton.setEnabled(true);
                nextRoundButton.setVisible(true);
                //update the whoWon message
                checkWhoWins(discard1.getDiscard().get(0), discard2.getDiscard().get(0));
                if (whoWon == 1) {
                    messageLabel.setText("You Won");
                    messageLabel.setVisible(true);
                } else if (whoWon == 2) {
                    messageLabel.setText("CPU Won");
                    messageLabel.setVisible(true);
                }
                hints.setText("");
            }
        });
        //update the message depending on what card is being hovered
        player1Card1Button.addMouseListener(new MouseAdapter() {
            //show what the card is called when mouse hovers over the first button
            public void mouseEntered(MouseEvent e) {
                player1Card1Label.setText(String.valueOf(hand1.getHand().get(0)));
            }
            public void mouseExited(MouseEvent e) {
                player1Card1Label.setText("");
            }
        });
        player1Card2Button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                //if the first button has been played or its invisible it will get the first card in the list
                if (!player1Card1Button.isVisible()) {
                    player1Card2Label.setText(String.valueOf(hand1.getHand().get(0)));
                //if not it will always get the second card in the list
                } else {
                    player1Card2Label.setText(String.valueOf(hand1.getHand().get(1)));
                }
            }
            public void mouseExited(MouseEvent e) {
                player1Card2Label.setText("");
            }
        });

        player1Card3Button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                //if either player card 1 or player card 2 button is invisible look at the second card
                if (!player1Card1Button.isVisible()|| !player1Card2Button.isVisible()) {
                    player1Card3Label.setText(String.valueOf(hand1.getHand().get(1)));
                //change the label to the third card in the hand
                } else {
                    player1Card3Label.setText(String.valueOf(hand1.getHand().get(2)));
                }
            }
            public void mouseExited(MouseEvent e) {
                player1Card3Label.setText("");
            }
        });

        topCardPic.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (topCardPic.isVisible()) {
                    trumpSuitCardLabel.setText(String.valueOf(trumpSuitCard));
                }
            }
            public void mouseExited(MouseEvent e) {
                trumpSuitCardLabel.setText("");
            }
        });

        player1PlayedCard.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (discard2.getDiscard().size() == 1) {
                    player1PlayedCardLabel.setText(String.valueOf(discard1.getDiscard().get(0)));
                }
            }
            public void mouseExited(MouseEvent e) {
                player1PlayedCardLabel.setText("");
            }
        });

        player2PlayedCard.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (discard2.getDiscard().size() == 1) {
                    player2PlayedCardLabel.setText(String.valueOf(discard2.getDiscard().get(0)));
                }
            }
            public void mouseExited(MouseEvent e) {
                player2PlayedCardLabel.setText("");
            }
        });
        // Set the frame to be visible after the window has been called
        gameFrame.setVisible(true);
    }

    /**
     * Closes the application.
     */
    private void exitGame() {
        System.exit(0);
    }

    /**
     * Sends the user back to the main menu frame from the instructions frame.
     */
    private void showMainMenuFrameFromInstructions() {
        if (instructionsFrame.isVisible()) {
            instructionsFrame.setVisible(false);
        } else if (pointsFrame.isVisible()) {
            pointsFrame.setVisible(false);
        } else if (scenarioOne.isVisible()) {
            scenarioOne.setVisible(false);
        } else if (scenarioTwo.isVisible()) {
            scenarioTwo.setVisible(false);
        } else if (scenarioThree.isVisible()) {
            scenarioThree.setVisible(false);
        }
        menuFrame.setVisible(true);
        startButton.setEnabled(true);
        startButton.setText("Play");
        wonOrLostLabel.setText("");
    }

    /**
     * Sends the user back to the instructions frame from the points explanation frame.
     */
    private void showInstructionsFromPointsFrame() {
        pointsFrame.setVisible(false);
        instructionsFrame.setVisible(true);
    }

    private void showInstructionsFromScenarioOne() {
        scenarioOne.setVisible(false);
        instructionsFrame.setVisible(true);
    }

    private void showInstructionsFromScenarioTwo() {
        scenarioTwo.setVisible(false);
        instructionsFrame.setVisible(true);
    }

    private void showInstructionsFromScenarioThree() {
        scenarioThree.setVisible(false);
        instructionsFrame.setVisible(true);
    }

    private void showPointsFrameFromScenarioOne() {
        scenarioOne.setVisible(false);
        showCardPoints();
    }

    private void showPointsFrameFromScenarioTwo() {
        scenarioTwo.setVisible(false);
        showCardPoints();
    }

    private void showPointsFrameFromScenarioThree() {
        scenarioThree.setVisible(false);
        showCardPoints();
    }

    private void showScenarioOneFromCardPoints() {
        pointsFrame.setVisible(false);
        showScenarioOne();
    }

    private void showScenarioTwoFromCardPoints() {
        pointsFrame.setVisible(false);
        showScenarioTwo();
    }

    private void showScenarioThreeFromCardPoints() {
        pointsFrame.setVisible(false);
        showScenarioThree();
    }

    private void showScenarioTwoFromScenarioOne() {
        scenarioOne.setVisible(false);
        showScenarioTwo();
    }

    private void showScenarioThreeFromScenarioOne() {
        scenarioOne.setVisible(false);
        showScenarioThree();
    }

    private void showScenarioOneFromScenarioTwo() {
        scenarioTwo.setVisible(false);
        showScenarioOne();
    }

    private void showScenarioThreeFromScenarioTwo() {
        scenarioTwo.setVisible(false);
        showScenarioThree();
    }

    private void showScenarioOneFromScenarioThree() {
        scenarioThree.setVisible(false);
        showScenarioOne();
    }

    private void showScenarioTwoFromScenarioThree() {
        scenarioThree.setVisible(false);
        showScenarioTwo();
    }

    private void showInstructionsWindow() {
        //make sure menu frame is closed before we begin to making the instruction window
        menuFrame.setVisible(false);
        instructionsFrame = new JFrame("Card Point Values");
        instructionsFrame.setSize(gameWidth, gameHeight);
        instructionsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        instructionsFrame.setResizable(false);

        //same method as used above to paint background this time with the same background
        JPanel instructionsPanel = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image img = new ImageIcon("src/images/instructions.png").getImage();
                Dimension size = getSize();
                g.drawImage(img,0,0,size.width,size.height,null);
            }
        };
        instructionsFrame.setContentPane(instructionsPanel);
        Container contentPane = instructionsFrame.getContentPane();
        contentPane.setLayout(null);

        //creating the back to main menu button so user can go back
        JButton mainMenu = new JButton("Main Menu");
        mainMenu.setBounds(gameWidth/5 - 90, gameHeight - 100, 110,50);
        mainMenu.addActionListener(e -> showMainMenuFrameFromInstructions());
        mainMenu.setFocusPainted(false);
        contentPane.add(mainMenu);

        JButton cardPoints = new JButton("Card Points");
        cardPoints.setBounds(gameWidth/5 - 90 + 210, gameHeight - 100, 110, 50);
        cardPoints.addActionListener(e -> showCardPoints());
        cardPoints.setFocusPainted(false);
        contentPane.add(cardPoints);

        JButton scenarioOne = new JButton("Scenario One");
        scenarioOne.setBounds(gameWidth/5 - 90 + 420, gameHeight - 100, 110, 50);
        scenarioOne.addActionListener(e -> showScenarioOne());
        scenarioOne.setFocusPainted(false);
        contentPane.add(scenarioOne);

        JButton scenarioTwo = new JButton("Scenario Two");
        scenarioTwo.setBounds(gameWidth/5 - 90 + 630, gameHeight - 100, 110, 50);
        scenarioTwo.addActionListener(e -> showScenarioTwo());
        scenarioTwo.setFocusPainted(false);
        contentPane.add(scenarioTwo);

        JButton scenarioThree = new JButton("Scenario Three");
        scenarioThree.setBounds(gameWidth/5 - 90 + 840, gameHeight - 100, 110, 50);
        scenarioThree.addActionListener(e -> showScenarioThree());
        scenarioThree.setFocusPainted(false);
        contentPane.add(scenarioThree);

        instructionsFrame.setVisible(true);
    }

    /**
     * Sends the user to the points explanation frame.
     */
    private void showCardPoints() {
        instructionsFrame.setVisible(false);

        pointsFrame = new JFrame("Instructions");
        pointsFrame.setSize(gameWidth, gameHeight);
        pointsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pointsFrame.setResizable(false);

        JPanel instructionsPanel = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image img = new ImageIcon("src/images/card_points.png").getImage();
                Dimension size = getSize();
                g.drawImage(img,0,0,size.width,size.height,null);
            }
        };

        pointsFrame.setContentPane(instructionsPanel);
        Container contentPane = pointsFrame.getContentPane();
        contentPane.setLayout(null);

        //creating the back to main menu button so user can go back
        JButton mainMenu = new JButton("Main Menu");
        mainMenu.setBounds(gameWidth/5 - 90, gameHeight - 100, 110,50);
        mainMenu.addActionListener(e -> showMainMenuFrameFromInstructions());
        mainMenu.setFocusPainted(false);
        contentPane.add(mainMenu);

        JButton howToPlay = new JButton("How To Play");
        howToPlay.setBounds(gameWidth/5 - 90 + 210, gameHeight - 100, 110, 50);
        howToPlay.addActionListener(e -> showInstructionsFromPointsFrame());
        howToPlay.setFocusPainted(false);
        contentPane.add(howToPlay);

        JButton scenarioOne = new JButton("Scenario One");
        scenarioOne.setBounds(gameWidth/5 - 90 + 420, gameHeight - 100, 110, 50);
        scenarioOne.addActionListener(e -> showScenarioOneFromCardPoints());
        scenarioOne.setFocusPainted(false);
        contentPane.add(scenarioOne);

        JButton scenarioTwo = new JButton("Scenario Two");
        scenarioTwo.setBounds(gameWidth/5 - 90 + 630, gameHeight - 100, 110, 50);
        scenarioTwo.addActionListener(e -> showScenarioTwoFromCardPoints());
        scenarioTwo.setFocusPainted(false);
        contentPane.add(scenarioTwo);

        JButton scenarioThree = new JButton("Scenario Three");
        scenarioThree.setBounds(gameWidth/5 - 90 + 840, gameHeight - 100, 110, 50);
        scenarioThree.addActionListener(e -> showScenarioThreeFromCardPoints());
        scenarioThree.setFocusPainted(false);
        contentPane.add(scenarioThree);

        //This needs to be the last action done to the frame that way all containers are visible at the same time.
        pointsFrame.setVisible(true);
    }

    private void showScenarioOne() {
        instructionsFrame.setVisible(false);

        scenarioOne = new JFrame("Scenario One");
        scenarioOne.setSize(gameWidth, gameHeight);
        scenarioOne.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        scenarioOne.setResizable(false);

        JPanel instructionsPanel = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image img = new ImageIcon("src/images/scenario1.png").getImage();
                Dimension size = getSize();
                g.drawImage(img,0,0,size.width,size.height,null);
            }
        };

        scenarioOne.setContentPane(instructionsPanel);
        Container contentPane = scenarioOne.getContentPane();
        contentPane.setLayout(null);

        //creating the back to main menu button so user can go back
        JButton mainMenu = new JButton("Main Menu");
        mainMenu.setBounds(gameWidth/5 - 90, gameHeight - 100, 110,50);
        mainMenu.addActionListener(e -> showMainMenuFrameFromInstructions());
        mainMenu.setFocusPainted(false);
        contentPane.add(mainMenu);

        JButton howToPlay = new JButton("How To Play");
        howToPlay.setBounds(gameWidth/5 - 90 + 210, gameHeight - 100, 110, 50);
        howToPlay.addActionListener(e -> showInstructionsFromScenarioOne());
        howToPlay.setFocusPainted(false);
        contentPane.add(howToPlay);

        JButton cardPoints = new JButton("Card Points");
        cardPoints.setBounds(gameWidth/5 - 90 + 420, gameHeight - 100, 110, 50);
        cardPoints.addActionListener(e -> showPointsFrameFromScenarioOne());
        cardPoints.setFocusPainted(false);
        contentPane.add(cardPoints);

        JButton scenarioTwo = new JButton("Scenario Two");
        scenarioTwo.setBounds(gameWidth/5 - 90 + 630, gameHeight - 100, 110, 50);
        scenarioTwo.addActionListener(e -> showScenarioTwoFromScenarioOne());
        scenarioTwo.setFocusPainted(false);
        contentPane.add(scenarioTwo);

        JButton scenarioThree = new JButton("Scenario Three");
        scenarioThree.setBounds(gameWidth/5 - 90 + 840, gameHeight - 100, 110, 50);
        scenarioThree.addActionListener(e -> showScenarioThreeFromScenarioOne());
        scenarioThree.setFocusPainted(false);
        contentPane.add(scenarioThree);

        //This needs to be the last action done to the frame that way all containers are visible at the same time.
        scenarioOne.setVisible(true);
    }

    private void showScenarioTwo() {
        instructionsFrame.setVisible(false);

        scenarioTwo = new JFrame("Scenario Two");
        scenarioTwo.setSize(gameWidth, gameHeight);
        scenarioTwo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        scenarioTwo.setResizable(false);

        JPanel instructionsPanel = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image img = new ImageIcon("src/images/scenario2.png").getImage();
                Dimension size = getSize();
                g.drawImage(img,0,0,size.width,size.height,null);
            }
        };

        scenarioTwo.setContentPane(instructionsPanel);
        Container contentPane = scenarioTwo.getContentPane();
        contentPane.setLayout(null);

        //creating the back to main menu button so user can go back
        JButton mainMenu = new JButton("Main Menu");
        mainMenu.setBounds(gameWidth/5 - 90, gameHeight - 100, 110,50);
        mainMenu.addActionListener(e -> showMainMenuFrameFromInstructions());
        mainMenu.setFocusPainted(false);
        contentPane.add(mainMenu);

        JButton howToPlay = new JButton("How To Play");
        howToPlay.setBounds(gameWidth/5 - 90 + 210, gameHeight - 100, 110, 50);
        howToPlay.addActionListener(e -> showInstructionsFromScenarioTwo());
        howToPlay.setFocusPainted(false);
        contentPane.add(howToPlay);

        JButton cardPoints = new JButton("Card Points");
        cardPoints.setBounds(gameWidth/5 - 90 + 420, gameHeight - 100, 110, 50);
        cardPoints.addActionListener(e -> showPointsFrameFromScenarioTwo());
        cardPoints.setFocusPainted(false);
        contentPane.add(cardPoints);

        JButton scenarioOne = new JButton("Scenario One");
        scenarioOne.setBounds(gameWidth/5 - 90 + 630, gameHeight - 100, 110, 50);
        scenarioOne.addActionListener(e -> showScenarioOneFromScenarioTwo());
        scenarioOne.setFocusPainted(false);
        contentPane.add(scenarioOne);

        JButton scenarioThree = new JButton("Scenario Three");
        scenarioThree.setBounds(gameWidth/5 - 90 + 840, gameHeight - 100, 110, 50);
        scenarioThree.addActionListener(e -> showScenarioThreeFromScenarioTwo());
        scenarioThree.setFocusPainted(false);
        contentPane.add(scenarioThree);

        //This needs to be the last action done to the frame that way all containers are visible at the same time.
        scenarioTwo.setVisible(true);
    }

    private void showScenarioThree() {
        instructionsFrame.setVisible(false);

        scenarioThree = new JFrame("Scenario Three");
        scenarioThree.setSize(gameWidth, gameHeight);
        scenarioThree.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        scenarioThree.setResizable(false);

        JPanel instructionsPanel = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image img = new ImageIcon("src/images/scenario3.png").getImage();
                Dimension size = getSize();
                g.drawImage(img,0,0,size.width,size.height,null);
            }
        };

        scenarioThree.setContentPane(instructionsPanel);
        Container contentPane = scenarioThree.getContentPane();
        contentPane.setLayout(null);

        //creating the back to main menu button so user can go back
        JButton mainMenu = new JButton("Main Menu");
        mainMenu.setBounds(gameWidth/5 - 90, gameHeight - 100, 110,50);
        mainMenu.addActionListener(e -> showMainMenuFrameFromInstructions());
        mainMenu.setFocusPainted(false);
        contentPane.add(mainMenu);

        JButton howToPlay = new JButton("How To Play");
        howToPlay.setBounds(gameWidth/5 - 90 + 210, gameHeight - 100, 110, 50);
        howToPlay.addActionListener(e -> showInstructionsFromScenarioThree());
        howToPlay.setFocusPainted(false);
        contentPane.add(howToPlay);

        JButton cardPoints = new JButton("Card Points");
        cardPoints.setBounds(gameWidth/5 - 90 + 420, gameHeight - 100, 110, 50);
        cardPoints.addActionListener(e -> showPointsFrameFromScenarioThree());
        cardPoints.setFocusPainted(false);
        contentPane.add(cardPoints);

        JButton scenarioOne = new JButton("Scenario One");
        scenarioOne.setBounds(gameWidth/5 - 90 + 630, gameHeight - 100, 110, 50);
        scenarioOne.addActionListener(e -> showScenarioOneFromScenarioThree());
        scenarioOne.setFocusPainted(false);
        contentPane.add(scenarioOne);

        JButton scenarioTwo = new JButton("Scenario Two");
        scenarioTwo.setBounds(gameWidth/5 - 90 + 840, gameHeight - 100, 110, 50);
        scenarioTwo.addActionListener(e -> showScenarioTwoFromScenarioThree());
        scenarioTwo.setFocusPainted(false);
        contentPane.add(scenarioTwo);

        //This needs to be the last action done to the frame that way all containers are visible at the same time.
        scenarioThree.setVisible(true);
    }

    /**
     * Sends the user to the main menu from the game frame.
     */
    private void showMainMenuFrame() {
        gameFrame.setVisible(false);
        menuFrame.setVisible(true);
        startButton.setEnabled(true);
        instructionsButton.setEnabled(true);
        startButton.setText("Play Again");
        if (pile1.getPoints() > 60) {
            wonOrLostLabel.setText("YOU WON WITH " + pile1.getPoints() + " POINTS");
        } else if (pile2.getPoints() > 60) {
            wonOrLostLabel.setText("YOU LOST WITH " + pile1.getPoints() + " POINTS");
        } else if (pile1.getPoints() == 60 && pile2.getPoints() == 60){
            wonOrLostLabel.setText("YOU TIED WITH " + pile1.getPoints() + " POINTS");
        }
        whoWon = 1;
        rounds = 20;
    }

    /**
     * Sends the user to the Main Menu from the Game
     */
    private void showMainMenuFrameFromGameFrame() {
        gameFrame.setVisible(false);
        menuFrame.setVisible(true);
        startButton.setEnabled(true);
        instructionsButton.setEnabled(true);
        startButton.setText("Play Again");
        wonOrLostLabel.setText("");
        whoWon = 1;
        rounds = 20;
    }

    /**
     * Scales image to a constant size.
     *
     * @param card that is going to be resized
     * @return resized image
     */
    private ImageIcon scaleImage(Card card) {
        ImageIcon originalImage = new ImageIcon(card.getImage().getImage());
        // Scale the image to a smaller size
        scaledHeight = (int) ((double) scaledWidth / originalImage.getIconWidth() * originalImage.getIconHeight());
        Image scaledImage = originalImage.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        return scaledIcon;
    }

    /**
     * Method for checking who wins a round. It compares two cards and changes the whoWon variable so the game understands
     * which player won. If whoWon changes to 1 the user won. If whoWon changes to 2 then the CPU won.
     *
     * @param card1 the card the user played
     * @param card2 the card the CPU played
     */
    private void checkWhoWins(Card card1, Card card2) {
        //
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

    /**
     * Method used for randomly choosing a number 0-2. These three integers represent the possible choices a random
     * opponent can choose from. Once it has chosen a variable it reassigns the global cardChosen variable.
     */
    private void randomCardPicker() {
        //if the hand size is one, only let the CPU choose its first card
        if (hand2.getHand().size() == 1) {
            cardChosen = 0;
        }
        //if the hand size is 2, let the CPU choose anywhere 0 or 1
        if (hand2.getHand().size() == 2) {
            Random random = new Random();
            cardChosen = random.nextInt(2);

        }
        //if the hand is full, let the CPU choose either 0, 1, 2.
        if (hand2.getHand().size() == 3) {
            Random random = new Random();
            cardChosen = random.nextInt(3);
        }
    }

    /**
     * Method used for logically picking cards to play as the CPU. This is all done after looking to see what card has
     * been played by the user. If the user is going second (which implies the CPU is going first) the CPU will choose
     * to play the card worth least in its hand.
     *
     * @param player1Card card that the user played
     */
    private void hardModePicker(Card player1Card) {
        //if CPU is going first then choose the card worth least in your hand
        if (player1Card == null) {
            cardChosen = lowestCardWorthIndex(hand2.getHand());
        } else {
            //If player one hs played a king or a card worth more (assuming they didn't play a trump card), and CPU has
            //a trump card in their hand, play the trump card.
            if (player1Card.getWorth() > 3 && playerHasTrumpSuit(hand2.getHand()) && !player1Card.getSuit().equals(trumpSuitCard.getSuit())) {
                cardChosen = trumpSuitCardIndex(hand2.getHand());
            //If player one played a trump card, the CPU will play a card worth the least from its hand.
            } else if (player1Card.getSuit().equals(trumpSuitCard.getSuit())) {
                cardChosen = lowestCardWorthIndex(hand2.getHand());
            //If the suit of the card that player one played equals the suit of the card worth the most in the CPU's hand
            //and the cpu will win by playing that card, play that card.
            } else if (player1Card.getSuit().equals(highestWorthCard(hand2.getHand()).getSuit()) && player1Card.getStrength() < highestWorthCard(hand2.getHand()).getStrength()) {
                cardChosen = highestCardWorthIndex(hand2.getHand());
            //else play the lowest worth card in your hand
            } else if (hand2.getHand().size() > 1 && player1Card.getSuit().equals(secondHighestWorthCard(hand2.getHand()).getSuit()) && player1Card.getStrength() < secondHighestWorthCard(hand2.getHand()).getStrength()) {
                cardChosen = secondHighestCardWorthIndex(hand2.getHand());
            }
            else {
                cardChosen = lowestCardWorthIndex(hand2.getHand());
            }
        }
    }

    /**
     * This method is only called when playing on easy mode. It modifies a JLabel on easy mode to give suggestions to
     * the user who may be a little unsure on how to play.
     */
    private void hints() {
        //if player two has not played a card suggest to play the card worth least in the user's hand
        if (player2Card == null) {
            hints.setText("Try playing the card worth least");
        } else {
            //If the CPU plays a king, three, or ace, it's not trump suits, and the user has a trump card in its hand
            //suggest to play the trump card
            if (player2Card.getWorth() > 3 && playerHasTrumpSuit(hand1.getHand()) && !player2Card.getSuit().equals(trumpSuitCard.getSuit())) {
                hints.setText("Try playing your trump suit card");
            //If player two played a trump suit card suggest to play the card worth least in your hand
            } else if (player2Card.getSuit().equals(trumpSuitCard.getSuit())) {
                hints.setText("Try playing the card worth least");
            //If the CPU plays a card that is not of trump suit and your highest worth card is greater then the card
            //the cpu played, suggest to play that card
            } else if (player2Card.getSuit().equals(highestWorthCard(hand1.getHand()).getSuit()) && player2Card.getStrength() < highestWorthCard(hand1.getHand()).getStrength()) {
                hints.setText("Try playing the " + hand1.getHand().get(highestCardWorthIndex(hand1.getHand())));
            //in any other scenario, suggest to play the card worth least
            } else if (hand1.getHand().size() > 1 && player2Card.getSuit().equals(secondHighestWorthCard(hand1.getHand()).getSuit()) && player2Card.getStrength() < secondHighestWorthCard(hand1.getHand()).getStrength()) {
                hints.setText("Try playing the " + hand1.getHand().get(secondHighestCardWorthIndex(hand1.getHand())));
            } else {
                hints.setText("Try playing the card worth least");
            }
        }
    }

    /**
     * This method sets the image for the card that the CPU decides to play.
     *
     * @param cardChosen integer from 0-2 representing the possible cards in the CPU's hand.
     */
    private void setImagesForCPU(int cardChosen) {
        if (cardChosen == 0) {
            //0 represents the first card in the CPU's hand
            player2Card = hand2.getHand().get(0);
            //scale the image to make sure it's the right size and then show the card on the screen that was played
            Icon scaledIconPlayer2Card = scaleImage(player2Card);
            player2PlayedCard.setBounds(455 + scaledWidth, scaledHeight / 2 + 97, scaledWidth, scaledHeight);
            contentPane.add(player2PlayedCard);
            player2PlayedCard.setIcon(scaledIconPlayer2Card);
            //make sure to send the card to the discard and set the played card to visible
            hand2.playFirstCard(discard2);
            player2PlayedCard.setVisible(true);
            cpuCard1.setVisible(false);
        } else if (cardChosen == 1) {
            //1 represents the second card in the CPU's hand
            player2Card = hand2.getHand().get(1);
            //scale the image to make sure it's the right size and then show the card on the screen that was played
            Icon scaledIconPlayer2Card = scaleImage(player2Card);
            player2PlayedCard.setBounds(445 + scaledWidth, scaledHeight / 2 + 97, scaledWidth, scaledHeight);
            contentPane.add(player2PlayedCard);
            player2PlayedCard.setIcon(scaledIconPlayer2Card);
            //make sure to send the card to the discard and set the played card to visible
            hand2.playSecondCard(discard2);
            player2PlayedCard.setVisible(true);
            cpuCard2.setVisible(false);
        } else {
            //2 represents the third card in the CPU's hand
            player2Card = hand2.getHand().get(2);
            //scale the image to make sure it's the right size and then show the card on the screen that was played
            Icon scaledIconPlayer2Card = scaleImage(player2Card);
            player2PlayedCard.setBounds(445 + scaledWidth, scaledHeight / 2 + 97, scaledWidth, scaledHeight);
            contentPane.add(player2PlayedCard);
            player2PlayedCard.setIcon(scaledIconPlayer2Card);
            //make sure to send the crd to  the discard and set the played card to visible
            hand2.playThirdCard(discard2);
            player2PlayedCard.setVisible(true);
            cpuCard3.setVisible(false);
        }
    }

    /**
     * This method is used for determining which card in the hand is worth the most.
     *
     * @param hand list of cards
     * @return card worth the most in list of cards
     */
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

    /**
     * This method is used for determining where the card with the highest value is located in the list.
     *
     * @param hand list of cards
     * @return index of the card worth the most
     */
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

    /**
     * Returns the second most valuable card in a list of cards.
     *
     * @param hand list of cards
     * @return card worth the second most
     */
    private Card secondHighestWorthCard(List<Card> hand) {
        int highestCardWorthIndex = highestCardWorthIndex(hand);
        Card highestCardWorth = hand.get(highestCardWorthIndex);
        Card secondHighestCard = null;
        for (Card card : hand) {
            if (card != highestCardWorth && (secondHighestCard == null || (card.getWorth() > secondHighestCard.getWorth() && card.getWorth() < highestCardWorth.getWorth()))) {
                secondHighestCard = card;
            }
        }
        return secondHighestCard;
    }

    /**
     * This method is used for determining where the second most valuable card is in the list.
     *
     * @param hand
     * @return index of the card worth the second most
     */
    private int secondHighestCardWorthIndex(List<Card> hand) {
        int highestCardWorth = -1;
        int secondHighestCardWorth = -1;
        int highestCardIndex = 0;
        int secondHighestCardIndex = 0;
        for (int i = 0; i < hand.size(); i++) {
            int worth = hand.get(i).getWorth();
            if (worth > highestCardWorth) {
                secondHighestCardWorth = highestCardWorth;
                highestCardWorth = worth;
                secondHighestCardIndex = highestCardIndex;
                highestCardIndex = i;
            } else if (worth > secondHighestCardWorth && worth != highestCardWorth) {
                secondHighestCardWorth = worth;
                secondHighestCardIndex = i;
            }
        }
        return secondHighestCardIndex;
    }

    /**
     * This method is used for determining where the card with the least value is located in the list.
     *
     * @param hand list of cards
     * @return index of the card worth the least
     */
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

    /**
     * This method is used for determining if a list of cards has a trump suit card in it.
     *
     * @param hand list of cards
     * @return boolean true if list of cards has a trump suit card
     */
    private boolean playerHasTrumpSuit(List<Card> hand) {
        boolean hasTrump = false;
        for (Card card : hand) {
            if (card.getSuit().equals(trumpSuitCard.getSuit())) {
                hasTrump = true;
            }
        }
        return hasTrump;
    }

    /**
     * This method is used for determining where the trump suit card is in a list of cards.
     *
     * @param hand list of cards
     * @return index of the trump suit card
     */
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

    /**
     * This method will reset all conditions to prepare the board for a new game. All objects are re-created, whoWon is set
     * to the default value of one, and the labels are reset.
     */
    private void resetGame() {
        pile1.startOver();
        pile2.startOver();
        deck = new Deck();
        dealButton.setEnabled(true);
        dealButton.setVisible(true);
        newGameButton.setVisible(false);
        newGameButton.setEnabled(false);
        whoWon = 1;
        nextRoundButton.setText("Next Round");
        userPointsLabel.setText("User Points: 0");
        cpuPointsLabel.setText("CPU Points: 0");
        trumpSuitLabel.setText("Trump Suit: ");
    }
}
