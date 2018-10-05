package grenf.gui;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;

public class Renderer extends AnimationTimer {

  private Game game;
  private GraphicsContext gc;

  public Renderer(Game game, GraphicsContext gc){
    this.game = game;
    this.gc = gc;
  }

  @Override
  public void handle(long now) {
    game.spriteBoard.render(gc);
    if (game.selectedSprite != null) {
      if (!game.potentialMoveSprites.isEmpty()) {
        for (int i = 0; i < game.potentialMoveSprites.size(); i++) {
          game.potentialMoveSprites.get(i).render(gc);
        }
      }
      game.selectedSprite.render(gc);
    }
    game.messageLog.render(gc, game.board.getCurrentColor());
  }
}
