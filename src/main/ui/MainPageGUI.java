package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPageGUI extends JFrame implements ActionListener {
    private final int width = 1000;
    private final int height = 600;
    JLabel label;
    JFrame frame;

    JButton instButton;
    JButton beginButton;
    JButton loadButton;

    public MainPageGUI() {
        ImageIcon image = new ImageIcon("src/BlackJack.png");
        label = new JLabel();
        frame = new JFrame();

        //resizing image ****
        Image img = image.getImage();
        Image imgScale = img.getScaledInstance(75, 75, Image.SCALE_SMOOTH);
        ImageIcon newImg = new ImageIcon(imgScale);

        //adding image to label ****
        label.setIcon(newImg);
        label.setText("Welcome to Blackjack!");
        label.setBounds(0, 115, width, height);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.TOP);
        label.setForeground(new Color(224, 225, 228));
        label.setFont(new Font("Mononess", Font.PLAIN, 40));
        label.setVerticalAlignment(JLabel.TOP);
        label.setHorizontalAlignment(JLabel.CENTER);

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
        instButton = instructionsButton();
        instButton.addActionListener(this);
        beginButton = beginGameButton();
        beginButton.addActionListener(this);
        loadButton = loadGameButton();
        loadButton.addActionListener(this);


        frame.add(instButton);
        frame.add(beginButton);
        frame.add(loadButton);
        frame.add(label);

        frame.setVisible(true);
    }

    public JButton instructionsButton() {
        JButton button = new JButton("Instructions");
        button.setBounds(width / 2 - 86, height / 2 + 100, 175, 50);
        return button;
    }

    public JButton loadGameButton() {
        JButton button = new JButton("Load Game");
        button.setBounds(width / 2 - 74, height / 2 + 40, 150, 50);
        return button;
    }

    public JButton beginGameButton() {
        JButton button = new JButton("Begin Game");
        button.setBounds(width / 2 - 74, height / 2 - 20, 150, 50);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == instButton) {
            System.out.println("Instructions");
            frame.dispose();
            InstructionsGUI instructionsGUI = new InstructionsGUI();
        } else if (e.getSource() == beginButton) {
            System.out.println("Setup Begun");
            frame.dispose();
            SetupGUI setupGUI = new SetupGUI();
        } else if (e.getSource() == loadButton) {
            System.out.println("Loaded Game");
        }
    }
}
