package myGame.gameObjects;

import gameEngine.GameContainer;
import gameEngine.Renderer;
import myGame.GameManager;
import myGame.components.AABBComponent;

import java.awt.Point;


public class TeleportPipe extends GameObject{
    private final Point teleportToCoords = new Point(1500, 100);
    public TeleportPipe(Point coords){
        System.out.println("in consticor");
        this.tag = "teleportPipe";
        this.posX = coords.x;
        this.posY = coords.y;
        width = 13;
        height = 10;
        centerX = (int) (posX) + (int) (width / 2);
	    centerY = (int) (posY) + (int) (height / 2);
        this.addComponent(new AABBComponent(this));
       
    }

    @Override
    public void update(GameContainer gc, GameManager gm, float dt) {
        this.updateComponents(gc, gm, dt);
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        //System.out.println("pipe render");
      r.drawFillRect((int)posX, (int)posY, width, height, 0);
      this.renderComponents(gc, r);
    }

    @Override
    public void collision(GameObject other) {
       
    }

    public Point getTeleportToCoords() {
        return teleportToCoords;
    }

    
    
}
