package grenf.gui;

import chess.Board;
import grenf.gui.eventhandlers.MouseClicked;
import grenf.gui.graphics.ImageLoader;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.json.JSONObject;

public class Main extends Application {

  public static Game game;
  public static final int WIDTH = 850;
  public static final int HEIGHT = 650;
  public static final String TITLE = "cnol-chess grenf-gui";

  public static void main(String[] args) {
    System.out.println("tjenare");
    ImageLoader.loadImages();
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle(TITLE);
    primaryStage.setWidth(WIDTH);
    primaryStage.setHeight(HEIGHT);
    primaryStage.setResizable(false);

    Canvas canvas = new Canvas(WIDTH, HEIGHT);
    game = new Game(canvas.getGraphicsContext2D(), Board.Layout.CLASSIC);

    Group root = new Group();
    Scene scene = new Scene(root);
    scene.setOnMouseClicked(new MouseClicked(game));
    primaryStage.setScene(scene);

    root.getChildren().add(canvas);

    primaryStage.show();
    game.renderer.render();
  }
}
