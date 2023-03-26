package ui;

import model.*;

import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ui.User.*;

public class GameGUI extends JFrame implements ActionListener {
    private final int width = 1000;
    private final int height = 600;

    private final int widthSmall = 600;
    private final int heightSmall = 400;

    private JPanel playerPanel;
    private JPanel dealerPanel;
    private JFrame gameFrame;

    //TODO: implement buttons - quit and playAgain button #######################################################
    private JButton hitButton;
    private JButton stayButton;
    private JButton saveButton;
    private JButton playAgainButton;
    private JButton quitButton;

    private DeckOfCards deck;
    private Game game = new Game();
    private Player player = new Player();
    private Dealer dealer = new Dealer();

    private int numberOfTimesHit = 3;



    public GameGUI(DeckOfCards cards, Player p) {
        deck = cards;
        player = p;
        game.setDeckOfCards(deck);

        ImageIcon image = new ImageIcon("src/BlackJack.png");
        ImageIcon newImg = getCardImage();

        //label = new JLabel();
        gameFrame = new JFrame();

        player.setPlayerHand(game.firstTwoPlayerCards());
        dealer.setDealerHand(game.firstTwoDealerCards());


        //PANELS
        playerPanel = getPlayerPanel(newImg, player.getPlayerHand());
        dealerPanel = getDealerPanel(newImg, dealer.getDealerHand());

        //TODO: BUTTONS ###############################################################################################
        hitButton = getHitButton();
        hitButton.addActionListener(this);
        stayButton = getStayButton();
        stayButton.addActionListener(this);
        saveButton = getSaveButton();
        saveButton.addActionListener(this);

        //setting up the frame ****
        gameFrame.setTitle("Game");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setResizable(false);
        gameFrame.setSize(width, height);
        gameFrame.setLayout(null);
        //color ****
        gameFrame.setIconImage(image.getImage());
        gameFrame.getContentPane().setBackground(new Color(136, 8, 8));

        //frame.add(label);
        updateFrame();
    }

    private void updateFrame() {
        gameFrame.add(playerPanel);
        gameFrame.add(dealerPanel);
        gameFrame.add(hitButton);
        gameFrame.add(stayButton);
        gameFrame.add(saveButton);
        gameFrame.setVisible(true);
    }

    //********************************************* CARD IMAGE *********************************************************
    private ImageIcon getCardImage() {
        ImageIcon card = new ImageIcon("src/card.png");
        //resizing image ****
        Image img = card.getImage();
        Image imgScale = img.getScaledInstance(78, 110, Image.SCALE_SMOOTH);
        ImageIcon newImg = new ImageIcon(imgScale);
        return newImg;
    }

    //*********************************************** PLAYER PANEL *****************************************************
    private JPanel getPlayerPanel(ImageIcon newImg, List<Card> pcards) {
        JLabel playerName = getTotalValue("Player", 30, 75, 0, 100);
        JLabel total = getTotalValue("Total: " + player.getValue(), 20, 250, 78, 100);
        JLabel balance = getTotalValue("Balance: $" + player.getBalance(), 18, 580, 125, 200);
        JLabel bet = getTotalValue("Bet: $" + player.getBet(), 18, 580, 148, 200);

        JPanel panel = new JPanel();;
        panel.setBackground(null);
        panel.setBounds(0,350, width, 200);
        panel.setLayout(null);

        addCardsToUserPanel(newImg, pcards, panel);

        System.out.println(pcards.size());  //testing ******************************

        panel.add(playerName);
        panel.add(total);
        panel.add(balance);
        panel.add(bet);
        return panel;
    }


    //************************************************ DEALER PANEL ****************************************************
    private JPanel getDealerPanel(ImageIcon newImg, List<Card> dcards) {
        JLabel dealerName = new JLabel();
        dealerName.setText("Dealer");
        dealerName.setForeground(new Color(224, 225, 228));
        dealerName.setBackground(Color.white);
        dealerName.setFont(new Font("Mononess", Font.PLAIN, 30));
        dealerName.setBounds(75, 70, 100, 30);

        JLabel total = getTotalValue("Total: " + dealer.getValue(), 20, 250, 2, 100);

        JPanel panel = new JPanel();
        panel.setBackground(null);
        panel.setBounds(0,50, width, 200);
        panel.setLayout(null);

        addCardsToUserPanel(newImg, dcards, panel);

        System.out.println(dcards.size());  //testing ******************************

        panel.add(dealerName);
        panel.add(total);
        return panel;
    }

    //************************************************ SIMILAR FUNCTIONS ***********************************************
    private void addCardsToUserPanel(ImageIcon newImg, List<Card> pcards, JPanel panel) {
        for (int i = 0; i < pcards.size(); i++) {
            JLabel label = new JLabel();
            label.setIcon(newImg);
            label.setBounds(width / 2 - 130 + (i * 90), 0, 78, 110);
            label.setText(pcards.get(i).getCardName());
            label.setForeground(new Color(224, 225, 228));
            label.setHorizontalTextPosition(JLabel.CENTER);
            label.setVerticalTextPosition(JLabel.CENTER);
            panel.add(label);
        }
    }

    private JLabel getTotalValue(String player, int size, int x, int y, int width) {
        JLabel total = new JLabel();
        total.setText(player);
        total.setForeground(new Color(224, 225, 228));
        total.setFont(new Font("Mononess", Font.PLAIN, size));
        total.setBounds(x, y, width, 30);
        return total;
    }

    //****************************************** BUTTONS ***************************************************************
    private JButton getHitButton() {
        JButton button = new JButton("Hit");
        button.setBounds(width / 2 - 135, height - 50, 60, 25);
        return button;
    }

