package myGame.gameObjects;

import gameEngine.GameContainer;
import gameEngine.Renderer;
import gameEngine.gfx.ImageTile;
import myGame.GameManager;
import myGame.components.AABBComponent;

public class MovingPlatform extends GameObject{
    private final ImageTile platForm;
    private int direction;
    private String dirAsString;
    private boolean playerColliding = false;
    boolean a = false;
    private float lowerBound;
    private float upperBound;
    private boolean isTurning = false;
    private boolean turnFinished = false;
    private float turnSpeed = 0.05f;
    public MovingPlatform(float posX, float posY, String dir, float upperBound, float lowerBound) {       
        assert dir.equals("UP") || dir.equals("DOWN") || dir.equals("LEFT") || dir.equals("RIGHT");

        
        direction = dir.equals("UP") || dir.equals("LEFT") ? -1 : 1;
        dirAsString = dir;
        tag = "movingPlatform";
        this.posX = posX;
        this.posY = posY;
        tileX = (int)posX/GameManager.TS;
        tileY = (int)posY/GameManager.TS;
         
        width = 48;
        height = 8;
        centerX = (int)posX + (int) (width / 2);
		centerY = (int) posY + (int) (height / 2);
        speedX = direction;
        speedY = direction;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        platForm = new ImageTile("/resources/images/movingPlatform.png", (int)width, height);   

        this.addComponent(new AABBComponent(this));
    }


    @Override
    public void update(GameContainer gc, GameManager gm, float dt) {
        if(dirAsString.equals("UP")){

            if(posY< 0) posY = gc.getHeight();
            else if(posY<=upperBound || isTurning){
                isTurning = true;
                speedY = changeDirection(speedY, 1, turnSpeed);

                if(turnFinished){
                    System.out.println("in turn finnishesdwlöekajdflasjkdlqakedla");
                    dirAsString = "DOWN";
                    direction = 1;
                    isTurning = false;
                    turnFinished = false;
                }

            }
            System.out.println("UP " + speedY);
            posY += speedY;
        } else if(dirAsString.equals("DOWN")){
            if(posY>gc.getHeight()) posY = 0;
            else if(posY>=lowerBound || isTurning){
                isTurning = true;
                speedY = changeDirection(speedY, -1, -turnSpeed);

                if(turnFinished){
                    dirAsString = "UP";
                    direction = -1;
                    isTurning = false;
                    turnFinished = false;
                }

            }
            System.out.println("DOWN " + speedY);
            posY += speedY;
        }else if(dirAsString.equals("LEFT")){

             if(posX<=lowerBound || isTurning){
                 isTurning = true;
                 speedX = changeDirection(speedX, 1, turnSpeed);
                 if(turnFinished){
                     dirAsString = "RIGHT";
                     direction = 1;
                     isTurning = false;
                     turnFinished = false;
                 }

            }
            posX += speedX;
        } else if(dirAsString.equals("RIGHT")){         
             if(posX>=upperBound || isTurning){
                 isTurning = true;
                 speedX = changeDirection(speedX, -1, -turnSpeed);
                 if(turnFinished){
                     dirAsString = "LEFT";
                     direction = -1;
                     isTurning = false;
                     turnFinished = false;
                 }
            }
            posX += speedX;
        }
     
       
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

    private float changeDirection(float currentSpeed, float targetSpeed, float speedChange){
        currentSpeed += speedChange;
        if(Math.abs(currentSpeed - targetSpeed)< Math.abs(speedChange)) turnFinished = true;
        return currentSpeed;


    }
    public boolean isPlayerColliding() {
        return playerColliding;
    }

    
}
