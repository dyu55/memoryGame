import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;

public class Deck {
    private List<Card> cards;

    public Deck(String theme) {
        cards = new ArrayList<>();
        try {
            String coverPath = "src/ICON/cover.JPG";
            Image cover = ImageIO.read(new File(coverPath));
            Card.setCoverImage(cover);
        } catch (IOException e) {
            System.err.println("Error loading cover image: " + e.getMessage());
        }

        for (int i = 1; i <= 10; i++) {
            Image front = null;
            try {
                String frontPath = "src/ICON/" + theme + "/" + i + ".JPG";
                front = ImageIO.read(new File(frontPath));
            } catch (IOException e) {
                System.err.println("Error loading front image for card " + i + ": " + e.getMessage());
            }
            cards.add(new Card(i, front));
            cards.add(new Card(i, front));
        }
        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public List<Card> getCards() {
        return cards;
    }
}
