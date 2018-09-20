package chess.piece;

import java.util.Objects;

public class Position {
  private final int column;
  private final int row;

  public Position(int column, int row) {
    this.column = column;
    this.row = row;
  }

  public Position(Position other) {
    this.column = other.column;
    this.row = other.row;
  }

  public int getRow() {
    return this.row;
  }

  public int getColumn() {
    return this.column;
  }

  public Position add(Position position) {
    return new Position(this.column + position.column, this.row + position.row);
  }

  public boolean inBound(int lowerColumn, int lowerRow, int upperColumn, int upperRow) {
    return lowerColumn <= column && column < upperColumn
        && lowerRow <= row && row < upperRow;
  }

  @Override public boolean equals(Object o) {
    if (o instanceof Position) {
      Position other = (Position)o;

      if (other == this) {
        return true;
      } else {
        return column == other.column && row == other.row;
      }
    }

    return false;
  }

  @Override public int hashCode() {
    return Objects.hash(column, row);
  }

  @Override public String toString() {
    return "(" + this.column + ", " + this.row + ")";
  }
}
