package piece.move;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import piece.Pawn;
import piece.Piece;
import piece.Position;

public class RelativeMoveTest {

  @Test
  public void expandPositions() {
    noCollision();

    hostileCapture();
    friendlyCollision();

    hostileNoCapture();

    outOfBoundsUpperColumn();
    outOfBoundsLowerColumn();

    outOfBoundsUpperRow();
    outOfBoundsLowerRow();
  }

  private void hostileNoCapture() {
    Move move = new RelativeMove(1, 2, Move.CaptureRule.NO_CAPTURE);

    Set<Position> positions = move.getDestinations(new Position(2, 2), 8, 8, position -> {
      if (position.equals(new Position(2, 2))) {
        return new Pawn(Piece.Color.BLACK);
      } else {
        return new Pawn(Piece.Color.WHITE);
      }
    });

    assertEquals(
        new HashSet<>(),
        positions
    );
  }

  private void friendlyCollision() {
    Move move = new RelativeMove(1, 2);

    Set<Position> positions = move.getDestinations(new Position(2, 2), 8, 8, position -> {
      if (position.equals(new Position(2, 2))) {
        return new Pawn(Piece.Color.BLACK);
      } else {
        return new Pawn(Piece.Color.BLACK);
      }
    });

    assertEquals(
        new HashSet<>(),
        positions
    );
  }

  private void hostileCapture() {
    Move move = new RelativeMove(1, 2);

    Set<Position> positions = move.getDestinations(new Position(2, 2), 8, 8, position -> {
      if (position.equals(new Position(2, 2))) {
        return new Pawn(Piece.Color.BLACK);
      } else {
        return new Pawn(Piece.Color.WHITE);
      }
    });

    assertEquals(
        new HashSet<>(Collections.singletonList(new Position(3, 4))),
        positions
    );
  }

  private void outOfBoundsLowerRow() {
    Move move = new RelativeMove(0, -1);

    Set<Position> positions = move.getDestinations(new Position(4, 0), 8, 8, position -> {
      if (position.equals(new Position(4, 0))) {
        return new Pawn(Piece.Color.BLACK);
      } else {
        return null;
      }
    });

    assertEquals(
        new HashSet<>(),
        positions
    );
  }

  private void outOfBoundsUpperRow() {
    Move move = new RelativeMove(0, 1);

    Set<Position> positions = move.getDestinations(new Position(4, 7), 8, 8, position -> {
      if (position.equals(new Position(4, 7))) {
        return new Pawn(Piece.Color.BLACK);
      } else {
        return null;
      }
    });

    assertEquals(
        new HashSet<>(),
        positions
    );
  }

  private void outOfBoundsLowerColumn() {
    Move move = new RelativeMove(-1, 0);

    Set<Position> positions = move.getDestinations(new Position(0, 2), 8, 8, position -> {
      if (position.equals(new Position(0, 2))) {
        return new Pawn(Piece.Color.BLACK);
      } else {
        return null;
      }
    });

    assertEquals(
        new HashSet<>(),
        positions
    );
  }

  private void outOfBoundsUpperColumn() {
    Move move = new RelativeMove(1, 0);

    Set<Position> positions = move.getDestinations(new Position(7, 2), 8, 8, position -> {
      if (position.equals(new Position(7, 2))) {
        return new Pawn(Piece.Color.BLACK);
      } else {
        return null;
      }
    });

    assertEquals(
        new HashSet<>(),
        positions
    );
  }

  private void noCollision() {
    Move move = new RelativeMove(1, 0);

    Set<Position> positions = move.getDestinations(new Position(2, 2), 8, 8, position -> {
      if (position.equals(new Position(2, 2))) {
        return new Pawn(Piece.Color.BLACK);
      } else {
        return null;
      }
    });

    assertEquals(
        new HashSet<>(Collections.singletonList(new Position(3, 2))),
        positions
    );
  }
}