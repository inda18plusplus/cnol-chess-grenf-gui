package chess.piece;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class KingTest {
  @Test
  public void toChar() {
    whiteCharacterRepresentation();
    blackCharacterRepresentation();
  }

  private void blackCharacterRepresentation() {
    King king = new King(Piece.Color.BLACK);

    assertEquals('k', king.toChar());
  }

  private void whiteCharacterRepresentation() {
    King king = new King(Piece.Color.WHITE);

    assertEquals('K', king.toChar());
  }
}