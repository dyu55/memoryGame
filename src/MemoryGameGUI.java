import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MemoryGameGUI extends JFrame implements KeyListener {
    private Game game;
    private BoardPanel boardPanel;
    private JButton resetButton;

    public MemoryGameGUI() {
        game = new Game();
        boardPanel = new BoardPanel(game);

        resetButton = new JButton("Reset Game");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.restart();
                boardPanel.repaint();
            }
        });

        JPanel controlPanel = new JPanel();
        controlPanel.add(resetButton);

        setLayout(new BorderLayout());
        add(boardPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        addKeyListener(this);
        setFocusable(true);
        setTitle("Memory Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_R) {
            game.restart();
            boardPanel.repaint();
        }
    }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MemoryGameGUI());
    }
}
