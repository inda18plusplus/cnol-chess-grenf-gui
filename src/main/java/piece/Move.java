package piece;

import java.util.List;
import java.util.function.Function;

public abstract class Move {
  enum CaptureRule {
    MUST_CAPTURE,
    CAN_CAPTURE,
    NO_CAPTURE
  }

  protected CaptureRule captureRule;

  /**
   * Get all possible moves.
   * @param origin Where to move from
   * @param boundWidth The width of the board
   * @param boundHeight The height of the board
   * @param getPiece The piece at a certain position
   * @return A list of positions this move allows a piece to move to
   */
  public abstract List<Position> expandPositions(Position origin,
                                                 int boundWidth, int boundHeight,
                                                 Function<Position, Piece> getPiece);
}
