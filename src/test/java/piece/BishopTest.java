package piece;

import org.junit.Test;
import static org.junit.Assert.*;

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