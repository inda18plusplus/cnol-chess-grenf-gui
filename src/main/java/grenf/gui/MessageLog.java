package grenf.gui;

import chess.piece.Piece;
import grenf.gui.graphics.SpriteBoard;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;

public class MessageLog {

  private static final String WHITE_TITLE = "White's turn.";
  private static final String BLACK_TITLE = "Black's turn.";
  private static final Color GRAY = Color.web("0xBFBFBF");

  private ArrayList<String> messages;
  private ArrayList<Color> colors;
  private Point2D position;
  private Font font;

  public MessageLog(Point2D position, int fontSize) {
    this.position = position;
    this.font = Font.font("Times New Roman", FontWeight.BOLD, fontSize);
    messages = new ArrayList<>();
    colors = new ArrayList<>();
  }

  public void addMessage(String message, Color color) {
    colors.add(color);
    messages.add(message);
  }

  public void render(GraphicsContext gc, Piece.Color currentTeam) {
    gc.setFill(Color.WHITE);
    gc.fillRect(position.getX(), position.getY(), Main.WIDTH - SpriteBoard.BOARD_WIDTH, Main.HEIGHT);

    gc.setFill(GRAY);
    gc.fillRect(position.getX(), position.getY() + 0.01 * (double) Main.HEIGHT, (Main.WIDTH - SpriteBoard.BOARD_WIDTH) * 0.90, Main.HEIGHT);

    Point2D separatorDistance = new Point2D(0, font.getSize() * 1.1);
    Point2D renderPosition = position.add(separatorDistance);
    if (currentTeam == Piece.Color.WHITE) {
      renderText(gc, WHITE_TITLE, renderPosition, Color.WHITE);
    } else {
      renderText(gc, BLACK_TITLE, renderPosition, Color.BLACK);
    }

    for (int i = messages.size() - 1; i >= 0; i--) {
      renderPosition = renderPosition.add(separatorDistance);
      renderText(gc, messages.get(i), renderPosition, colors.get(i));
    }
  }

  private void renderText(GraphicsContext gc, String text, Point2D textPosition, Color fillColor) {
    gc.setFill(fillColor);
    gc.setStroke(Color.BLACK);
    gc.setLineWidth(2);
    gc.setFont(font);
    gc.fillText(text, textPosition.getX(), textPosition.getY());
    gc.strokeText(text, textPosition.getX(), textPosition.getY());
  }
}
