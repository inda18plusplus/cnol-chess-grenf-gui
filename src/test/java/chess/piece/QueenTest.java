package chess.piece;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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