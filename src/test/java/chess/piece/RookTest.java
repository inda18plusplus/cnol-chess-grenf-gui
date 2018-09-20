package chess.piece;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RookTest {
  @Test
  public void toChar() {
    whiteCharacterRepresentation();
    blackCharacterRepresentation();
  }

  private void blackCharacterRepresentation() {
    Rook rook = new Rook(Piece.Color.BLACK);

    assertEquals('r', rook.toChar());
  }

  private void whiteCharacterRepresentation() {
    Rook rook = new Rook(Piece.Color.WHITE);

    assertEquals('R', rook.toChar());
  }
}