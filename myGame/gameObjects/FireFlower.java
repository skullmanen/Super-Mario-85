

package myGame.gameObjects;


import gameEngine.GameContainer;
import gameEngine.Renderer;
import gameEngine.gfx.ImageTile;
import myGame.GameManager;
import myGame.components.AABBComponent;




public class FireFlower extends GameObject
{
   // private final ImageTile spriteSheet = new ImageTile("/images/FireFlower/fireFlower.png", 16, 16);
   
    public FireFlower(int tileX, int tileY){
	tag = "fireFlower";
	this.tileX = tileX;
	this.tileY = tileY;
	posX = tileX * GameManager.TS;
	posY = tileY * GameManager.TS;
	width = GameManager.TS;
	height = GameManager.TS;
	centerX = (int) posX + (int) (width / 2);
	centerY = (int) posY + (int) (height / 2);		
	padding = 0;
	paddingTop = 0;
	
	this.addComponent(new AABBComponent(this));
	

    }


    @Override
    public void update (GameContainer gc, GameManager gm, float dt)
    {
	this.updateComponents(gc, gm, dt);
    }

    @Override
    public void render (GameContainer gc, Renderer r)
    {
	
	this.renderComponents(gc, r);
    }

    @Override
    public void collision (GameObject other)
    {
	if ( other.getTag().equals("player") )
	{
	    this.setDead(true);
	}
    }
    
}
