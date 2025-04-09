import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;

public class Game {
    private Deck deck;
    private Card firstSelected;
    private Card secondSelected;
    private boolean busy; // used to prevent extra clicks
    private boolean isHard;
    private int moveCount;
    private final int MOVE_LIMIT = 20;

    public Game(String theme, boolean isHard) {
        deck = new Deck(theme);
        resetSelections();
        busy = false;
        this.isHard = isHard;
        moveCount = 0;
    }

    public Deck getDeck() {
        return deck;
    }

    public Card getFirstSelected() {
        return firstSelected;
    }

    public Card getSecondSelected() {
        return secondSelected;
    }

    public void selectCard(Card card) {
        if (busy) {
            System.out.println("Game is busy, ignoring click.");
            return;
        }
        if (card.isFaceUp() || card.isMatched()) {
            System.out.println("Card " + card + " is already face up or matched.");
            return;
        }

        card.flip();
        System.out.println("Card selected: " + card);
        if (firstSelected == null) {
            firstSelected = card;
            System.out.println("First selected card: " + card);
        } else if (secondSelected == null) {
            secondSelected = card;
            System.out.println("Second selected card: " + card);
        }
    }

    public boolean isTwoCardsSelected() {
        return firstSelected != null && secondSelected != null;
    }

    public boolean checkMatch() {
        if (firstSelected != null && secondSelected != null) {
            return firstSelected.getValue() == secondSelected.getValue();
        }
        return false;
    }


    public void flipBackSelections() {
        if (firstSelected != null && secondSelected != null) {
            if (!checkMatch()) {
                System.out.println("Cards do not match: " + firstSelected + " and " + secondSelected);
                firstSelected.flip();
                secondSelected.flip();
            } else {
                System.out.println("Cards match: " + firstSelected + " and " + secondSelected);
                firstSelected.setMatched(true);
                secondSelected.setMatched(true);
            }
        }
        resetSelections();
        if (isHard) {
            moveCount++;
            System.out.println("Hard Mode - Move count: " + moveCount);
        }
    }

    private void resetSelections() {
        firstSelected = null;
        secondSelected = null;
    }

    public boolean isGameOver() {
        for (Card card : deck.getCards()) {
            if (!card.isMatched()) {
                return false;
            }
        }
        return true;
    }

    public boolean isTimeUp() {
        return isHard && (moveCount >= MOVE_LIMIT) && !isGameOver();
    }

    public boolean isHardMode() {
        return isHard;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void restart(String theme, boolean isHard) {
        deck = new Deck(theme);
        resetSelections();
        busy = false;
        this.isHard = isHard;
        moveCount = 0;
        System.out.println("Game restarted.");
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
        System.out.println("Set busy to " + busy);
    }

    public boolean isBusy() {
        return busy;
    }
}
