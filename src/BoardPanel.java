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

                            if (game.isGameOver()) {
                                System.out.println("Game Over detected. All cards matched.");
                                JOptionPane.showMessageDialog(BoardPanel.this, "Congratulations, you won!");
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

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
