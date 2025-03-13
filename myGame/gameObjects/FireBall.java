package myGame.gameObjects;

import gameEngine.GameContainer;
import gameEngine.Renderer;
import gameEngine.gfx.ImageTile;
import myGame.GameManager;

public class FireBall extends GameObject{
    private final ImageTile fireballSpritesheet = new ImageTile("/resources/images/FireBallSpritesheet.png", 16, 16);
    private boolean isExplosion = false;
    private int animation = 0;
    private int animationFrames = 4;
    public FireBall(float posX, float posY){
        this.posX = posX;
        this.posY = posY;
    }

    @Override
    public void update(GameContainer gc, GameManager gm, float dt) {
        animation++;
        animation %= animationFrames;
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
       // r.drawFillRect((int)posX, (int)posY, 100, 100, 0xff0000ff);
        r.drawImageTile(fireballSpritesheet, (int) posX, (int) posY, 0,0);

    }

    @Override
    public void collision(GameObject other) {
        
    }

}
