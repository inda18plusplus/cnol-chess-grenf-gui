package grenf.gui;

import chess.Board;
import chess.piece.Position;
import grenf.gui.graphics.ImageLoader;
import grenf.gui.graphics.Sprite;
import grenf.gui.graphics.SpriteBoard;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Set;

public class Game extends AnimationTimer {

  public Board board;
  private SpriteBoard spriteBoard;
  private GraphicsContext gc;

  private ArrayList<Sprite> potentialMoveSprites;
  private Sprite selectedSprite;
  private Position moveStart;
  private Set<Position> legalMoves;

  public Game(GraphicsContext gc, Board.Layout layout) {
    this.gc = gc;
    board = new Board(layout);
    spriteBoard = new SpriteBoard();
    refreshSpriteBoard();
    potentialMoveSprites = new ArrayList<>();
    selectedSprite = null;
    legalMoves = null;
    moveStart = null;
  }

  private void refreshSpriteBoard() {
    spriteBoard.setUpPieces(board.toString());
  }

  @Override
  public void handle(long now) {
    spriteBoard.render(gc);
    if (selectedSprite != null) {
      selectedSprite.render(gc);
      if (!potentialMoveSprites.isEmpty()) {
        for (int i = 0; i < potentialMoveSprites.size(); i++) {
          potentialMoveSprites.get(i).render(gc);
        }
      }
    }
  }

  public void handleClick(Point2D clickPos) {
    Position position = spriteBoard.posToIndex(clickPos);
    if (spriteBoard.indexInBounds(position)) {
      System.out.println("Clicked: " + position.getColumn() + " " + position.getRow());
      if (selectedSprite == null) {
        handleClickNotSelected(position);
      } else {
        handleClickSelected(position);
      }
    }
  }

  private void handleClickNotSelected(Position position) {
    Sprite target = spriteBoard.getSprite(position);
    if (target != null) {
      moveStart = position;
      selectedSprite = new Sprite(target.getPosition(), ImageLoader.getImageId('O'));

      legalMoves = board.legalDestinations(position);
      for (Position potentialPos : legalMoves) {
        potentialMoveSprites.add(new Sprite(spriteBoard.indexToPos(potentialPos), ImageLoader.getImageId('G')));
      }
    }
  }

  private void handleClickSelected(Position position) {
    potentialMoveSprites.clear();
    selectedSprite = null;
    if (moveStart.equals(position)) {
      return;
    }

    if (legalMoves != null && legalMoves.contains(position)) {
      makeMove(position);
    } else {
      selectedSprite = null;
      handleClickNotSelected(position);
    }
  }

  private void makeMove(Position moveTarget) {
    //Board.MoveResult result = board.tryMove(moveStart, moveTarget);

  }
}
