package ui;

import model.Card;
import model.DeckOfCards;
import model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// gui for the user to set up the deck
public class SetupGUI extends JFrame implements ActionListener {
    private final int widthSetup = 600;
    private final int heightSetup = 400;

    private JTextArea openingMessage;
    private JTextArea smallMessage;

    private JLabel cardName;
    private JLabel cardValue;
    private JTextField cardNameInput;
    private JTextField cardValueInput;
    private JLabel cardsAddedSoFar;

    private JPanel panel;
    private JFrame frame;

    private JButton addCardButton;
    private JButton beginButton;
    private JButton undoButton;

    private DeckOfCards deck = new DeckOfCards();
    private Player player = new Player();
    private int numberOfCards = 0;

    // MODIFIES: this
    // EFFECTS: set up the screen for the user to add cards to deck
    public SetupGUI(Player p) {
        player = p;
        ImageIcon image = new ImageIcon("src/BlackJack.png");
        panel = new JPanel();
        cardName = new JLabel();
        cardNameInput = new JTextField(10);
        cardValue = new JLabel();
        cardValueInput = new JTextField(10);
        cardsAddedSoFar = new JLabel();
        frame = new JFrame();

        openingMessage = getOpeningMessage();
        smallMessage = getSmallMessage();

        //Add Card button
        setupButtons();

        //cardName label
        setupTitleAndInput(cardName, "Card Name:", 15, cardNameInput);

        //cardValue label
        setupTitleAndInput(cardValue, "Card Value:", 60, cardValueInput);

        //cards added so far
        cardsAddedSoFar = getCardsAddedSoFar();

        //setup panel
        addEverythingToPanel();

        //setting up the frame ****
        setupFrame(image);

        frame.add(panel);
        frame.setVisible(true);

    }

