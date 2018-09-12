import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import piece.Bishop;
import piece.King;
import piece.Knight;
import piece.Pawn;
import piece.Piece;
import piece.Position;
import piece.Queen;
import piece.Rook;
import piece.move.Move;

public class Board {
  private Piece[][] pieces;
  private Piece.Color currentPlayingColor;

  private List<Position> promotingPieces = new ArrayList<>();

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
   * Creates a copy of a board.
   *
   * @param board The board to copy.
   */
  private Board(Board board) {
    this.pieces = new Piece[8][8];

    for (Position position : board.promotingPieces) {
      this.promotingPieces.add(new Position(position));
    }

    this.currentPlayingColor = board.currentPlayingColor;

    for (int row = 0; row < 8; row++) {
      for (int col = 0; col < 8; col++) {
        Piece piece = board.pieces[row][col];

        if (piece != null) {
          this.pieces[row][col] = piece.makeCopy();
        }
      }
    }
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
      // Make sure that the user cannot interfere with the game logic.
      Piece newPiece = piece.makeCopy();
      this.setPiece(newPiece, position);

      return true;
    }

    return false;
  }

  /**
   * Moves a piece on the board.
   *
   * @param piecePosition The position of the piece to piece.move.
   * @param newPosition   The position to piece.move the piece to.
   * @return true if the piece.move is valid, false otherwise.
   */
  public boolean move(Position piecePosition, Position newPosition) {
    if (!this.needsPromotion() && isValidMove(piecePosition, newPosition)) {
      Piece sourcePiece = this.getPiece(piecePosition);

      if (sourcePiece.isOfColor(this.currentPlayingColor)) {
        Move move = this.availableDestinations(piecePosition).get(newPosition);

        move.perform(piecePosition, newPosition, this::getPiece, this::setPiece);

        if (sourcePiece.canPromote()) {
          int promoteRow = sourcePiece.getColor() == Piece.Color.BLACK ? 7 : 0;

          if (newPosition.getRow() == promoteRow) {
            this.promote(newPosition);
          }
        }

        this.nextColor();
        return true;
      }
    }

    return false;
  }

  /**
   * Determines if a piece needs promotion.
   *
   * @return True if a piece needs promotion
   */
  public boolean needsPromotion() {
    return !this.promotingPieces.isEmpty();
  }

  /**
   * Compute the available legal destinations of a piece.
   *
   * @param piecePosition The position of the piece
   * @return The destinations
   */
  public Set<Position> legalDestinations(Position piecePosition) {
    Set<Position> availablePositions = this.availableDestinations(piecePosition).keySet();

    Piece piece = this.getPiece(piecePosition);

    Set<Position> positionsResultingInCheck = new HashSet<>();

    for (Position position : availablePositions) {
      Board temporaryBoard = new Board(this);

      temporaryBoard.move(piecePosition, position);

      if (temporaryBoard.isColorInCheck(piece.getColor())) {
        positionsResultingInCheck.add(position);
      }
    }

    availablePositions.removeAll(positionsResultingInCheck);

    return availablePositions;
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

  /**
   * Determines if there is, and the severity of, a check.
   *
   * @param color The color of the player to
   * @return The type of check
   */
  public CheckType getCheck(Piece.Color color) {
    Map<Position, Set<Position>> possibleMoves = this.getAllPossibleMoves(color);

    if (this.isColorInCheck(color)) {
      if (possibleMoves.isEmpty()) {
        return CheckType.CHECKMATE;
      } else {
        return CheckType.CHECK;
      }
    } else {
      if (possibleMoves.isEmpty()) {
        return CheckType.STALEMATE;
      } else {
        return CheckType.NONE;
      }
    }
  }

  /**
   * Promotes the next piece in the queue.
   *
   * @param newPieceType The type of the new piece
   * @return If the promotion was successful or not
   */
  public boolean promoteTo(PromotionOption newPieceType) {
    if (this.needsPromotion()) {
      Position position = this.promotingPieces.get(0);
      this.promotingPieces.remove(0);

      Piece piece = this.getPiece(position);
      if (piece != null) {
        Piece newPiece = newPieceType.toPiece(piece.getColor());
        this.setPiece(newPiece, position);
        return true;
      }
    }

    return false;
  }

  /**
   * For each of a color's pieces return a set of legal moves.
   *
   * @param color The color of the pieces
   * @return The moves of the pieces
   */
  private Map<Position, Set<Position>> getAllPossibleMoves(Piece.Color color) {
    Set<Position> friendlyTiles = this.getTilesWherePiece(piece -> piece.isOfColor(color));

    Map<Position, Set<Position>> possibleMoves = new HashMap<>();

    for (Position tile : friendlyTiles) {
      Set<Position> legalMoves = this.legalDestinations(tile);

      if (!legalMoves.isEmpty()) {
        possibleMoves.put(tile, legalMoves);
      }
    }

    return possibleMoves;
  }

  private void promote(Position piecePosition) {
    this.promotingPieces.add(piecePosition);
  }

  private boolean isColorInCheck(Piece.Color color) {
    return !this.getCheckedTiles(color).isEmpty();
  }


  private Map<Position, Move> availableDestinations(Position piecePosition) {
    Map<Position, Move> destinations = new HashMap<>();

    if (onBoard(piecePosition)) {
      Piece piece = this.getPiece(piecePosition);

      if (piece != null) {
        Set<Move> moves = piece.getMoveSet();

        for (Move move : moves) {
          Set<Position> availablePositions =
              move.getDestinations(piecePosition, 8, 8, this::getPiece);

          for (Position position : availablePositions) {
            destinations.put(position, move);
          }
        }
      }
    }

    return destinations;
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

    this.updateThreatenedTiles();
  }

  private void updateThreatenedTiles() {
    Piece.Color[] colors = {Piece.Color.BLACK, Piece.Color.WHITE};

    for (Piece.Color color : colors) {
      Set<Position> friendlyTiles = this.getTilesWherePiece(piece -> piece.isOfColor(color));
      Set<Position> threatenedTiles = this.getControlledTiles(color.opposite());

      for (Position tile : friendlyTiles) {
        boolean threatened = threatenedTiles.contains(tile);

        this.getPiece(tile).setThreatened(threatened);
      }
    }
  }

  private Piece getPiece(Position position) {
    if (!onBoard(position)) {
      return null;
    }

    return this.pieces[position.getRow()][position.getColumn()];
  }

  private void nextColor() {
    Set<Position> endedTiles =
        this.getTilesWherePiece(piece -> piece.isOfColor(this.currentPlayingColor));

    for (Position tile : endedTiles) {
      this.getPiece(tile).onTurnEnd();
    }

    this.currentPlayingColor = this.currentPlayingColor.opposite();
  }

  private boolean isValidMove(Position piecePosition, Position newPosition) {
    Set<Position> availablePositions = availableDestinations(piecePosition).keySet();

    return availablePositions.contains(newPosition);
  }

  private Set<Position> getCheckedTiles(Piece.Color color) {
    Set<Position> attackedTiles = this.getControlledTiles(color.opposite());
    Set<Position> nonExpendableTiles = this.getNonExpendableTiles(color);

    attackedTiles.retainAll(nonExpendableTiles);

    return attackedTiles;
  }

  private Set<Position> getTilesWherePiece(Function<Piece, Boolean> predicate) {
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
    return this.getTilesWherePiece(piece -> piece.isOfColor(color))
        .stream()
        .map(this::availableDestinations)
        .map(Map::keySet)
        .flatMap(Set::stream)
        .collect(Collectors.toSet());
  }

  private Set<Position> getNonExpendableTiles(Piece.Color color) {
    return this.getTilesWherePiece(piece -> piece.isOfColor(color) && !piece.isExpendable());
  }


  enum Layout {
    CLASSIC
  }

  public enum CheckType {
    NONE,
    CHECK,
    CHECKMATE,
    STALEMATE
  }

  public enum PromotionOption {
    QUEEN,
    BISHOP,
    ROOK,
    KNIGHT;

    /**
     * Creates the corresponding piece.
     *
     * @param color The color of the new piece.
     * @return The new piece
     */
    Piece toPiece(Piece.Color color) {
      switch (this) {
        case QUEEN:
          return new Queen(color);
        case BISHOP:
          return new Bishop(color);
        case ROOK:
          return new Rook(color);
        case KNIGHT:
          return new Knight(color);
        default:
          return null;
      }
    }
  }
}
