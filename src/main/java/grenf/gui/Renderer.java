package grenf.gui;

import javafx.scene.canvas.GraphicsContext;

public class Renderer {

  private Game game;
  private GraphicsContext gc;

  public Renderer(Game game, GraphicsContext gc){
    this.game = game;
    this.gc = gc;
  }


  public void render() {
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
