package grenf.network;

import chess.piece.Position;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Connection {

  public static final JSONObject INVALID_MOVE_RESPONSE = new JSONObject("{\"type\":\"response\", \"response\":\"invalid\"}");
  public static final JSONObject OK_MOVE_RESPONSE = new JSONObject("{\"type\":\"response\", \"response\":\"ok\"}");

  private String host;
  private int port;
  private Socket socket;
  private InputStream input;
  private OutputStream output;

  public Connection(String host, int port) throws IOException {
    this.host = host;
    this.port = port;

    socket = new Socket(host, port);
    input = socket.getInputStream();
    output = socket.getOutputStream();
  }

  public Connection(int port) throws IOException {
    this.port = port;
    this.host = "";

    ServerSocket listener = new ServerSocket(port);
    System.out.println("waiting for connection");
    socket = listener.accept();
    System.out.println("connected");
    listener.close();

    input = socket.getInputStream();
    output = socket.getOutputStream();
  }

  private void write(byte bytes[]) {
    try {
      output.write(bytes);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void sendJSON(JSONObject json) {
    byte buffer[] = json.toString().getBytes();

    byte length[] = shortToBytes((short)buffer.length);
    write(length);
    write(buffer);
  }

  public void sendMove(Position startPos, Position endPos, char promotionChoice) {
    JSONObject json = new JSONObject();
    json.put("type", "move");
    json.put("promotion", ""+promotionChoice); // FIX THIS
    json.put("from", PositionParser.toString(startPos));
    json.put("to", PositionParser.toString(endPos));
    sendJSON(json);
  }

  private byte[] recv(int size) {
    byte buffer[] = new byte[size];
    try {
      input.read(buffer, 0, size);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return buffer;
  }

  public JSONObject recvJSON() {
    short jsonLength = bytesToShort(recv(2));
    String jsonString = new String(recv(jsonLength));
    return new JSONObject(jsonString);
  }

  private short bytesToShort(byte bytes[]) {
    ByteBuffer byteBuffer = ByteBuffer.allocate(2);
    byteBuffer.order(ByteOrder.BIG_ENDIAN);
    byteBuffer.put(bytes);
    return byteBuffer.getShort(0);
  }

  private byte[] shortToBytes(short number) {
    ByteBuffer byteBuffer = ByteBuffer.allocate(2);
    byteBuffer.order(ByteOrder.BIG_ENDIAN);
    byteBuffer.putShort(number);
    return byteBuffer.array();
  }
}
