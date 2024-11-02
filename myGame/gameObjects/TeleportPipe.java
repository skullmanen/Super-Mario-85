package myGame.gameObjects;

import gameEngine.GameContainer;
import gameEngine.Renderer;
import myGame.GameManager;
import myGame.components.AABBComponent;

import java.awt.Point;


public class TeleportPipe extends GameObject{
    private final Point teleportToCoords;//in tiles
    private final int cameraPosition;
    private final int entranceKeycode;
    public TeleportPipe(Point coords, int width, int height, int keyCode, Point telePortToCoords, int cameraPosition){
        System.out.println("in consticor");
        this.tag = "teleportPipe";
        this.posX = coords.x;
        this.posY = coords.y;
        this.width = width;
        this.height = height;
        this.teleportToCoords = telePortToCoords;
        this.cameraPosition = cameraPosition;
        centerX = (int) (posX) + (int) (width / 2);
	    centerY = (int) (posY) + (int) (height / 2);
        entranceKeycode = keyCode;
        this.addComponent(new AABBComponent(this));
       
    }

    @Override
    public void update(GameContainer gc, GameManager gm, float dt) {
        this.updateComponents(gc, gm, dt);
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        //System.out.println("pipe render");
      r.drawFillRect((int)posX, (int)posY, width, height, 255);
      this.renderComponents(gc, r);
    }

    @Override
    public void collision(GameObject other) {
       
    }

    public Point getTeleportToCoords() {
        return teleportToCoords;
    }

    public int getCameraPosition() {
        return cameraPosition;
    }

    public int getEntranceKeycode() {
        return entranceKeycode;
    }   

    
}
