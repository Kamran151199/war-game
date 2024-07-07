package war.gui;

import war.WarModel;

import javax.swing.*;
import java.awt.*;

/**
 * A<strong>Final Project</strong><br>
 * Represents the content at the top of the window for the application.
 *
 * @author Komron Valijonov
 */
public class HeaderPanel extends JPanel {
    public static final String MESSAGE_WELCOME = "This means war!";
    public static final String MESSAGE_TURN_OVER = "%s won that battle!";
    public static final String MESSAGE_GAME_OVER = "Game Over! %s is the winner!";
    private final WarModel model;
    private final JLabel message = new JLabel(MESSAGE_WELCOME, SwingConstants.CENTER);
    private final NameTag nameTag1 = new NameTag(), nameTag2 = new NameTag();

    /**
     * Creates a new header panel.
     *
     * @param model to use as a reference
     */
    public HeaderPanel(WarModel model) {
        super(new BorderLayout());
        this.model = model;

        add(nameTag1, BorderLayout.WEST);
        add(message, BorderLayout.CENTER);
        add(nameTag2, BorderLayout.EAST);
    }

    /**
     * Sets the message displayed in the header.
     *
     * @param message to display
     * @param args    to format with
     */
    public void setMessage(String message, Object... args) {
        this.message.setText(String.format(message, args));
    }

    /**
     * Updates the name tags to reflect the player's actual names.
     */
    public void updateNameTags() {
        nameTag1.setPlayer(model.getPlayer(true));
        nameTag2.setPlayer(model.getPlayer(false));
    }
}
