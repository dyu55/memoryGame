import java.awt.*;
import javax.swing.UIManager;

public class Card {
    private int value;
    private boolean faceUp;
    private boolean matched;
    private Image frontImage;
    private static Image coverImage;

    public Card(int value, Image frontImage) {
        this.value = value;
        this.frontImage = frontImage;
        this.faceUp = false;
        this.matched = false;
    }

    public int getValue() {
        return value;
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    public void flip() {
        if (!matched) {
            faceUp = !faceUp;
        }
    }

    public static void setCoverImage(Image cover) {
        coverImage = cover;
    }

    public void draw(Graphics2D g, int x, int y, int width, int height) {
        if (faceUp || matched) {
            if(frontImage != null) {
                g.drawImage(frontImage, x, y, width, height, null);
            } else {
                g.setColor(Color.WHITE);
                g.fillRect(x, y, width, height);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, width, height);
                g.setFont(new Font("Arial", Font.BOLD, 24));
                String text = String.valueOf(value);
                FontMetrics fm = g.getFontMetrics();
                int textWidth = fm.stringWidth(text);
                int textHeight = fm.getAscent();
                int tx = x + (width - textWidth) / 2;
                int ty = y + (height + textHeight) / 2;
                g.drawString(text, tx, ty);
            }
        } else {
            if (coverImage != null) {
                g.drawImage(coverImage, x, y, width, height, null);
            } else {
                Color bg = UIManager.getColor("Button.background");
                g.setColor(bg);
                g.fillRect(x, y, width, height);

                Color topLeft = bg.brighter();
                Color bottomRight = bg.darker();
                g.setColor(topLeft);
                g.drawLine(x, y, x + width - 1, y);
                g.drawLine(x, y, x, y + height - 1);
                g.setColor(bottomRight);
                g.drawLine(x, y + height - 1, x + width - 1, y + height - 1);
                g.drawLine(x + width - 1, y, x + width - 1, y + height - 1);
            }
        }
    }

    @Override
    public String toString() {
        return "Card(" + value + ") " + (faceUp ? "FaceUp" : "FaceDown");
    }
}
