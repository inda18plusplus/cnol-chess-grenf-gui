package chess.piece.move;

import chess.piece.Pawn;
import chess.piece.Piece;
import chess.piece.Position;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LinearMoveTest {

  @Test
  public void expandPositions() {
    expandToUpperColumn();
    expandToLowerColumn();
    expandToUpperRow();
    expandToLowerRow();

    expandToCaptureHostile();
    expandToCaptureFriendly();
  }

  private void expandToCaptureFriendly() {
    LinearMove move = new LinearMove(1, 0);

    Set<Position> positions = move.getDestinations(new Position(4, 4), 8, 8, position -> {
      if (position.equals(new Position(4, 4))) {
        return new Pawn(Piece.Color.BLACK);
      } else if (position.equals(new Position(6, 4))) {
        return new Pawn(Piece.Color.BLACK);
      } else {
        return null;
      }
    });

    assertEquals(
        new HashSet<>(Collections.singletonList(
            new Position(5, 4)
        )),
        positions
    );
  }

  private void expandToCaptureHostile() {
    LinearMove move = new LinearMove(1, 0);

    Set<Position> positions = move.getDestinations(new Position(4, 4), 8, 8, position -> {
      if (position.equals(new Position(4, 4))) {
        return new Pawn(Piece.Color.BLACK);
      } else if (position.equals(new Position(6, 4))) {
        return new Pawn(Piece.Color.WHITE);
      } else {
        return null;
      }
    });

    assertEquals(
        new HashSet<>(Arrays.asList(
            new Position(5, 4),
            new Position(6, 4)
        )),
        positions
    );
  }

  private void expandToLowerRow() {
    LinearMove move = new LinearMove(0, -1);

    Set<Position> positions = move.getDestinations(new Position(4, 4), 8, 8, position -> {
      if (position.equals(new Position(4, 4))) {
        return new Pawn(Piece.Color.BLACK);
      } else {
        return null;
      }
    });

    assertEquals(
        new HashSet<>(Arrays.asList(
            new Position(4, 3),
            new Position(4, 2),
            new Position(4, 1),
            new Position(4, 0)
        )),
        positions
    );
  }

  private void expandToUpperRow() {
    LinearMove move = new LinearMove(0, 1);

    Set<Position> positions = move.getDestinations(new Position(4, 4), 8, 8, position -> {
      if (position.equals(new Position(4, 4))) {
        return new Pawn(Piece.Color.BLACK);
      } else {
        return null;
      }
    });

    assertEquals(
        new HashSet<>(Arrays.asList(
            new Position(4, 5),
            new Position(4, 6),
            new Position(4, 7)
        )),
        positions
    );
  }

  private void expandToLowerColumn() {
    LinearMove move = new LinearMove(-1, 0);

    Set<Position> positions = move.getDestinations(new Position(4, 4), 8, 8, position -> {
      if (position.equals(new Position(4, 4))) {
        return new Pawn(Piece.Color.BLACK);
      } else {
        return null;
      }
    });

    assertEquals(
        new HashSet<>(Arrays.asList(
            new Position(3, 4),
            new Position(2, 4),
            new Position(1, 4),
            new Position(0, 4)
        )),
        positions
    );
  }

  private void expandToUpperColumn() {
    LinearMove move = new LinearMove(1, 0);

    Set<Position> positions = move.getDestinations(new Position(4, 4), 8, 8, position -> {
      if (position.equals(new Position(4, 4))) {
        return new Pawn(Piece.Color.BLACK);
      } else {
        return null;
      }
    });

    assertEquals(
        new HashSet<>(Arrays.asList(
            new Position(5, 4),
            new Position(6, 4),
            new Position(7, 4)
        )),
        positions
    );
  }
}