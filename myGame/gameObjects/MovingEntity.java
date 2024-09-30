
package myGame.gameObjects;


import gameEngine.gfx.ImageTile;
import myGame.GameManager;


// om mushroom speed och enemy speed är samma sätt in värdet här istället
public abstract class MovingEntity extends GameObject
{

    protected ImageTile spriteSheet;
    public final float fallSpeed = 10f;
    private boolean ground = false;
    public float fallDistance = 0f;

    public float anim = 0;
    public boolean newStatsApplied = false;
    public boolean blockDeath;
    public float tempPosY;
    public boolean playerProximity = false;

    public MovingEntity ()
    {

    }

    public void updatePosX (GameManager gm, float dt)
    {

	//posX += velocity.x * direction;
	if ( direction == 1 )
	{
	    if ( (gm.getCollision(tileX + 1, tileY) || gm.getCollision(tileX + 1, tileY + (int) Math.signum((int) offY))) && !dieAnimationPlaying )
	    {
		offX += dt * speedX;

		if ( offX > padding )
		{
		    //speedX = 0;
		    offX = padding;
		}

		direction = -direction;

	    } else
	    {
		offX += dt * speedX;
	    }
	} else
	{

	    if ( ((gm.getCollision(tileX - 1, tileY) || gm.getCollision(tileX - 1, tileY + (int) Math.signum((int) offY)))) && !dieAnimationPlaying )
	    {
		if ( tileX - 1 > 0 )
		    direction = -direction; //enemy inte studsar inte på kanten av världen


	    } else
	    {
		offX -= dt * speedX;
	    }
	}

    }

    public void updatePosY (GameManager gm, float dt)
    {
	if ( fallDistance != 0 )
	    ground = false;

	fallDistance += dt * fallSpeed;
	offY += fallDistance;

	if ( fallDistance > 0 )
	{
	    if ( (gm.getCollision(tileX, tileY + 1) || gm.getCollision(tileX + (int) Math.signum((int) Math.abs(offX) > padding ? offX : 0), tileY + 1)) && offY > 0 && !dieAnimationPlaying )
	    {
		fallDistance = 0;
		offY = 0;
		ground = true;

		if ( gm.getMap().getBlock(tileX, tileY + 1) != null && gm.getMap().getBlock(tileX, tileY + 1).getHit() )
		{
		    blockDeath = true;
		    tempPosY = posY;
		}

	    }
	}
    }

    public void finalPosition ()
    {
	if ( offY > GameManager.TS / 2 )
	{
	    tileY++;
	    offY -= GameManager.TS;
	}

	if ( offY < -GameManager.TS / 2 )
	{
	    tileY--;
	    offY += GameManager.TS;
	}

	if ( offX > GameManager.TS / 2 )
	{
	    tileX++;
	    offX -= GameManager.TS;
	}

	if ( offX < -GameManager.TS / 2 )
	{
	    tileX--;
	    offX += GameManager.TS;
	}

	posX = tileX * GameManager.TS + offX;
	posY = tileY * GameManager.TS + offY;
    }

}
