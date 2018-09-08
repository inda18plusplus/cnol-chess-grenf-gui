import java.util.Scanner;
import java.util.Set;
import piece.Knight;
import piece.Pawn;
import piece.Piece;
import piece.Piece.Color;
import piece.Position;


/**
 * Commandline interface for the chess library.
 */
public class Chess {

  private Board board = new Board();

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
    String[] words = line.split(" ");

    if (words.length == 0) {
      return;
    }

    String command = words[0];

    switch (command) {
        // Display the board
      case "d":
        this.displayBoard();
        break;

        // Move a piece
      case "m":
        if (words.length == 3) {
          board.move(parsePosition(words[1]), parsePosition(words[2]));
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

          Set<Position> possible = board.availableDestinations(position);

          StringBuffer buffer = new StringBuffer(board.toString());

          for (Position pos : possible) {
            int index = (pos.getRow() + 1) * 9 + pos.getColumn() + 1;

            buffer.setCharAt(index, 'X');
          }

          System.out.println(buffer);
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

      default: return null;
    }
  }

  private void displayBoard() {
    System.out.println(this.board);
  }

  private Position parsePosition(String word) {
    int x = Integer.parseInt(word.substring(0, 1));
    int y = Integer.parseInt(word.substring(1, 2));

    return new Position(x, y);
  }
}
