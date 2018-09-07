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
   * @return true if this piece cannot move onto the other
   */
  public boolean isObstructedBy(Piece other) {
    return other != null && other.color == this.color;
  }

  public abstract char toChar();

  public abstract Set<Move> getMoveSet();
}
