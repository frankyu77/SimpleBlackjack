package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// the instructions page displaying how to play the game
public class InstructionsGUI extends JFrame implements ActionListener {
    private final int width1 = 800;
    private final int height1 = 500;
    JLabel title;
    JTextArea instructions1;
    JTextArea instructions2;
    JTextArea instructions3;
    JFrame frame;
    JButton back;

    // MODIFIES: this
    // EFFECTS: creates an instructions gui
    public InstructionsGUI() {
        title = new JLabel();
        instructions1 = new JTextArea(theInstructions1());
        instructions2 = new JTextArea(theInstructions2());
        instructions3 = new JTextArea(theInstructions3());
        frame = new JFrame();

        //setup title
        title.setText("Instructions");
        title.setBounds(0, 40, width1, height1);
        title.setForeground(new Color(224, 225, 228));
        title.setFont(new Font("Mononess", Font.PLAIN, 30));
        title.setVerticalAlignment(JLabel.TOP);
        title.setHorizontalAlignment(JLabel.CENTER);

        //setup instructions
        setupInstructions(instructions1, 90, 115);
        setupInstructions(instructions2, 210, 115);
        setupInstructions(instructions3, 310, 100);

        //setup new frame
        setupFrame();

        //button
        back = backButton();
        back.addActionListener(this);

        addEverythingToFrame();


        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: sets up the frame for the instructions
    private void setupFrame() {
        frame.setTitle("Instructions");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width1, height1);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(81, 80, 77));
    }

    // MODIFIES: this
    // EFFECTS: adds everything to the frame
    private void addEverythingToFrame() {
        frame.add(instructions3);
        frame.add(title);
        frame.add(back);
        frame.add(instructions1);
        frame.add(instructions2);
    }

    // EFFECTS: sets up what label will look like for each instruction
    private void setupInstructions(JTextArea instructions, int y, int height) {
        instructions.setBounds(50, y, 700, height);
        instructions.setFont(new Font("Mononess", Font.PLAIN, 14));
        instructions.setBackground(new Color(81, 80, 77));
        instructions.setForeground(new Color(224, 225, 228));
        instructions.setLineWrap(true);
        instructions.setEditable(false);
    }

    // EFFECTS: first paragraph of instructions
    private String theInstructions1() {
        String words = "Black Jack is a game where you and the dealer are dealt 2 cards. From there your goal is to "
                + "get"
                + " closer \nto 21 than the dealer when adding up all the cards you are dealt. If you get dealt a "
                + "BlackJack, which is a 11 value and a 10 value card, then you win automatically. You lose if your "
                + "cards go over 21 or if the dealer gets closer to 21 than you. The dealer will deal you two "
                + "cards, and then deal themselves two \ncards"
                + ". You will then be allowed to add cards to your hand until you want to stop, then the "
                + "dealer will \nstart adding cards to their deck until they either beat "
                + "you, lose, or draw with you.";
        return words;
    }

    // EFFECTS: second paragraph of instructions
    private String theInstructions2() {
        String words = "In this game of Black Jack, everyone will start with $20. You can bet any amount of money, "
                + "as long as \nyou have "
                + "it and if you win, you get double the money back. However, if your balance is below $5, then you "
                + "lose."
                + " If you lose, you lose the money that you bet."
                + " If you draw with the dealer, you keep the money"
                + "you bet. If you get a Black Jack, you get paid "
                + "150% of the value that you bet. One"
                + " thing to keep in mind"
                + " is that the dealer will never give themselves cards if their total card"
                + " value is 17 or more.";
        return words;
    }

    // EFFECTS: third paragraph of instructions
    private String theInstructions3() {
        String words = "For this version of Blackjack, the player must set up their own deck by entering the value "
                + "and the \ncorresponding face value of the card. Once the setup is done, the player will have the "
                + "option to bet an amount of money and begin the game";
        return words;
    }

    // MODIFIES: this
    // EFFECTS: creates a back button
    private JButton backButton() {
        JButton button = new JButton("Back");
        button.setBounds(10, height1 - 60, 80, 25);
        return button;
    }

    // MODIFIES: this
    // EFFECTS: if back button is pressed go back to main page gui
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back) {
            System.out.println("Back to main menu from instructions");
            frame.dispose();
            MainPageGUI mainPageGUI = new MainPageGUI();
        }
    }
}
