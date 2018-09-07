package piece;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * A move, relative to a position.
 */
public class RelativeMove extends Move {
  private int deltaColumn;
  private int deltaRow;

  public RelativeMove(int deltaColumn, int deltaRow) {
    this.deltaColumn = deltaColumn;
    this.deltaRow = deltaRow;
  }

  @Override
  public List<Position> expandPositions(Position origin, int boundWidth, int boundHeight,
                                        Function<Position, Piece> getPiece) {
    List<Position> positions = new ArrayList<>();

    Position resultingPos = origin.add(new Position(deltaColumn, deltaRow));


    if (resultingPos.inBound(0, 0, boundWidth, boundHeight)
        && origin.inBound(0, 0, boundWidth, boundHeight)) {
      Piece sourcePiece = getPiece.apply(origin);
      Piece targetPiece = getPiece.apply(resultingPos);

      if (sourcePiece != null && !sourcePiece.isObstructedBy(targetPiece)) {
        positions.add(resultingPos);
      }
    }

    return positions;
  }
}
