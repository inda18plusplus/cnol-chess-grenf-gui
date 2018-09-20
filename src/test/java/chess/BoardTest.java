package chess;

import static org.junit.Assert.assertEquals;

import chess.piece.Bishop;
import chess.piece.King;
import chess.piece.Knight;
import chess.piece.Pawn;
import chess.piece.Piece;
import chess.piece.Position;
import chess.piece.Queen;
import chess.piece.Rook;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import org.junit.Test;

public class BoardTest {
  @Test
  public void promotePawnToQueen() {
    Board board = new Board();

    assert board.place(new Pawn(Piece.Color.WHITE), new Position(4, 1));

    assert board.tryMove(new Position(4, 1), new Position(4, 0))
        == Board.MoveResult.PROMOTION_REQUIRED;

    assert board.needsPromotion();
    assert board.promoteTo(Board.PromotionOption.QUEEN);

    assertEquals(
        "....Q...\n"
            + "........\n"
            + "........\n"
            + "........\n"
            + "........\n"
            + "........\n"
            + "........\n"
            + "........\n",
        board.toString()
    );
  }

  @Test
  public void promotePawnToKnight() {
    Board board = new Board();

    assert board.place(new Pawn(Piece.Color.WHITE), new Position(4, 1));

    assert board.tryMove(new Position(4, 1), new Position(4, 0))
        == Board.MoveResult.PROMOTION_REQUIRED;

    assert board.needsPromotion();
    assert board.promoteTo(Board.PromotionOption.KNIGHT);

    assertEquals(
        "....N...\n"
            + "........\n"
            + "........\n"
            + "........\n"
            + "........\n"
            + "........\n"
            + "........\n"
            + "........\n",
        board.toString()
    );
  }


  @Test
  public void kingCheckKing() {
    Board board = new Board();

    assert board.place(new King(Piece.Color.WHITE), new Position(3, 3));

    assert board.place(new King(Piece.Color.BLACK), new Position(4, 4));

    assertEquals(
        Board.CheckType.CHECKMATE,
        board.getCheck(Piece.Color.BLACK)
    );
  }

  @Test
  public void kingInCheckAvertByCapture() {
    Board board = new Board();

    assert board.place(new King(Piece.Color.WHITE), new Position(0, 0));
    assert board.place(new Pawn(Piece.Color.WHITE), new Position(3, 1));

    assert board.place(new Rook(Piece.Color.BLACK), new Position(2, 0));
    assert board.place(new Rook(Piece.Color.BLACK), new Position(2, 1));

    assertEquals(
        Board.CheckType.CHECK,
        board.getCheck(Piece.Color.WHITE)
    );
  }

  @Test
  public void kingInCheckmate() {
    Board board = new Board();

    assert board.place(new King(Piece.Color.WHITE), new Position(0, 0));

    assert board.place(new Rook(Piece.Color.BLACK), new Position(2, 0));
    assert board.place(new Rook(Piece.Color.BLACK), new Position(2, 1));

    assertEquals(
        Board.CheckType.CHECKMATE,
        board.getCheck(Piece.Color.WHITE)
    );
  }

  @Test
  public void kingInStalemate() {
    Board board = new Board();

    assert board.place(new King(Piece.Color.WHITE), new Position(0, 0));

    assert board.place(new Rook(Piece.Color.BLACK), new Position(2, 1));
    assert board.place(new Rook(Piece.Color.BLACK), new Position(1, 2));

    assertEquals(
        Board.CheckType.STALEMATE,
        board.getCheck(Piece.Color.WHITE)
    );
  }

  @Test
  public void pawnCheckKing() {
    Board board = new Board();

    assert board.place(new Pawn(Piece.Color.BLACK), new Position(2, 3));
    assert board.place(new King(Piece.Color.WHITE), new Position(3, 4));

    assertEquals(
        Board.CheckType.CHECK,
        board.getCheck(Piece.Color.WHITE)
    );

    assertEquals(
        Board.CheckType.NONE,
        board.getCheck(Piece.Color.BLACK)
    );
  }

  @Test
  public void pawnCaptureEnPassant() {
    Board board = new Board();

    assert board.place(new Pawn(Piece.Color.WHITE), new Position(4, 6));
    assert board.place(new Pawn(Piece.Color.BLACK), new Position(5, 4));

    assert board.tryMove(new Position(4, 6), new Position(4, 4)) == Board.MoveResult.OK;
    assert board.tryMove(new Position(5, 4), new Position(4, 5)) == Board.MoveResult.OK;

    assertEquals(
        "........\n"
            + "........\n"
            + "........\n"
            + "........\n"
            + "........\n"
            + "....p...\n"
            + "........\n"
            + "........\n",
        board.toString()
    );
  }

