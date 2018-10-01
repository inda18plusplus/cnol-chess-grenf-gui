package grenf.gui;

import chess.Board;
import chess.piece.Piece;
import chess.piece.Position;
import grenf.network.Connection;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class NetworkedGame extends Game {

  private Connection connection;
  private Piece.Color myTeam;

  public NetworkedGame(GraphicsContext gc, Board.Layout layout, Connection connection, Piece.Color myTeam) {
    super(gc, layout);
    this.connection = connection;
    this.myTeam = myTeam;
  }


  protected void handleClickSelected(Position position) {
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

  protected void makeMove(Position moveTarget) {
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

}
