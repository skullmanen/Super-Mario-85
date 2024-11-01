package myGame.gameObjects;

import gameEngine.GameContainer;
import gameEngine.Renderer;
import myGame.GameManager;
import java.awt.Point;


public class TeleportPipe extends GameObject{
    private final Point teleportToCoords = new Point(500, 100);
    public TeleportPipe(Point coords){
        this.tag = "teleportPipe";
        this.posX = coords.x;
        this.posY = coords.y;
        width = 13;
        height = -10;
       
    }

    @Override
    public void update(GameContainer gc, GameManager gm, float dt) {
       
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
      
    }

    @Override
    public void collision(GameObject other) {
       
    }

    public Point getTeleportToCoords() {
        return teleportToCoords;
    }

    
    
}
