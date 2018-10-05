package grenf.gui;

import chess.Board;
import chess.piece.Piece;
import chess.piece.Position;
import grenf.gui.eventhandlers.MouseClicked;
import grenf.gui.graphics.ImageLoader;
import grenf.network.Connection;
import grenf.network.Handshake;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.CookieHandler;
import java.util.concurrent.TimeUnit;

public class NetworkedMain extends Application {

  public static Game game;
  public static final int WIDTH = 850;
  public static final int HEIGHT = 650;
  public static final String TITLE = "cnol-chess grenf-gui";

  private static String[] args;

  public static void main(String[] args) {
    System.out.println("tjenare");

    NetworkedMain.args = args;

    ImageLoader.loadImages();
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    boolean isListener = args.length == 1;

    primaryStage.setTitle(isListener ? "Listener" : "Joiner");
    primaryStage.setWidth(WIDTH);
    primaryStage.setHeight(HEIGHT);
    primaryStage.setResizable(false);


    if(args.length != 1 && args.length != 2){
      System.out.println("run as \"./game <host> <port>\" for joining");
      System.out.println("or     \"./game <port>\"        for listening");
      System.exit(0);
    }

    Connection connection = setUpConnection(isListener);
    if (connection == null) {
      System.out.println("Error in establishing connection.");
      System.exit(0);
    }

    Piece.Color myColor = Handshake.handshakeStartingColor(connection, isListener);

    Canvas canvas = new Canvas(WIDTH, HEIGHT);
    game =
        new NetworkedGame(canvas.getGraphicsContext2D(), Board.Layout.CLASSIC, connection, myColor);


    Group root = new Group();
    Scene scene = new Scene(root);
    scene.setOnMouseClicked(new MouseClicked(game));
    primaryStage.setScene(scene);

    root.getChildren().add(canvas);


    primaryStage.show();
    game.renderer.handle(0);
    TimeUnit.SECONDS.sleep(1);
    if(myColor == Piece.Color.BLACK){
      System.out.println("STARTING AS BLACK");
      ((NetworkedGame)game).receiveMove();
    }
    game.renderer.start();
  }

  private Connection setUpConnection(boolean isListener) {
    Connection connection = null;
    if (isListener) {
      int port = Integer.parseInt(args[0]);
      connection = setUpListenerConnection(port);
    } else {
      String host = args[0];
      int port = Integer.parseInt(args[1]);
      connection = setUpJoinerConnection(host, port);
    }
    return connection;
  }

  private Connection setUpJoinerConnection(String host, int port) {
    Connection connection = null;
    try {
      connection = new Connection(host, port);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return connection;
  }

  private Connection setUpListenerConnection(int port) {
    Connection connection = null;
    try {
      connection = new Connection(port);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return connection;
  }
}
