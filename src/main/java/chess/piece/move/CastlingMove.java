package chess.piece.move;

import chess.piece.Piece;
import chess.piece.Position;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class CastlingMove extends Move {
  private final Function<Piece, Boolean> pieceIsTarget;

  public CastlingMove(Function<Piece, Boolean> predicate) {
    this.pieceIsTarget = predicate;
  }

  @Override public Set<Position> getDestinations(Position origin, int boundWidth, int boundHeight,
      Function<Position, Piece> getPiece) {
    Set<Position> destinations = new HashSet<>();

    Piece sourcePiece = getPiece.apply(origin);
    if (sourcePiece == null) {
      return destinations;
    }

    for (int i = origin.getColumn() + 1; i < boundWidth; i++) {
      Position position = new Position(i, origin.getRow());

      Piece piece = getPiece.apply(position);

      if (piece != null) {
        if (position.getColumn() - origin.getColumn() >= 2) {
          if (pieceIsTarget.apply(piece)) {
            destinations.add(new Position(origin.getColumn() + 2, position.getRow()));
          }
        }
        break;
      }
    }


    for (int i = origin.getColumn() - 1; i >= 0; i--) {
      Position position = new Position(i, origin.getRow());

      Piece piece = getPiece.apply(position);

      if (piece != null) {
        if (origin.getColumn() - position.getColumn() >= 2) {
          if (pieceIsTarget.apply(piece)) {
            destinations.add(new Position(origin.getColumn() - 2, position.getRow()));
          }
        }
        break;
      }
    }

    destinations.remove(origin);

    return destinations;
  }

  @Override public void perform(Position oldPosition, Position newPosition,
      Function<Position, Piece> getPiece, BiConsumer<Piece, Position> setPiece) {
    int deltaColumn = newPosition.getColumn() - oldPosition.getColumn();

    int direction = deltaColumn < 0 ? -1 : 1;

    Position newTargetPosition = newPosition.add(new Position(-direction, 0));

    Position oldTargetPosition = new Position(newPosition);
    while (getPiece.apply(oldTargetPosition) == null) {
      oldTargetPosition = oldTargetPosition.add(new Position(direction, 0));
    }


    Piece sourcePiece = getPiece.apply(oldPosition);
    Piece targetPiece = getPiece.apply(oldTargetPosition);

    setPiece.accept(sourcePiece, newPosition);
    setPiece.accept(targetPiece, newTargetPosition);

    setPiece.accept(null, oldPosition);
    setPiece.accept(null, oldTargetPosition);

    sourcePiece.onMove(oldPosition, newPosition, getPiece);
    targetPiece.onMove(oldTargetPosition, newTargetPosition, getPiece);
  }
}
