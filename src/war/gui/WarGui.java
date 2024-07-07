package war.gui;

import war.*;

import javax.swing.*;
import java.awt.*;

/**
 * <strong>Final Project</strong><br>
 * GUI Implementation of "War" card game.
 *
 * @author Komron Valijonov
 */
public class WarGui extends JPanel implements IWarView, Runnable {
    public static final String FRAME_TITLE = "War Card Game";  // window title
    public static final Dimension FRAME_SIZE = new Dimension(600, 300); // width, height in pixels
    private WarModel model = new WarModel(this);  // model that controls the game
    private final StorageModel storage = new StorageModel(this);  // storage for saving and loading games
    private final GameOptionsDialog options = new GameOptionsDialog(this, storage);  // dialog for starting a new game or loading a saved game
    private final HeaderPanel header = new HeaderPanel(model);  // header panel
    private final TablePanel table = new TablePanel();  // playing table
    private final ControlPanel controls = new ControlPanel(model, this);  // control panel (the section with buttons)
    private WarSimulator sim;  // simulator for auto-play mode
    private Card mobilized1, mobilized2;

    /**
     * Creates and initializes the game.
     */
    public WarGui() {
        // set the layout
        super(new BorderLayout());

        // add the header on top
        add(header, BorderLayout.NORTH);

        // add playing table in the center
        add(table, BorderLayout.CENTER);

        // add control panel at the bottom
        add(controls, BorderLayout.SOUTH);
    }

    /**
     * Main method to start the game.
     * It creates a new instance of the GUI and runs it on the Event Dispatch Thread.
     * The reason for invokeLater is to ensure that the GUI is created and updated by the EDT.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new WarGui());
    }

    /**
     * Starts a new game in auto-play mode.
     * It creates a new thread and starts the simulator.
     * The separate thread is used to prevent the GUI from freezing.
     * Because we would need to be able to interact with the GUI while the game is running.
     */
    public void startSimulator() {
        new Thread(sim = new WarSimulator(model, this)).start();
    }

    /**
     * Stops the auto-play mode.
     */
    public void stopSimulator() {
        // cancel simulator and set to null
        sim.cancel();
        sim = null;
        // reset auto-play button text
        controls.getAutoPlayButton().setText("Auto-play");
    }

    /**
     * Returns currently running simulator.
     *
     * @return simulator
     */
    public WarSimulator getActiveSimulator() {
        return sim;
    }

    /**
     * Returns the header panel.
     *
     * @return header panel
     */
    public HeaderPanel getHeader() {
        return header;
    }

    /**
     * Returns the table panel.
     *
     * @return table panel
     */
    public TablePanel getTable() {
        return table;
    }

    /**
     * Returns control panel.
     *
     * @return control panel
     */
    public ControlPanel getControls() {
        return controls;
    }


    /**
     * Runs the game.
     * It creates a new window and displays the game.
     */
    @Override
    public void run() {
        // create window
        JFrame frame = new JFrame(FRAME_TITLE);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // add content to window (this panel)
        frame.setContentPane(this);

        // display window
        frame.setSize(FRAME_SIZE);
        frame.setVisible(true);

//        // automatically start a new game
//        model.newGame();

        // show a popup displaying the options to start a new game or load a saved game
        new GameOptionsDialog(this, storage).setVisible(true);
    }

    @Override
    public void onGameStart() {
        // enable draw button and update stats
        controls.getActionButton().setEnabled(true);
        controls.updateStats();

        // clear board
        table.reset();
        controls.reset();

        // set welcome message
        header.setMessage(HeaderPanel.MESSAGE_WELCOME);
        header.updateNameTags();
    }

    @Override
    public void onGameSave() {

    }

    @Override
    public void onGameSaveError() {

    }

    @Override
    public void onGameLoad() {

    }

    @Override
    public void onGameLoadError() {

    }

    @Override
    public void onTurnStart(Card card1, Card card2) {
        // clear "war panel" if displayed
        if (!model.isWar())
            table.reset();

        // show drawn cards
        table.showCards(card1, card2);

        // remove deck image if player is on last card
        table.getPlayerPanel(true).getDeck().setVisible(model.getPlayer(true).hasCard());
        table.getPlayerPanel(false).getDeck().setVisible(model.getPlayer(false).hasCard());
    }

    @Override
    public void onWarStart() {
        // set title
        header.setMessage(FRAME_TITLE);
        // set action to "mobilize"
        controls.setAction("Mobilize", model::prepareForWar);
        // update stats
        controls.updateStats();
    }

    @Override
    public void onWarPreparation(Card card1, Card card2) {
        // save cards for later
        mobilized1 = card1;
        mobilized2 = card2;

        // reset table
        table.reset();

        // show card back in "war" pane
        table.getPlayerPanel(true).getWarPanel().showBack();
        table.getPlayerPanel(false).getWarPanel().showBack();

        // disable mobilize button and enable draw button
        controls.setAction("Draw", model::nextTurn);
        controls.updateStats();
    }

    @Override
    public void onWarEnd() {
        // reveal hidden cards next time action button is clicked
        controls.setAction("Reveal", () -> {
            table.getPlayerPanel(true).getWarPanel().showCard(mobilized1);
            table.getPlayerPanel(false).getWarPanel().showCard(mobilized2);
            controls.setAction("Draw", model::nextTurn);
        });
    }

    @Override
    public void onTurnEnd(Card card1, Card card2, Player winner) {
        // set winner message
        header.setMessage(HeaderPanel.MESSAGE_TURN_OVER, winner.getName());
        controls.updateStats();
    }

    @Override
    public void onGameOver(Player winner) {
        header.setMessage(HeaderPanel.MESSAGE_GAME_OVER, winner.getName());
        controls.getActionButton().setEnabled(false);
        controls.updateStats();
    }
}