    private JButton getStayButton() {
        JButton button = new JButton("Stay");
        button.setBounds(width / 2 - 75, height - 50, 60, 25);
        return button;
    }

    private JButton getSaveButton() {
        JButton button = new JButton("Save");
        button.setBounds(width / 2 - 15, height - 50, 60, 25);
        return button;
    }

    private JButton getPlayAgainButton() {
        JButton button = new JButton("Play Again");
        button.setBounds(widthSmall / 2 - 50, heightSmall / 2 + 10, 100, 50);
        return button;
    }

    private JButton getQuitButton() {
        JButton button = new JButton("Quit");
        button.setBounds(widthSmall / 2 - 37, heightSmall / 2 - 50, 75, 50);
        return button;
    }


    //************************************************ ACTION PERFORMED ************************************************
    @Override
    public void actionPerformed(ActionEvent e) {

        //TODO 1: HANDLE IF FIRST TWO CARDS DEALT IS A BJ #############################################################
        if (e.getSource() == hitButton) {
            numberOfTimesHit++;
            player.playerHits(numberOfTimesHit, deck);

            gameFrame.remove(playerPanel);
            playerPanel = getPlayerPanel(getCardImage(), player.getPlayerHand());
            gameFrame.add(playerPanel);
            SwingUtilities.updateComponentTreeUI(gameFrame);

            if (game.playerGreaterThan21(player)) {
                gameFrame.dispose();
                Color bkgColor = new Color(40,40,43);
                Color wordColor = new Color(220, 20, 60);
                endFrame("You Lost", "Lose", bkgColor, wordColor);
            }

            System.out.println("Player hits!");
        } else if (e.getSource() == stayButton) {
            while (dealer.getValue() <= 17) {
                numberOfTimesHit++;
                dealer.dealerHits(numberOfTimesHit, deck);

                gameFrame.remove(dealerPanel);
                dealerPanel = getDealerPanel(getCardImage(), dealer.getDealerHand());
                gameFrame.add(dealerPanel);
                SwingUtilities.updateComponentTreeUI(gameFrame);
            }

            int n = game.whoWins(player, dealer);
            if (n == DRAW) {
                player.playerMoney(n, player.getBet());
                gameFrame.dispose();
                Color bkgColor = new Color(255, 215, 0);
                Color wordColor = new Color(40,40,43);
                endFrame("Draw!", "Draw", bkgColor, wordColor);

            } else if (n == PBJ) {
                player.playerMoney(n, player.getBet());
                gameFrame.dispose();
                Color bkgColor = new Color(50,205,50);
                Color wordColor = new Color(40,40,43);
                endFrame("You win with Blackjack!", "Player Blackjack", bkgColor, wordColor);

            } else if (n == DBJ) {
                player.playerMoney(n, player.getBet());
                gameFrame.dispose();
                Color bkgColor = new Color(40,40,43);
                Color wordColor = new Color(220, 20, 60);
                endFrame("Dealer wins with Blackjack", "Dealer Blackjack", bkgColor, wordColor);

            } else if (n == LOSE) {
                player.playerMoney(n, player.getBet());
                gameFrame.dispose();
                Color bkgColor = new Color(40,40,43);
                Color wordColor = new Color(220, 20, 60);
                endFrame("You Lost", "Lose", bkgColor, wordColor);

            } else if (n == WIN) {
                player.playerMoney(n, player.getBet());
                gameFrame.dispose();
                Color bkgColor = new Color(50,205,50);
                Color wordColor = new Color(40,40,43);
                endFrame("You Win!", "Win", bkgColor, wordColor);

            }

            System.out.println("Player stays!");
        } else if (e.getSource() == saveButton) {
            //TODO: SAVE BUTTON  #####################################################################################

            System.out.println("Saving...");
        }
    }


    //********************************************* ENDING FRAME *******************************************************
    private JFrame endFrame(String title, String frameTitle, Color back, Color front) {
        JFrame frameEnd = new JFrame();
        JLabel frameStatement = new JLabel();
        JLabel moneyAfter = new JLabel();

        //lost title
        frameStatement.setText(title);
        frameStatement.setBounds(0, 40, widthSmall, heightSmall);
        frameStatement.setForeground(front);
        frameStatement.setFont(new Font("Mononess", Font.PLAIN, 30));
        frameStatement.setVerticalAlignment(JLabel.TOP);
        frameStatement.setHorizontalAlignment(JLabel.CENTER);

        //money after lost
        double balance = player.playerMoney(LOSE, player.getBet());
        moneyAfter.setText("Remaining balance: $" + balance);
        moneyAfter.setBounds(0, 80, widthSmall, heightSmall);
        moneyAfter.setForeground(front);
        moneyAfter.setFont(new Font("Mononess", Font.PLAIN, 20));
        moneyAfter.setVerticalAlignment(JLabel.TOP);
        moneyAfter.setHorizontalAlignment(JLabel.CENTER);

        //frame setup
        frameEnd.setTitle(frameTitle);
        frameEnd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameEnd.setResizable(false);
        frameEnd.setSize(widthSmall, heightSmall);
        frameEnd.setLayout(null);
        //color ****
        frameEnd.setIconImage(getIconImage());
        frameEnd.getContentPane().setBackground(back);

        //buttons
        playAgainButton = getPlayAgainButton();
        quitButton = getQuitButton();

        frameEnd.add(frameStatement);
        frameEnd.add(moneyAfter);
        frameEnd.add(playAgainButton);
        frameEnd.add(quitButton);

        frameEnd.setVisible(true);

        return frameEnd;
    }


}
