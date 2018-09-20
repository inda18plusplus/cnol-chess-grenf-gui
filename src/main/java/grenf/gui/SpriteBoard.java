package grenf.gui;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class SpriteBoard {

  private static final int BOARD_START_OFFSET_X = 39;
  private static final int BOARD_START_OFFSET_Y = 13;
  private static final int PIECE_SPRITE_DIMENSION = 67;

  private Sprite board;
  private Sprite[][] pieces;
  private int width;
  private int height;

  public SpriteBoard(String board) {
    String[] lines = board.split("\n");
    width = lines[0].length();
    height = lines.length;
    pieces = new Sprite[height][width];

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (lines[y].charAt(x) != '.') {
          pieces[y][x] = new Sprite(indexToPos(x, y), PIECE_SPRITE_DIMENSION, PIECE_SPRITE_DIMENSION,
              ImageLoader.getImageId(lines[y].charAt(x)));
        } else {
          pieces[y][x] = null;
        }
      }
    }

    this.board = new Sprite(new Point2D(0, 0), Main.WIDTH, Main.HEIGHT, ImageLoader.getImageId('X'));
  }

  private Point2D indexToPos(int x, int y) {
    return new Point2D((double) (BOARD_START_OFFSET_X + x * PIECE_SPRITE_DIMENSION),
        (double) (BOARD_START_OFFSET_Y + y * PIECE_SPRITE_DIMENSION));
  }

  private int[] posToIndex(Point2D position) {
    int x = (int) Math.floor((position.getX() - (double) BOARD_START_OFFSET_X) / (double) PIECE_SPRITE_DIMENSION);
    int y = (int) Math.floor((position.getY() - (double) BOARD_START_OFFSET_Y) / (double) PIECE_SPRITE_DIMENSION);
    return new int[]{x, y};
  }

  public void removeSprite(int x, int y) {
    pieces[y][x] = null;
  }

  public void addSprite(Sprite sprite, int x, int y) {
    pieces[y][x] = sprite;
  }

  public void render(GraphicsContext gc) {
    board.render(gc);

    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        if (pieces[i][j] != null) {
          pieces[i][j].render(gc);
        }
      }
    }
  }
}
