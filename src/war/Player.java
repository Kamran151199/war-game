package war;

import java.util.Collection;
import java.util.LinkedList;

/**
 * <strong>Final Project</strong><br>
 * Represents a participant in "War".
 *
 * @author Komron Valijonov
 */
public class Player {
    private final LinkedList<Card> deck;
    private String name;

    /**
     * Creates a new player.
     *
     * @param name of player
     * @param deck cards player has
     */
    public Player(String name, LinkedList<Card> deck) {
        /*
         * We used the LinkedList for the deck because it is a more efficient data structure for this use case.
         * In particular, we need to be able to draw cards from the top of the deck and add cards to the bottom of the deck.
         */
        this.name = name;
        this.deck = deck;
    }

    /**
     * Returns true if the player has any cards left.
     *
     * @return true when player has cards
     */
    public boolean hasCard() {
        return !deck.isEmpty();
    }

    /**
     * Returns the number of cards this player has left.
     *
     * @return number of cards left in the player's deck
     */
    public int cardsLeft() {
        return deck.size();
    }

    /**
     * Pops a card from the top of the deck (the last card added).
     *
     * @return card drawn from deck (the top/last card)
     */
    public Card drawCard() {
        return deck.pop();
    }

    /**
     * Adds a collection of cards to this player's deck.
     *
     * @param cards to be added to deck
     */
    public void addCards(Collection<Card> cards) {
        deck.addAll(cards);
    }

    /**
     * Returns this player's name.
     *
     * @return player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets this player's name
     *
     * @param name to be set
     */
    public void setName(String name) {
        this.name = name;
    }
}
