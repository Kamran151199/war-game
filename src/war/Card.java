package war;

import java.util.ArrayList;
import java.util.List;

/**
 * <strong>Final Project</strong><br>
 * Represents a playing card that is comparable to other cards.
 * A card is considered greater than another card if it's
 * rank is greater than the other.
 *
 * @author Komron Valijonov
 */
public class Card implements Comparable<Card> {
    // Ranks
    public static final int RANK_FIRST = 2; // least value
    public static final int RANK_LAST = 14; // greatest value (ace)

    public static final int STANDARD_DECK_SIZE = 52;

    protected final int rank;
    protected final Suit suit;
    protected boolean faceUp = true;

    /**
     * Constructs a new Card.
     *
     * @param rank of card (between 2 and 14 (inclusive))
     * @param suit of card
     */
    public Card(int rank, Suit suit) {
        if (rank < RANK_FIRST || rank > RANK_LAST)
            throw new IllegalArgumentException("Card rank must be between 2 and 14 (RANK_ACE)");
        this.rank = rank;
        this.suit = suit;
    }

    /**
     * Returns a list of all possible unique {@link Card}s.
     *
     * @return list of all cards
     */
    public static List<Card> all() {
        List<Card> cards = new ArrayList<>(STANDARD_DECK_SIZE);
        for (int rank = RANK_FIRST; rank <= RANK_LAST; rank++) {
            for (Suit suit : Suit.values())
                cards.add(new Card(rank, suit));
        }
        return cards;
    }

    /**
     * Returns this card's rank.
     *
     * @return rank of card
     */
    public int getRank() {
        return rank;
    }

    /**
     * Returns this card's suit.
     *
     * @return card's suit
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Returns true if this card is face up.
     *
     * @return true if face up
     */
    public boolean isFaceUp() {
        return faceUp;
    }

    /**
     * Sets if this card is face up or face down.
     *
     * @param faceUp true when face should be up
     */
    public void setFaceUp(boolean faceUp) {
        this.faceUp = faceUp;
    }

    /**
     * Returns the difference between this card's rank and another card's rank.
     *
     * @param card to compare to
     * @return difference between ranks (integer)
     */
    @Override
    public int compareTo(Card card) {
        return rank - card.rank;
    }

    /**
     * String representation of a card is the first letter of the suit and the rank.
     * For example, the ace of spades is "s14".
    */
    @Override
    public String toString() {
        return "" + suit.toString().toLowerCase().charAt(0) + rank;
    }

    /**
     * Represents a playing card's suit.
     */
    public static enum Suit {
        SPADES,
        CLUBS,
        HEARTS,
        DIAMONDS
    }
}
