package myGame.gameObjects;

import gameEngine.GameContainer;
import gameEngine.Renderer;
import gameEngine.gfx.ImageTile;
import gameEngine.gfx.Image;
import myGame.GameManager;
import myGame.components.AABBComponent;

public class PiranhaPlant extends GameObject{
    private final ImageTile image = new ImageTile("/resources/images/enemies/piranhaPlantImage.gif", 18, 24);

    private float upperPositionBound;
    private float lowerPositionBound;

    public PiranhaPlant(int posX, float posY){
        tag = "piranhaPlant";
        this.posX = posX;
		this.posY = posY;
        width = 18;
		height = 24;
        direction = 1;
        speedY = 1/2;
        upperPositionBound = posY;
        lowerPositionBound = upperPositionBound + height;
        centerX = (int) posX + (int) (width / 2);
		centerY = (int) posY + (int) (height / 2);

        this.addComponent(new AABBComponent(this));
    }

    @Override
    public void update(GameContainer gc, GameManager gm, float dt) {
       
        if(posY > lowerPositionBound || posY < upperPositionBound){
            System.out.println("hello");
            direction = -direction;
        }

        posY += speedY;
       
        this.updateComponents(gc, gm, dt);
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawImageTile(image, (int) posX, (int) posY, 0, 0);

        this.renderComponents(gc, r);
    }

    @Override
    public void collision(GameObject other) {
        
    }

}
