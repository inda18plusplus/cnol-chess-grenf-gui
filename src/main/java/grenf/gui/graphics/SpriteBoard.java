package grenf.gui.graphics;

import chess.piece.Piece;
import chess.piece.Position;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class SpriteBoard {

  public static final int BOARD_WIDTH = 612;
  private static final int BOARD_START_OFFSET_X = 56;
  private static final int BOARD_START_OFFSET_Y = 21;
  private static final int PIECE_SPRITE_DIMENSION = 67;

  private Sprite board;
  private Sprite[][] pieces;
  private int width;
  private int height;

  public SpriteBoard() {
    this.board = new Sprite(new Point2D(0, 0), ImageLoader.getImageId('M'), Piece.Color.BLACK);
  }

  public void setUpPieces(String board) {
    String[] lines = board.split("\n");
    width = lines[0].length();
    height = lines.length;
    pieces = new Sprite[height][width];

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (lines[y].charAt(x) != '.') {
          Position temp = new Position(x, y);
          Piece.Color color = Character.isUpperCase(lines[y].charAt(x)) ? Piece.Color.WHITE : Piece.Color.BLACK;
          pieces[y][x] = new Sprite(indexToPos(temp), ImageLoader.getImageId(lines[y].charAt(x)), color);
        } else {
          pieces[y][x] = null;
        }
      }
    }
  }

  public Point2D indexToPos(Position boardIndex) {
    return new Point2D((double) (BOARD_START_OFFSET_X + boardIndex.getColumn() * PIECE_SPRITE_DIMENSION),
        (double) (BOARD_START_OFFSET_Y + boardIndex.getRow() * PIECE_SPRITE_DIMENSION));
  }

  public Position posToIndex(Point2D position) {
    int x = (int) Math.floor((position.getX() - (double) BOARD_START_OFFSET_X) / (double) PIECE_SPRITE_DIMENSION);
    int y = (int) Math.floor((position.getY() - (double) BOARD_START_OFFSET_Y) / (double) PIECE_SPRITE_DIMENSION);
    return new Position(x, y);
  }

  public boolean indexInBounds(Position boardIndex) {
    return (boardIndex.getColumn() >= 0 && boardIndex.getColumn() < width)
        && (boardIndex.getRow() >= 0 && boardIndex.getRow() < height);
  }

  public Sprite getSprite(Position position) {
    if (indexInBounds(position)) {
      return pieces[position.getRow()][position.getColumn()];
    }
    return null;
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
