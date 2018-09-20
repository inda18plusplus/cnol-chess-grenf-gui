package chess.piece;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BishopTest {
  @Test
  public void toChar() {
    whiteCharacterRepresentation();
    blackCharacterRepresentation();
  }

  private void blackCharacterRepresentation() {
    Bishop bishop = new Bishop(Piece.Color.BLACK);

    assertEquals('b', bishop.toChar());
  }

  private void whiteCharacterRepresentation() {
    Bishop bishop = new Bishop(Piece.Color.WHITE);

    assertEquals('B', bishop.toChar());
  }
}