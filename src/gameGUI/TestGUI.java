package gameGUI;

import gameStructure.BriscolaCard;
import gameStructure.Sprite;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TestGUI extends JFrame {
    private final JPanel contentPane;
    private final DrawingPanel drawing;
    private final JTextField textField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    final JFrame frame = new TestGUI();
                    frame.setVisible(true);

                    final Timer myTimer = new Timer();
                    myTimer.scheduleAtFixedRate(new TimerTask() {
                        public void run() {
                            frame.repaint();
                        }
                    }, 10, 10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public TestGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btnClickMe = new JButton("Click Me!");
        btnClickMe.setBounds(318, 11, 94, 52);
        btnClickMe.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
        contentPane.add(btnClickMe);

        textField = new JTextField();
        textField.setBounds(318, 74, 94, 34);
        contentPane.add(textField);
        textField.setColumns(10);

        drawing = new DrawingPanel();
        drawing.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        drawing.setBounds(10, 11, 295, 300);

        // THIS IS NEW!!!
        JFrame003_MouseListener drawingListener = new JFrame003_MouseListener();
        drawing.addMouseListener(drawingListener);
        drawing.addMouseMotionListener(drawingListener);

        contentPane.add(drawing);

        btnClickMe.addActionListener(new JFrame003_ActionListener());
        textField.addActionListener(new JFrame003_ActionListener());

        this.addMouseListener(new JFrame003_MouseListener());
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                // THIS IS GONE!!!
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }
        });
        this.setFocusable(true);
    }

    private class DrawingPanel extends JPanel {
        public List<Sprite> drawList = new ArrayList<>();

        public DrawingPanel() {
            // THIS IS NEW!!!
            BriscolaCard s = new BriscolaCard("King", "Swords");
            BriscolaCard t = new BriscolaCard("Ace", "Cups");
            t.setCoords(50, 1);
            t.setHeight(290);
            t.setWidth(150);
            System.out.println("Card t's Value: " + t.getValue());
            System.out.println("Card s's Value: " + s.getValue());

            drawList.add(s);
            drawList.add(t);
        }

        public void paintComponent(Graphics g) {
            for (Sprite s : drawList) {
                s.draw(g);
            }
        }
    }

    private class JFrame003_ActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(contentPane, textField.getText());
        }
    }

    private class JFrame003_MouseListener implements MouseListener, MouseMotionListener, MouseWheelListener {
        // THESE ARE NEW!!!
        private BriscolaCard dragging;
        private int[] oldCoords;

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
        }

        @Override
        // THIS IS ALL NEW!!!
        public void mouseDragged(MouseEvent e) {
            if (dragging != null) {
                dragging.moveBy(e.getX() - oldCoords[0], e.getY() - oldCoords[1]);
                oldCoords[0] = e.getX();
                oldCoords[1] = e.getY();
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        // THIS IS ALL NEW!!!
        public void mousePressed(MouseEvent e) {
            System.out.printf("Mouse pressed at (%d, %d). Button %d.%n", e.getX(), e.getY(), e.getButton());
            dragging = null;

            oldCoords = new int[2];
            oldCoords[0] = e.getX();
            oldCoords[1] = e.getY();
            boolean foundFirst = false;
            for (Sprite s : drawing.drawList) {
                if (s instanceof BriscolaCard) {
                    final BriscolaCard card = (BriscolaCard) s;
                    if (card.contains(e.getPoint()) && !foundFirst) {
                        foundFirst = true;
                        dragging = card;
                        card.clicked(e.getPoint());
                        card.select();
                    } else {
                        card.deselect();
                    }
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }
    }
}
