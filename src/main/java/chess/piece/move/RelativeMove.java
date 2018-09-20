package chess.piece.move;

/**
 * A chess.piece.move, relative to a position.
 */
public class RelativeMove extends LinearMove {
  public RelativeMove(int deltaColumn, int deltaRow, CaptureRule captureRule) {
    super(deltaColumn, deltaRow, 1, captureRule);
  }

  public RelativeMove(int deltaColumn, int deltaRow) {
    this(deltaColumn, deltaRow, CaptureRule.CAN_CAPTURE);
  }
}
