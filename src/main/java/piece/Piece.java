package piece;

import java.util.Set;

public abstract class Piece {

  public enum Color {
    BLACK, WHITE
  }

  protected Color color;

  Piece(Color color) {
    this.color = color;
  }


  /**
   * Determines if this piece can capture another.
   *
   * @param other       Another piece
   * @param captureRule How this piece can capture the other piece
   * @return true if this piece can move onto the other piece
   */
  public boolean canCapture(Piece other, Move.CaptureRule captureRule) {
    if (other == null && captureRule != Move.CaptureRule.MUST_CAPTURE) {
      return true;
    } else if (other != null && other.color != this.color
        && (captureRule == Move.CaptureRule.CAN_CAPTURE
        || captureRule == Move.CaptureRule.MUST_CAPTURE)) {
      return true;
    }

    return false;
  }

  public boolean isOfColor(Color color) {
    return this.color == color;
  }

  // The char representation of a piece
  public abstract char toChar();

  // The set of moves of a piece can perform
  public abstract Set<Move> getMoveSet();

  // Called when a piece is moved
  public void onMove() {}
}
