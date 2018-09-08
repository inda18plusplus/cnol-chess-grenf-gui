package piece;

import java.util.HashSet;
import java.util.Set;

public class Queen extends Piece {
  public Queen(Color color) {
    super(color);
  }

  @Override public char toChar() {
    switch (super.color) {
      case BLACK:
        return 'q';
      case WHITE:
        return 'Q';
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

    moves.add(new LinearMove(1, 1));
    moves.add(new LinearMove(-1, 1));
    moves.add(new LinearMove(1, -1));
    moves.add(new LinearMove(-1, -1));

    return moves;
  }
}
