package piece;

/**
 * A move, relative to a position.
 */
class RelativeMove extends LinearMove {
  RelativeMove(int deltaColumn, int deltaRow, CaptureRule captureRule) {
    super(deltaColumn, deltaRow, 1, captureRule);
  }

  RelativeMove(int deltaColumn, int deltaRow) {
    this(deltaColumn, deltaRow, CaptureRule.CAN_CAPTURE);
  }
}