  @Test
  public void legalDestinationPawnNearEdge() {
    Board board = new Board();

    assert board.place(new Pawn(Piece.Color.WHITE), new Position(0, 6));

    assertEquals(
        new HashSet<>(Arrays.asList(
            new Position(0, 5),
            new Position(0, 4)
        )),
        board.legalDestinations(new Position(0, 6))
    );
  }

  @Test
  public void legalDestinationPawnEnPassant() {
    Board board = new Board();

    assert board.place(new Pawn(Piece.Color.WHITE), new Position(4, 6));
    assert board.place(new Pawn(Piece.Color.BLACK), new Position(5, 2));

    assert board.tryMove(new Position(4, 6), new Position(4, 4)) == Board.MoveResult.OK;
    assert board.tryMove(new Position(5, 2), new Position(5, 4)) == Board.MoveResult.OK;

    assertEquals(
        new HashSet<>(Arrays.asList(
            new Position(4, 3),
            new Position(5, 3)
        )),
        board.legalDestinations(new Position(4, 4))
    );
  }

  @Test
  public void legalDestinationsPawnSecondMove() {
    Board board = new Board();

    assert board.place(new Pawn(Piece.Color.WHITE), new Position(4, 6));
    assert board.tryMove(new Position(4, 6), new Position(4, 5)) == Board.MoveResult.OK;

    assertEquals(
        new HashSet<>(Collections.singletonList(
            new Position(4, 4)
        )),
        board.legalDestinations(new Position(4, 5))
    );
  }

  @Test
  public void legalDestinationsPawnFirstMove() {
    Board board = new Board();

    assert board.place(new Pawn(Piece.Color.WHITE), new Position(4, 6));

    assertEquals(
        new HashSet<>(Arrays.asList(
            new Position(4, 5),
            new Position(4, 4)
        )),
        board.legalDestinations(new Position(4, 6))
    );
  }

  @Test
  public void legalDestinationsKingCastlingWhileCheck() {
    Board board = new Board();

    assert board.place(new King(Piece.Color.WHITE), new Position(4, 7));

    assert board.place(new Rook(Piece.Color.WHITE), new Position(0, 7));
    assert board.place(new Rook(Piece.Color.WHITE), new Position(7, 7));

    assert board.place(new Rook(Piece.Color.BLACK), new Position(4, 0));

    assertEquals(
        new HashSet<>(Arrays.asList(
            new Position(3, 6),
            new Position(5, 6),

            new Position(3, 7),
            new Position(5, 7)
        )),
        board.legalDestinations(new Position(4, 7))
    );
  }

  @Test
  public void legalDestinationsKingCastling() {
    Board board = new Board();

    assert board.place(new King(Piece.Color.WHITE), new Position(4, 7));

    assert board.place(new Rook(Piece.Color.WHITE), new Position(0, 7));
    assert board.place(new Rook(Piece.Color.WHITE), new Position(7, 7));

    assertEquals(
        new HashSet<>(Arrays.asList(
            new Position(3, 6),
            new Position(4, 6),
            new Position(5, 6),

            new Position(3, 7),
            new Position(5, 7),

            new Position(2, 7),
            new Position(6, 7)
        )),
        board.legalDestinations(new Position(4, 7))
    );
  }


  @Test
  public void legalDestinationsKing() {
    Board board = new Board();

    assert board.place(new King(Piece.Color.WHITE), new Position(4, 4));

    assert board.place(new Rook(Piece.Color.BLACK), new Position(4, 3));
    assert board.place(new Rook(Piece.Color.BLACK), new Position(3, 3));

    assertEquals(
        new HashSet<>(Arrays.asList(
            new Position(5, 4),
            new Position(5, 5)
        )),
        board.legalDestinations(new Position(4, 4))
    );
  }

  @Test
  public void legalDestinationsQueen() {
    Board board = new Board();

    assert board.place(new Queen(Piece.Color.WHITE), new Position(4, 4));

    assertEquals(
        new HashSet<>(Arrays.asList(
            // Diagonal
            new Position(0, 0),
            new Position(1, 1),
            new Position(2, 2),
            new Position(3, 3),

            new Position(5, 5),
            new Position(6, 6),
            new Position(7, 7),


            new Position(7, 1),
            new Position(6, 2),
            new Position(5, 3),

            new Position(3, 5),
            new Position(2, 6),
            new Position(1, 7),


            // Line
            new Position(0, 4),
            new Position(1, 4),
            new Position(2, 4),
            new Position(3, 4),

            new Position(5, 4),
            new Position(6, 4),
            new Position(7, 4),


            new Position(4, 0),
            new Position(4, 1),
            new Position(4, 2),
            new Position(4, 3),

            new Position(4, 5),
            new Position(4, 6),
            new Position(4, 7)
        )),
        board.legalDestinations(new Position(4, 4))
    );
  }

