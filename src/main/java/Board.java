import java.util.HashSet;
import java.util.List;
import java.util.Set;
import piece.Move;
import piece.Piece;
import piece.Position;

public class Board {
  private Piece[][] pieces;
  private Piece.Color currentPlayingColor;

  Board() {
    this.pieces = new Piece[8][8];
    this.currentPlayingColor = Piece.Color.WHITE;
  }


  /**
   * Places an additional piece on the board.
   *
   * @param piece    The piece
   * @param position Where to place
   * @return true if the placement was successful
   */
  public boolean place(Piece piece, Position position) {
    Piece targetPiece = this.getPiece(position);

    if (piece != null && targetPiece == null) {
      this.setPiece(piece, position);
      return true;
    }

    return false;
  }

  /**
   * Moves a piece on the board.
   *
   * @param piecePosition The position of the piece to move.
   * @param newPosition   The position to move the piece to.
   * @return true if the move is valid, false otherwise.
   */
  public boolean move(Position piecePosition, Position newPosition) {
    if (isValidMove(piecePosition, newPosition)) {
      Piece sourcePiece = this.getPiece(piecePosition);

      if (sourcePiece.isOfColor(this.currentPlayingColor)) {
        this.setPiece(sourcePiece, newPosition);
        this.setPiece(null, piecePosition);
        
        this.nextColor();
        
        return true;
      }
    }

    return false;
  }

  private void nextColor() {
    if (this.currentPlayingColor == Piece.Color.BLACK) {
      this.currentPlayingColor = Piece.Color.WHITE;
    } else {
      this.currentPlayingColor = Piece.Color.BLACK;
    }
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

  private boolean onBoard(Position position) {
    return position.inBound(0, 0, 8, 8);
  }

  private void setPiece(Piece piece, Position position) {
    assert (onBoard(position));

    this.pieces[position.getRow()][position.getColumn()] = piece;
  }


  private Piece getPiece(Position position) {
    assert (onBoard(position));

    return this.pieces[position.getRow()][position.getColumn()];
  }

  private boolean isValidMove(Position piecePosition, Position newPosition) {
    if (onBoard(piecePosition) && onBoard(newPosition)) {
      Piece piece = this.getPiece(piecePosition);

      if (piece != null) {
        Set<Move> moves = piece.getMoveSet();

        Set<Position> availablePositions = resultingPositions(piecePosition, moves);

        if (availablePositions.contains(newPosition)) {
          return true;
        }
      }
    }

    return false;
  }

  private Set<Position> resultingPositions(Position piecePosition, Set<Move> moves) {
    Set<Position> positions = new HashSet<>();

    for (Move move : moves) {
      List<Position> availablePositions = move.expandPositions(piecePosition, 8, 8,
                                                               this::getPiece);
      for (Position position : availablePositions) {
        positions.add(position);
      }
    }

    return positions;
  }
}
