package piece.move;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import piece.Piece;
import piece.Position;

public abstract class Move {
  CaptureRule captureRule = CaptureRule.CAN_CAPTURE;

  /**
   * Perform this piece.move.
   * @param oldPosition The old position of the piece to piece.move
   * @param newPosition The position to piece.move the piece to
   * @param getPiece Returns the piece at a certain position
   * @param setPiece Sets the piece at a certain position
   */
  public abstract void perform(Position oldPosition, Position newPosition,
      Function<Position, Piece> getPiece,
      BiConsumer<Piece, Position> setPiece);

  /**
   * Get all possible destinations of a piece.move.
   *
   * @param origin      Where to piece.move from
   * @param boundWidth  The width of the board
   * @param boundHeight The height of the board
   * @param getPiece    The piece at a certain position
   * @return A list of positions this piece.move allows a piece to piece.move to
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
