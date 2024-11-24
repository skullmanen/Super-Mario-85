package myGame.gameObjects;

import gameEngine.GameContainer;
import gameEngine.Renderer;
import gameEngine.gfx.ImageTile;
import myGame.GameManager;
import myGame.components.AABBComponent;

public class MovingPlatform extends GameObject{
    private final ImageTile platForm;
    private int direction;
    private boolean playerColliding = false;
    boolean a = false;
    public MovingPlatform(float posX, float posY, String dir){       
        assert dir.equals("UP") || dir.equals("DOWN");
        direction = dir.equals("UP") ? -1 : 1;

        tag = "movingPlatform";
        this.posX = posX;
        this.posY = posY;
        tileX = (int)posX/GameManager.TS;
        tileY = (int)posY/GameManager.TS;
         
        width = 48;
        height = 8;
        centerX = (int)posX + (int) (width / 2);
		centerY = (int) posY + (int) (height / 2);
        speedY = 1;
        platForm = new ImageTile("/resources/images/movingPlatform.png", (int)width, height);   

        this.addComponent(new AABBComponent(this));
    }


    @Override
    public void update(GameContainer gc, GameManager gm, float dt) {
        if(direction == -1 && posY<0)posY = gc.getHeight();
        else if(direction == 1 && posY>gc.getHeight())posY = -height;
        posY += speedY*direction;
        this.updateComponents(gc, gm, dt);

    }

    @Override
    public void render(GameContainer gc, Renderer r) {
      r.drawImageTile(platForm, (int)posX, (int)posY, 0,0);
      r.drawFillRect((int)posX,(int) posY, width, height,0);
       this.renderComponents(gc, r);

    }

    @Override
    public void collision(GameObject other) {
        /*if(other.tag.equals("player"))playerColliding = true;
        else playerColliding = false;*/

        //if(other.tag.equals("player"))a = true;
        //else playerColliding = false;
    }


    public boolean isPlayerColliding() {
        return playerColliding;
    }

    
}
