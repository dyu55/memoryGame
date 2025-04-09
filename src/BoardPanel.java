import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;

public class BoardPanel extends JPanel implements MouseListener {
    private Game game;
    private int rows = 4;
    private int cols = 5;

    public BoardPanel(Game game) {
        this.game = game;
        addMouseListener(this);
        setPreferredSize(new Dimension(500, 400));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        List<Card> cards = game.getDeck().getCards();
        int cardWidth = getWidth() / cols;
        int cardHeight = getHeight() / rows;
        for (int i = 0; i < cards.size(); i++) {
            int row = i / cols;
            int col = i % cols;
            int x = col * cardWidth;
            int y = row * cardHeight;
            cards.get(i).draw(g2d, x, y, cardWidth, cardHeight);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (game.isBusy() || game.isTwoCardsSelected()) {
            System.out.println("Ignoring click: Game is busy or two cards are already selected.");
            return;
        }

        int cardWidth = getWidth() / cols;
        int cardHeight = getHeight() / rows;
        int col = e.getX() / cardWidth;
        int row = e.getY() / cardHeight;
        int index = row * cols + col;
        System.out.println("Mouse clicked at (" + e.getX() + ", " + e.getY() + "), computed index: " + index);

        if (index < game.getDeck().getCards().size()) {
            Card selected = game.getDeck().getCards().get(index);
            if (!selected.isFaceUp() && !selected.isMatched()) {
                game.selectCard(selected);
                repaint();

                if (game.isTwoCardsSelected()) {
                    game.setBusy(true);
                    System.out.println("Two cards selected. Starting timer for flip back.");
                    Timer timer = new Timer(1000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            System.out.println("Timer triggered. Checking match and flipping back if needed.");
                            game.flipBackSelections();
                            game.setBusy(false);
                            repaint();

                            Window window = SwingUtilities.getWindowAncestor(BoardPanel.this);
                            if (window instanceof MemoryGameGUI) {
                                ((MemoryGameGUI) window).updateTitle();
                            }

                            // Check win condition.
                            if (game.isGameOver()) {
                                System.out.println("Game Over detected. All cards matched.");
                                Object[] options = {"Quit", "Play Again"};
                                int choice = JOptionPane.showOptionDialog(
                                        BoardPanel.this,
                                        "Congratulations, you win!\nWhat do you want to do next?",
                                        "Game Over",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.INFORMATION_MESSAGE,
                                        null,
                                        options,
                                        options[1]);

                                if (choice == 0) {  // Quit
                                    Window win = SwingUtilities.getWindowAncestor(BoardPanel.this);
                                    if (win != null) {
                                        win.dispose();
                                    }
                                } else if (choice == 1) {  // Play Again
                                    new GameConfigSelector();
                                    Window win = SwingUtilities.getWindowAncestor(BoardPanel.this);
                                    if (win != null) {
                                        win.dispose();
                                    }
                                }
                            }
                            // Check if move limit is reached in hard mode.
                            else if (game.isHardMode() && game.isTimeUp()) {
                                System.out.println("Hard mode: move limit reached.");
                                int option = JOptionPane.showOptionDialog(
                                        BoardPanel.this,
                                        "Game Over! You have exceeded the move limit.\nDo you want to try again?",
                                        "Game Over",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.INFORMATION_MESSAGE,
                                        null,
                                        new Object[]{"Yes", "No"},
                                        "Yes");

                                if (option == JOptionPane.YES_OPTION) {
                                    new GameConfigSelector();
                                    Window win = SwingUtilities.getWindowAncestor(BoardPanel.this);
                                    if (win != null) {
                                        win.dispose();
                                    }
                                } else {
                                    Window win = SwingUtilities.getWindowAncestor(BoardPanel.this);
                                    if (win != null) {
                                        win.dispose();
                                    }
                                }
                            }
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
                }
            } else {
                System.out.println("Card already face up or matched: " + selected);
            }
        }
    }




}
