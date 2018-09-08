import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.ArrayBlockingQueue;
import org.junit.Test;
import piece.Knight;
import piece.Position;
import piece.Pawn;
import piece.Piece;

import static org.junit.Assert.*;

public class BoardTest {

  @Test
  public void place() {
    placeBlackPawn();
    placeWhitePawn();
  }

  @Test
  public void move() {
    movePawn();
    movePawnInvalidOrder();


    movePawnAttackingHostileToRightSide();
    movePawnAttackingFriendlyToRightSide();

    movePawnAttackingHostileToLeftSide();
    movePawnAttackingFriendlyToLeftSide();


    moveWhitePawnInvalidDirectionSide();
    moveWhitePawnInvalidDirectionBack();

    moveBlackPawnInvalidDirectionSide();
    moveBlackPawnInvalidDirectionBack();
  }

  @Test
  public void availableDestinations() {
    availableDestinationsKnight();
    availableDestinationsKnightOutOfBounds();
  }

  private void availableDestinationsKnightOutOfBounds() {
    Board board = new Board();

    assert board.place(new Knight(Piece.Color.WHITE), new Position(7, 6));

    assertEquals(
        new HashSet<>(Arrays.asList(
            new Position(6, 4),
            new Position(5, 5),
            new Position(5, 7)
        )),
        board.availableDestinations(new Position(7, 6))
    );
  }

  private void availableDestinationsKnight() {
    Board board = new Board();

    assert board.place(new Knight(Piece.Color.WHITE), new Position(4, 4));

    assertEquals(
        new HashSet<>(Arrays.asList(
            new Position(3, 2),
            new Position(5, 2),
            new Position(2, 3),
            new Position(6, 3),
            new Position(2, 5),
            new Position(6, 5),
            new Position(3, 6),
            new Position(5, 6)
        )),
        board.availableDestinations(new Position(4, 4))
    );
  }

  private void movePawnAttackingFriendlyToLeftSide() {
    Board board = new Board();
    assert board.place(new Pawn(Piece.Color.WHITE), new Position(4, 3));
    assert board.place(new Pawn(Piece.Color.WHITE), new Position(3, 2));

    assert !board.move(new Position(4, 3), new Position(3, 2));

    assertEquals(
        "........\n" +
            "........\n" +
            "...P....\n" +
            "....P...\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n",
        board.toString()
    );
  }

  private void movePawnAttackingHostileToLeftSide() {
    Board board = new Board();
    assert board.place(new Pawn(Piece.Color.WHITE), new Position(4, 3));
    assert board.place(new Pawn(Piece.Color.BLACK), new Position(3, 2));

    assert board.move(new Position(4, 3), new Position(3, 2));

    assertEquals(
        "........\n" +
            "........\n" +
            "...P....\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n",
        board.toString()
    );
  }

  private void movePawnAttackingFriendlyToRightSide() {
    Board board = new Board();
    assert board.place(new Pawn(Piece.Color.WHITE), new Position(2, 3));
    assert board.place(new Pawn(Piece.Color.WHITE), new Position(3, 2));

    assert !board.move(new Position(2, 3), new Position(3, 2));

    assertEquals(
        "........\n" +
            "........\n" +
            "...P....\n" +
            "..P.....\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n",
        board.toString()
    );
  }

  private void movePawnAttackingHostileToRightSide() {
    Board board = new Board();
    assert board.place(new Pawn(Piece.Color.WHITE), new Position(2, 3));
    assert board.place(new Pawn(Piece.Color.BLACK), new Position(3, 2));

    assert board.move(new Position(2, 3), new Position(3, 2));

    assertEquals(
        "........\n" +
            "........\n" +
            "...P....\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n",
        board.toString()
    );
  }

  private void movePawnInvalidOrder() {
    Board board = new Board();
    assert board.place(new Pawn(Piece.Color.WHITE), new Position(6, 6));
    assert board.place(new Pawn(Piece.Color.BLACK), new Position(3, 1));

    assert !board.move(new Position(3, 1), new Position(6, 2));
    assert board.move(new Position(6, 6), new Position(6, 5));

    assertEquals(
        "........\n" +
            "...p....\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "......P.\n" +
            "........\n" +
            "........\n",
        board.toString()
    );
  }

  private void moveBlackPawnInvalidDirectionBack() {
    Board board = new Board();
    assert board.place(new Pawn(Piece.Color.BLACK), new Position(3, 2));

    assert !board.move(new Position(3, 2), new Position(3, 1));

    assertEquals(
        "........\n" +
            "........\n" +
            "...p....\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n",
        board.toString()
    );
  }

  private void moveBlackPawnInvalidDirectionSide() {
    Board board = new Board();
    assert board.place(new Pawn(Piece.Color.BLACK), new Position(3, 2));

    assert !board.move(new Position(3, 2), new Position(4, 2));

    assertEquals(
        "........\n" +
            "........\n" +
            "...p....\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n",
        board.toString()
    );
  }

  private void moveWhitePawnInvalidDirectionBack() {
    Board board = new Board();
    assert board.place(new Pawn(Piece.Color.WHITE), new Position(6, 6));

    assert !board.move(new Position(6, 6), new Position(6, 7));

    assertEquals(
        "........\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "......P.\n" +
            "........\n",
        board.toString()
    );
  }

  private void moveWhitePawnInvalidDirectionSide() {
    Board board = new Board();
    assert board.place(new Pawn(Piece.Color.WHITE), new Position(6, 6));

    assert !board.move(new Position(6, 6), new Position(5, 6));

    assertEquals(
        "........\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "......P.\n" +
            "........\n",
        board.toString()
    );
  }

  private void movePawn() {
    Board board = new Board();
    assert board.place(new Pawn(Piece.Color.WHITE), new Position(6, 6));
    assert board.place(new Pawn(Piece.Color.BLACK), new Position(3, 1));

    assert board.move(new Position(6, 6), new Position(6, 5));
    assert board.move(new Position(3, 1), new Position(3, 2));

    assertEquals(
        "........\n" +
            "........\n" +
            "...p....\n" +
            "........\n" +
            "........\n" +
            "......P.\n" +
            "........\n" +
            "........\n",
        board.toString()
    );
  }

  private void placeBlackPawn() {
    Board board = new Board();

    assert board.place(new Pawn(Piece.Color.BLACK), new Position(3, 2));

    assertEquals(
        "........\n" +
            "........\n" +
            "...p....\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n",
        board.toString()
    );
  }

  private void placeWhitePawn() {
    Board board = new Board();

    assert board.place(new Pawn(Piece.Color.WHITE), new Position(5, 6));

    assertEquals(
        "........\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            ".....P..\n" +
            "........\n",
        board.toString()
    );
  }
}