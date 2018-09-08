package piece;

import java.util.HashSet;
import java.util.Set;

public class King extends Piece {
  public King(Color color) {
    super(color);
  }

  @Override public char toChar() {
    switch (super.color) {
      case BLACK:
        return 'k';
      case WHITE:
        return 'K';
      default:
        return '?';
    }
  }

  @Override public Set<Move> getMoveSet() {
    Set<Move> moves = new HashSet<>();

    moves.add(new RelativeMove(-1, -1));
    moves.add(new RelativeMove(0, -1));
    moves.add(new RelativeMove(1, -1));

    moves.add(new RelativeMove(-1, 0));
    moves.add(new RelativeMove(1, 0));

    moves.add(new RelativeMove(-1, 1));
    moves.add(new RelativeMove(0, 1));
    moves.add(new RelativeMove(1, 1));

    return moves;
  }

  @Override public boolean isExpendable() {
    return false;
  }
}
