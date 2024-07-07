package war.gui;

import javax.swing.*;
import java.awt.*;

/**
 * <strong>Final Project</strong><br>
 * Represents a players "hand"
 *
 * @author Komron Valijonov
 */
public class PlayerPanel extends JPanel {
    private final JLabel deck;
    private final CardPanel battlePanel, warPanel;

    /**
     * Creates a new player.
     *
     * @param p1 true if player 1's panel
     */
    public PlayerPanel(boolean p1) {
        super(new GridLayout(1, 3));
        if (!p1)
            setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        setOpaque(false);

        int ha = p1 ? SwingConstants.LEFT : SwingConstants.RIGHT;
        add(deck = new JLabel(JCard.DEFAULT_BACK_IMAGE));
        add(battlePanel = new CardPanel(ha));
        add(warPanel = new CardPanel(ha));
    }

    /**
     * Returns the "deck" label.
     *
     * @return deck label
     */
    public JLabel getDeck() {
        return deck;
    }

    /**
     * Returns the panel for the "battle" slot.
     *
     * @return panel for battle slot
     */
    public CardPanel getBattlePanel() {
        return battlePanel;
    }

    /**
     * Returns the panel for the "war" slot.
     *
     * @return panel for war slot
     */
    public CardPanel getWarPanel() {
        return warPanel;
    }
}
