package piece;

public class Pawn extends Piece {
  public Pawn(Color color) {
    super(color);
  }

  @Override
  public char toChar() {
    switch (super.color) {
      case BLACK:
        return 'p';
      case WHITE:
        return 'P';
        default:
          return '?';
    }
  }
}
