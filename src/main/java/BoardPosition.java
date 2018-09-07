public class BoardPosition {
  private int column;
  private int row;

  public BoardPosition(int column, int row) {
    this.column = column;
    this.row = row;
  }

  public int getRow() {
    return this.row;
  }

  public int getColumn() {
    return this.column;
  }
}
