package ui;

import model.*;
import model.Event;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ui.User.*;

// gui for the actual game
public class GameGUI extends JFrame implements ActionListener {
    static final String JSON_MONEY = "./data/Money.json";
    private final int width = 1000;
    private final int height = 610;

    private final int widthSmall = 600;
    private final int heightSmall = 410;

    private JsonWriter jsonWriter;

    private JPanel playerPanel;
    private JPanel dealerPanel;
    private JFrame gameFrame;
    private JFrame drawFrame = new JFrame();
    private JFrame playerBJFrame = new JFrame();
    private JFrame dealerBJFrame = new JFrame();
    private JFrame loseFrame = new JFrame();
    private JFrame winFrame = new JFrame();
    private JFrame deckTooSmallFrame = new JFrame();

    private JButton hitButton;
    private JButton stayButton;
    private JButton saveButton;
    private JButton bigSaveButton;
    private JButton playAgainButton;
    private JButton quitButton;

    private DeckOfCards deck;
    private Game game = new Game();
    private Player player = new Player();
    private Dealer dealer = new Dealer();

    private int numberOfTimesHit = 3;
    private final int noMoney = 1;

    // MODIFIES: this, game
    // EFFECTS: sets up the game gui
    public GameGUI(DeckOfCards cards, Player p, Dealer d, Game g, int n) throws FileNotFoundException {
        deck = cards;
        player = p;
        dealer = d;
        game = g;
        numberOfTimesHit = n;
        game.setDeckOfCards(deck);
        game.setPlayer(player);
        game.setDealer(dealer);

        ImageIcon image = new ImageIcon("src/BlackJack.png");
        ImageIcon newImg = getCardImage();

        //label = new JLabel();
        gameFrame = new JFrame();

        //PANELS
        playerPanel = getPlayerPanel(newImg, player.getPlayerHand());
        dealerPanel = getDealerPanel(newImg, dealer.getDealerHand());

        hitButton = getHitButton();
        hitButton.addActionListener(this);
        stayButton = getStayButton();
        stayButton.addActionListener(this);
        saveButton = getSaveButton();
        saveButton.addActionListener(this);

        //setting up the frame ****
        setupEndFrame(gameFrame, "Game", width, height, image.getImage(), new Color(136, 8, 8));

        //frame.add(label);
        updateFrame();

        handleBlackJack(game.whoWins(player, dealer));

        jsonWriter = new JsonWriter(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: adds everything to the frame
    private void updateFrame() {
        gameFrame.add(playerPanel);
        gameFrame.add(dealerPanel);
        gameFrame.add(hitButton);
        gameFrame.add(stayButton);
        gameFrame.add(saveButton);
        gameFrame.setVisible(true);
    }

    //********************************************* CARD IMAGE *********************************************************
    // EFFECTS: creates an image of the card
    private ImageIcon getCardImage() {
        ImageIcon card = new ImageIcon("src/card.png");
        //resizing image ****
        Image img = card.getImage();
        Image imgScale = img.getScaledInstance(78, 110, Image.SCALE_SMOOTH);
        ImageIcon newImg = new ImageIcon(imgScale);
        return newImg;
    }

    //*********************************************** PLAYER PANEL *****************************************************
    // EFFECTS: creates a panel for the player
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

        panel.add(playerName);
        panel.add(total);
        panel.add(balance);
        panel.add(bet);
        return panel;
    }

    //************************************************ DEALER PANEL ****************************************************
    // EFFECTS: creates a panel for the dealer
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

        panel.add(dealerName);
        panel.add(total);
        return panel;
    }

    //************************************************ SIMILAR FUNCTIONS ***********************************************
    // EFFECTS: creates a label for each card that is dealt out
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

    // EFFECTS: creates a label to calculate total of the hand given
    private JLabel getTotalValue(String player, int size, int x, int y, int width) {
        JLabel total = new JLabel();
        total.setText(player);
        total.setForeground(new Color(224, 225, 228));
        total.setFont(new Font("Mononess", Font.PLAIN, size));
        total.setBounds(x, y, width, 30);
        return total;
    }

