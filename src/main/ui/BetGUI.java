package ui;

import model.*;
import model.Event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

// gui for the user to bet money
public class BetGUI extends JFrame implements ActionListener {
    private final int widthSetup = 600;
    private final int heightSetup = 400;

    JFrame frame;
    JLabel currentMoney;
    JLabel betMessage;
    JTextField amountBetted;
    JButton beginButton;

    Player player;
    DeckOfCards cards;

    // MODIFIES: this
    // EFFECTS: sets up the page for the user to bet
    public BetGUI(DeckOfCards deck, Player p) {
        cards = deck;
        ImageIcon image = new ImageIcon("src/BlackJack.png");
        frame = new JFrame();
        currentMoney = new JLabel();
        betMessage = new JLabel();
        amountBetted = new JTextField(10);
        beginButton = new JButton();
        player = p;

        //currentMoney label
        setupLabelMessage(currentMoney, "Current Balance: $" + player.getBalance(), 15, 15,
                250, 25);

        //betMessage label
        setupLabelMessage(betMessage, "How much would you like to bet?", 15, 15,
                350, 75);
        //amountbetted user input
        amountBetted.setBounds(widthSetup / 2 - 112, heightSetup / 3 + 70, 165, 25);

        //button
        beginButton = getBeginButton();
        beginButton.addActionListener(this);

        setupFrame(image);
        addEverythingToFrame();
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: adds everything to the frame
    private void addEverythingToFrame() {
        frame.add(currentMoney);
        frame.add(betMessage);
        frame.add(amountBetted);
        frame.add(beginButton);
    }

    // MODIFIES: this
    // EFFECTS: sets up the frame
    private void setupFrame(ImageIcon image) {
        frame.setTitle("Bet");

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                for (Event f : EventLog.getInstance()) {
                    System.out.println(f.toString());
                }
                System.exit(0);
            }
        });

        frame.setResizable(false);
        frame.setSize(widthSetup, heightSetup);
        frame.setLayout(null);
        //color ****
        frame.setIconImage(image.getImage());
        frame.getContentPane().setBackground(new Color(196, 196, 199));
    }

    // REQUIRES: size >= 0, width >= 0, height >= 0
    // EFFECTS: creates a label to display a message
    private void setupLabelMessage(JLabel currentMoney, String player, int size, int size1, int width, int height) {
        currentMoney.setText(player);
        currentMoney.setFont(new Font("Mononess", Font.PLAIN, size));
        currentMoney.setForeground(new Color(40, 40, 43));
        currentMoney.setBounds(widthSetup / 2 - 110, heightSetup / 3 + size1, width, height);
    }

    // EFFECTS: creates a begin button
    private JButton getBeginButton() {
        JButton button = new JButton("Begin");
        button.setBounds(widthSetup - 100, heightSetup - 60, 100, 25);
        return button;
    }

    // MODIFIES: player, this
    // EFFECTS: handles what happens when each button is pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == beginButton) {
            double bet = Double.parseDouble(amountBetted.getText());

            if (bet > player.getBalance()) {
                amountBetted.setText("");
                frame.add(tryAgain());
                SwingUtilities.updateComponentTreeUI(frame);
            } else {
                Dealer dealer = new Dealer();
                Game game = new Game();
                game.setDeckOfCards(cards);
                player.setBet(bet);
                player.betMade(bet);
                player.setPlayerHand(game.firstTwoPlayerCards());
                dealer.setDealerHand(game.firstTwoDealerCards());

                frame.dispose();
                try {
                    GameGUI gameGUI = new GameGUI(cards, player, dealer, game, 3);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    // EFFECTS: message to display when bet more than money you have
    private JLabel tryAgain() {
        JLabel label = new JLabel();
        setupLabelMessage(label, "You don't have that amount of money :p", 8, 50, 350, 100);
        label.setBackground(Color.white);
        return label;
    }
}
