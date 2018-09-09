package piece;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

public abstract class Move {
  CaptureRule captureRule = CaptureRule.CAN_CAPTURE;

  /**
   * Perform this move.
   * @param oldPosition The old position of the piece to move
   * @param newPosition The position to move the piece to
   * @param getPiece Returns the piece at a certain position
   * @param setPiece Sets the piece at a certain position
   */
  public abstract void perform(Position oldPosition, Position newPosition,
      Function<Position, Piece> getPiece,
      BiConsumer<Piece, Position> setPiece);

  /**
   * Get all possible destinations of a move.
   *
   * @param origin      Where to move from
   * @param boundWidth  The width of the board
   * @param boundHeight The height of the board
   * @param getPiece    The piece at a certain position
   * @return A list of positions this move allows a piece to move to
   */
  public abstract Set<Position> getDestinations(Position origin,
      int boundWidth, int boundHeight,
      Function<Position, Piece> getPiece);


  /**
   * Determines if this move is considered illegal if there's a check.
   * @return True if the move is blocked
   */
  public boolean blockedByCheck() {
    return false;
  }

  enum CaptureRule {
    MUST_CAPTURE,
    CAN_CAPTURE,
    NO_CAPTURE
  }
}
