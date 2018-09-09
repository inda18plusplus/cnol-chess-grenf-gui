package piece;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/**
 * A move, relative to a position.
 */
public class RelativeMove extends LinearMove {
  RelativeMove(int deltaColumn, int deltaRow, CaptureRule captureRule) {
    super(deltaColumn, deltaRow, 1, captureRule);
  }

  RelativeMove(int deltaColumn, int deltaRow) {
    this(deltaColumn, deltaRow, CaptureRule.CAN_CAPTURE);
  }
}
