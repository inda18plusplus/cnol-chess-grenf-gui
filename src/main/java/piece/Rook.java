package piece;

import java.util.Set;

public class Rook extends Piece {
  public Rook(Color color) {
    super(color);
  }

  @Override public char toChar() {
    switch (super.color) {
      case BLACK:
        return 'r';
      case WHITE:
        return 'R';
      default:
        return '?';
    }
  }

  @Override public Set<Move> getMoveSet() {
    return null;
  }
}
