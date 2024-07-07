package war;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * <strong>Final Project</strong><br>
 * Represents the storage of the game. Enables saving and loading of games from files.
 *
 * @author Komron Valijonov
 */
public class StorageModel {
    private static final Logger logger = Logger.getLogger(StorageModel.class.getName());
    private static final String FOLDER_NAME = "./war-saves";
    private static final String POOL_CARDS_FILE_NAME = FOLDER_NAME + File.pathSeparator + "pool-cards.dat";
    private static final String GAME_STATE_FILE_NAME = FOLDER_NAME + File.pathSeparator + "game-state.dat";
    private static final String PLAYER1_FILE_NAME = FOLDER_NAME + File.pathSeparator + "player1.dat";
    private static final String PLAYER2_FILE_NAME = FOLDER_NAME + File.pathSeparator + "player2.dat";
    private static final String MOBILISED_CARD1_FILE_NAME = FOLDER_NAME + File.pathSeparator + "mobilised-card1.dat";
    private static final String MOBILISED_CARD2_FILE_NAME = FOLDER_NAME + File.pathSeparator + "mobilised-card2.dat";
    private static final String CURRENTLY_DRAWN_CARD1_FILE_NAME = FOLDER_NAME + File.pathSeparator + "currently-drawn-card1.dat";
    private static final String CURRENTLY_DRAWN_CARD2_FILE_NAME = FOLDER_NAME + File.pathSeparator + "currently-drawn-card2.dat";

    private final IWarView view;

    public StorageModel(IWarView view) {
        this.view = view;
    }

    /**
     * Saves the current game to a file.
     */
    public void save(WarModel model) {
        try {
            File folder = new File(FOLDER_NAME);
            if (!folder.exists()) {
                folder.mkdir();
            }
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(POOL_CARDS_FILE_NAME))) {
                out.writeObject(model.getPool());
            }
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(PLAYER1_FILE_NAME))) {
                out.writeObject(model.getPlayer(true));
            }
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(PLAYER2_FILE_NAME))) {
                out.writeObject(model.getPlayer(false));
            }
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(GAME_STATE_FILE_NAME))) {
                out.writeObject(model);
            }
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(MOBILISED_CARD1_FILE_NAME))) {
                out.writeObject(view.getMobilisedCard(true));
            }
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(MOBILISED_CARD2_FILE_NAME))) {
                out.writeObject(view.getMobilisedCard(false));
            }
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(CURRENTLY_DRAWN_CARD1_FILE_NAME))) {
                out.writeObject(model.getCurrentlyDrawnCard(true));
            }
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(CURRENTLY_DRAWN_CARD2_FILE_NAME))) {
                out.writeObject(model.getCurrentlyDrawnCard(false));
            }
            view.onGameSave();
        } catch (IOException e) {
            logger.severe(e.getMessage());
            view.onGameSaveError();
        }

    }

    public List<Card> loadPool() {
        List<Card> pool = new ArrayList<Card>();
        try {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(POOL_CARDS_FILE_NAME))) {
                pool = (ArrayList<Card>) in.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            logger.severe(e.getMessage());
            throw new RuntimeException("Failed to load game");
        }
        return pool;
    }

    public Player loadPlayer1() {
        Player player1 = new Player("Player 1", new LinkedList<Card>());
        try {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(PLAYER1_FILE_NAME))) {
                player1 = (Player) in.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            logger.severe(e.getMessage());
            throw new RuntimeException("Failed to load game");
        }
        return player1;
    }

    public Player loadPlayer2() {
        Player player2 = new Player("Player 2", new LinkedList<Card>());
        try {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(PLAYER2_FILE_NAME))) {
                player2 = (Player) in.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            logger.severe(e.getMessage());
            throw new RuntimeException("Failed to load game");
        }
        return player2;
    }

    public WarModel loadGameState() {
        WarModel model = new WarModel(view, this);
        try {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(GAME_STATE_FILE_NAME))) {
                WarModel model1 = (WarModel) in.readObject();
                boolean gameOver = model1.isGameOver();
                boolean war = model1.isWar();

                model.setIsGameOver(gameOver);
                model.setIsWar(war);
            }
        } catch (IOException | ClassNotFoundException e) {
            logger.severe(e.getMessage());
            throw new RuntimeException("Failed to load game");
        }
        return model;
    }

    public Card loadMobilisedCard1() {
        try {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(MOBILISED_CARD1_FILE_NAME))) {
                return (Card) in.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            logger.severe(e.getMessage());
            throw new RuntimeException("Failed to load game");
        }
    }

    public Card loadMobilisedCard2() {
        try {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(MOBILISED_CARD2_FILE_NAME))) {
                return (Card) in.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            logger.severe(e.getMessage());
            throw new RuntimeException("Failed to load game");
        }
    }

    public Card loadCurrentlyDrawnCard1() {
        try {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(CURRENTLY_DRAWN_CARD1_FILE_NAME))) {
                return (Card) in.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            logger.severe(e.getMessage());
            throw new RuntimeException("Failed to load game");
        }
    }

    public Card loadCurrentlyDrawnCard2() {
        try {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(CURRENTLY_DRAWN_CARD2_FILE_NAME))) {
                return (Card) in.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            logger.severe(e.getMessage());
            throw new RuntimeException("Failed to load game");
        }
    }
}