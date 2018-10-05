package grenf.gui.eventhandlers;

import grenf.gui.Game;
import grenf.gui.NetworkedMain;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

public class MouseClicked implements EventHandler<MouseEvent> {

  private Game game;

  public MouseClicked(Game game){
    this.game = game;
  }

  @Override
  public void handle(MouseEvent event) {
    Point2D clickPos = new Point2D(event.getX(), event.getY());
    game.handleClick(clickPos);
  }
}
