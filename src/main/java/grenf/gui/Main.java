package grenf.gui;

import grenf.gui.eventhandlers.MouseClicked;
import grenf.gui.eventhandlers.MouseMoved;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

public class Main extends Application {

  public static void main(String[] args) {
    System.out.println("tjenare");
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle(Game.TITLE);
    primaryStage.setWidth(Game.WIDTH);
    primaryStage.setHeight(Game.HEIGHT);
    primaryStage.setResizable(false);

    Group root = new Group();
    Scene scene = new Scene(root);
    scene.setOnMouseMoved(new MouseMoved());
    scene.setOnMouseClicked(new MouseClicked());
    primaryStage.setScene(scene);

    Canvas canvas = new Canvas(Game.WIDTH, Game.HEIGHT);
    root.getChildren().add(canvas);

    Game game = new Game(canvas.getGraphicsContext2D());
    primaryStage.show();
    game.start();
  }
}
