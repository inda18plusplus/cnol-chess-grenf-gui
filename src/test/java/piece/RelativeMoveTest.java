package piece;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class RelativeMoveTest {

  @Test
  public void expandPositions() {
    noCollision();

    hostileCollision();
    friendlyCollision();

    outOfBoundsUpperColumn();
    outOfBoundsLowerColumn();

    outOfBoundsUpperRow();
    outOfBoundsLowerRow();
  }

  private void friendlyCollision() {
    Move move = new RelativeMove(1, 2);

    List<Position> positions = move.expandPositions(new Position(2, 2), 8, 8, position -> {
      if (position.equals(new Position(2, 2))) {
        return new Pawn(Piece.Color.BLACK);
      } else {
        return new Pawn(Piece.Color.BLACK);
      }
    });

    assertEquals(
        Arrays.asList(),
        positions
    );
  }

  private void hostileCollision() {
    Move move = new RelativeMove(1, 2);

    List<Position> positions = move.expandPositions(new Position(2, 2), 8, 8, position -> {
      if (position.equals(new Position(2, 2))) {
        return new Pawn(Piece.Color.BLACK);
      } else {
        return new Pawn(Piece.Color.WHITE);
      }
    });

    assertEquals(
        Arrays.asList(new Position(3, 4)),
        positions
    );
  }

  private void outOfBoundsLowerRow() {
     Move move = new RelativeMove(0, -1);

    List<Position> positions = move.expandPositions(new Position(4, 0), 8, 8, position -> {
      if (position.equals(new Position(4, 0))) {
        return new Pawn(Piece.Color.BLACK);
      } else {
        return null;
      }
    });

    assertEquals(
        Arrays.asList(),
        positions
    );
  }

  private void outOfBoundsUpperRow() {
    Move move = new RelativeMove(0, 1);

    List<Position> positions = move.expandPositions(new Position(4, 7), 8, 8, position -> {
      if (position.equals(new Position(4, 7))) {
        return new Pawn(Piece.Color.BLACK);
      } else {
        return null;
      }
    });

    assertEquals(
        Arrays.asList(),
        positions
    );
  }

  private void outOfBoundsLowerColumn() {
    Move move = new RelativeMove(-1, 0);

    List<Position> positions = move.expandPositions(new Position(0, 2), 8, 8, position -> {
      if (position.equals(new Position(0, 2))) {
        return new Pawn(Piece.Color.BLACK);
      } else {
        return null;
      }
    });

    assertEquals(
        Arrays.asList(),
        positions
    );  }

  private void outOfBoundsUpperColumn() {
    Move move = new RelativeMove(1, 0);

    List<Position> positions = move.expandPositions(new Position(7, 2), 8, 8, position -> {
      if (position.equals(new Position(7, 2))) {
        return new Pawn(Piece.Color.BLACK);
      } else {
        return null;
      }
    });

    assertEquals(
        Arrays.asList(),
        positions
    );
  }

  private void noCollision() {
    Move move = new RelativeMove(1, 0);

    List<Position> positions = move.expandPositions(new Position(2, 2), 8, 8, position -> {
      if (position.equals(new Position(2, 2))) {
        return new Pawn(Piece.Color.BLACK);
      } else {
        return null;
      }
    });

    assertEquals(
        Arrays.asList(new Position(3, 2)),
        positions
    );
  }
}