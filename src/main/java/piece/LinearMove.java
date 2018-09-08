package piece;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/**
 * A move which seeks in a direction and stops at hostiles.
 */
public class LinearMove extends Move {
  private int deltaColumn;
  private int deltaRow;

  // The maximum number of steps this move can move a piece.
  // If <= 0: no limit
  private int maxSteps = 0;

  LinearMove(int deltaColumn, int deltaRow) {
    this.deltaColumn = deltaColumn;
    this.deltaRow = deltaRow;
  }

  LinearMove(int deltaColumn, int deltaRow, int maxSteps, CaptureRule captureRule) {
    this(deltaColumn, deltaRow);
    super.captureRule = captureRule;
    this.maxSteps = maxSteps;
  }

  @Override public Set<Position> expandPositions(Position origin, int boundWidth, int boundHeight,
                                                  Function<Position, Piece> getPiece) {
    Set<Position> positions = new HashSet<>();

    Position delta = new Position(deltaColumn, deltaRow);
    Position resultingPos = origin.add(delta);
    Piece sourcePiece = getPiece.apply(origin);

    int steps = 0;

    while (resultingPos.inBound(0, 0, boundWidth, boundHeight)) {
      Piece targetPiece = getPiece.apply(resultingPos);

      if (sourcePiece != null) {
        if (sourcePiece.canCapture(targetPiece, captureRule)) {
          positions.add(resultingPos);

          if (targetPiece != null) {
            break;
          }
        } else {
          break;
        }
      }

      resultingPos = resultingPos.add(delta);

      steps++;
      if (steps == this.maxSteps) {
        break;
      }
    }

    return positions;
  }
}
