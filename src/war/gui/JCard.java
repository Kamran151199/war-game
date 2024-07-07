package war.gui;

import war.Card;

import javax.swing.*;

/**
 * <strong>Final Project</strong><br>
 * Represents a playing card that can be displayed on a GUI.
 *
 * @author Komron Valijonov
 */
public class JCard extends Card {
    public static final String DEFAULT_IMAGE_DIRECTORY = "cards";
    public static final ImageIcon DEFAULT_BACK_IMAGE = new ImageIcon(DEFAULT_IMAGE_DIRECTORY + "/b1fv.png");

    private final JLabel label = new JLabel();
    private final ImageIcon frontImage, backImage;

    /**
     * Constructs a new card.
     *
     * @param rank       of card (between 2 and 14 (inclusive))
     * @param suit       of card
     * @param frontImage image to display when card is face up
     * @param backImage  image to display when card is face down
     */
    public JCard(int rank, Suit suit, ImageIcon frontImage, ImageIcon backImage) {
        super(rank, suit);
        this.frontImage = frontImage;
        this.backImage = backImage;
        label.setIcon(frontImage);
    }

    /**
     * Constructs a new card.
     *
     * @param rank       of card (between 2 and 14 (inclusive))
     * @param suit       of card
     * @param frontImage image to display when card is face up
     * @param backImage  image to display when card is face down
     */
    public JCard(int rank, Suit suit, String frontImage, String backImage) {
        this(rank, suit, new ImageIcon(frontImage), new ImageIcon(backImage));
    }

    /**
     * Constructs a new card. This constructor uses the default back image.
     *
     * @param rank       of card (between 2 and 14 (inclusive))
     * @param suit       of card
     * @param frontImage image to display when card is face up
     */
    public JCard(int rank, Suit suit, ImageIcon frontImage) {
        this(rank, suit, frontImage, DEFAULT_BACK_IMAGE);
    }

    /**
     * Constructs a new card. This constructor uses the default back image.
     *
     * @param rank       of card (between 2 and 14 (inclusive))
     * @param suit       of card
     * @param frontImage image to display when card is face up
     */
    public JCard(int rank, Suit suit, String frontImage) {
        this(rank, suit, new ImageIcon(frontImage));
    }

    /**
     * Constructs a new card. This constructor assumes the front and back file names from the suit and rank. A card with
     * a rank 2 and {@link Suit#HEARTS} would translate to h2.png
     *
     * @param rank of card (between 2 and 14 (inclusive))
     * @param suit of card
     */
    public JCard(int rank, Suit suit) {
        this(rank, suit, defaultImageName(rank, suit));
    }

    /**
     * Creates a JCard from a regular card.
     *
     * @param card to use to construct a JCard
     */
    public JCard(Card card) {
        this(card.getRank(), card.getSuit());
        setFaceUp(card.isFaceUp());
    }

    private static String defaultImageName(int rank, Suit suit) {
        // infer the filename from rank and suit
        return DEFAULT_IMAGE_DIRECTORY + "/" + new Card(rank, suit).toString() + ".png";
    }

    /**
     * Returns the {@link JLabel} to add to the screen.
     *
     * @return label to attach
     */
    public JLabel getComponent() {
        return label;
    }

    /**
     * Returns this card's image that is displayed when the card is face up.
     *
     * @return front image
     */
    public ImageIcon getFrontImage() {
        return frontImage;
    }

    /**
     * Returns this card's image that is displayed when the card is face down.
     *
     * @return back image
     */
    public ImageIcon getBackImage() {
        return backImage;
    }

    private void flipImage() {
        // change displayed image
        if (faceUp)
            label.setIcon(backImage);
        else
            label.setIcon(frontImage);
    }

    @Override
    public void setFaceUp(boolean faceUp) {
        if (faceUp != this.faceUp)
            flipImage();
        super.setFaceUp(faceUp);
    }
}
