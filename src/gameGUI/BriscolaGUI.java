package gameGUI;

import gameStructure.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

// A comment
public class BriscolaGUI extends JFrame {

    public static void main(String[] args) {
        new BriscolaGUI();
    }

    private List<Card> drawList = new ArrayList<>();
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
    private final int scaledWidth = 149;
    private int scaledHeight;
    private Card playerCard1;
    private Card playerCard2;
    private Card playerCard3;
    private Card player2Card1;
    private Card player2Card2;
    private Card player2Card3;
    private Card playedCard;
    private ImageIcon scaledIcon;
    private Card backOfCard = new Card(Card.Suit.Coins, Card.FaceName.Back);
    private JLabel cpuCard1;
    private JLabel cpuCard2;
    private JLabel cpuCard3;
    private JLabel player2PlayedCard;
    private JLabel messageLabel;
    private JLabel topCardPic;
    private JLabel backgroundImage;
    private JLabel userPointsLabel;
    private JLabel cpuPointsLabel;
    private JLabel trumpSuitLabel;
    private JLabel deckSizeLabel;
    private JLabel wonOrLostLabel;
    private JLabel infoLabel;
    private int whoWon;
    private int rand;
    private int cardChosen;
    private Card trumpSuitCard;
    private Boolean easyMode = true;
    private Graphics cardGraphic;

    public BriscolaGUI() {
        super();
        //creating the main menu frame. This includes a way for the user to choose a difficulty and look at instructions
        // on how to play
        menuFrame = new JFrame("Main Menu");
        menuFrame.setSize(gameWidth,gameHeight);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setVisible(true);

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
        startButton.setBounds(gameWidth/2 - 50, gameHeight - 100, 110,50);
        startButton.addActionListener(e -> showGameWindow());
        contentPane.add(startButton);

        //display point label after a game has been played
        wonOrLostLabel = new JLabel("");
        wonOrLostLabel.setBounds(gameWidth/2 - 125, gameHeight - 700, 300, 100);
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
        JButton instructionsButton = new JButton("How To Play");
        instructionsButton.setBounds(gameWidth - 1125, gameHeight - 100, 110, 50);
        contentPane.add(instructionsButton);

        //This calls the method which opens the other window
        instructionsButton.addActionListener(e -> showInstructionsWindow());
    }

