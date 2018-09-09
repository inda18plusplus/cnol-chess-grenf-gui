package piece;

import java.util.HashSet;
import java.util.Set;

public class Pawn extends Piece {
  private boolean hasMoved = false;

  public Pawn(Color color) {
    super(color);
  }

  private Pawn(Pawn pawn) {
    super(pawn.color);
    this.hasMoved = pawn.hasMoved;
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

    moves.add(new LinearMove(0, deltaRow, this.hasMoved ? 1 : 2, Move.CaptureRule.NO_CAPTURE));
    moves.add(new RelativeMove(-1, deltaRow, Move.CaptureRule.MUST_CAPTURE));
    moves.add(new RelativeMove(1, deltaRow, Move.CaptureRule.MUST_CAPTURE));

    return moves;
  }

  @Override public void onMove() {
    this.hasMoved = true;
  }

  @Override public boolean canPromote() {
    return true;
  }

  @Override public Piece makeCopy() {
    return new Pawn(this);
  }
}