    //****************************************** BUTTONS ***************************************************************
    // EFFECTS: creates a hit button
    private JButton getHitButton() {
        JButton button = new JButton("Hit");
        button.setBounds(width / 2 - 135, height - 60, 60, 25);
        return button;
    }

    // EFFECTS: creates a stay button
    private JButton getStayButton() {
        JButton button = new JButton("Stay");
        button.setBounds(width / 2 - 75, height - 60, 60, 25);
        return button;
    }

    // EFFECTS: creates a save button during the game
    private JButton getSaveButton() {
        JButton button = new JButton("Save");
        button.setBounds(width / 2 - 15, height - 60, 60, 25);
        return button;
    }

    // EFFECTS: creates a save button in the end frame
    private JButton getBigSaveButton() {
        JButton button = new JButton("Save");
        button.setBounds(widthSmall / 2 - 37, heightSmall / 2, 75, 50);
        return button;
    }

    // EFFECTS: creates a play again button
    private JButton getPlayAgainButton() {
        JButton button = new JButton("Play Again");
        button.setBounds(widthSmall / 2 - 50, heightSmall / 2 + 110, 100, 50);
        return button;
    }

    // EFFECTS: creates a quit button
    private JButton getQuitButton() {
        JButton button = new JButton("Quit");
        button.setBounds(widthSmall / 2 - 37, heightSmall / 2 + 55, 75, 50);
        return button;
    }

