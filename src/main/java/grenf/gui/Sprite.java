package grenf.gui;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite {

  private int imageId;
  private Point2D position;
  private int width;
  private int height;

  public Sprite(Point2D position, int width, int height, int imageId) {
    this.imageId = imageId;
    this.position = position;
    this.width = width;
    this.height = height;
  }

  private Image getImage() {
    return ImageLoader.getImage(imageId);
  }

  private double getX() {
    return position.getX();
  }

  private double getY() {
    return position.getY();
  }

  public void render(GraphicsContext gc) {
    gc.drawImage(getImage(), getX(), getY());
  }

  public boolean contains(Point2D point) {
    return (new Rectangle2D(position.getX(), position.getY(), width, height)).contains(point);
  }
}