  @Test
  public void legalDestinationsBishop() {
    Board board = new Board();

    assert board.place(new Bishop(Piece.Color.WHITE), new Position(4, 4));

    assertEquals(
        new HashSet<>(Arrays.asList(
            new Position(0, 0),
            new Position(1, 1),
            new Position(2, 2),
            new Position(3, 3),

            new Position(5, 5),
            new Position(6, 6),
            new Position(7, 7),


            new Position(7, 1),
            new Position(6, 2),
            new Position(5, 3),

            new Position(3, 5),
            new Position(2, 6),
            new Position(1, 7)
        )),
        board.legalDestinations(new Position(4, 4))
    );
  }

  @Test
  public void legalDestinationsRook() {
    Board board = new Board();

    assert board.place(new Rook(Piece.Color.WHITE), new Position(4, 4));

    assertEquals(
        new HashSet<>(Arrays.asList(
            new Position(0, 4),
            new Position(1, 4),
            new Position(2, 4),
            new Position(3, 4),

            new Position(5, 4),
            new Position(6, 4),
            new Position(7, 4),


            new Position(4, 0),
            new Position(4, 1),
            new Position(4, 2),
            new Position(4, 3),

            new Position(4, 5),
            new Position(4, 6),
            new Position(4, 7)
        )),
        board.legalDestinations(new Position(4, 4))
    );
  }

  @Test
  public void legalDestinationsKnightOutOfBounds() {
    Board board = new Board();

    assert board.place(new Knight(Piece.Color.WHITE), new Position(7, 6));

    assertEquals(
        new HashSet<>(Arrays.asList(
            new Position(6, 4),
            new Position(5, 5),
            new Position(5, 7)
        )),
        board.legalDestinations(new Position(7, 6))
    );
  }

