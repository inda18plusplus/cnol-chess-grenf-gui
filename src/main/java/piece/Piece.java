package piece;

import java.util.Set;

public abstract class Piece {

  protected Color color;

  Piece(Color color) {
    this.color = color;
  }

  /**
   * Returns the color of the piece.
   * @return the color
   */
  public Color getColor() {
    return this.color;
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

  // Determines if a piece can die and still keep the game going.
  public boolean isExpendable() {
    return true;
  }

  // Called when a piece is moved
  public void onMove() {}

  public enum Color {
    BLACK, WHITE;

    /**
     * Returns the color that isn't this.
     * @return The color
     */
    public Color opposite() {
      switch (this) {
        case BLACK:
          return WHITE;
        case WHITE:
          return BLACK;
        default:
          throw new RuntimeException("UNKNOWN COLOR");
      }
    }
  }
}
