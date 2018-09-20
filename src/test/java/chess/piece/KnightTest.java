package chess.piece;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KnightTest {

  @Test
  public void toChar() {
    whiteCharacterRepresentation();
    blackCharacterRepresentation();
  }

  private void blackCharacterRepresentation() {
    Knight knight = new Knight(Piece.Color.BLACK);

    assertEquals('n', knight.toChar());
  }

  private void whiteCharacterRepresentation() {
    Knight knight = new Knight(Piece.Color.WHITE);

    assertEquals('N', knight.toChar());
  }
}