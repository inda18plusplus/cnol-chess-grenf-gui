package piece;

import java.util.HashSet;
import java.util.Set;

public class Knight extends Piece {
  public Knight(Color color) {
    super(color);
  }

  private Knight(Knight knight) {
    super(knight.color);
  }

  @Override public char toChar() {
    switch (super.color) {
      case BLACK:
        return 'n';
      case WHITE:
        return 'N';
      default:
        return '?';
    }
  }

  @Override public Set<Move> getMoveSet() {
    Set<Move> moves = new HashSet<>();

    moves.add(new RelativeMove(1, 2));
    moves.add(new RelativeMove(-1, 2));
    moves.add(new RelativeMove(1, -2));
    moves.add(new RelativeMove(-1, -2));

    moves.add(new RelativeMove(2, 1));
    moves.add(new RelativeMove(-2, 1));
    moves.add(new RelativeMove(2, -1));
    moves.add(new RelativeMove(-2, -1));

    return moves;
  }

  @Override public Piece makeCopy() {
    return new Knight(this);
  }
}
