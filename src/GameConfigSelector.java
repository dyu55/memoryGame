import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameConfigSelector extends JFrame {
    private ButtonGroup themeGroup;
    private ButtonGroup difficultyGroup;
    private JRadioButton rbAnimals;
    private JRadioButton rbColors;
    private JRadioButton rbFlowers;
    private JRadioButton rbEasy;
    private JRadioButton rbHard;
    private JButton startButton;

    public GameConfigSelector() {
        setTitle("Game Configuration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        // Theme selection panel (radio buttons)
        JPanel themePanel = new JPanel(new GridLayout(1, 3, 10, 10));
        themePanel.setBorder(BorderFactory.createTitledBorder("Select Theme"));
        themePanel.setBackground(Color.WHITE);

        rbAnimals = new JRadioButton("Animals");
        rbColors = new JRadioButton("Colors");
        rbFlowers = new JRadioButton("Flowers");
        rbAnimals.setBackground(Color.WHITE);
        rbColors.setBackground(Color.WHITE);
        rbFlowers.setBackground(Color.WHITE);

        themeGroup = new ButtonGroup();
        themeGroup.add(rbAnimals);
        themeGroup.add(rbColors);
        themeGroup.add(rbFlowers);

        themePanel.add(rbAnimals);
        themePanel.add(rbColors);
        themePanel.add(rbFlowers);

        JPanel difficultyPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        difficultyPanel.setBorder(BorderFactory.createTitledBorder("Select Difficulty"));
        difficultyPanel.setBackground(Color.WHITE);

        rbEasy = new JRadioButton("Easy");
        rbHard = new JRadioButton("Hard");
        rbEasy.setBackground(Color.WHITE);
        rbHard.setBackground(Color.WHITE);

        difficultyGroup = new ButtonGroup();
        difficultyGroup.add(rbEasy);
        difficultyGroup.add(rbHard);

        difficultyPanel.add(rbEasy);
        difficultyPanel.add(rbHard);

        startButton = new JButton("Start Game");
        startButton.setBackground(Color.WHITE);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedTheme = null;
                if (rbAnimals.isSelected()) {
                    selectedTheme = "animals";
                } else if (rbColors.isSelected()) {
                    selectedTheme = "colors";
                } else if (rbFlowers.isSelected()) {
                    selectedTheme = "flowers";
                }

                // Determine difficulty: if "Hard" is selected, set isHard to true; otherwise false.
                boolean isHard = rbHard.isSelected();

                if (selectedTheme != null && (rbEasy.isSelected() || rbHard.isSelected())) {
                    new MemoryGameGUI(selectedTheme, isHard);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(GameConfigSelector.this,
                            "Please select both a theme and difficulty.",
                            "Incomplete Selection",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(themePanel);
        centerPanel.add(difficultyPanel);

        add(centerPanel, BorderLayout.CENTER);
        add(startButton, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameConfigSelector());
    }
}
