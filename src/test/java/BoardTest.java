import org.junit.Test;
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

    moveWhitePawnInvalidDirection();
  }

  private void moveWhitePawnInvalidDirection() {
    Board board = new Board();
    board.place(new Pawn(Piece.Color.WHITE), new BoardPosition(6, 6));

    board.move(new BoardPosition(6, 6), new BoardPosition(5, 6));

    assertEquals(
        board.toString(),
        "........\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "......P.\n" +
            "........\n"
    );
  }

  private void moveWhitePawn() {
    Board board = new Board();
    board.place(new Pawn(Piece.Color.WHITE), new BoardPosition(6, 6));

    board.move(new BoardPosition(6, 6), new BoardPosition(6, 5));

    assertEquals(
        board.toString(),
        "........\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "......P.\n" +
            "........\n" +
            "........\n"
    );
  }

  private void moveBlackPawn() {
    Board board = new Board();
    board.place(new Pawn(Piece.Color.BLACK), new BoardPosition(3, 2));

    board.move(new BoardPosition(3, 2), new BoardPosition(3, 3));

    assertEquals(
        board.toString(),
        "........\n" +
            "........\n" +
            "........\n" +
            "...p....\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n"
    );
  }


  private void placeBlackPawn() {
    Board board = new Board();

    board.place(new Pawn(Piece.Color.BLACK), new BoardPosition(3, 2));

    assertEquals(
        board.toString(),
        "........\n" +
            "........\n" +
            "...p....\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n"
    );
  }


  private void placeWhitePawn() {
    Board board = new Board();

    board.place(new Pawn(Piece.Color.WHITE), new BoardPosition(5, 6));

    assertEquals(
        board.toString(),
        "........\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            ".....P..\n" +
            "........\n"
    );
  }
}