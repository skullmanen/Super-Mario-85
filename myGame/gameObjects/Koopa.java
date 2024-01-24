
package myGame.gameObjects;


import gameEngine.GameContainer;
import gameEngine.Renderer;
import gameEngine.gfx.ImageTile;
import myGame.GameManager;
import myGame.components.AABBComponent;


public class Koopa extends MovingEntity
{

    //boolean shellForm = false;
    private int shellTime = 10;
    private float shellTimer;
    boolean dieAnimation = false;

    //private final ImageTile shell;
    private final float shellSpeed = 160;

    private int imageTileY = 0;

    public Koopa (int tileX, int tileY)
    {
	tag = "koopa";
	this.tileX = tileX;
	this.tileY = tileY;
	posX = tileX * GameManager.TS;
	posY = tileY * GameManager.TS;
	speedX = 80;
	direction = -1;

	width = GameManager.TS;
	height = GameManager.TS;
	centerX = (int) posX + (int) (width / 2);
	centerY = (int) posY + (int) (height / 2);

	spriteSheet = new ImageTile("/images/enemies/Koopa/Koopa_Troopa_SpriteSheet.png", 16, 24);

	offX = 0;
	offY = 0;
	padding = 0;
	paddingTop = -8;
	dieAnimationPlaying = false;
	lifes = 2;
	shellForm = false;

	this.addComponent(new AABBComponent(this));
    }

    @Override
    public void update (GameContainer gc, GameManager gm, float dt)
    {
	if ( playerProximity )
	{
	    //anim += dt * 5;

	    updatePosX(gm, dt);
	    updatePosY(gm, dt);
	    finalPosition();

	    updateAnimation(dt);
	    if ( dieAnimation || blockDeath )
	    {
		dieAnimation();
	    }
	}

	if ( Math.abs(gm.getObject("player").getTileX() - tileX) <= 17 )
	{
	    playerProximity = true;
	}


	this.updateComponents(gc, gm, dt);


    }

    private void updateAnimation (float dt)
    {
	anim += spriteSheet.getTileAmountX() * dt;

	if ( shellForm )
	    imageTileY = 2;
	else if ( direction == -1 )
	    imageTileY = 0;
	else
	    imageTileY = 1;

	if ( shellForm )
	{
	    anim = 1;
	    
	    if ((int)speedX != 0)
	    {
		shellTimer = 0;
	    } else 
	    {
		shellTimer += dt;
	    }
	    
	    if ( shellTimer >= shellTime )
	    {
		shellForm = false;
		speedX = 80;
		shellTimer = 0;
	    } else if ( shellTimer >= shellTime - 1 )
	    {
		anim = 0;

	    }
	}


    }

    @Override
    public void render (GameContainer gc, Renderer r)
    {
	try
	{
	    r.drawImageTile(spriteSheet, (int) posX, (int) posY + paddingTop, (int) anim % 2, imageTileY);
	} catch ( Exception e )
	{

	}


	this.renderComponents(gc, r);
    }

    @Override
    public void collision (GameObject other)
    {
	if ( !other.isDieAnimationPlaying() )
	{
	    if ( other.getTag().equals("player") )
	    {
		AABBComponent myC = (AABBComponent) this.findComponent("aabb");
		AABBComponent otherC = (AABBComponent) other.findComponent("aabb");

		if ( otherC.getCenterY() < myC.getCenterY() )
		{ //player is over koopa
		    attackFromAbove(other);
		} else if ( myC.getCenterY() < otherC.getCenterY() )
		{
		    //over player		    
		} else
		{

		    if ( this.isShellForm() && this.getSpeedX() == 0 )
		    {
			this.setSpeedX(shellSpeed);
			if ( other.getPosX() < this.getPosX() ) //other left of me
			{
			    direction = 1;
			} else //other right of me
			{
			    direction = -1;
			}
		    }
		}


	    } else if ( other.getTag().equals("goomba") )
	    {
		AABBComponent myC = (AABBComponent) this.findComponent("aabb");
		AABBComponent otherC = (AABBComponent) other.findComponent("aabb");

		if ( otherC.getCenterY() < myC.getCenterY() )
		{ //koopa is over goomba

		} else if ( myC.getCenterY() < otherC.getCenterY() )
		{
		    //koopa over 
		} else
		{
		  
		    if ( !shellForm )
			direction = -direction;


		}
	    } else if ( other.getTag().equals("koopa") && other != this )
	    {
		AABBComponent myC = (AABBComponent) this.findComponent("aabb");
		AABBComponent otherC = (AABBComponent) other.findComponent("aabb");

		if ( otherC.getCenterY() < myC.getCenterY() )
		{ //koopa is over goomba

		} else
		{
		    
		    if ( other.isShellForm() && other.getSpeedX() != 0 )
		    {
			if ( other.getPosX() < this.getPosX() ) //other left of me
			{
			    direction = 1;
			} else //other right of me
			{
			    direction = -1;
			}

			dieAnimation = true;
		    } else
		    {
			if ( !shellForm )
			    direction = -direction;
		    }

		}
	    }
	}

    }

    private void dieAnimation ()
    {
	dieAnimationPlaying = true;
	shellForm = true;
	if ( newStatsApplied == false )
	{
	    speedX = 200;
	    fallDistance = -5f;

	    newStatsApplied = true;
	}

	if ( getPosY() > 600 )
	{
	    setDead(true);
	    newStatsApplied = false;
	    dieAnimation = false;
	    //dieAnimationPlaying = false;
	}
    }

    private void attackFromAbove (GameObject player)
    {
	if ( shellForm )
	{
	    if ( speedX == 0 )
	    {
		speedX = shellSpeed;
		if ( (player.getPosX() + player.getWidth()) / 2 < (getPosX() + getWidth()) / 2 )
		{

		    direction = 1;
		} else
		{
		    direction = -1;

		}
	    } else
	    {
		speedX = 0;
	    }

	} else
	{
	    this.setShellForm(true);
	    shellForm = true;
	    speedX = 0;

	    //setHeight(50);
	}
    }
}
