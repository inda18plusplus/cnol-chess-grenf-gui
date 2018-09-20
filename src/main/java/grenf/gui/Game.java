package grenf.gui;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Game extends AnimationTimer {

  public static final int WIDTH = 600;
  public static final int HEIGHT = 600;
  public static final String TITLE = "Chess";

  private GraphicsContext gc;
  private int tempy;

  public Game(GraphicsContext gc) {
    this.gc = gc;
    tempy = 0;
  }

  @Override
  public void handle(long now) {
    gc.setFill(Color.WHITE);
    gc.fillRect(0,0,WIDTH, HEIGHT);

    gc.setFill(Color.RED);
    gc.setStroke(Color.BLACK);
    gc.setLineWidth(1);
    Font font = Font.font("Times New Roman", FontWeight.BOLD, 48);
    gc.fillText("tjenare", 60, 50 + tempy);
    gc.strokeText("tjenare", 60, 100 + tempy);


    tempy = (tempy + 1) % 100;
  }
}
