package war.gui;

import war.Player;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Final Project
 * Represents a name tag for a player.
 *
 * @author Komron Valijonov
 */
public class NameTag extends JPanel {
    private final JLabel label = new JLabel();
    private final JTextField textField = new JTextField();
    private Player player;
    private boolean editing = false;

    /**
     * Creates a new name tag.
     */
    public NameTag() {
        super();

        // add label and add click listener
        add(label);
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                // edit tag when double clicked
                if (e.getClickCount() == 2)
                    edit();
            }
        });

        // add key listener to text field
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                // save changes when enter is pressed
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    save();
            }
        });
    }

    /**
     * Sets the player this name tag is for.
     *
     * @param player name tag is for
     */
    public void setPlayer(Player player) {
        this.player = player;
        // change back to label if editing
        save();
        // set label text to player name
        label.setText(player.getName());
    }

    /**
     * Starts editing the name tag if the label is present.
     */
    public void edit() {
        // check if already editing
        if (editing)
            return;

        // remove label
        remove(label);

        // add text field
        textField.setText(label.getText());
        add(textField);
        textField.requestFocus();
        textField.selectAll();

        revalidate();
        editing = true;
    }

    /**
     * Saves any changes made to the name tag if editing.
     */
    public void save() {
        // make sure that we're actually editing
        if (!editing)
            return;

        // remove text field
        remove(textField);

        // add label
        label.setText(textField.getText());
        add(label);

        // set player name in model
        player.setName(label.getText());
        revalidate();
        editing = false;
    }
}
