package chess.piece.move;

import chess.piece.Piece;
import chess.piece.Position;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

public abstract class Move {
  CaptureRule captureRule = CaptureRule.CAN_CAPTURE;

  /**
   * Perform this chess.piece.move.
   * @param oldPosition The old position of the chess.piece to chess.piece.move
   * @param newPosition The position to chess.piece.move the chess.piece to
   * @param getPiece Returns the chess.piece at a certain position
   * @param setPiece Sets the chess.piece at a certain position
   */
  public abstract void perform(Position oldPosition, Position newPosition,
      Function<Position, Piece> getPiece,
      BiConsumer<Piece, Position> setPiece);

  /**
   * Get all possible destinations of a chess.piece.move.
   *
   * @param origin      Where to chess.piece.move from
   * @param boundWidth  The width of the board
   * @param boundHeight The height of the board
   * @param getPiece    The chess.piece at a certain position
   * @return A list of positions this chess.piece.move allows a chess.piece to chess.piece.move to
   */
  public abstract Set<Position> getDestinations(Position origin,
      int boundWidth, int boundHeight,
      Function<Position, Piece> getPiece);

  public enum CaptureRule {
    MUST_CAPTURE,
    CAN_CAPTURE,
    NO_CAPTURE
  }
}
