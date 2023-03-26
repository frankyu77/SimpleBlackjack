package ui;

import model.Card;
import model.DeckOfCards;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetupGUI extends JFrame implements ActionListener {
    private final int widthSetup = 600;
    private final int heightSetup = 400;

    JTextArea openingMessage;
    JTextArea smallMessage;

    JLabel cardName;
    JLabel cardValue;
    JTextField cardNameInput;
    JTextField cardValueInput;
    JLabel cardsAddedSoFar;

    JPanel panel;
    JFrame frame;

    JButton addCardButton;
    JButton beginButton;

    DeckOfCards deck = new DeckOfCards();
    private int numberOfCards = 0;

    public SetupGUI() {
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
        addCardButton = getAddCardButton();
        addCardButton.addActionListener(this);

        //Begin Game button
        beginButton = getBeginButton();
        beginButton.addActionListener(this);


        //cardName label
        cardName.setText("Card Name:");
        cardName.setFont(new Font("Mononess", Font.PLAIN, 15));
        cardName.setForeground(new Color(40, 40, 43));
        cardName.setBounds(widthSetup / 2 - 110, heightSetup / 3 + 15, 100, 25);
        //cardNameInput
        cardNameInput.setBounds(widthSetup / 2 - 27, heightSetup / 3 + 15, 165, 25);

        //cardValue label
        cardValue.setText("Card Value:");
        cardValue.setFont(new Font("Mononess", Font.PLAIN, 15));
        cardValue.setForeground(new Color(40, 40, 43));
        cardValue.setBounds(widthSetup / 2 - 110, heightSetup / 3 + 60, 100, 25);
        //cardNameInput
        cardValueInput.setBounds(widthSetup / 2 - 27, heightSetup / 3 + 60, 165, 25);

        //cards added so far
        cardsAddedSoFar = getCardsAddedSoFar();

        //setup panel
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

        //setting up the frame ****
        frame.setTitle("Setup");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(widthSetup, heightSetup);
        frame.setLayout(null);
        //color ****
        frame.setIconImage(image.getImage());
        frame.getContentPane().setBackground(new Color(196, 196, 199));

        frame.add(panel);

        frame.setVisible(true);

    }

    private JLabel getCardsAddedSoFar() {
        JLabel labelC = new JLabel();
        labelC.setText("Number Of Cards In Deck: " + numberOfCards);
        labelC.setFont(new Font("Mononess", Font.PLAIN, 10));
        labelC.setForeground(new Color(40, 40, 43));
        labelC.setBounds(widthSetup / 2 - 110, 265, 300, 25);
        return labelC;
    }

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

    private JButton getAddCardButton() {
        JButton button = new JButton("Add Card");
        button.setBounds(widthSetup / 2 - 117, heightSetup / 3 + 107, 120, 25);
        return button;
    }

    private JButton getBeginButton() {
        JButton button = new JButton("Begin");
        button.setBounds(widthSetup - 100, heightSetup - 50, 100, 25);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addCardButton) {
            String cardN = cardNameInput.getText();
            int cardV = Integer.parseInt(cardValueInput.getText());
            cardNameInput.setText("");
            cardValueInput.setText("");

            Card card = new Card(cardN, cardV);
            deck.addCard(card);

            numberOfCards++;
            panel.remove(cardsAddedSoFar);
            cardsAddedSoFar = getCardsAddedSoFar();
            panel.add(cardsAddedSoFar);
            SwingUtilities.updateComponentTreeUI(panel);

        } else if (e.getSource() == beginButton) {
            for (int i = 0; i < deck.getSize(); i++) {
                System.out.print(deck.getCardName(i) + ", ");
            }
            System.out.println();
            for (int i = 0; i < deck.getSize(); i++) {
                System.out.print(deck.getCardValue(i) + ", ");
            }
            System.out.println();

            System.out.println("Betting Begins");
            frame.dispose();
            BetGUI betGUI = new BetGUI(deck);
        }
    }
}
