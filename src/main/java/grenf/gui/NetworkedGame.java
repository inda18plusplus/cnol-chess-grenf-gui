package grenf.gui;

import chess.Board;
import chess.piece.Piece;
import chess.piece.Position;
import grenf.gui.graphics.ImageLoader;
import grenf.gui.graphics.Sprite;
import grenf.network.Connection;
import grenf.network.PositionParser;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.json.JSONObject;

public class NetworkedGame extends Game {

  private Connection connection;
  private Position moveTarget;
  private char promotionChoice;
  private Piece.Color myTeam;

  public NetworkedGame(
      GraphicsContext gc, Board.Layout layout, Connection connection, Piece.Color myTeam) {
    super(gc, layout);
    this.myTeam = myTeam;
    this.connection = connection;
    promotionChoice = 'Q';
  }

  public void handleClick(Point2D clickPos) {
    Position position = spriteBoard.posToIndex(clickPos);
    if (spriteBoard.indexInBounds(position)) {
      System.out.println("Clicked: " + position.getColumn() + " " + position.getRow());
      if (currentlyPromoting) {
        handlePromotionClick(position);
      } else if (selectedSprite == null) {
        handleClickNotSelected(position);
      } else {
        handleClickSelected(position);
      }
    }
  }

  protected void handlePromotionClick(Position position) {
    Sprite target = spriteBoard.getSprite(position);
    selectedSprite = null;
    potentialMoveSprites.clear();
    if (target != null) {
      if (board.getCurrentColor() == Piece.Color.WHITE) {
        promotionChoice =
            PROMOTION_PIECES_WHITE.charAt(position.getRow() * 9 + position.getColumn());
      } else {
        promotionChoice =
            PROMOTION_PIECES_BLACK.charAt(position.getRow() * 9 + position.getColumn());
      }
      board.promoteTo(charToPromotionOption(promotionChoice));
      promotionChoice = Character.toUpperCase(promotionChoice);
      currentlyPromoting = false;
      refreshSpriteBoard();

      sendMove(moveTarget);
      receiveMove();
    }
  }

  protected void handleClickSelected(Position position) {
    if (myTeam != board.getCurrentColor()) return;
    potentialMoveSprites.clear();
    selectedSprite = null;
    if (moveStart.equals(position)) {
      return;
    }

    if (legalMoves != null && legalMoves.contains(position)) {
      makeMove(position);
      if (currentlyPromoting) {
        moveTarget = position;
      } else {
        sendMove(position);
        renderer.handle(0);
        receiveMove();
      }
    } else {
      selectedSprite = null;
      handleClickNotSelected(position);
    }
  }

  protected void handleClickNotSelected(Position position) {
    if (myTeam != board.getCurrentColor()) return;
    Sprite target = spriteBoard.getSprite(position);
    if (target != null && target.getColor() == myTeam) {
      moveStart = position;
      selectedSprite = new Sprite(target.getPosition(), ImageLoader.getImageId('O'), null);

      legalMoves = board.legalDestinations(position);
      for (Position potentialPos : legalMoves) {
        potentialMoveSprites.add(
            new Sprite(spriteBoard.indexToPos(potentialPos), ImageLoader.getImageId('G'), null));
      }
    }
  }

  protected void makeMove(Position moveTarget) {
    Board.MoveResult result = board.tryMove(moveStart, moveTarget);
    messageLog.addMessage(
        moveToString(moveStart, moveTarget),
        board.getCurrentColor() == Piece.Color.WHITE ? Color.BLACK : Color.WHITE);

    if (result == Board.MoveResult.PROMOTION_REQUIRED) {
      promptPromotion();
      return;
    }
    refreshSpriteBoard();
    checkGameState();
  }

  public void receiveMove() {
    JSONObject enemyMove = connection.recvJSON();
    Position enemyStartPosition = PositionParser.toPosition(enemyMove.getString("from"));
    Position enemyTargetPosition = PositionParser.toPosition(enemyMove.getString("to"));

    Board.MoveResult result = board.tryMove(enemyStartPosition, enemyTargetPosition);
    if (result == Board.MoveResult.INVALID_MOVE) {
      messageLog.addMessage("SYNC ERROR", Color.RED);
      messageLog.addMessage("Their move was invalid", Color.RED);
      connection.sendJSON(Connection.INVALID_MOVE_RESPONSE);
      renderer.stop();
      return;
    }

    messageLog.addMessage(
        moveToString(enemyStartPosition, enemyTargetPosition),
        board.getCurrentColor() == Piece.Color.WHITE ? Color.BLACK : Color.WHITE);

    if (result == Board.MoveResult.PROMOTION_REQUIRED) {
      String enemyPromotionChoice = enemyMove.getString("promotion");
      board.promoteTo(charToPromotionOption(enemyPromotionChoice.charAt(0)));
    }

    connection.sendJSON(Connection.OK_MOVE_RESPONSE);
    refreshSpriteBoard();
    checkGameState();
  }

  private void sendMove(Position targetPosition) {
    connection.sendMove(moveStart, targetPosition, promotionChoice);
    JSONObject response = connection.recvJSON();
    if (response.getString("response").equals("invalid")) {
      messageLog.addMessage("SYNC ERROR", Color.RED);
      messageLog.addMessage("Your move is invalid", Color.RED);
      renderer.stop();
      return;
    }
  }
}
