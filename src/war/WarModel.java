package war;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * <strong>Final Project</strong><br>
 * Represents the logic of the game. Manages the game state and controls the flow of the game,
 * notifying the view as needed (MVC).
 *
 * @author Komron Valijonov
 */
public class WarModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private transient final IWarView view;
    private List<Card> pool = new ArrayList<>();  // cards in the pool (Cards that are currently in the middle of a turn)
    private Player player1, player2;
    private boolean gameOver = true;  // true if the game is over
    private boolean war = false;  // true if the game is in a state of war (the war state is when two cards are equal)
    private transient StorageModel storage;
    private Card currentlyDrawnCard1;
    private Card currentlyDrawnCard2;

    public Card getCurrentlyDrawnCard(boolean p1) {
        return p1 ? currentlyDrawnCard1 : currentlyDrawnCard2;
    }

    public void setCurrentlyDrawnCard(Card card, boolean p1) {
        if (p1)
            currentlyDrawnCard1 = card;
        else
            currentlyDrawnCard2 = card;
    }


    /**
     * Constructs a new model.
     *
     * @param view to notify as game progresses (MVC)
     */
    public WarModel(IWarView view, StorageModel storage) {
        this.view = view;
        this.storage = storage;
    }

    /**
     * Clears current game and starts a new one.
     */
    public void newGame() {
        // set or reset to the initial state
        gameOver = false;
        war = false;
        pool.clear();

        // create a standard deck of cards (52 cards)
        List<Card> deck = Card.all();

        // shuffle (randomize) the deck and split it in half for each player
        Collections.shuffle(deck);
        LinkedList<Card> deck1 = new LinkedList<>(deck.subList(0, deck.size() / 2));
        LinkedList<Card> deck2 = new LinkedList<>(deck.subList(deck.size() / 2, deck.size()));

        // initialize the players
        player1 = new Player("Player 1", deck1);
        player2 = new Player("Player 2", deck2);

        // notify view
        view.onGameStart();
    }

    /**
     * Returns True if the game can continue. If a player runs out of cards, the game is over.
     *
     * @return true if the game can continue (both players have cards)
     */
    private boolean gameContinues() {
        if (!player1.hasCard()) {
            endGame(player2);
            return false;
        }
        if (!player2.hasCard()) {
            endGame(player1);
            return false;
        }
        return true;
    }

    /**
     * Starts a new turn.
     */
    public void nextTurn() {
        // make sure the game can continue
        if (!gameContinues())
            return;

        // draw cards from each player
        Card card1 = player1.drawCard();
        Card card2 = player2.drawCard();

        // add cards to pool (middle of the table)
        pool.add(card1);
        pool.add(card2);

        // set the drawn cards to the view
        setCurrentlyDrawnCard(card1, true);
        setCurrentlyDrawnCard(card2, false);

        // notify view of the turn
        view.onTurnStart(card1, card2);

        // compare cards
        int diff = card1.compareTo(card2);
        // diff > 0 means player 1 wins, diff < 0 means player 2 wins, diff = 0 means war
        Player winner;

        if (diff > 0)
            // player is the winner
            winner = player1;
        else if (diff < 0)
            // player 2 is the winner
            winner = player2;
        else {
            // if the cards are equal, start a war (draw 2 more cards)
            // make sure the game can continue (both players have cards)
            if (!gameContinues())
                return;
            view.onWarStart();
            war = true;
            return;
        }

        // award pool to winner and clear it
        winner.addCards(pool);
        pool.clear();

        // end war if it was in progress
        if (war) {
            war = false;
            view.onWarEnd();
        }

        // notify view of the end of the turn
        view.onTurnEnd(card1, card2, winner);
    }

    /**
     * Flips a card face down for each player to prepare for war.
     */
    public void prepareForWar() {
        // check if the game can continue and if there is a war in progress
        if (!war || !gameContinues())
            return;

        // draw two cards and add them to the pool
        Card card1 = player1.drawCard(), card2 = player2.drawCard();

        // set cards face down since the war is about to start and each player draws a card face down during war
        card1.setFaceUp(false);
        card2.setFaceUp(false);

        // add cards to the pool
        pool.add(card1);
        pool.add(card2);

        // notify view
        view.onWarPreparation(card1, card2);
    }

    /**
     * Ends the game and notifies the view of the winner.
     *
     * @param winner player that still has cards
     */
    public void endGame(Player winner) {
        gameOver = true;
        view.onGameOver(winner);
    }

    /**
     * Returns the player whose turn it is.
     *
     * @param p1 true when it's player 1's turn
     * @return player
     */
    public Player getPlayer(boolean p1) {
        return p1 ? player1 : player2;
    }

    /**
     * Whether the game is over.
     *
     * @return true if game is over
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Returns true if war is currently in progress.
     *
     * @return true if wartime
     */
    public boolean isWar() {
        return war;
    }

    public void setIsGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void setIsWar(boolean war) {
        this.war = war;
    }

    public List<Card> getPool() {
        return pool;
    }

    public void setPool(List<Card> pool) {
        this.pool = pool;
    }

    public void setPlayer(Player player, boolean p1) {
        if (p1)
            this.player1 = player;
        else
            this.player2 = player;
    }

    public void saveGame() {
        storage.save(this);
    }

    public void loadGame() {
        WarModel model = storage.loadGameState();
        this.setIsGameOver(model.isGameOver());
        this.setIsWar(model.isWar());
        this.setPlayer(storage.loadPlayer1(), true);
        this.setPlayer(storage.loadPlayer2(), false);
        this.setPool(storage.loadPool());
        this.view.setMobilisedCard(true, storage.loadMobilisedCard1());
        this.view.setMobilisedCard(false, storage.loadMobilisedCard2());
        this.setCurrentlyDrawnCard(storage.loadCurrentlyDrawnCard1(), true);
        this.setCurrentlyDrawnCard(storage.loadCurrentlyDrawnCard2(), false);
        this.view.onGameLoad();
    }
}
