package war.gui;

import war.Card;

import javax.swing.*;
import java.awt.*;

/**
 * <strong>Final Project</strong><br>
 * Represents a JPanel that contains the field of play.
 *
 * @author Komron Valijonov
 */
public class TablePanel extends JPanel {
    public static final Color BACKGROUND_COLOR = new Color(128, 208, 153);
    private final PlayerPanel player1, player2;

    /**
     * Creates a new table.
     */
    public TablePanel() {
        super(new BorderLayout());
        setBackground(BACKGROUND_COLOR);
        add(player1 = new PlayerPanel(true), BorderLayout.WEST);
        add(player2 = new PlayerPanel(false), BorderLayout.EAST);
    }

    /**
     * Returns player 1's panel if true, player 2's panel otherwise.
     *
     * @param p1 true if player 1
     * @return panel
     */
    public PlayerPanel getPlayerPanel(boolean p1) {
        return p1 ? player1 : player2;
    }

    /**
     * Resets the table to a blank state.
     */
    public void reset() {
        player1.getDeck().setVisible(true);
        player1.getBattlePanel().setVisible(false);
        player1.getWarPanel().setVisible(false);
        player2.getDeck().setVisible(true);
        player2.getBattlePanel().setVisible(false);
        player2.getWarPanel().setVisible(false);
    }

    /**
     * Shows the cards in the "battle" slots for player 1 and 2 respectively.
     *
     * @param card1 to show for player 1
     * @param card2 to show for player 2
     */
    public void showCards(Card card1, Card card2) {
        player1.getBattlePanel().showCard(card1);
        player2.getBattlePanel().showCard(card2);
    }
}
