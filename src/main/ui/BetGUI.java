package ui;

import model.Dealer;
import model.DeckOfCards;
import model.Game;
import model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

//TODO: HANDLE IF USER ENTERS IN TOO MUCH MONEY ########################################################################
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
        currentMoney.setText("Current Balance: $" + player.getBalance());
        currentMoney.setFont(new Font("Mononess", Font.PLAIN, 15));
        currentMoney.setForeground(new Color(40, 40, 43));
        currentMoney.setBounds(widthSetup / 2 - 110, heightSetup / 3 + 15, 250, 25);

        //betMessage label
        betMessage.setText("How much would you like to bet?");
        betMessage.setFont(new Font("Mononess", Font.PLAIN, 15));
        betMessage.setForeground(new Color(40, 40, 43));
        betMessage.setBounds(widthSetup / 2 - 110, heightSetup / 3 + 15, 350, 75);
        //amountbetted user input
        amountBetted.setBounds(widthSetup / 2 - 112, heightSetup / 3 + 70, 165, 25);

        //button
        beginButton = getBeginButton();
        beginButton.addActionListener(this);

        frame.setTitle("Bet");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(widthSetup, heightSetup);
        frame.setLayout(null);
        //color ****
        frame.setIconImage(image.getImage());
        frame.getContentPane().setBackground(new Color(196, 196, 199));

        frame.add(currentMoney);
        frame.add(betMessage);
        frame.add(amountBetted);
        frame.add(beginButton);

        frame.setVisible(true);
    }

    private JButton getBeginButton() {
        JButton button = new JButton("Begin");
        button.setBounds(widthSetup - 100, heightSetup - 50, 100, 25);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == beginButton) {
            double bet = Integer.parseInt(amountBetted.getText());

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

                System.out.println("Game begun");
                frame.dispose();
                try {
                    GameGUI gameGUI = new GameGUI(cards, player, dealer, game, 3);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    private JLabel tryAgain() {
        JLabel label = new JLabel();
        label.setText("You don't have that amount of money :p");
        label.setFont(new Font("Mononess", Font.PLAIN, 8));
        label.setForeground(new Color(40, 40, 43));
        label.setBounds(widthSetup / 2 - 110, heightSetup / 3 + 50, 350, 100);
        label.setBackground(Color.white);
        return label;
    }
}
