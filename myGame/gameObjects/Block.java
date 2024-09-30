
package myGame.gameObjects;


import gameEngine.GameContainer;
import gameEngine.Renderer;
import gameEngine.gfx.ImageTile;
import myGame.GameManager;


public class Block extends GameObject
{
    //animation
    protected ImageTile spriteSheet;
    protected final float offY;
    protected float anim = 0;
    protected float animationTime = 20;
    
    protected int tileX, tileY;
    
    protected boolean hit = false;

    //states
    protected final int NORMAL_STATE = 0;
    protected final int HIT_STATE = 1;
    protected final int EMPTY_STATE = 2;
    protected int state = NORMAL_STATE;
    
    
    

    public Block (int tileX, int tileY, String path)
    {
	tag = "block";
	
	width = GameManager.TS;
	height = GameManager.TS;
	
	this.tileX = (int)tileX;
	this.tileY = (int)tileY;
	
	this.posX = this.tileX * width;
	this.posY = this.tileY * height;
	
	spriteSheet = new ImageTile(path, 16, 24);
	
	offY = -(spriteSheet.getTileH() - GameManager.TS);
    }

    @Override
    public void update (GameContainer gc, GameManager gm, float dt) {}

    @Override
    public void render (GameContainer gc, Renderer r) {}

    public void setHit (boolean hit)
    {
	this.hit = hit;
    }

    public boolean getHit ()
    {
	return hit;
    }
    
    public int getTileX ()
    {
	return tileX;
    }

    public int getTileY ()
    {
	return tileY;
    }

    @Override
    public void collision (GameObject other)
    {
	throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    

}
