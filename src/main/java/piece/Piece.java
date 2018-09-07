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
   * @param other Another piece
   * @param captureRule How this piece can capture the other piece
   * @return true if this piece can move onto the other piece
   */
  public boolean canCapture(Piece other, Move.CaptureRule captureRule) {
    if (other == null && captureRule != Move.CaptureRule.MUST_CAPTURE) {
      return true;
    } else if (other.color != this.color
        && (captureRule == Move.CaptureRule.CAN_CAPTURE
        || captureRule == Move.CaptureRule.MUST_CAPTURE)) {
      return true;
    }

    return false;
  }

  public abstract char toChar();

  public abstract Set<Move> getMoveSet();
}
