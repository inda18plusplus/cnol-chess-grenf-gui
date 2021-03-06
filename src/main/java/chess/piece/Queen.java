package chess.piece;

import chess.piece.move.LinearMove;
import chess.piece.move.Move;
import java.util.HashSet;
import java.util.Set;

public class Queen extends Piece {
  public Queen(Color color) {
    super(color);
  }

  private Queen(Queen queen) {
    super(queen.color);
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

  @Override public Piece makeCopy() {
    return new Queen(this);
  }
}
