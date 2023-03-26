package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InstructionsGUI extends JFrame implements ActionListener {
    private final int width1 = 800;
    private final int height1 = 500;
    JLabel title;
    JTextArea instructions1;
    JTextArea instructions2;
    JTextArea instructions3;
    JFrame frame;
    JButton back;

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
        instructions1.setBounds(50, 90, 700, 115);
        instructions1.setFont(new Font("Mononess", Font.PLAIN, 14));
        instructions1.setBackground(new Color(81, 80, 77));
        instructions1.setForeground(new Color(224, 225, 228));
        instructions1.setLineWrap(true);
        instructions1.setEditable(false);

        instructions2.setBounds(50, 210, 700, 115);
        instructions2.setFont(new Font("Mononess", Font.PLAIN, 14));
        instructions2.setBackground(new Color(81, 80, 77));
        instructions2.setForeground(new Color(224, 225, 228));
        instructions2.setLineWrap(true);
        instructions2.setEditable(false);

        instructions3.setBounds(50, 310, 700, 100);
        instructions3.setFont(new Font("Mononess", Font.PLAIN, 14));
        instructions3.setBackground(new Color(81, 80, 77));
        instructions3.setForeground(new Color(224, 225, 228));
        instructions3.setLineWrap(true);
        instructions3.setEditable(false);

        //setup new frame
        frame.setTitle("Instructions");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width1, height1);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(81, 80, 77));

        //button
        back = backButton();
        back.addActionListener(this);

        frame.add(instructions3);
        frame.add(title);
        frame.add(back);
        frame.add(instructions1);
        frame.add(instructions2);


        frame.setVisible(true);
    }

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

    private String theInstructions3() {
        String words = "For this version of Blackjack, the player must set up their own deck by entering the value "
                + "and the \ncorresponding face value of the card. Once the setup is done, the player will have the "
                + "option to bet an amount of money and begin the game";
        return words;
    }

    private JButton backButton() {
        JButton button = new JButton("Back");
        button.setBounds(10, height1 - 60, 80, 25);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back) {
            System.out.println("Back to main menu from instructions");
            frame.dispose();
            MainPageGUI mainPageGUI = new MainPageGUI();
        }
    }
}
