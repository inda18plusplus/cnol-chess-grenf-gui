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

  RelativeMove(int deltaColumn, int deltaRow, CaptureRule captureRule) {
    super.captureRule = captureRule;

    this.deltaColumn = deltaColumn;
    this.deltaRow = deltaRow;
  }

  RelativeMove(int deltaColumn, int deltaRow) {
    super.captureRule = CaptureRule.CAN_CAPTURE;

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

      if (sourcePiece != null) {
        if (sourcePiece.canCapture(targetPiece, captureRule)) {
          positions.add(resultingPos);
        }
      }
    }

    return positions;
  }
}