    // EFFECTS: setting up the buttons on the screen
    private void setupButtons() {
        addCardButton = getAddCardButton();
        addCardButton.addActionListener(this);
        beginButton = getBeginButton();
        beginButton.addActionListener(this);
        undoButton = getUndoButton();
        undoButton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: setting up the frame for the setup page
    private void setupFrame(ImageIcon image) {
        frame.setTitle("Setup");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(widthSetup, heightSetup);
        frame.setLayout(null);
        //color ****
        frame.setIconImage(image.getImage());
        frame.getContentPane().setBackground(new Color(196, 196, 199));
    }

    // EFFECTS: set up the panel and add everything to the panel
    private void addEverythingToPanel() {
        panel.setBackground(null);
        panel.setBounds(0,0, widthSetup, heightSetup);
        panel.setLayout(null);
        panel.add(cardName);
        panel.add(cardNameInput);
        panel.add(cardValue);
        panel.add(cardValueInput);
        panel.add(openingMessage);
        panel.add(smallMessage);
        panel.add(addCardButton);
        panel.add(beginButton);
        panel.add(cardsAddedSoFar);
        panel.add(undoButton);
    }

    // EFFECTS: setting up the card name label, and also for the user to input a card name
    private void setupTitleAndInput(JLabel cardName, String text, int x, JTextField cardNameInput) {
        cardName.setText(text);
        cardName.setFont(new Font("Mononess", Font.PLAIN, 15));
        cardName.setForeground(new Color(40, 40, 43));
        cardName.setBounds(widthSetup / 2 - 110, heightSetup / 3 + x, 100, 25);
        //cardNameInput
        cardNameInput.setBounds(widthSetup / 2 - 27, heightSetup / 3 + x, 165, 25);
    }

    // EFFECTS: displays the number of cards added to deck so far
    private JLabel getCardsAddedSoFar() {
        JLabel labelC = new JLabel();
        labelC.setText("Number Of Cards In Deck: " + numberOfCards);
        labelC.setFont(new Font("Mononess", Font.PLAIN, 10));
        labelC.setForeground(new Color(40, 40, 43));
        labelC.setBounds(widthSetup / 2 - 110, 290, 300, 25);
        return labelC;
    }

    // EFFECTS: main message at top of screen
    private JTextArea getOpeningMessage() {
        JTextArea msg = new JTextArea();
        msg.setLineWrap(true);
        msg.setEditable(false);
        msg.setText("Before we begin, you must first set up \n               your deck to play!");
        msg.setFont(new Font("Mononess", Font.PLAIN, 18));
        msg.setBackground(null);
        msg.setForeground(new Color(40, 40, 43));
        msg.setBounds(widthSetup / 2 - 168, 75, widthSetup, 70);
        return msg;
    }

    // EFFECTS: small message to inform user to add enough cards
    private JTextArea getSmallMessage() {
        JTextArea msg = new JTextArea();
        msg.setLineWrap(true);
        msg.setEditable(false);
        msg.setText("Please add a minimum of 4 cards");
        msg.setFont(new Font("Mononess", Font.PLAIN, 8));
        msg.setBackground(null);
        msg.setForeground(new Color(40, 40, 43));
        msg.setBounds(widthSetup / 2, 251, widthSetup, 10);
        return msg;
    }

    // EFFECTS: buttons to add card
    private JButton getAddCardButton() {
        JButton button = new JButton("Add Card");
        button.setBounds(widthSetup / 2 - 117, heightSetup / 3 + 107, 120, 25);
        return button;
    }

    // EFFECTS: button to begin game
    private JButton getBeginButton() {
        JButton button = new JButton("Begin");
        button.setBounds(widthSetup - 100, heightSetup - 60, 100, 25);
        return button;
    }

    // EFFECTS: button to undo
    private JButton getUndoButton() {
        JButton button = new JButton("Undo");
        button.setBounds(widthSetup / 2 - 117, 270, 100, 25);
        return button;
    }

    // MODIFIES: deck, this
    // EFFECTS: if addCardButton pressed add card to deck, if beginButton pressed then begin game
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addCardButton) {
            String cardN = cardNameInput.getText();
            int cardV = Integer.parseInt(cardValueInput.getText());
            resetInputBox();

            Card card = new Card(cardN, cardV);
            deck.addCard(card);

            numberOfCards++;
            panel.remove(cardsAddedSoFar);
            cardsAddedSoFar = getCardsAddedSoFar();
            panel.add(cardsAddedSoFar);
            SwingUtilities.updateComponentTreeUI(panel);

        } else if (e.getSource() == beginButton) {
            handleIfDeckTooSmall();
        } else if (e.getSource() == undoButton) {
            deck.removeCard(deck.getSize() - 1);

            numberOfCards--;
            panel.remove(cardsAddedSoFar);
            cardsAddedSoFar = getCardsAddedSoFar();
            panel.add(cardsAddedSoFar);
            SwingUtilities.updateComponentTreeUI(panel);
        }
    }

    // MODIFIES: this
    // EFFECTS: if deck.size() is smaller than 4 then display a message, else go into betting phase
    private void handleIfDeckTooSmall() {
        if (deck.getSize() < 4) {
            resetInputBox();
            panel.add(tryAgain());
            SwingUtilities.updateComponentTreeUI(panel);
        } else {
            frame.dispose();
            BetGUI betGUI = new BetGUI(deck, player);
        }
    }

    // EFFECTS: label to display when not enough cards are added
    private JLabel tryAgain() {
        JLabel label = new JLabel();
        label.setText("Please add enough cards to the deck");
        label.setFont(new Font("Mononess", Font.PLAIN, 8));
        label.setForeground(new Color(220, 20, 60));
        label.setBounds(widthSetup / 2 - 110, 265, 350, 100);
        label.setBackground(Color.white);
        return label;
    }

    // EFFECTS: reset the input box to empty
    private void resetInputBox() {
        cardNameInput.setText("");
        cardValueInput.setText("");
    }
}
