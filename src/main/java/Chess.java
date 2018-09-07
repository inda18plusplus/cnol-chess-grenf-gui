import piece.Pawn;
import piece.Piece;
import piece.Piece.Color;

import java.util.Scanner;


/**
 * Commandline interface for the chess library.
 */
public class Chess {

  private Board board = new Board();

  public static void main(String[] args) {
    System.out.println("Hello, chess!");

    Scanner scanner = new Scanner(System.in);

    Chess chess = new Chess();

    chess.interpretCommand("d");

    System.out.println("> ");
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();

      chess.interpretCommand(line);

      System.out.println("> ");
    }
  }


  private void interpretCommand(String line) {
    String[] words = line.split(" ");

    if (words.length == 0) {
      return;
    }

    String command = words[0];

    switch (command) {
      case "d": this.displayBoard(); break;

      case "m":
        if (words.length == 3) {
          board.move(parsePosition(words[1]), parsePosition(words[2]));
        } else {
          System.out.println("Invalid number of arguments!");
        }
        break;

      case "a":
        if (words.length == 3) {
          Piece piece = parsePiece(words[1]);
          BoardPosition position = parsePosition(words[2]);

          this.board.place(piece, position);
        }
        break;
    }
  }

  private Piece parsePiece(String word) {
    switch (word) {
      case "p": return new Pawn(Color.BLACK);
      case "P": return new Pawn(Color.WHITE);

      default: return null;
    }
  }

  private void displayBoard() {
    System.out.println(this.board);
  }

  private BoardPosition parsePosition(String word) {
    int x = Integer.parseInt(word.substring(0, 1));
    int y = Integer.parseInt(word.substring(1, 2));

    return new BoardPosition(x, y);
  }
}
