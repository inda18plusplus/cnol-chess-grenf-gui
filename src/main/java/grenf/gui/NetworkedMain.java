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
    primaryStage.setTitle(TITLE);
    primaryStage.setWidth(WIDTH);
    primaryStage.setHeight(HEIGHT);
    primaryStage.setResizable(false);

    Group root = new Group();
    Scene scene = new Scene(root);
    scene.setOnMouseClicked(new MouseClicked());
    primaryStage.setScene(scene);

    Canvas canvas = new Canvas(WIDTH, HEIGHT);
    root.getChildren().add(canvas);

    Connection connection = setUpConnection(args);
    if (connection == null) {
      System.out.println("run as ./game host port");
      System.exit(0);
    }

    boolean isListener = args.length == 1;
    Piece.Color myColor = Handshake.handshakeStartingColor(connection, isListener);

    game =
        new NetworkedGame(canvas.getGraphicsContext2D(), Board.Layout.CLASSIC, connection, myColor);
    primaryStage.show();
    game.start();
  }

  private Connection setUpConnection(String[] args) {
    Connection connection = null;
    if (args.length == 1) {
      int port = Integer.parseInt(args[0]);
      connection = setUpListenerConnection(port);
    } else if (args.length == 2) {
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
