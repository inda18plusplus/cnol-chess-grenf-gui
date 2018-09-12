package piece;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import piece.move.EnPassantMove;
import piece.move.LinearMove;
import piece.move.Move;
import piece.move.RelativeMove;

public class Pawn extends Piece {
  private boolean hasMoved = false;
  private Integer enPassantColumnDirection = null;

  public Pawn(Color color) {
    super(color);
  }

  private Pawn(Pawn pawn) {
    super(pawn.color);
    this.hasMoved = pawn.hasMoved;
    this.enPassantColumnDirection = pawn.enPassantColumnDirection;
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

    if (enPassantColumnDirection != null) {
      moves.add(new EnPassantMove(enPassantColumnDirection, deltaRow));
    }

    return moves;
  }

  @Override public void onMove(Position oldPosition, Position newPosition,
      Function<Position, Piece> getPiece) {
    this.hasMoved = true;

    int deltaRow = newPosition.getRow() - oldPosition.getRow();
    if (Math.abs(deltaRow) == 2) {
      this.updateAdjacentPawns(newPosition, getPiece);
    }
  }

  @Override public void onTurnEnd() {
    this.enPassantColumnDirection = null;
  }

  @Override public boolean canPromote() {
    return true;
  }

  @Override public Piece makeCopy() {
    return new Pawn(this);
  }

  private void updateAdjacentPawns(Position newPosition, Function<Position, Piece> getPiece) {
    for (int i = 0; i < 2; i++) {
      int columnDirection = 1 - 2 * i;

      Position adjacentTile = new Position(newPosition.getColumn() + columnDirection,
          newPosition.getRow());

      Piece adjacentPiece = getPiece.apply(adjacentTile);

      if (adjacentPiece instanceof Pawn) {
        Pawn adjacentPawn = (Pawn) adjacentPiece;

        adjacentPawn.addEnPassant(-columnDirection);
      }
    }
  }

  private void addEnPassant(int direction) {
    this.enPassantColumnDirection = direction;
  }
}
