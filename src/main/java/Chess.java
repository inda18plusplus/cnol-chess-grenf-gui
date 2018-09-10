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
   *
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
      System.out.println();

      try {
        chess.interpretCommand(line);
      } catch (Exception e) {
        e.printStackTrace();
      }

      System.out.print("\n> ");
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
          if (!board.move(parsePosition(words[1]), parsePosition(words[2]))) {
            System.out.println("Invalid move!");
          } else {
            if (board.needsPromotion()) {
              System.out.println("Promotion required!");
            } else {
              this.interpretCommand("c");
            }
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
            int index = (pos.getRow() + 1) * 13 + pos.getColumn();

            buffer.setCharAt(index, 'X');
          }

          System.out.println(buffer);
        }
        break;

      // Determine if check
      case "c":
        System.out.println("White in check: " + board.getCheck(Color.WHITE));
        System.out.println("Black in check: " + board.getCheck(Color.BLACK));
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

          this.interpretCommand("c");
        }
        break;

      // Help
      case "h":
      case "help":
        System.out.println("===help===");
        System.out.println("Coordinates are given in the format '[column][row]' ie. 47 denotes "
            + "the 4th column from the left and the 7th row from the top (counting starts from 0,"
            + " as is expected");

        System.out.println("===commands===");
        System.out.println("d - displays the board as it is right currently");
        System.out.println("m [from] [to] - move a piece at [from] to [to]");
        System.out.println("p [where] - prints all possible destinations for a piece located at "
            + "[where]");
        System.out.println("c - display any checks");
        System.out.println("pr [piece] - Promotes a piece to a new [piece]. [piece] can be any of"
            + " 'q' for queen, 'b' for bishop, 'r' for rook, 'n' for a knight");
        System.out.println("a [what] [where] - adds a new piece to the board, capital letters are"
            + " interpreted as white pieces and lowercase as black.");
        break;

      default:
        System.err.println("Invalid command: '" + command + "'");
        break;
    }
  }

  private Piece parsePiece(String word) {
    switch (word) {
      case "p":
        return new Pawn(Color.BLACK);
      case "P":
        return new Pawn(Color.WHITE);

      case "n":
        return new Knight(Color.BLACK);
      case "N":
        return new Knight(Color.WHITE);

      case "r":
        return new Rook(Color.BLACK);
      case "R":
        return new Rook(Color.WHITE);

      case "b":
        return new Bishop(Color.BLACK);
      case "B":
        return new Bishop(Color.WHITE);

      case "q":
        return new Queen(Color.BLACK);
      case "Q":
        return new Queen(Color.WHITE);

      case "k":
        return new King(Color.BLACK);
      case "K":
        return new King(Color.WHITE);

      default:
        return null;
    }
  }

  private String formattedBoard() {
    StringBuilder builder = new StringBuilder(board.toString());

    for (int row = 7; row >= 0; row--) {
      builder.insert((row) * 9 + 8, " " + Integer.toString(row));
      builder.insert(row * 9, Integer.toString(row) + " ");
    }

    builder.insert(0, "  01234567\n");
    builder.insert(builder.length() - 1, "\n  01234567\n");

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
