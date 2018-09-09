import java.util.Scanner;
import java.util.Set;
import piece.Bishop;
import piece.King;
import piece.Knight;
import piece.Pawn;
import piece.Piece;
import piece.Piece.Color;
import piece.Position;
import piece.Queen;
import piece.Rook;


/**
 * Commandline interface for the chess library.
 */
class Chess {

  private Board board = new Board(Board.Layout.CLASSIC);

  /**
   * Starts a game of chess.
   * @param args Commandline arguments
   */
  public static void main(String[] args) {
    System.out.println("Hello, chess!");

    Scanner scanner = new Scanner(System.in);

    Chess chess = new Chess();

    chess.interpretCommand("d");

    System.out.print("> ");
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();

      chess.interpretCommand(line);

      System.out.print("> ");
    }
  }


  private void interpretCommand(String line) {
    String[] words = line.trim().replaceAll(" +", " ").split(" ");

    if (words.length == 0) {
      return;
    }

    String command = words[0];

    switch (command) {
      // Display the board
      case "d":
        System.out.println(this.formattedBoard());
        break;

        // Move a piece
      case "m":
        if (words.length == 3) {
          board.move(parsePosition(words[1]), parsePosition(words[2]));

          if (board.needsPromotion()) {
            System.out.println("Promotion required!");
          }
        } else {
          System.out.println("Invalid number of arguments!");
        }
        break;

        // Add a piece
      case "a":
        if (words.length == 3) {
          Piece piece = parsePiece(words[1]);
          Position position = parsePosition(words[2]);

          this.board.place(piece, position);
        }
        break;

        // Show possible moves
      case "p":
        if (words.length == 2) {
          Position position = parsePosition(words[1]);

          Set<Position> possible = board.legalDestinations(position);

          StringBuffer buffer = new StringBuffer(this.formattedBoard());

          for (Position pos : possible) {
            int index = (pos.getRow() + 1) * 10 + pos.getColumn() + 1;

            buffer.setCharAt(index, 'X');
          }

          System.out.println(buffer);
        }
        break;

        // Determine if check
      case "c":
        System.out.println("White in check: " + board.getCheckType(Color.WHITE));
        System.out.println("Black in check: " + board.getCheckType(Color.BLACK));
        break;

        // Promote a piece
      case "pr":
        if (words.length == 2) {
          Board.PromotionOption promotionOption;
          switch (words[1].toLowerCase()) {
            case "q":
              promotionOption = Board.PromotionOption.QUEEN;
              break;
            case "n":
              promotionOption = Board.PromotionOption.KNIGHT;
              break;
            case "b":
              promotionOption = Board.PromotionOption.BISHOP;
              break;
            case "r":
              promotionOption = Board.PromotionOption.ROOK;
              break;
            default:
              promotionOption = Board.PromotionOption.QUEEN;
          }

          board.promoteTo(promotionOption);
        }
        break;

      default:
        break;
    }
  }

  private Piece parsePiece(String word) {
    switch (word) {
      case "p": return new Pawn(Color.BLACK);
      case "P": return new Pawn(Color.WHITE);

      case "n": return new Knight(Color.BLACK);
      case "N": return new Knight(Color.WHITE);

      case "r": return new Rook(Color.BLACK);
      case "R": return new Rook(Color.WHITE);

      case "b": return new Bishop(Color.BLACK);
      case "B": return new Bishop(Color.WHITE);

      case "q": return new Queen(Color.BLACK);
      case "Q": return new Queen(Color.WHITE);

      case "k": return new King(Color.BLACK);
      case "K": return new King(Color.WHITE);

      default: return null;
    }
  }

  private String formattedBoard() {
    StringBuilder builder = new StringBuilder(board.toString());

    for (int row = 7; row >= 0; row--) {
      builder.insert(row * 9, Integer.toString(row));
    }

    builder.insert(0, " 01234567\n");

    builder.append("Current turn: ");
    builder.append(board.getCurrentColor().toString());

    return builder.toString();
  }

  private Position parsePosition(String word) {
    if (word.length() == 2) {
      int x = Integer.parseInt(word.substring(0, 1));
      int y = Integer.parseInt(word.substring(1, 2));
      return new Position(x, y);
    }
    return null;
  }
}
