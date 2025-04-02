public class Game {
    private Deck deck;
    private Card firstSelected;
    private Card secondSelected;
    private boolean busy;

    public Game() {
        deck = new Deck();
        resetSelections();
        busy = false;
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

    public void restart() {
        deck = new Deck();
        resetSelections();
        busy = false;
        System.out.println("Game restarted.");
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
        System.out.println("Set busy to " + busy);
    }
}
