import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import piece.Bishop;
import piece.King;
import piece.Knight;
import piece.Move;
import piece.Pawn;
import piece.Piece;
import piece.Position;
import piece.Queen;
import piece.Rook;

public class Board {
  private Piece[][] pieces;
  private Piece.Color currentPlayingColor;

  /**
   * Creates an empty board.
   */
  public Board() {
    this.pieces = new Piece[8][8];
    this.currentPlayingColor = Piece.Color.WHITE;
  }


  /**
   * Creates a board with a predefined layout.
   *
   * @param layout The layout to use
   */
  public Board(Layout layout) {
    this();

    this.applyLayout(layout);
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

        sourcePiece.onMove();

        this.nextColor();

        return true;
      }
    }

    return false;
  }

  /**
   * Compute the available legal destinations of a piece.
   *
   * @param piecePosition The position of the piece
   * @return The destinations
   */
  public Set<Position> availableDestinations(Position piecePosition) {
    Set<Position> destinations = new HashSet<>();

    if (onBoard(piecePosition)) {
      Piece piece = this.getPiece(piecePosition);

      if (piece != null) {
        Set<Move> moves = piece.getMoveSet();

        for (Move move : moves) {
          Set<Position> availablePositions = move.expandPositions(piecePosition, 8, 8,
                                                                  this::getPiece);
          destinations.addAll(availablePositions);
        }
      }
    }

    return destinations;
  }

  /**
   * Get the color of the current player.
   *
   * @return the color
   */
  public Piece.Color getCurrentColor() {
    return this.currentPlayingColor;
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


  private void applyLayout(Layout layout) {
    switch (layout) {
      case CLASSIC:
        this.layoutFromText(
            "rnbqkbnr\n"
                + "pppppppp\n"
                + "        \n"
                + "        \n"
                + "        \n"
                + "        \n"
                + "PPPPPPPP\n"
                + "RNBQKBNR\n"
        );
        break;

      default:
        break;
    }
  }


  private void layoutFromText(String text) {
    int column = 0;
    int row = 0;

    for (int i = 0; i < text.length(); i++) {
      char character = text.charAt(i);

      switch (character) {
        case '\n':
          row++;
          column = 0;
          break;
        default:
          Piece piece = this.pieceFromCharacter(character);
          this.place(piece, new Position(column, row));

          column++;
          break;
      }
    }
  }


  private Piece pieceFromCharacter(char character) {
    Piece.Color color;
    if (Character.isUpperCase(character)) {
      color = Piece.Color.WHITE;
    } else {
      color = Piece.Color.BLACK;
    }

    switch (Character.toLowerCase(character)) {
      case 'p':
        return new Pawn(color);
      case 'n':
        return new Knight(color);
      case 'r':
        return new Rook(color);
      case 'b':
        return new Bishop(color);
      case 'q':
        return new Queen(color);
      case 'k':
        return new King(color);
      default:
        return null;
    }
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


  private void nextColor() {
    this.currentPlayingColor = this.currentPlayingColor.opposite();
  }


  private boolean isValidMove(Position piecePosition, Position newPosition) {
    Set<Position> availablePositions = availableDestinations(piecePosition);

    return availablePositions.contains(newPosition);
  }


  /**
   * Determines if a player of a specific color is in check.
   *
   * @param color The color of the player
   * @return Check or not
   */
  public boolean isColorInCheck(Piece.Color color) {
    return !this.getCheckedTiles(color).isEmpty();
  }


  private Set<Position> getCheckedTiles(Piece.Color color) {
    Set<Position> attackedTiles = this.getControlledTiles(color.opposite());
    Set<Position> nonExpendableTiles = this.getNonExpendableTiles(color);

    attackedTiles.retainAll(nonExpendableTiles);

    return attackedTiles;
  }


  private Set<Position> getTilesWhere(Function<Piece, Boolean> predicate) {
    Set<Position> tiles = new HashSet<>();

    for (int row = 0; row < 8; row++) {
      for (int col = 0; col < 8; col++) {
        Position tile = new Position(col, row);
        Piece piece = this.getPiece(tile);

        if (piece != null && predicate.apply(piece)) {
          tiles.add(tile);
        }
      }
    }

    return tiles;
  }


  private Set<Position> getControlledTiles(Piece.Color color) {
    return this.getTilesWhere(piece -> piece.isOfColor(color))
        .stream()
        .map(this::availableDestinations)
        .flatMap(Set::stream)
        .collect(Collectors.toSet());
  }

  private Set<Position> getNonExpendableTiles(Piece.Color color) {
    return this.getTilesWhere(piece -> piece.isOfColor(color) && !piece.isExpendable());
  }


  enum Layout {
    CLASSIC
  }
}
