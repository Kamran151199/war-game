package war;

import java.io.*;
import java.util.logging.Logger;

/**
 * <strong>Final Project</strong><br>
 * Represents the storage of the game. Enables saving and loading of games from files.
 *
 * @author Komron Valijonov
 */
public class StorageModel {
    private static final Logger logger = Logger.getLogger(StorageModel.class.getName());
    private static final String FILE_NAME = "game.dat";
    private static final long serialVersionUID = 1L;
    private final IWarView view;

    /**
     * Creates a new storage.
     *
     * @param view to notify as game progresses (MVC)
     */
    public StorageModel(IWarView view) {
        this.view = view;
    }

    /**
     * Saves the current game to a file.
     */
    public void save(WarModel model) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(model);
            view.onGameSave();
        } catch (IOException e) {
            logger.severe(e.getMessage());
            view.onGameSaveError();
        }
    }

    /**
     * Loads a game from a file.
     *
     * @return the loaded game
     * @throws RuntimeException if the game cannot be loaded
     */
    public WarModel load() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            WarModel war = (WarModel) in.readObject();
            view.onGameLoad();
            return war;
        } catch (IOException | ClassNotFoundException e) {
            logger.severe(e.getMessage());
            view.onGameLoadError();
        }
        return null;
    }
}