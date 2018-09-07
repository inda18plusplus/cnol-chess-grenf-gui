import piece.Piece;

public class Board {
  private Piece[][] pieces;

  Board() {
    this.pieces = new Piece[8][8];
  }


  public void place(Piece piece, BoardPosition position) {
    assert (piece != null);

    this.setPiece(piece, position);
  }


  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    for (int row = 0; row < 8; row++) {
      for (int col = 0; col < 8; col++) {
        Piece piece = this.pieces[row][col];

        char representation;
        if (piece != null) {
          representation = piece.toChar();
        } else {
          representation = '.';
        }

        builder.append(representation);
      }

      builder.append('\n');
    }

    return builder.toString();
  }

  private boolean onBoard(BoardPosition position) {
    int row = position.getRow();
    int column = position.getColumn();

    return 0 <= row && row < 8 &&
      0 <= column && column < 8;
  }

  private void setPiece(Piece piece, BoardPosition position) {
    assert (onBoard(position));

    this.pieces[position.getRow()][position.getColumn()] = piece;
  }

  /**
   * Moves a piece on the board.
   * @param piecePosition The position of the piece to move.
   * @param newPosition The position to move the piece to.
   * @return true if the move is valid, false otherwise.
   */
  public boolean move(BoardPosition piecePosition, BoardPosition newPosition) {
    if (isValidMove(piecePosition, newPosition)) {
      Piece sourcePiece = this.getPiece(piecePosition);

      this.setPiece(sourcePiece, newPosition);
      this.setPiece(null, piecePosition);

      return true;
    } else {
      return false;
    }
  }

  private Piece getPiece(BoardPosition position) {
    assert(onBoard(position));

    return this.pieces[position.getRow()][position.getColumn()];
  }

  private boolean isValidMove(BoardPosition piecePosition, BoardPosition newPosition) {
    if (onBoard(piecePosition) && onBoard(newPosition)) {
      Piece piece = this.getPiece(piecePosition);

      if (piece != null) {
        return true;
      }
    }

    return false;
  }
}
