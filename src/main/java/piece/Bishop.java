package piece;

import java.util.HashSet;
import java.util.Set;

public class Bishop extends Piece {
  public Bishop(Color color) {
    super(color);
  }

  public Bishop(Bishop bishop) {
    super(bishop.color);
  }

  @Override public char toChar() {
    switch (super.color) {
      case BLACK:
        return 'b';
      case WHITE:
        return 'B';
      default:
        return '?';
    }
  }

  @Override public Set<Move> getMoveSet() {
    Set<Move> moves = new HashSet<>();

    moves.add(new LinearMove(1, 1));
    moves.add(new LinearMove(-1, 1));
    moves.add(new LinearMove(1, -1));
    moves.add(new LinearMove(-1, -1));

    return moves;
  }

  @Override public Piece makeCopy() {
    return new Bishop(this);
  }
}
