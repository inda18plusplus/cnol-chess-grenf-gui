package grenf.gui;

import chess.Board;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;

public class Game extends AnimationTimer {

  public Board board;
  private SpriteBoard spriteBoard;
  private GraphicsContext gc;

  public Game(GraphicsContext gc) {
    this.gc = gc;
    board = new Board(Board.Layout.CLASSIC);
    spriteBoard = new SpriteBoard(board.toString());
  }

  @Override
  public void handle(long now) {

    spriteBoard.render(gc);
  }
}
