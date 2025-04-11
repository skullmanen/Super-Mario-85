package myGame.gameObjects;

import gameEngine.GameContainer;
import gameEngine.Renderer;
import gameEngine.gfx.ImageTile;
import myGame.GameManager;
import myGame.components.AABBComponent;

public class FireBall extends GameObject{
    private final ImageTile fireballSpritesheet = new ImageTile("/resources/images/FireFlower/FireBallSpritesheet.png", 16, 16);
    private final ImageTile smallMarioSpriteSheet = new ImageTile("/resources/images/mario/MarioSpriteSheet_small.png",
			16, 16);
    private boolean isExplosion = false;
    private float animation = 0;
    private int animationFrames = 4;
    private float speedX;
    private float speedY = 2f; 
    private final float gravity = 0.1f; // Gravity for bouncing
    private final float bounceFactor = -0.6f; // Reduce speed after each bounce


    public FireBall(float posX, float posY, int dir){
        this.tag = "fireball";
        this.posX = posX;
        this.posY = posY;
        this.width = 16;
        this.height = 16;
        this.direction = dir==0?1:-1;
        this.speedX = direction * 3f; // Set speed based on direction

        this.addComponent(new AABBComponent(this));

    }

    @Override
    public void update(GameContainer gc, GameManager gm, float dt) {
        posX += speedX;
        posY += speedY;

        // Apply gravity
        speedY += gravity;
        
        if (gm.getCollision((int) (posX / GameManager.TS), (int) (posY / GameManager.TS)+1)) {
            posY = (int) (posY / GameManager.TS) * GameManager.TS; // Snap to ground
            speedY *= bounceFactor; // Reverse and reduce speed for bounce

            // Stop bouncing if speed is too low
            if (Math.abs(speedY) < 0.5f) {
                speedY = 0;
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
           
            //System.out.println("animatiin: " +animation);
            //System.out.println("animationFrames: " +animationFrames);
            if(animation >= animationFrames){
                dead = true;
            }
        }
        animation %= animationFrames;
        animation+=0.25;

        this.updateComponents(gc, gm, dt);

        
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
       // r.drawFillRect((int)posX, (int)posY, 100, 100, 0xff0000ff);
       if(isExplosion && animation >= animationFrames)return;
           
       r.drawImageTile(fireballSpritesheet, (int) posX, (int) posY, (int)animation,isExplosion?1:0);
      //r.drawImageTile(smallMarioSpriteSheet, (int) posX, (int) posY, animation,0);

      this.renderComponents(gc, r);


    }

    @Override
    public void collision(GameObject other) {
        System.out.println("collision: " + other.getTag());
        AABBComponent myC = (AABBComponent) this.findComponent("aabb");
		AABBComponent otherC = (AABBComponent) other.findComponent("aabb");

        if(!other.isDieAnimationPlaying()){
            if(other.getTag().equals("goomba") || other.getTag().equals("koopa")){
                isExplosion = true;
            }

        }
    }

}
