package myGame.gameObjects;

import gameEngine.GameContainer;
import gameEngine.Renderer;
import gameEngine.gfx.ImageTile;
import gameEngine.gfx.Image;
import myGame.GameManager;
import myGame.components.AABBComponent;

public class PiranhaPlant extends GameObject{
    private final ImageTile piranhaPlant = new ImageTile("/resources/images/enemies/piranhaPlant/piranhaPlantImage.gif", 18, 24);
    private final ImageTile pipeCover = new ImageTile("/resources/images/enemies/piranhaPlant/pipeCover.png", 32, 30);



    private float upperPositionBound;
    private float lowerPositionBound;
    private int pipeCoverPosX;
    private int pipeCoverPosY;

    private boolean isEmerging;
    private long lastStateChangeTime;
    private static final long EMERGE_DURATION = 2000; // 2 seconds
    private static final long RETREAT_DURATION = 2000; // 2 seconds

    public PiranhaPlant(int posX, float posY){
        tag = "piranhaPlant";

        this.posX = posX;
		this.posY = posY;
        pipeCoverPosX = posX-6;
        pipeCoverPosY = (int)posY+24;
        width = 18;
		height = 24;
        direction = 1;
        speedY = 1;
        upperPositionBound = posY;
        lowerPositionBound = upperPositionBound + height+3;
       
        centerX = (int) posX + (int) (width / 2);
		centerY = (int) posY + (int) (height / 2);

        isEmerging = true;
        lastStateChangeTime = System.currentTimeMillis();

        this.addComponent(new AABBComponent(this));
    }

    @Override
    public void update(GameContainer gc, GameManager gm, float dt) {
       
        /*if(posY > lowerPositionBound || posY < upperPositionBound){
            direction = -direction;
        }
        
        posY += speedY * direction;*/

        long currentTime = System.currentTimeMillis();
        if (isEmerging && currentTime - lastStateChangeTime > EMERGE_DURATION) {
            isEmerging = false;
            lastStateChangeTime = currentTime;
        } else if (!isEmerging && currentTime - lastStateChangeTime > RETREAT_DURATION) {
            isEmerging = true;
            lastStateChangeTime = currentTime;
        }

        if (isEmerging) {
            posY -= speedY;
            if (posY < upperPositionBound) {
                posY = upperPositionBound;
            }
        } else {
            posY += speedY;
            if (posY > lowerPositionBound) {
                posY = lowerPositionBound;
            }
        }
       
        this.updateComponents(gc, gm, dt);
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawImageTile(piranhaPlant, (int) posX, (int) posY, 0, 0);
        r.drawImageTile(pipeCover, (int) pipeCoverPosX, (int) pipeCoverPosY, 0, 0);

        this.renderComponents(gc, r);
    }

    @Override
    public void collision(GameObject other) {
        
    }

}
