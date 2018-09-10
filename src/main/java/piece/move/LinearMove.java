package piece.move;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import piece.Piece;
import piece.Position;

/**
 * A piece.move which seeks in a direction and stops at hostiles.
 */
public class LinearMove extends Move {
  protected final int deltaColumn;
  protected final int deltaRow;

  // The maximum number of steps this piece.move can piece.move a piece.
  // If <= 0: no limit
  private int maxSteps = 0;

  public LinearMove(int deltaColumn, int deltaRow) {
    this.deltaColumn = deltaColumn;
    this.deltaRow = deltaRow;
  }

  /**
   * Create a new Move seeking in a direction until it hits another piece/edge.
   *
   * @param deltaColumn The length and direction of a step along the columns
   * @param deltaRow The length and direction of a step along the rows
   * @param maxSteps The maximum number of steps to travel
   * @param captureRule Determines what pieces this move can capture
   */
  public LinearMove(int deltaColumn, int deltaRow, int maxSteps, CaptureRule captureRule) {
    this(deltaColumn, deltaRow);
    super.captureRule = captureRule;
    this.maxSteps = maxSteps;
  }

  @Override public Set<Position> getDestinations(Position origin, int boundWidth, int boundHeight,
      Function<Position, Piece> getPiece) {
    Piece sourcePiece = getPiece.apply(origin);

    return this.seekLineUntil(origin, boundWidth, boundHeight, getPiece,
        piece -> sourcePiece.canCapture(piece, this.captureRule));
  }

  private Set<Position> seekLineUntil(Position origin, int boundWidth, int boundHeight,
      Function<Position, Piece> getPiece,
      Function<Piece, Boolean> predicate) {
    Set<Position> positions = new HashSet<>();

    Position delta = new Position(deltaColumn, deltaRow);
    Position resultingPos = origin.add(delta);
    Piece sourcePiece = getPiece.apply(origin);

    int steps = 0;

    if (sourcePiece != null) {
      while (resultingPos.inBound(0, 0, boundWidth, boundHeight)) {
        Piece targetPiece = getPiece.apply(resultingPos);

        if (predicate.apply(targetPiece)) {
          positions.add(resultingPos);

          if (targetPiece != null) {
            break;
          }
        } else {
          break;
        }

        resultingPos = resultingPos.add(delta);

        steps++;
        if (steps == this.maxSteps) {
          break;
        }
      }
    }

    return positions;
  }

  @Override public void perform(Position oldPosition, Position newPosition,
      Function<Position, Piece> getPiece,
      BiConsumer<Piece, Position> setPiece) {
    Piece sourcePiece = getPiece.apply(oldPosition);

    setPiece.accept(sourcePiece, newPosition);
    setPiece.accept(null, oldPosition);

    sourcePiece.onMove(oldPosition, newPosition, getPiece);
  }
}
