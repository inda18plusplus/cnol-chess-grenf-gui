package piece.move;

import java.util.function.BiConsumer;
import java.util.function.Function;
import piece.Piece;
import piece.Position;

public class EnPassantMove extends RelativeMove {
  public EnPassantMove(int passantDirection, int forwardDirection) {
    super(passantDirection, forwardDirection);
  }

  @Override public void perform(Position oldPosition, Position newPosition,
      Function<Position, Piece> getPiece, BiConsumer<Piece, Position> setPiece) {
    super.perform(oldPosition, newPosition, getPiece, setPiece);

    setPiece.accept(null, oldPosition.add(new Position(super.deltaColumn, 0)));
  }
}
