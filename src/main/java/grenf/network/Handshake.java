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

    if (choice != 0 || choice != 1) {
      System.out.println("ERROR IN HANDSHAKE, BAD CHOICE");
      System.exit(1);
    }

    if (choice == response.getInt("choice")) {
      return Piece.Color.WHITE;
    } else {
      return Piece.Color.BLACK;
    }
  }

  private static Piece.Color handshakeJoiner(Connection connection) {
    JSONObject seedHash = connection.recvJSON();

    int myChoice = generateChoice();
    JSONObject choice = new JSONObject();
    choice.put("type", "init");
    choice.put("choice", myChoice);
    connection.sendJSON(choice);

    JSONObject seed = connection.recvJSON();
    String opponentHash = hash(seed.getString("seed"));
    System.out.println(seed.getString("seed"));
    System.out.println(opponentHash);
    System.out.println(seedHash.getString("hash"));
    if (!opponentHash.equals(seedHash.getString("hash"))) {
      System.out.println("ERROR IN HANDSHAKE, INVALID HASH");
      System.exit(1);
    }

    if (seed.getString("seed").charAt(0) == ((char) seed.getInt("choice"))) {
      System.out.println("ERROR IN HANDSHAKE, OPPONENT TRIED TO CHEAT");
      System.exit(0);
    }

    int opponentChoice = seed.getInt("choice");
    if (opponentChoice != 0 && opponentChoice != 1) {
      System.out.println("ERROR IN HANDSHAKE, bad choice");
      System.exit(1);
    }

    if (myChoice == seed.getInt("choice")) {
      return Piece.Color.BLACK;
    } else {
      return Piece.Color.WHITE;
    }
  }

  private static int generateChoice() {
    SecureRandom random = new SecureRandom();
    return random.nextInt(2);
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