    private void showInstructionsWindow() {
        menuFrame.setVisible(false);
        instructionsFrame = new JFrame("Instructions");
        instructionsFrame.setSize(gameWidth, gameHeight);
        instructionsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel instructionsPanel = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image img = new ImageIcon("src/images/BriCenMenu.png").getImage();
                Dimension size = getSize();
                g.drawImage(img,0,0,size.width,size.height,null);
            }
        };

        instructionsFrame.setContentPane(instructionsPanel);
        instructionsFrame.setVisible(true);
        Container contentPane = instructionsFrame.getContentPane();

        JLabel howToPlay = new JLabel();
        howToPlay.setText("How To Play");
        howToPlay.setBounds(100,100,100,100);
        contentPane.add(howToPlay);

        //creating the back to main menu button
        JButton startButton = new JButton("Main Menu");
        startButton.setBounds(gameWidth/2 - 50, 500, 110,50);
        startButton.addActionListener(e -> showMainMenuFrameFromInstrucions());
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
    }

    private void showGameWindow() {
        startButton.setEnabled(false);
        //objects that need to be created for the game to start
        deck = new Deck();
        hand1 = new Hand();
        hand2 = new Hand();
        discard1 = new Discard();
        discard2 = new Discard();
        pile1 = new Pile();
        pile2 = new Pile();
        //lets the game know that the user starts
        whoStartsGame();

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
                for (Sprite s : drawList) {
                    s.draw(g);
                }
            }
        };
        gameFrame.setContentPane(gamePanel);
        gameFrame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Card playedCard = null;
                for (Card card : drawList) {
                    if (card.contains(e.getPoint())) {
                        // Is this the player's card?
                        playedCard = card;
                        break;
                    }
                }
                if (playedCard == null) {
                    return;
                }

                if (playedCard == playerCard1) {
                    playedCard.setCoords(439, playerCard1.getHeight() / 2 + 97);
                    hand1.playFirstCard(discard1);

                    //method for CPU card selection
                    if (easyMode) {
                        randomCardPicker();
                    } else {
                        hardModePicker(playerCard1);
                    }
                    setImagesForCPU(cardChosen);

                } else if (playedCard == playerCard2) {
                    playedCard.setCoords(439, playerCard2.getHeight() / 2 + 97);
                    hand1.playSecondCard(discard1);

                    //method for selecting card for now just pick first
                    if (easyMode) {
                        randomCardPicker();
                    } else {
                        hardModePicker(playerCard2);
                    }
                    setImagesForCPU(cardChosen);

                } else if (playedCard == playerCard3) {
                    playerCard3.setCoords(439, playerCard3.getHeight() / 2 + 97);
                    hand1.playThirdCard(discard1);

                    if (easyMode) {
                        randomCardPicker();
                    } else {
                        hardModePicker(playerCard3);
                    }

                    setImagesForCPU(cardChosen);
                }
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

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

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

        //add the back of card image to the screen
        backOfCard.setCoords( 50, gameHeight / 2 - backOfCard.getHeight() / 2  - 40);
        drawList.add(backOfCard);

        //add message on who won after every round
        JLabel messageLabel = new JLabel("Who Won: ");
        messageLabel.setBounds(gameWidth - 400, gameHeight - 100, 150, 50);
        contentPane.add(messageLabel);

        //add message for what card is being hovered
        infoLabel = new JLabel("Card: ");
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
            trumpSuitCard = deck.dealCards(hand1, hand2, whoWon);
            deckSizeLabel.setText("Cards Left: " + deck.getDeck().size());
            trumpSuitLabel.setText("Trump Suit: " + trumpSuitCard.getSuit());
            playerCard1 = hand1.getHand().get(0);
            playerCard2 = hand1.getHand().get(1);
            playerCard3 = hand1.getHand().get(2);
            player2Card1 = hand2.getHand().get(0);
            player2Card2 = hand2.getHand().get(1);
            player2Card3 = hand2.getHand().get(2);

            drawList.add(playerCard1);
            drawList.add(playerCard2);
            drawList.add(playerCard3);
            drawList.add(player2Card1);
            drawList.add(player2Card2);
            drawList.add(player2Card3);
            drawList.add(trumpSuitCard);

            playerCard1.setCoords(840, 400);
            playerCard2.setCoords(845 + playerCard2.getWidth(), 400);
            playerCard3.setCoords(850 + playerCard3.getWidth() + playerCard3.getWidth(), 400);
            player2Card1.setCoords(840, 30);
            player2Card2.setCoords(845 + player2Card2.getWidth(), 30);
            player2Card3.setCoords(850 + player2Card3.getWidth() + player2Card3.getWidth(), 30);
            trumpSuitCard.setCoords(105 + trumpSuitCard.getWidth() - 50, gameHeight / 2 - trumpSuitCard.getHeight() / 2 - 40);

            dealButton.setEnabled(false);
            dealButton.setVisible(false);

            gameFrame.repaint();
        });

        newGameButton.addActionListener(e -> {
            resetGame();
            messageLabel.setText("Who Won: ");
            newGameButton.setVisible(false);
            newGameButton.setEnabled(false);
        });

        nextRoundButton.addActionListener(e -> {
            messageLabel.setVisible(false);
            checkWhoWins(discard1.getDiscard().get(0), discard2.getDiscard().get(0));

            if (deck.getDeck().size() > 1) {
                if (whoWon == 1) {
                    discard1.cardsWon(pile1);
                    discard2.cardsWon(pile1);

                    Card card1 = deck.dealTopCard(hand1);
                    Card card2 = deck.dealTopCard(hand2);

                    playerCard1 = hand1.getHand().get(0);
                    playerCard2 = hand1.getHand().get(1);
                    playerCard3 = hand1.getHand().get(2);

                    playerCard1.setCoords(840, 400);
                    playerCard2.setCoords(845 + playerCard2.getWidth(), 400);
                    playerCard3.setCoords(850 + playerCard3.getWidth() + playerCard3.getWidth(), 400);
                    player2Card1.setCoords(840, 30);
                    player2Card2.setCoords(845 + player2Card2.getWidth(), 30);
                    player2Card3.setCoords(850 + player2Card3.getWidth() + player2Card3.getWidth(), 30);

                    nextRoundButton.setVisible(false);
                    nextRoundButton.setEnabled(false);

                    gameFrame.repaint();

                } else if (whoWon == 2) {
                    discard1.cardsWon(pile2);
                    discard2.cardsWon(pile2);

                    deck.dealTopCard(hand2);
                    deck.dealTopCard(hand1);

                    playerCard1 = hand1.getHand().get(0);
                    playerCard2 = hand1.getHand().get(1);
                    playerCard3 = hand1.getHand().get(2);

                    playerCard1.setCoords(840, 400);
                    playerCard2.setCoords(845 + playerCard2.getWidth(), 400);
                    playerCard3.setCoords(850 + playerCard3.getWidth() + playerCard3.getWidth(), 400);
                    player2Card1.setCoords(840, 30);
                    player2Card2.setCoords(845 + player2Card2.getWidth(), 30);
                    player2Card3.setCoords(850 + player2Card3.getWidth() + player2Card3.getWidth(), 30);

                    nextRoundButton.setVisible(false);
                    nextRoundButton.setEnabled(false);

                    gameFrame.repaint();
                }

            } else if (deck.getDeck().size() == 1) {
                if (whoWon == 1) {
                    discard1.cardsWon(pile1);
                    discard2.cardsWon(pile1);

                    deck.dealTopCard(hand1);
                    hand2.dealTrumpSuitCard(trumpSuitCard);
                    playerCard1 = hand1.getHand().get(0);
                    playerCard2 = hand1.getHand().get(1);
                    playerCard3 = hand1.getHand().get(2);

                    playerCard1.setCoords(840, 400);
                    playerCard2.setCoords(845 + playerCard2.getWidth(), 400);
                    playerCard3.setCoords(850 + playerCard3.getWidth() + playerCard3.getWidth(), 400);
                    player2Card1.setCoords(840, 30);
                    player2Card2.setCoords(845 + player2Card2.getWidth(), 30);
                    player2Card3.setCoords(850 + player2Card3.getWidth() + player2Card3.getWidth(), 30);

                    nextRoundButton.setVisible(false);
                    nextRoundButton.setEnabled(false);

                    gameFrame.repaint();

                } else if (whoWon == 2) {
                    discard1.cardsWon(pile2);
                    discard2.cardsWon(pile2);

                    deck.dealTopCard(hand2);
                    hand1.dealTrumpSuitCard(trumpSuitCard);

                    playerCard1 = hand1.getHand().get(0);
                    playerCard2 = hand1.getHand().get(1);
                    playerCard3 = hand1.getHand().get(2);

                    playerCard1.setCoords(840, 400);
                    playerCard2.setCoords(845 + playerCard2.getWidth(), 400);
                    playerCard3.setCoords(850 + playerCard3.getWidth() + playerCard3.getWidth(), 400);
                    player2Card1.setCoords(840, 30);
                    player2Card2.setCoords(845 + player2Card2.getWidth(), 30);
                    player2Card3.setCoords(850 + player2Card3.getWidth() + player2Card3.getWidth(), 30);

                    nextRoundButton.setVisible(false);
                    nextRoundButton.setEnabled(false);

                    gameFrame.repaint();
                }

            } else if (deck.getDeck().size() == 0 && hand1.getHand().size() == 2){
                if (whoWon == 1) {
                    discard1.cardsWon(pile1);
                    discard2.cardsWon(pile1);

                    playerCard1 = hand1.getHand().get(0);
                    playerCard2 = hand1.getHand().get(1);

                    playerCard1.setCoords(840, 400);
                    playerCard2.setCoords(845 + playerCard2.getWidth(), 400);
                    player2Card1.setCoords(840, 30);
                    player2Card2.setCoords(845 + player2Card2.getWidth(), 30);

                    nextRoundButton.setVisible(false);
                    nextRoundButton.setEnabled(false);

                    gameFrame.repaint();

                } else if (whoWon == 2) {
                    discard1.cardsWon(pile2);
                    discard2.cardsWon(pile2);

                    playerCard1 = hand1.getHand().get(0);
                    playerCard2 = hand1.getHand().get(1);

                    playerCard1.setCoords(840, 400);
                    playerCard2.setCoords(845 + playerCard2.getWidth(), 400);
                    player2Card1.setCoords(840, 30);
                    player2Card2.setCoords(845 + player2Card2.getWidth(), 30);

                    nextRoundButton.setVisible(false);
                    nextRoundButton.setEnabled(false);

                    gameFrame.repaint();
                }
            } else if (deck.getDeck().size() == 0 && hand1.getHand().size() == 1){
                if (whoWon == 1) {
                    discard1.cardsWon(pile1);
                    discard2.cardsWon(pile1);

                    playerCard1 = hand1.getHand().get(0);

                    playerCard1.setCoords(840, 400);
                    player2Card1.setCoords(840, 30);

                    nextRoundButton.setVisible(false);
                    nextRoundButton.setEnabled(false);
                    nextRoundButton.setText("Check Score");

                    gameFrame.repaint();

                } else if (whoWon == 2) {

                    discard1.cardsWon(pile2);
                    discard2.cardsWon(pile2);

                    playerCard1 = hand1.getHand().get(0);

                    playerCard1.setCoords(840, 400);
                    player2Card1.setCoords(840, 30);

                    nextRoundButton.setVisible(false);
                    nextRoundButton.setEnabled(false);

                    gameFrame.repaint();

                    nextRoundButton.setText("Check Score");
                }
            } else if (hand1.getHand().size() == 0 && hand2.getHand().size() == 0 && discard1.getDiscard().size() == 1) {
                if (whoWon == 1) {
                    discard1.cardsWon(pile1);
                    discard2.cardsWon(pile1);

                    messageLabel.setVisible(true);
                    nextRoundButton.setVisible(false);
                    nextRoundButton.setVisible(false);
                    newGameButton.setVisible(true);
                    newGameButton.setEnabled(true);
                    showMainMenuFrame();
                } else if (whoWon == 2) {
                    discard1.cardsWon(pile2);
                    discard2.cardsWon(pile2);

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
        startButton.setText("Play Again");
    }

    private void showMainMenuFrame() {
        gameFrame.setVisible(false);
        menuFrame.setVisible(true);
        startButton.setEnabled(true);
        startButton.setText("Play Again");
        wonOrLostLabel.setFont(new Font("Serif", Font.PLAIN, 22));
        if (pile1.getPoints() > 60) {
            wonOrLostLabel.setText("You won with " + pile1.getPoints() + " points");
        } else if (pile2.getPoints() > 60) {
            wonOrLostLabel.setText("You lost with " + pile1.getPoints() + " points");
        } else {
            wonOrLostLabel.setText("You tied with " + pile1.getPoints() + " points");
        }
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

    private void setImagesForCPU(int cardChosen) {
        if (cardChosen == 0) {
            Card player2Card = hand2.getHand().get(0);
            player2Card.setCoords(455 + player2Card.getWidth(), player2Card.getHeight() / 2 + 97);
            hand2.playFirstCard(discard2);

        } else if (cardChosen == 1) {
            Card player2Card = hand2.getHand().get(1);
            player2Card.setCoords(445 + player2Card.getWidth(), player2Card.getHeight() / 2 + 97);
            hand2.playSecondCard(discard2);

        } else {
            Card player2Card = hand2.getHand().get(2);
            player2Card.setCoords(445 + player2Card.getWidth(), player2Card.getHeight() / 2 + 97);
            hand2.playThirdCard(discard2);
        }
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

