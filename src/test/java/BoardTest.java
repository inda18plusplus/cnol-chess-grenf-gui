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
    movePawn();
    movePawnInvalidOrder();

    moveWhitePawnInvalidDirectionSide();
    moveWhitePawnInvalidDirectionBack();

    moveBlackPawnInvalidDirectionSide();
    moveBlackPawnInvalidDirectionBack();
  }

  private void movePawnInvalidOrder() {
    Board board = new Board();
    board.place(new Pawn(Piece.Color.WHITE), new Position(6, 6));
    board.place(new Pawn(Piece.Color.BLACK), new Position(3, 1));

    board.move(new Position(3, 1), new Position(6, 2));
    board.move(new Position(6, 6), new Position(6, 5));

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

  private void movePawn() {
    Board board = new Board();
    board.place(new Pawn(Piece.Color.WHITE), new Position(6, 6));
    board.place(new Pawn(Piece.Color.BLACK), new Position(3, 1));

    board.move(new Position(6, 6), new Position(6, 5));
    board.move(new Position(3, 1), new Position(6, 2));

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