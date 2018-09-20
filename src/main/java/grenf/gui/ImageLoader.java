package grenf.gui;

import javafx.scene.image.Image;

public class ImageLoader {

  private static Image[] images;
  private static final String[] IMAGE_FILENAMES = new String[]{"board.png", "greenBorder.png", "whiteRook.png", "whiteKnight.png", "whiteBishop.png", "whiteKing.png", "whiteQueen.png", "whitePawn.png", "blackRook.png", "blackKnight.png", "blackBishop.png", "blackKing.png", "blackQueen.png", "blackPawn.png"};
  private static final String imageChars = "XYrnbkqpRNBKQP";

  public static void loadImages() {
    images = new Image[IMAGE_FILENAMES.length];
    for (int i = 0; i < IMAGE_FILENAMES.length; i++) {
      images[i] = new Image(IMAGE_FILENAMES[i]);
    }
  }

  public static int getImageId(char imageChar) {
    return imageChars.indexOf(imageChar);
  }

  public static Image getImage(int imageId) {
    if (imageId < 0 || imageId >= images.length) {
      return null;
    }
    return images[imageId];
  }

  public static Image getImage(char imageChar) {
    return getImage(getImageId(imageChar));
  }

}
