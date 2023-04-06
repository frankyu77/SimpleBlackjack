package ui;

import model.Dealer;
import model.Game;
import model.Player;
import org.json.JSONObject;
import persistence.JsonReader;
import persistence.Writable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static ui.User.JSON_STORE;

// handles everything in the main gui
public class MainPageGUI extends JFrame implements ActionListener {
    private final int width = 1000;
    private final int height = 600;
    private JLabel label;
    private JLabel moneyAdded;
    private JFrame frame;

    private JButton instButton;
    private JButton beginButton;
    private JButton loadButton;
    private JButton loadMoney;

    private JsonReader jsonReader;

    private Game game;
    private Player player;
    private Dealer dealer;
    private int numberOfTimesHit;

    // MODIFIES: this
    // EFFECTS: setting up the welcome page for the game and creates the welcome page
    public MainPageGUI() {
        label = new JLabel();
        frame = new JFrame();
    }

    public void begin() {
        ImageIcon image = new ImageIcon("src/BlackJack.png");

        //resizing image ****
        Image img = image.getImage();
        Image imgScale = img.getScaledInstance(75, 75, Image.SCALE_SMOOTH);
        ImageIcon newImg = new ImageIcon(imgScale);

        //adding image to label ****
        setupTitle(newImg);

        //setting up the frame ****
        frame.setTitle("BlackJack");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(width, height);
        frame.setLayout(null);

        //color ****
        frame.setIconImage(image.getImage());
        frame.getContentPane().setBackground(new Color(136, 8, 8));

        //buttons ****
        addButtons();

        addEverythingToFrame();
        frame.setVisible(true);

        jsonReader = new JsonReader(JSON_STORE);
        game = new Game();
        player = new Player();
        dealer = new Dealer();
        numberOfTimesHit = 0;
    }

    // MODIFIES: this
    // EFFECTS: adds everything to the main page frame
    private void addEverythingToFrame() {
        frame.add(instButton);
        frame.add(beginButton);
        frame.add(loadButton);
        frame.add(loadMoney);
        frame.add(label);
    }

    // EFFECTS: sets up the buttons
    private void addButtons() {
        instButton = instructionsButton();
        instButton.addActionListener(this);
        beginButton = beginGameButton();
        beginButton.addActionListener(this);
        loadButton = loadGameButton();
        loadButton.addActionListener(this);
        loadMoney = getLoadMoneyButton();
        loadMoney.addActionListener(this);
    }

    // EFFECTS: sets up the title of the page
    private void setupTitle(ImageIcon newImg) {
        label.setIcon(newImg);
        label.setText("Welcome to Blackjack!");
        label.setBounds(0, 115, width, height);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.TOP);
        label.setForeground(new Color(224, 225, 228));
        label.setFont(new Font("Mononess", Font.PLAIN, 40));
        label.setVerticalAlignment(JLabel.TOP);
        label.setHorizontalAlignment(JLabel.CENTER);
    }

    // EFFECTS: creates instructions button
    public JButton instructionsButton() {
        JButton button = new JButton("Instructions");
        button.setBounds(width / 2 - 86, height / 2 + 150, 175, 50);
        return button;
    }

    // EFFECTS: creates load game button
    public JButton loadGameButton() {
        JButton button = new JButton("Load Game");
        button.setBounds(width / 2 - 74, height / 2 + 90, 150, 50);
        return button;
    }

    // EFFECTS: creates load money button
    public JButton getLoadMoneyButton() {
        JButton button = new JButton("Load Money");
        button.setBounds(width / 2 - 74, height / 2 + 30, 150, 50);
        return button;
    }

    // EFFECTS: creates begin game button
    public JButton beginGameButton() {
        JButton button = new JButton("Begin Game");
        button.setBounds(width / 2 - 74, height / 2 - 30, 150, 50);
        return button;
    }

    // MODIFIES: this
    // EFFECTS: handles what happens when each button is pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == instButton) {
            frame.dispose();
            InstructionsGUI instructionsGUI = new InstructionsGUI();
        } else if (e.getSource() == beginButton) {
            frame.dispose();
            SetupGUI setupGUI = new SetupGUI(player);
        } else if (e.getSource() == loadButton) {
            frame.dispose();
            loadWorkRoom();
        } else if (e.getSource() == loadMoney) {
            loadMoneyWorkRoom();
            moneyAdded = moneyLoadedLabel();
            frame.add(moneyAdded);
            SwingUtilities.updateComponentTreeUI(frame);
            loadButton.setEnabled(false);
        }
    }

    // EFFECTS: small message to display that money has been loaded
    private JLabel moneyLoadedLabel() {
        JLabel label = new JLabel();
        label.setText("Money loaded :D");
        label.setFont(new Font("Mononess", Font.PLAIN, 15));
        label.setForeground(new Color(224, 225, 228));
        label.setBounds(width - 135, height - 50, 150, 20);
        label.setBackground(Color.white);
        return label;
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadMoneyWorkRoom() {
        try {
            game = jsonReader.read();
            player = game.getPlayer();
            double money = player.getBalance();
            Player p = new Player();
            p.setMoney(money);

            System.out.println("Loaded deck from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadWorkRoom() {
        try {
            game = jsonReader.read();
            player = game.getPlayer();
            dealer = game.getDealer();
            numberOfTimesHit = player.getSize() + dealer.getSize() - 1;

            GameGUI gameGUI = new GameGUI(game.getDeck(), player, dealer, game, numberOfTimesHit);

            System.out.println("Loaded deck from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}
