package war.gui;

import war.Card;

import javax.swing.*;
import java.awt.*;

/**
 * <strong>Final Project</strong><br>
 * Represents a panel that can show any card.
 *
 * @author Komron Valijonov
 */
public class CardPanel extends JPanel {
    public CardPanel(int horizontalAlignment) {
        super(new CardLayout());
        setOpaque(false);
        // add all cards
        add("back", new JLabel(JCard.DEFAULT_BACK_IMAGE));
        for (Card card : Card.all()) {
            JLabel label = new JCard(card).getComponent();
            label.setHorizontalAlignment(horizontalAlignment);
            add(card.toString(), label);
        }
        setVisible(false);
    }

    private void show(String name) {
        setVisible(true);
        ((CardLayout) getLayout()).show(this, name);
    }

    /**
     * Shows the specified card in the panel.
     *
     * @param card to display
     */
    public void showCard(Card card) {
        show(card.toString());
    }

    /**
     * Shows the card "back" in the panel.
     */
    public void showBack() {
        show("back");
    }
}