    //************************************************ ACTION PERFORMED ************************************************
    // MODIFIES: this, numberOfTimesHit, player
    // EFFECTS: handles what happens when each button is pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == hitButton) {
            numberOfTimesHit++;
            handlesWhenPlayerHits();
        } else if (e.getSource() == stayButton) {
            handleDealersTurn();
            handleWhoWinsIfEnoughCardsInDeck();
        } else if (e.getSource() == saveButton || e.getSource() == bigSaveButton) {
            disposeAllFrames();
            saveToJSon();
        } else if (e.getSource() == quitButton) {
            disposeAllFrames();
            /**
             * PRINT OUT EVENT LOG *************************************************************************************
             */
            for (Event f : EventLog.getInstance()) {
                System.out.println(f.getDescription());
            }

        } else if (e.getSource() == playAgainButton) {
            disposeAllFrames();
            SetupGUI setupGUI = new SetupGUI(player);
        }
    }

    // MODIFIES: this
    // EFFECTS: handles when player hits
    private void handlesWhenPlayerHits() {
        if (numberOfTimesHit > game.getDeck().getSize() - 1) {
            gameFrame.dispose();
            deckTooSmallFrame = notEnoughCardsFrame();
        } else {
            updatePlayerHandWhenHit();
            handleIfPlayerGoOver21();
        }
    }

    // EFFECTS: handles who wins
    private void handleWhoWinsIfEnoughCardsInDeck() {
        if (numberOfTimesHit < game.getDeck().getSize()) {
            int n = game.whoWins(player, dealer);
            handleWinLoseDraw(n);
        }
    }

    // MODIFIES: this
    // EFFECTS: saves game to json file
    private void saveToJSon() {
        try {
            jsonWriter.open();
            jsonWriter.write(game);
            jsonWriter.close();
            System.out.println("Saved deck to " + JSON_STORE);
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this, player
    // EFFECTS: updates the players hand when user hits
    private void updatePlayerHandWhenHit() {
        player.playerHits(numberOfTimesHit, deck);
        gameFrame.remove(playerPanel);
        playerPanel = getPlayerPanel(getCardImage(), player.getPlayerHand());
        gameFrame.add(playerPanel);
        SwingUtilities.updateComponentTreeUI(gameFrame);
    }

    // MODIFIES: this
    // EFFECTS: dispose frame if player gets over 21 and display end frame lose or no money
    private void handleIfPlayerGoOver21() {
        if (game.playerGreaterThan21(player)) {
            gameFrame.dispose();
            Color bkgColor = new Color(40, 40, 43);
            Color wordColor = new Color(220, 20, 60);
            if (player.getBalance() <= 0) {
                loseFrame = endFrame("No More Money :(", "No Money", bkgColor, wordColor, noMoney);
            } else {
                loseFrame = endFrame("You Lost", "Lose", bkgColor, wordColor, 0);
            }
        }
    }

    // MODIFIES: this, dealer
    // EFFECTS: handles when the dealers total is still less than 17
    private void handleDealersTurn() {
        while (dealer.getValue() <= 17) {
            numberOfTimesHit++;
            if (numberOfTimesHit > game.getDeck().getSize() - 1) {
                gameFrame.dispose();
                deckTooSmallFrame = notEnoughCardsFrame();
                break;
            } else {
                dealer.dealerHits(numberOfTimesHit, deck);
                gameFrame.remove(dealerPanel);
                dealerPanel = getDealerPanel(getCardImage(), dealer.getDealerHand());
                gameFrame.add(dealerPanel);
                SwingUtilities.updateComponentTreeUI(gameFrame);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: disposes all frames
    private void disposeAllFrames() {
        drawFrame.dispose();
        playerBJFrame.dispose();
        dealerBJFrame.dispose();
        loseFrame.dispose();
        winFrame.dispose();
        gameFrame.dispose();
        deckTooSmallFrame.dispose();
    }

    //************************************** WIN/LOSE/DRAW FRAME *******************************************************
    // MODIFIES: this, player
    // EFFECTS: handles what happens when the player win/lose/draw/win bj/lose bj
    private void handleWinLoseDraw(int n) {
        if (n == DRAW) {
            player.playerMoney(n, player.getBet());
            gameFrame.dispose();
            Color bkgColor = new Color(255, 215, 0);
            Color wordColor = new Color(40,40,43);
            drawFrame = endFrame("Draw!", "Draw", bkgColor, wordColor, 0);

        } else if (n == LOSE) {
            player.playerMoney(n, player.getBet());
            gameFrame.dispose();
            Color bkgColor = new Color(40, 40, 43);
            Color wordColor = new Color(220, 20, 60);

            if (player.getBalance() <= 0) {
                drawFrame = endFrame("No More Money :(", "No Money", bkgColor, wordColor, noMoney);
            } else {
                loseFrame = endFrame("You Lost", "Lose", bkgColor, wordColor, 0);
            }

        } else if (n == WIN) {
            player.playerMoney(n, player.getBet());
            gameFrame.dispose();
            Color bkgColor = new Color(50,205,50);
            Color wordColor = new Color(40,40,43);
            winFrame = endFrame("You Win!", "Win", bkgColor, wordColor, 0);
        }
    }

    //************************************** NOT ENOUGH CARDS FRAME ****************************************************
    // EFFECTS: frame to display when there are not enough cards
    private JFrame notEnoughCardsFrame() {
        JFrame noCardsFrame = new JFrame();
        JLabel frameStatement = new JLabel();

        //frame title
        setupLabelTitles(frameStatement, "Not Enough Cards to Continue :(", heightSmall / 2 - 60,
                new Color(196, 196, 199), 30);

        //frame setup
        setupEndFrame(noCardsFrame, "Not Enough Cards", widthSmall, heightSmall, getIconImage(),
                new Color(40, 40, 43));

        //buttons
        quitButton = getQuitButton();
        quitButton.addActionListener(this);

        noCardsFrame.add(frameStatement);
        noCardsFrame.add(quitButton);

        noCardsFrame.setVisible(true);

        return noCardsFrame;
    }

    //********************************************* ENDING FRAME *******************************************************
    // EFFECTS: ending frame to display after game is over
    private JFrame endFrame(String title, String frameTitle, Color back, Color front, int n) {
        JFrame frameEnd = new JFrame();
        JLabel frameStatement = new JLabel();
        JLabel moneyAfter = new JLabel();
        JLabel dealerAmount = new JLabel();
        JLabel playerAmount = new JLabel();

        //frame title
        setupLabelTitles(frameStatement, title, 40, front, 30);

        //dealerAmount
        setupLabelTitles(dealerAmount, "Dealer Total: " + dealer.getValue(), 100, front, 20);

        //playerAmount
        setupLabelTitles(playerAmount, "Player Total: " + player.getValue(), 130, front, 20);

        //money after lost
        double balance = player.playerMoney(LOSE, player.getBet());
        setupLabelTitles(moneyAfter, "Remaining balance: $" + balance, 160, front, 15);

        //frame setup
        setupEndFrame(frameEnd, frameTitle, widthSmall, heightSmall, getIconImage(), back);

        //buttons
        playAgainButton = getPlayAgainButton();
        playAgainButton.addActionListener(this);
        quitButton = getQuitButton();
        quitButton.addActionListener(this);
        bigSaveButton = getBigSaveButton();
        bigSaveButton.addActionListener(this);

        addEverythingToEndFrame(n, frameEnd, frameStatement, moneyAfter, dealerAmount, playerAmount);

        frameEnd.setVisible(true);

        return frameEnd;
    }

    // EFFECTS: adds everything to the ending frame
    private void addEverythingToEndFrame(int n, JFrame frameEnd, JLabel frameStatement, JLabel moneyAfter,
                                         JLabel dealerAmount, JLabel playerAmount) {
        frameEnd.add(frameStatement);
        frameEnd.add(dealerAmount);
        frameEnd.add(playerAmount);
        frameEnd.add(moneyAfter);
        if (!(n == noMoney)) {
            frameEnd.add(playAgainButton);
            frameEnd.add(bigSaveButton);
        }
        frameEnd.add(quitButton);
    }

    // EFFECTS: sets up the ending frame
    private void setupEndFrame(JFrame frameEnd, String frameTitle, int widthSmall, int heightSmall,
                               Image iconImage, Color back) {
        frameEnd.setTitle(frameTitle);
        frameEnd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameEnd.setResizable(false);
        frameEnd.setSize(widthSmall, heightSmall);
        frameEnd.setLayout(null);
        //color ****
        frameEnd.setIconImage(iconImage);
        frameEnd.getContentPane().setBackground(back);
    }

    // EFFECTS: template for the frame
    private void setupLabelTitles(JLabel frameStatement, String title, int y, Color front, int size) {
        frameStatement.setText(title);
        frameStatement.setBounds(0, y, widthSmall, heightSmall);
        frameStatement.setForeground(front);
        frameStatement.setFont(new Font("Mononess", Font.PLAIN, size));
        frameStatement.setVerticalAlignment(JLabel.TOP);
        frameStatement.setHorizontalAlignment(JLabel.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: handles what happens when the first two cards dealt is a black jack
    private JFrame handleBlackJack(int n) {
        if (n == PBJ) {
            player.playerMoney(n, player.getBet());
            gameFrame.dispose();
            Color bkgColor = new Color(50,205,50);
            Color wordColor = new Color(40,40,43);
            playerBJFrame = endFrame("You win with Blackjack!", "Player Blackjack", bkgColor, wordColor, 0);
            return playerBJFrame;
        } else if (n == DBJ) {
            player.playerMoney(n, player.getBet());
            gameFrame.dispose();
            Color bkgColor = new Color(40,40,43);
            Color wordColor = new Color(220, 20, 60);
            if (player.getBalance() <= 0) {
                dealerBJFrame = endFrame("No More Money :(", "No Money", bkgColor, wordColor, noMoney);
            } else {
                dealerBJFrame = endFrame("Dealer wins with Blackjack", "Dealer Blackjack", bkgColor, wordColor, 0);
            }
            return dealerBJFrame;
        } else {
            return new JFrame();
        }
    }

}
