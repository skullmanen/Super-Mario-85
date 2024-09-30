

package myGame.gameObjects;


import gameEngine.GameContainer;
import gameEngine.Renderer;
import gameEngine.gfx.Image;
import myGame.GameManager;




public class Flag extends GameObject
{
    private boolean isDown = false;
    
    private Image flagImg = new Image("/resources/images/Flag/Flag.png");
    
    public Flag(int tileY)
    {
	tag = "flag";
	tileX = 198;
	posX = tileX * GameManager.TS - GameManager.TS / 2;
	posY = tileY * GameManager.TS + 1;
	width = GameManager.TS;
	height = GameManager.TS;
	speedY = 80;
    }

   @Override
    public void update (GameContainer gc, GameManager gm, float dt)
    {
	if(gm.gameState == gm.WIN_STATE && (int)posY < 11*GameManager.TS - 4)
	{
	    posY += speedY * dt;
	} else 
	{
	    isDown = true;
	}
	
	this.updateComponents(gc, gm, dt);
    }

    @Override
    public void render (GameContainer gc, Renderer r)
    {
	r.drawImage(flagImg, (int)posX, (int)posY);
	this.renderComponents(gc, r);
    }

    @Override
    public void collision (GameObject other)
    {
    }
    
    public boolean isDown() 
    {
	return isDown;
    }
    
}
