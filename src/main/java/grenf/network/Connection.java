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

    ByteBuffer length = ByteBuffer.allocate(2);
    length.order(ByteOrder.BIG_ENDIAN);
    length.putShort((short)(buffer.length));

    write(length.array());
    write(buffer);
  }

  public void sendMove(Position startPos, Position endPos) {
    JSONObject json = new JSONObject();
    json.put("type", "move");
    json.put("from", PositionParser.toString(startPos));
    json.put("to", PositionParser.toString(endPos));
    sendJSON(json);
  }

  private byte[] recv(int size){
    byte buffer[] = new byte[size];
    try {
      input.read(buffer, 0, size);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return buffer;
  }

  public JSONObject recvJSON(){
    ByteBuffer length = ByteBuffer.allocate(2);
    length.order(ByteOrder.BIG_ENDIAN);
    length.put(recv(2));

    short jsonLength = length.getShort(0);
    String jsonString = new String(recv(jsonLength));
    return new JSONObject(jsonString);
  }
}
