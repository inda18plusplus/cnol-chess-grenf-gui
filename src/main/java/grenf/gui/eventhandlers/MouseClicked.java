package grenf.gui.eventhandlers;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class MouseClicked implements EventHandler<MouseEvent> {
  @Override
  public void handle(MouseEvent event) {
    System.out.println("Clicked at " + event.getX() + " " + event.getY());

  }
}
