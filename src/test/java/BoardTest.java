import org.junit.Test;
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
    moveBlackPawn();
    moveWhitePawn();

    moveWhitePawnInvalidDirectionSide();
    moveWhitePawnInvalidDirectionBack();

    moveBlackPawnInvalidDirectionSide();
    moveBlackPawnInvalidDirectionBack();
  }

  private void moveBlackPawnInvalidDirectionBack() {
    Board board = new Board();
    board.place(new Pawn(Piece.Color.BLACK), new Position(3, 2));

    board.move(new Position(3, 2), new Position(3, 1));

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
    board.place(new Pawn(Piece.Color.BLACK), new Position(3, 2));

    board.move(new Position(3, 2), new Position(4, 2));

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
    board.place(new Pawn(Piece.Color.WHITE), new Position(6, 6));

    board.move(new Position(6, 6), new Position(6, 7));

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
    board.place(new Pawn(Piece.Color.WHITE), new Position(6, 6));

    board.move(new Position(6, 6), new Position(5, 6));

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

  private void moveWhitePawn() {
    Board board = new Board();
    board.place(new Pawn(Piece.Color.WHITE), new Position(6, 6));

    board.move(new Position(6, 6), new Position(6, 5));

    assertEquals(
        "........\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "......P.\n" +
            "........\n" +
            "........\n",
        board.toString()
    );
  }

  private void moveBlackPawn() {
    Board board = new Board();
    board.place(new Pawn(Piece.Color.BLACK), new Position(3, 2));

    board.move(new Position(3, 2), new Position(3, 3));

    assertEquals(
        "........\n" +
            "........\n" +
            "........\n" +
            "...p....\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n",
        board.toString()
    );
  }


  private void placeBlackPawn() {
    Board board = new Board();

    board.place(new Pawn(Piece.Color.BLACK), new Position(3, 2));

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

    board.place(new Pawn(Piece.Color.WHITE), new Position(5, 6));

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