  @Test
  public void legalDestinationsKnight() {
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
        board.legalDestinations(new Position(4, 4))
    );
  }

  @Test
  public void movePawnAttackingFriendlyToLeftSide() {
    Board board = new Board();
    assert board.place(new Pawn(Piece.Color.WHITE), new Position(4, 3));
    assert board.place(new Pawn(Piece.Color.WHITE), new Position(3, 2));

    assert board.tryMove(new Position(4, 3), new Position(3, 2)) == Board.MoveResult.INVALID_MOVE;

    assertEquals(
        "........\n"
            + "........\n"
            + "...P....\n"
            + "....P...\n"
            + "........\n"
            + "........\n"
            + "........\n"
            + "........\n",
        board.toString()
    );
  }

  @Test
  public void movePawnAttackingHostileToLeftSide() {
    Board board = new Board();
    assert board.place(new Pawn(Piece.Color.WHITE), new Position(4, 3));
    assert board.place(new Pawn(Piece.Color.BLACK), new Position(3, 2));

    assert board.tryMove(new Position(4, 3), new Position(3, 2)) == Board.MoveResult.OK;

    assertEquals(
        "........\n"
            + "........\n"
            + "...P....\n"
            + "........\n"
            + "........\n"
            + "........\n"
            + "........\n"
            + "........\n",
        board.toString()
    );
  }

  @Test
  public void movePawnAttackingFriendlyToRightSide() {
    Board board = new Board();
    assert board.place(new Pawn(Piece.Color.WHITE), new Position(2, 3));
    assert board.place(new Pawn(Piece.Color.WHITE), new Position(3, 2));

    assert board.tryMove(new Position(2, 3), new Position(3, 2)) == Board.MoveResult.INVALID_MOVE;

    assertEquals(
        "........\n"
            + "........\n"
            + "...P....\n"
            + "..P.....\n"
            + "........\n"
            + "........\n"
            + "........\n"
            + "........\n",
        board.toString()
    );
  }

  @Test
  public void movePawnAttackingHostileToRightSide() {
    Board board = new Board();
    assert board.place(new Pawn(Piece.Color.WHITE), new Position(2, 3));
    assert board.place(new Pawn(Piece.Color.BLACK), new Position(3, 2));

    assert board.tryMove(new Position(2, 3), new Position(3, 2)) == Board.MoveResult.OK;

    assertEquals(
        "........\n"
            + "........\n"
            + "...P....\n"
            + "........\n"
            + "........\n"
            + "........\n"
            + "........\n"
            + "........\n",
        board.toString()
    );
  }

  @Test
  public void movePawnInvalidOrder() {
    Board board = new Board();
    assert board.place(new Pawn(Piece.Color.WHITE), new Position(6, 6));
    assert board.place(new Pawn(Piece.Color.BLACK), new Position(3, 1));

    assert board.tryMove(new Position(3, 1), new Position(6, 2)) == Board.MoveResult.INVALID_MOVE;
    assert board.tryMove(new Position(6, 6), new Position(6, 5)) == Board.MoveResult.OK;

    assertEquals(
        "........\n"
            + "...p....\n"
            + "........\n"
            + "........\n"
            + "........\n"
            + "......P.\n"
            + "........\n"
            + "........\n",
        board.toString()
    );
  }

  @Test
  public void moveBlackPawnInvalidDirectionBack() {
    Board board = new Board();
    assert board.place(new Pawn(Piece.Color.BLACK), new Position(3, 2));

    assert board.tryMove(new Position(3, 2), new Position(3, 1)) == Board.MoveResult.INVALID_MOVE;

    assertEquals(
        "........\n"
            + "........\n"
            + "...p....\n"
            + "........\n"
            + "........\n"
            + "........\n"
            + "........\n"
            + "........\n",
        board.toString()
    );
  }

  @Test
  public void moveBlackPawnInvalidDirectionSide() {
    Board board = new Board();
    assert board.place(new Pawn(Piece.Color.BLACK), new Position(3, 2));

    assert board.tryMove(new Position(3, 2), new Position(4, 2)) == Board.MoveResult.INVALID_MOVE;

    assertEquals(
        "........\n"
            + "........\n"
            + "...p....\n"
            + "........\n"
            + "........\n"
            + "........\n"
            + "........\n"
            + "........\n",
        board.toString()
    );
  }

  @Test
  public void moveWhitePawnInvalidDirectionBack() {
    Board board = new Board();
    assert board.place(new Pawn(Piece.Color.WHITE), new Position(6, 6));

    assert board.tryMove(new Position(6, 6), new Position(6, 7)) == Board.MoveResult.INVALID_MOVE;

    assertEquals(
        "........\n"
            + "........\n"
            + "........\n"
            + "........\n"
            + "........\n"
            + "........\n"
            + "......P.\n"
            + "........\n",
        board.toString()
    );
  }

  @Test
  public void moveWhitePawnInvalidDirectionSide() {
    Board board = new Board();
    assert board.place(new Pawn(Piece.Color.WHITE), new Position(6, 6));

    assert board.tryMove(new Position(6, 6), new Position(5, 6)) == Board.MoveResult.INVALID_MOVE;

    assertEquals(
        "........\n"
            + "........\n"
            + "........\n"
            + "........\n"
            + "........\n"
            + "........\n"
            + "......P.\n"
            + "........\n",
        board.toString()
    );
  }

  @Test
  public void movePawn() {
    Board board = new Board();
    assert board.place(new Pawn(Piece.Color.WHITE), new Position(6, 6));
    assert board.place(new Pawn(Piece.Color.BLACK), new Position(3, 1));

    assert board.tryMove(new Position(6, 6), new Position(6, 5)) == Board.MoveResult.OK;
    assert board.tryMove(new Position(3, 1), new Position(3, 2)) == Board.MoveResult.OK;

    assertEquals(
        "........\n"
            + "........\n"
            + "...p....\n"
            + "........\n"
            + "........\n"
            + "......P.\n"
            + "........\n"
            + "........\n",
        board.toString()
    );
  }

  @Test
  public void placeBlackPawn() {
    Board board = new Board();

    assert board.place(new Pawn(Piece.Color.BLACK), new Position(3, 2));

    assertEquals(
        "........\n"
            + "........\n"
            + "...p....\n"
            + "........\n"
            + "........\n"
            + "........\n"
            + "........\n"
            + "........\n",
        board.toString()
    );
  }

  @Test
  public void placeWhitePawn() {
    Board board = new Board();

    assert board.place(new Pawn(Piece.Color.WHITE), new Position(5, 6));

    assertEquals(
        "........\n"
            + "........\n"
            + "........\n"
            + "........\n"
            + "........\n"
            + "........\n"
            + ".....P..\n"
            + "........\n",
        board.toString()
    );
  }
}