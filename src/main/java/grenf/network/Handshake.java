package grenf.network;

import chess.piece.Piece;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Handshake {
  public static Piece.Color handshakeStartingColor(Connection connection, boolean isListener) {
    if (isListener) {
      return handshakeListener(connection);
    } else {
      return handshakeJoiner(connection);
    }
  }

  private static Piece.Color handshakeListener(Connection connection) {
    String seed = generateSeed();
    int choice = seed.charAt(0) == '0' ? 0 : 1;
    JSONObject challenge = new JSONObject();
    challenge.put("type", "init");
    challenge.put("hash", hash(seed));
    connection.sendJSON(challenge);

    JSONObject response = connection.recvJSON();

    JSONObject reveal = new JSONObject();
    reveal.put("type", "init");
    reveal.put("choice", choice);
    reveal.put("seed", seed);
    connection.sendJSON(reveal);

    if(choice == response.getInt("choice")){
      return Piece.Color.WHITE;
    } else {
      return Piece.Color.BLACK;
    }
  }

  private static Piece.Color handshakeJoiner(Connection connection) {
    return null;
  }

  private static String generateSeed() {
    StringBuilder seed = new StringBuilder();
    SecureRandom random = new SecureRandom();

    seed.append(random.nextInt(2));
    for (int i = 0; i < 10; i++) {
      seed.append((char) ((int) ('A') + random.nextInt(26)));
    }
    return seed.toString();
  }

  private static String hash(String input) {
    MessageDigest digest = null;
    try {
      digest = MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    byte[] hashedBytes = digest.digest(input.getBytes());

    assert hashedBytes.length == 32;
    StringBuilder builder = new StringBuilder();

    for (byte hashedByte : hashedBytes) {
      builder.append(String.format("%02X", hashedByte));
    }

    assert builder.toString().length() == 64;

    return builder.toString();
  }
}
