package grenf.gui.graphics;

import chess.piece.Piece;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite {

  private int imageId;
  private Point2D position;
  private Piece.Color color;

  public Sprite(Point2D position, int imageId, Piece.Color color) {
    this.imageId = imageId;
    this.position = position;
    this.color = color;
  }

  public Piece.Color getColor() {
    return color;
  }

  private Image getImage() {
    return ImageLoader.getImage(imageId);
  }

  public Point2D getPosition() {
    return position;
  }

  public void render(GraphicsContext gc) {
    gc.drawImage(getImage(), position.getX(), position.getY());
  }
}
