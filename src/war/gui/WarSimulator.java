package war.gui;

import war.WarModel;

import javax.swing.*;

/**
 * <strong>Final Project</strong><br>
 * Represents a runnable that can simulate a game.
 *
 * @author Komron Valijonov
 */
public class WarSimulator implements Runnable {
    private final WarModel model;
    private final WarGui gui;
    private boolean cancel = false;

    /**
     * Creates a new simulator.
     *
     * @param model to use
     * @param gui   to use
     */
    public WarSimulator(WarModel model, WarGui gui) {
        this.model = model;
        this.gui = gui;
    }

    /**
     * Stops the simulator.
     */
    public void cancel() {
        cancel = true;
    }

    @Override
    public void run() {
        // start a game
        model.newGame();
        JButton newGameBtn = gui.getControls().getNewGameButton();
        newGameBtn.setEnabled(false);
        // press buttons until the game is over or cancelled
        while (!model.isGameOver() && !cancel)
            gui.getControls().getActionButton().doClick();
        newGameBtn.setEnabled(true);
    }
}
