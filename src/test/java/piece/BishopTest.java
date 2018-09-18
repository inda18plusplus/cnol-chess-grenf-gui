package piece;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

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