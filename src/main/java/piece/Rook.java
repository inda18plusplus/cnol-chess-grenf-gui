package piece;

import java.util.HashSet;
import java.util.Set;

public class Rook extends Piece {
  public Rook(Color color) {
    super(color);
  }

  Rook(Rook rook) {
    super(rook.color);
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
    Set<Move> moves = new HashSet<>();

    moves.add(new LinearMove(1, 0));
    moves.add(new LinearMove(-1, 0));
    moves.add(new LinearMove(0, 1));
    moves.add(new LinearMove(0, -1));

    return moves;
  }

  @Override public Piece makeCopy() {
    return new Rook(this);
  }
}
