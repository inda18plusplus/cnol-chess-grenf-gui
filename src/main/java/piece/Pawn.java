package piece;

import java.util.HashSet;
import java.util.Set;

public class Pawn extends Piece {
  public Pawn(Color color) {
    super(color);
  }

  @Override
  public char toChar() {
    switch (super.color) {
      case BLACK:
        return 'p';
      case WHITE:
        return 'P';
      default:
        return '?';
    }
  }

  @Override
  public Set<Move> getMoveSet() {
    HashSet<Move> moves = new HashSet<>();

    int deltaRow = super.color == Color.BLACK ? 1 : -1;

    moves.add(new RelativeMove(0, deltaRow, Move.CaptureRule.NO_CAPTURE));

    return moves;
  }
}
