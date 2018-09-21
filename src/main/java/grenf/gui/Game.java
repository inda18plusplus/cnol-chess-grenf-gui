package grenf.gui;

import chess.Board;
import chess.piece.Piece;
import chess.piece.Position;
import grenf.gui.graphics.ImageLoader;
import grenf.gui.graphics.Sprite;
import grenf.gui.graphics.SpriteBoard;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Set;

public class Game extends AnimationTimer {

  public Board board;
  private SpriteBoard spriteBoard;
  private MessageLog messageLog;
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
    messageLog = new MessageLog(new Point2D(SpriteBoard.BOARD_WIDTH, 0), 38);
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
      if (!potentialMoveSprites.isEmpty()) {
        for (int i = 0; i < potentialMoveSprites.size(); i++) {
          potentialMoveSprites.get(i).render(gc);
        }
      }
      selectedSprite.render(gc);
    }
    messageLog.render(gc, board.getCurrentColor());
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
    Board.MoveResult result = board.tryMove(moveStart, moveTarget);
    if (result == Board.MoveResult.OK) {
      messageLog.addMessage(moveToString(moveStart, moveTarget), board.getCurrentColor() == Piece.Color.WHITE ? Color.BLACK : Color.WHITE);
      Board.CheckType checkType = board.getCheck(board.getCurrentColor());
      if (checkType == Board.CheckType.CHECKMATE || checkType == Board.CheckType.STALEMATE) {
        endGame(checkType);
      } else if (checkType == Board.CheckType.CHECK) {
        warnForCheck();
      }

      refreshSpriteBoard();
    } else if (result == Board.MoveResult.PROMOTION_REQUIRED) {
      promptPromotion();
    }
  }

  private void warnForCheck() {
    messageLog.addMessage("checked", Color.RED);
    if (board.getCurrentColor() == Piece.Color.WHITE) {
      messageLog.addMessage("White is", Color.RED);
    } else {
      messageLog.addMessage("Black is", Color.RED);
    }
  }

  private void endGame(Board.CheckType checkType) {
    messageLog.clearMessages();
    if (checkType == Board.CheckType.STALEMATE) {
      messageLog.addMessage("STALEMATE!", Color.RED);
    } else if (board.getCurrentColor() == Piece.Color.WHITE) {
      messageLog.addMessage("Black won!", Color.RED);
    } else {
      messageLog.addMessage("White won!", Color.RED);
    }
    stop();
    handle(0);
  }

  private void promptPromotion() {
    messageLog.clearMessages();
    messageLog.addMessage("REQUIRED!", Color.RED);
    messageLog.addMessage("PROMOTION", Color.RED);
    messageLog.addMessage("PANIC!", Color.RED);
    stop();
    handle(0);
  }

  private String moveToString(Position start, Position target) {
    return "" + intToLetter(start.getColumn()) + start.getRow() + " -> " + intToLetter(target.getColumn()) + target.getRow();
  }

  private char intToLetter(int n) {
    return (char)(n + (int)('A'));
  }
}
