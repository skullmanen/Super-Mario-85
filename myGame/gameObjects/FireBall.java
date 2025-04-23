package myGame.gameObjects;

import gameEngine.GameContainer;
import gameEngine.Renderer;
import gameEngine.gfx.ImageTile;
import myGame.GameManager;
import myGame.components.AABBComponent;

public class FireBall extends GameObject{
    private final ImageTile fireballSpritesheet = new ImageTile("/resources/images/FireFlower/FireBallSpritesheet.png", 16, 16);
    private boolean isExplosion = false;
    private float animation = 0;
    private int animationFrames = 4;
    private float speedX;
    private float speedY = 5f; // Initial vertical speed
    private final float gravity = 0.5f; // Gravity for bouncing
    private int bounceCount = 0;
    private final int maxBounces = 3; // Maximum number of bounces before stopping

    public FireBall(float posX, float posY, int dir){
        this.tag = "fireball";
        this.posX = posX+15;
        this.posY = posY;
        this.width = 16;
        this.height = 16;
        this.direction = dir==0?1:-1;
        this.speedX = direction * 5f; // Set speed based on direction


        this.addComponent(new AABBComponent(this));

    }

    @Override
    public void update(GameContainer gc, GameManager gm, float dt) {
        posX += speedX;
        posY += speedY;

        // Apply gravity
        speedY += gravity;
        if(speedY > 5f){
            speedY = 5f;
        }
        
        if (gm.getCollision((int) (posX / GameManager.TS), (int) (posY / GameManager.TS)+1)) {
            posY = (int) (posY / GameManager.TS) * GameManager.TS; // Snap to ground
            speedY = -4f; 
            bounceCount++;
            if (bounceCount >= maxBounces) {
                dead = true; // Mark the fireball for removal
            }
        }

         // Check for wall collision
        if (gm.getCollision((int) ((posX + (speedX > 0 ? 16 : 0)) / GameManager.TS), (int) (posY / GameManager.TS))) {
            animation = 0;
            isExplosion = true;
        }

        if(isExplosion){
            speedX = 0;
            speedY = 0;
            if(animation >= animationFrames){
                dead = true;
            }
        }
        animation %= animationFrames;
        animation+=0.4;

        this.updateComponents(gc, gm, dt);

        
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
       if(isExplosion && animation >= animationFrames)return;
       r.drawImageTile(fireballSpritesheet, (int) posX, (int) posY, (int)animation,isExplosion?1:0);
      this.renderComponents(gc, r);
    }

    @Override
    public void collision(GameObject other) {
        if(!other.isDieAnimationPlaying()){
            if(other.getTag().equals("goomba") || other.getTag().equals("koopa")){
                isExplosion = true;
            }

        }
    }

}
