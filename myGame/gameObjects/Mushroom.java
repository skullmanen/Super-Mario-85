
package myGame.gameObjects;


import gameEngine.GameContainer;
import gameEngine.Renderer;
import gameEngine.gfx.Image;
import gameEngine.gfx.ImageTile;
import java.awt.Point;
import myGame.GameManager;
import myGame.components.AABBComponent;


public class Mushroom extends MovingEntity
{

    public final int SPAWN_STATE = 0, NORMAL_STATE = 1;
    int state;
    float spawnPosY;
    boolean blockBounce = false;

    public Mushroom (int tileX, int tileY)
    {
	tag = "mushroom";
	width = GameManager.TS;
	height = GameManager.TS;
	this.tileX = tileX;
	this.tileY = tileY;
	posX = tileX * GameManager.TS;
	posY = tileY * GameManager.TS;
	spawnPosY = posY;
	centerX = (int) posX + (int) (width / 2);
	centerY = (int) posY + (int) (height / 2);
	direction = 1;
	offX = 0;
	offY = 0;
	speedX = 80;
	padding = 0;
	paddingTop = 0;
	dieAnimationPlaying = false;
	spriteSheet = new ImageTile("/images/mushroom/mushroom.png", 16, 16);
	state = SPAWN_STATE;
	
	this.addComponent(new AABBComponent(this));
    }

    @Override
    public void update (GameContainer gc, GameManager gm, float dt)
    {	
	
	switch ( state )
	{
	    case SPAWN_STATE:
		posY -= 0.5;
		offY -= 0.5;
		
		if ( posY <= spawnPosY - height )
		{
		    
		    state = NORMAL_STATE;
		}


		break;


	    case NORMAL_STATE:
		
		anim += dt * 5;
		
		finalPosition();		
		updatePosY(gm, dt);
		updatePosX(gm, dt);		
		
		
		if(blockDeath){
		    blockBounce();
		}
		
		this.updateComponents(gc, gm, dt);
		break;
	}


    }

    @Override
    public void render (GameContainer gc, Renderer r)
    {


	
	    r.drawImageTile(spriteSheet, (int) posX, (int) posY, (int) 0, 0);
	


	this.renderComponents(gc, r);
	//if(dead )
    }

    @Override
    public void collision (GameObject other)
    {

	if ( other.getTag().equals("player") )
	{
	    this.setDead(true);
	}
    }
    
    private void blockBounce ()
    {
	
	
	if ( newStatsApplied == false )
	{
	   
	    fallDistance = -3f;

	    newStatsApplied = true;
	}

	if ( posY >= tempPosY )
	{
	   
	    newStatsApplied = false;
	    blockDeath = false;
	    //dieAnimationPlaying = false;
	}
    }

}
