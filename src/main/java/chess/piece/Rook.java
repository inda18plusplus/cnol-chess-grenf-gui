package chess.piece;

import chess.piece.move.LinearMove;
import chess.piece.move.Move;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class Rook extends Piece {
  private boolean hasMoved = false;

  public Rook(Color color) {
    super(color);
  }

  private Rook(Rook rook) {
    super(rook.color);
    this.hasMoved = rook.hasMoved;
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

  @Override public void onMove(Position oldPosition, Position newPosition,
      Function<Position, Piece> getPiece) {
    hasMoved = true;
  }

  @Override public Piece makeCopy() {
    return new Rook(this);
  }

  public boolean hasMoved() {
    return this.hasMoved;
  }
}
