 
package myGame;


import myGame.gameObjects.GameObject;
import gameEngine.GameContainer;



public class Camera
{

    private float offX, offY;

    String targetTag;
    private GameObject target = null;

    public Camera (String tag)
    {
	this.targetTag = tag;
	offX = 0;
	offY = 0;
    }

    public void update (GameContainer gc, GameManager gm, float dt)
    {
	if ( target == null )
	{
	    target = gm.getObject(targetTag);
	}

	if ( target == null )
	    return;

	float targetX = (target.getPosX() + target.getWidth() / 2) - gc.getWidth() / 2;
	
	if (target.getPosX() + target.getWidth() / 2 > offX + gc.getWidth() / 2) {
	    offX = targetX;
	    //offX -= dt * (offX - targetX) * 2;
	}
	
	
	
	
	
	//if(targetX > gm.getMap().getWidth() - 24 * GameManager.TS){
	    //offX = gm.getMap().getWidth() - 16 * GameManager.TS;
	//} else{
	    if (offX < 0) offX = 0;
	    if (offY < 0) offY = 0;
	    if (offX + gc.getWidth() > gm.getMap().getWidth()) offX = gm.getMap().getWidth() - gc.getWidth();
	    if (offY + gc.getHeight()> gm.getMap().getHeight()) offY = gm.getMap().getHeight() - gc.getHeight();
	//}
	gc.getRenderer().setCamX((int)offX);
	
	
    }

    public float getOffX ()
    {
	return offX;
    }

    public void setOffX (float offX)
    {
	this.offX = offX;
    }

    public float getOffY ()
    {
	return offY;
    }

    public void setOffY (float offY)
    {
	this.offY = offY;
    }

    public String getTargetTag ()
    {
	return targetTag;
    }

    public void setTargetTag (String targetTag)
    {
	this.targetTag = targetTag;
    }

    public GameObject getTarget ()
    {
	return target;
    }

    public void setTarget (GameObject target)
    {
	this.target = target;
    }
    
    
}
