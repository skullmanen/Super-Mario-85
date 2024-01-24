
package myGame.blockTypes;


import gameEngine.GameContainer;
import gameEngine.Renderer;
import java.util.Random;
import myGame.GameManager;
import myGame.gameObjects.Block;
import myGame.gameObjects.Coin;
import myGame.gameObjects.Goomba;
import myGame.gameObjects.Mushroom;


public class CoinBlock extends Block
{

    private Random random = new Random();
    
    private String contains;
    
    public CoinBlock (int tileX, int tileY, String contains)
    {
	super(tileX, tileY, "/images/blocks/coin_block/coinBlock_spriteSheet.png");
	tag = "coinBlock";
	
	this.contains = contains;
    }

    @Override
    public void update (GameContainer gc, GameManager gm, float dt)
    {
	switch ( state )
	{
	    case NORMAL_STATE:
		anim += dt * animationTime / 4;
		if ( hit )
		{
		    state = HIT_STATE;
		    anim = 0;
		}
		break;
		
	    case HIT_STATE:
		if (anim == 0) 
		{
		    //int a = random.nextInt(2);
		    int a = 1;
		    if (contains.equals("coin")) {
			gm.addObject(new Coin(tileX, tileY - 1));
		    } else if (contains.equals("mushroom")) {
			gm.addObject(new Mushroom(tileX, tileY ));
		    }
		    
		    
		}
		anim += dt * animationTime;
		break;
		
	    case EMPTY_STATE:
		anim = 0;
		break;
	}
    }

    @Override
    public void render (GameContainer gc, Renderer r)
    {
	switch ( state )
	{
	    case NORMAL_STATE:
		r.drawImageTile(spriteSheet, (int) (posX), (int) (posY + offY), (int) (anim % 5), NORMAL_STATE);
		break;
		
	    case HIT_STATE:
		r.drawImageTile(spriteSheet, (int) (posX), (int) (posY + offY), (int) (anim % 6), HIT_STATE);
		if ( (int) (anim % 6) == 5 )
		{
		    state = EMPTY_STATE;
		    hit = false;
		}
		break;
		
	    case EMPTY_STATE:
		r.drawImageTile(spriteSheet, (int) (posX), (int) (posY + offY), 5, HIT_STATE);
		break;
	}
    }
}
