package piece;

public abstract class Piece {
  public enum Color {
    BLACK, WHITE
  }

  protected Color color;

  Piece(Color color) {
    this.color = color;
  }

  public abstract char toChar();
}
