package piece;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class King extends Piece {
  private boolean hasMoved = false;

  public King(Color color) {
    super(color);
  }

  private King(King king) {
    super(king.color);
    this.hasMoved = king.hasMoved;
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

    if (!this.hasMoved && !this.getThreatened()) {
      moves.add(new CastlingMove(piece -> {
        if (piece instanceof Rook) {
          Rook rook = (Rook) piece;

          return !rook.hasMoved() && rook.isOfColor(this.color);
        }
        return false;
      }));
    }

    return moves;
  }

  @Override public boolean isExpendable() {
    return false;
  }

  @Override public void onMove(Position oldPosition, Position newPosition,
      Function<Position, Piece> getPiece) {
    this.hasMoved = true;
  }

  @Override public Piece makeCopy() {
    return new King(this);
  }
}
