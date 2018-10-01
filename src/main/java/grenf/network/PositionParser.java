package grenf.network;

import chess.piece.Position;

public class PositionParser {
  private static String COLUMN_ALPHABET = "ABCDEFGH";
  private static String ROW_ALPHABET    = "87654321";

  public static String toString(Position position){
    StringBuilder res = new StringBuilder();
    res.append(COLUMN_ALPHABET.charAt(position.getColumn()));
    res.append(ROW_ALPHABET.charAt(position.getRow()));
    return res.toString();
  }

  public static Position toPosition(String stringPosition){
    return new Position(COLUMN_ALPHABET.indexOf(stringPosition.charAt(0)), ROW_ALPHABET.indexOf(stringPosition.charAt(1)));
  }
}
