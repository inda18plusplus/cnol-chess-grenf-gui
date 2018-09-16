package piece.move;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import piece.King;
import piece.Piece;
import piece.Position;
import piece.Rook;
import static org.junit.Assert.*;

public class CastlingMoveTest {

  @Test
  public void getDestinations() {
    CastlingMove move = new CastlingMove(piece -> {
      if (piece instanceof Rook) {
        Rook rook = (Rook) piece;

        return !rook.hasMoved() && rook.isOfColor(Piece.Color.BLACK);
      }
      return false;
    });

    Set<Position> destinations = move.getDestinations(
        new Position(4, 0), 8, 8, position -> {
          if (position.equals(new Position(4, 0))) {
            return new King(Piece.Color.BLACK);
          } else if (position.equals(new Position(0, 0))) {
            return new Rook(Piece.Color.BLACK);
          }
          return null;
        }
    );

    assertEquals(
        new HashSet<>(Collections.singletonList(new Position(2, 0))),
        destinations
    );
  }

  @Test
  public void perform() {
    CastlingMove move = new CastlingMove(piece -> {
      if (piece instanceof Rook) {
        Rook rook = (Rook) piece;

        return !rook.hasMoved() && rook.isOfColor(Piece.Color.BLACK);
      }
      return false;
    });

    Piece king = new King(Piece.Color.BLACK);
    Piece rook = new Rook(Piece.Color.WHITE);

    move.perform(new Position(4, 0), new Position(6, 0), position -> {
      if (position.equals(new Position(4, 0))) {
        return king;
      }
      if (position.equals(new Position(7, 0))) {
        return rook;
      }
      return null;
    }, (piece, position) -> {
      if (position.equals(new Position(4, 0))) {
        assert piece == null;
      } else if (position.equals(new Position(7, 0))) {
        assert piece == null;
      } else if (position.equals(new Position(6, 0))) {
        assert piece == king;
      } else if (position.equals(new Position(5, 0))) {
        assert piece == rook;
      } else {
        assert false;
      }
    });
  }
}