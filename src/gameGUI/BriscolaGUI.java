
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
    private JFrame instructionsFrame;
    private Container contentPane;
    private JButton dealButton;
    private JButton player1Card1Button;
    private JButton player1Card2Button;
    private JButton player1Card3Button;
    private JButton nextRoundButton;
    private JButton newGameButton;
    private JButton instructionsButton;
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
    private int rand;
    private int cardChosen;
    private Card trumpSuitCard;
    private Boolean easyMode = true;

    public BriscolaGUI() {
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
                Image img = new ImageIcon("src/images/BriCenMenu.png").getImage();
                Dimension size = getSize();
                g.drawImage(img,0,0,size.width,size.height, null);
            }
        };
        menuFrame.setContentPane(menuPanel);

        Container contentPane = menuFrame.getContentPane();
        contentPane.setLayout(null);

        //creating the start game button
        startButton = new JButton("Start Game");
        startButton.setBounds(gameWidth/2 - 60, gameHeight - 100, 110,50);
        startButton.addActionListener(e -> showGameWindow());
        contentPane.add(startButton);

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
        contentPane.add(instructionsButton);

        //This needs to be the last action done to the frame that way all containers are visible at the same time.
        menuFrame.setVisible(true);

        //This calls the method which opens the other window
        instructionsButton.addActionListener(e -> showInstructionsWindow());
    }


    private void showInstructionsWindow() {
        //make sure menu frame is closed before we begin to making the instruction window
        menuFrame.setVisible(false);
        instructionsFrame = new JFrame("Instructions");
        instructionsFrame.setSize(gameWidth, gameHeight);
        instructionsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //same method as used above to paint background this time with the same background
        JPanel instructionsPanel = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image img = new ImageIcon("src/images/BriCenMenu.png").getImage();
                Dimension size = getSize();
                g.drawImage(img,0,0,size.width,size.height,null);
            }
        };

        instructionsFrame.setContentPane(instructionsPanel);

        Container contentPane = instructionsFrame.getContentPane();
        contentPane.setLayout(null);

        //create the how to play label with specific font and color
        JLabel howToPlay = new JLabel();
        howToPlay.setText("How To Play");
        howToPlay.setFont(new Font("SANS_SERIF", Font.BOLD, 22));
        howToPlay.setForeground(Color.decode("#545454"));
        howToPlay.setBounds(gameWidth / 2 - 75,gameHeight - 800,200,100);
        contentPane.add(howToPlay);

        //creating the back to main menu button
        JButton mainMenu = new JButton("Main Menu");
        mainMenu.setBounds(gameWidth/2 - 55, gameHeight - 100, 110,50);
        mainMenu.addActionListener(e -> showMainMenuFrameFromInstrucions());
        contentPane.add(mainMenu);

        instructionsFrame.setVisible(true);
    }

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
                Image img = new ImageIcon("src/images/backgroundImage.png").getImage();
                Dimension size = getSize();
                g.drawImage(img,0,0,size.width,size.height,null);
            }
        };

        gameFrame.setContentPane(gamePanel);
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

        //add message for hints
        hints = new JLabel("");
        hints.setBounds(gameWidth - 820, gameHeight - 700, 200, 50);
        contentPane.add(hints);

        menuFrame.setVisible(false);

        dealButton.addActionListener(e -> {
            //deal cards will give each player three cards and then we will assign the trump suit card for the game
            trumpSuitCard = deck.dealCards(hand1, hand2);
            deckSizeLabel.setText("Cards Left: " + deck.getDeck().size());
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
            //add hints if on easy mode.
            if (easyMode) {
                hints.setText("Try playing the card worth least");
            }
        });
        //button that is shown at the end of the game will reset the board
        newGameButton.addActionListener(e -> {
            resetGame();
            messageLabel.setText("Who Won: ");
            newGameButton.setVisible(false);
            newGameButton.setEnabled(false);
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
                        player2Card = hand2.getHand().get(cardChosen);
                        hints();
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
                    cpuCard2.setVisible(false);
                    cpuCard3.setVisible(false);

                    nextRoundButton.setVisible(false);
                    nextRoundButton.setEnabled(false);

                    if (easyMode) {
                        randomCardPicker();
                        player2Card = hand2.getHand().get(cardChosen);
                        hints();
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

                    Icon scaledIconCard1 = scaleImage(playerCard1);

                    player1Card1Button.setIcon(scaledIconCard1);
                    player1Card1Button.setVisible(true);
                    player1Card1Button.setEnabled(true);

                    player1Card2Button.setVisible(false);
                    player1Card2Button.setEnabled(false);

                    player1Card3Button.setVisible(false);
                    player1Card3Button.setEnabled(false);

                    cpuCard1.setVisible(false);
                    cpuCard1.setIcon(scaledIcon);

                    nextRoundButton.setVisible(false);
                    nextRoundButton.setEnabled(false);

                    if (easyMode) {
                        randomCardPicker();
                        player2Card = hand2.getHand().get(cardChosen);
                        hints();
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

                    messageLabel.setVisible(true);
                    nextRoundButton.setVisible(false);
                    nextRoundButton.setVisible(false);
                    newGameButton.setVisible(true);
                    newGameButton.setEnabled(true);
                    showMainMenuFrame();
                } else if (whoWon == 2) {
                    discard1.cardsWon(pile2);
                    discard2.cardsWon(pile2);
                    player1PlayedCard.setVisible(false);
                    player2PlayedCard.setVisible(false);

                    messageLabel.setVisible(true);
                    nextRoundButton.setVisible(false);
                    nextRoundButton.setEnabled(false);
                    newGameButton.setVisible(true);
                    newGameButton.setEnabled(true);
                    showMainMenuFrame();
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
                if (easyMode) {
                    randomCardPicker();
                    hints.setText("Try playing the card worth least");
                    player2Card = hand2.getHand().get(cardChosen);
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

                nextRoundButton.setEnabled(true);
                nextRoundButton.setVisible(true);

                checkWhoWins(discard1.getDiscard().get(0), discard2.getDiscard().get(0));

                /////// add this to checkwhoWins method
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
                if (easyMode) {
                    randomCardPicker();
                    player2Card = hand2.getHand().get(cardChosen);
                    hints.setText("Try playing the card worth least");
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
                if (easyMode) {
                    randomCardPicker();
                    player2Card = hand2.getHand().get(cardChosen);
                    hints.setText("Try playing the card worth least");
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
        // Set the frame to be visible after the window has been called
        gameFrame.setVisible(true);
    }
    private void showMainMenuFrameFromInstrucions() {
        instructionsFrame.setVisible(false);
        menuFrame.setVisible(true);
        startButton.setEnabled(true);
        startButton.setText("Play");
        wonOrLostLabel.setText("");
    }

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
        } else {
            wonOrLostLabel.setText("YOU TIED WITH " + pile1.getPoints() + " POINTS");
        }
    }

    //changed
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
    }
    //unit test
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
    //unit test
    private void hardModePicker(Card player1Card) {
        if (player1Card == null) {
            cardChosen = lowestCardWorthIndex(hand2.getHand());
        } else {
            if (player1Card.getWorth() > 3 && playerHasTrumpSuit(hand2.getHand()) && !player1Card.getSuit().equals(trumpSuitCard.getSuit())) {
                cardChosen = trumpSuitCardIndex(hand2.getHand());

            } else if (player1Card.getSuit().equals(trumpSuitCard.getSuit())) {
                cardChosen = lowestCardWorthIndex(hand2.getHand());

            } else if (player1Card.getSuit().equals(highestWorthCard(hand2.getHand()).getSuit()) && player1Card.getStrength() < highestWorthCard(hand2.getHand()).getStrength()) {
                cardChosen = highestCardWorthIndex(hand2.getHand());

            } else {
                cardChosen = lowestCardWorthIndex(hand2.getHand());
            }
        }
    }

    private void hints() {
        System.out.println(player2Card);
        if (player2Card == null) {
            hints.setText("Try playing the card worth least");
        } else {
            if (player2Card.getWorth() > 3 && playerHasTrumpSuit(hand1.getHand()) && !player2Card.getSuit().equals(trumpSuitCard.getSuit())) {
                hints.setText("Try playing your trump suit card");
            } else if (player2Card.getSuit().equals(trumpSuitCard.getSuit())) {
                hints.setText("Try playing the card worth least");
            } else if (player2Card.getSuit().equals(highestWorthCard(hand1.getHand()).getSuit()) && player2Card.getStrength() < highestWorthCard(hand1.getHand()).getStrength()) {
                hints.setText("Try playing the " + hand1.getHand().get(highestCardWorthIndex(hand1.getHand())));
            } else {
                hints.setText("Try playing the card worth least");
            }
        }
    }

    private void setImagesForCPU(int cardChosen) {
        if (cardChosen == 0) {
            player2Card = hand2.getHand().get(0);

            Icon scaledIconPlayer2Card = scaleImage(player2Card);
            player2PlayedCard.setBounds(455 + scaledWidth, scaledHeight / 2 + 97, scaledWidth, scaledHeight);
            contentPane.add(player2PlayedCard);
            player2PlayedCard.setIcon(scaledIconPlayer2Card);

            hand2.playFirstCard(discard2);

            player2PlayedCard.setVisible(true);
            cpuCard1.setVisible(false);

        } else if (cardChosen == 1) {
            player2Card = hand2.getHand().get(1);

            Icon scaledIconPlayer2Card = scaleImage(player2Card);
            player2PlayedCard.setBounds(445 + scaledWidth, scaledHeight / 2 + 97, scaledWidth, scaledHeight);
            contentPane.add(player2PlayedCard);
            player2PlayedCard.setIcon(scaledIconPlayer2Card);

            hand2.playSecondCard(discard2);

            player2PlayedCard.setVisible(true);
            cpuCard2.setVisible(false);

        } else {
            player2Card = hand2.getHand().get(2);

            Icon scaledIconPlayer2Card = scaleImage(player2Card);
            player2PlayedCard.setBounds(445 + scaledWidth, scaledHeight / 2 + 97, scaledWidth, scaledHeight);
            contentPane.add(player2PlayedCard);
            player2PlayedCard.setIcon(scaledIconPlayer2Card);

            hand2.playThirdCard(discard2);

            player2PlayedCard.setVisible(true);
            cpuCard3.setVisible(false);
        }
    }

    //unit test this one
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
    //unit test
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
    //unit test
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
    //unit test
    private boolean playerHasTrumpSuit(List<Card> hand) {
        boolean hasTrump = false;
        for (Card card : hand) {
            if (card.getSuit().equals(trumpSuitCard.getSuit())) {
                hasTrump = true;
            }
        }
        return hasTrump;
    }
    //unit test
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
