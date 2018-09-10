package exceptions;

import piece.Position;

public class OutOfBoundsException extends RuntimeException {
  public OutOfBoundsException(Position position) {
    super("Position out of bounds: " + position.toString());
  }
}
