package chess.piece;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class QueenTest {
  @Test
  public void toChar() {
    whiteCharacterRepresentation();
    blackCharacterRepresentation();
  }

  private void blackCharacterRepresentation() {
    Queen queen = new Queen(Piece.Color.BLACK);

    assertEquals('q', queen.toChar());
  }

  private void whiteCharacterRepresentation() {
    Queen queen = new Queen(Piece.Color.WHITE);

    assertEquals('Q', queen.toChar());
  }
}