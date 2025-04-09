import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MemoryGameGUI extends JFrame implements KeyListener {
    private Game game;
    private BoardPanel boardPanel;
    private JButton resetButton;
    private String theme;
    private boolean isHard;

    public MemoryGameGUI(String theme, boolean isHard) {
        this.theme = theme;
        this.isHard = isHard;
        game = new Game(theme, isHard);
        boardPanel = new BoardPanel(game);

        resetButton = new JButton("Reset Game");
        resetButton.setBackground(Color.WHITE);
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                promptForConfigAndRestart();
            }
        });

        JPanel controlPanel = new JPanel();
        controlPanel.add(resetButton);

        setLayout(new BorderLayout());
        add(boardPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        addKeyListener(this);
        setFocusable(true);
        updateTitle();  // Initialize the title (includes remaining moves if hard mode)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void updateTitle() {
        if (isHard) {
            int remaining = Math.max(20 - game.getMoveCount(), 0);
            setTitle("Memory Card Game - Theme: " + theme + " - Hard (Remaining: " + remaining + ")");
        } else {
            setTitle("Memory Card Game - Theme: " + theme + " - Easy");
        }
    }

    private void promptForConfigAndRestart() {
        new GameConfigSelector();
        dispose();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_R) {
            promptForConfigAndRestart();
        }
    }
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}
}
