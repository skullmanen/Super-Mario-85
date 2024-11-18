package myGame.gameObjects;

import gameEngine.GameContainer;
import gameEngine.Renderer;
import gameEngine.gfx.ImageTile;
import myGame.GameManager;
import myGame.components.AABBComponent;

public class MovingPlatform extends GameObject{
    private final ImageTile platForm;
    int direction;
    public MovingPlatform(float posX, float posY, String dir){       
        assert dir.equals("UP") || dir.equals("DOWN");
        direction = dir.equals("UP") ? 1 : -1;

        tag = "movingPlatform";
        this.posX = posX;
        this.posY = posY;
        tileX = (int)posX/GameManager.TS;
        tileY = (int)posY/GameManager.TS;
        platForm = new ImageTile("/resources/images/movingPlatform.png", tileX, tileY);     
        width = 48;
        height = 8;
        centerX = (int)posX + (int) (width / 2);
		centerY = (int) posY + (int) (height / 2);
        speedY = 2;

        this.addComponent(new AABBComponent(this));
    }


    @Override
    public void update(GameContainer gc, GameManager gm, float dt) {
        posY += speedY*direction;
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
       r.drawImageTile(platForm, centerY, centerX, 0,0);
    }

    @Override
    public void collision(GameObject other) {
        
    }
}
