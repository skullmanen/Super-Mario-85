
package myGame.components;


import gameEngine.GameContainer;
import gameEngine.Renderer;
import myGame.AABBCollision;
import myGame.GameManager;
import myGame.gameObjects.GameObject;


public class AABBComponent extends Component
{

    private GameObject parent;
    private int centerX, centerY, halfWidth, halfHeight;

    public AABBComponent (GameObject parent)
    {
        tag = "aabb";
        this.parent = parent;
    }

    @Override
    public void update (GameContainer gc, GameManager gm, float dt)
    {
        centerX = (int) parent.getPosX() + (int) (parent.getWidth() / 2);
        centerY = (int) parent.getPosY() + (int) (parent.getHeight() / 2);
        halfWidth = parent.getWidth() / 2;
        halfHeight = parent.getHeight() / 2;
        
        AABBCollision.addAABBComponent(this);
    }

    @Override
    public void render (GameContainer gc, Renderer r)
    {
        //System.out.println("rendering AABBComponent: " + parent.getTag());
	    r.drawRect((int) (centerX - halfWidth), (int) (centerY - halfHeight), (int) (halfWidth * 2), (int) (halfHeight * 2), 0xffff00ff);

    }

    public GameObject getParent ()
    {
	return parent;
    }

    public void setParent (GameObject parent)
    {
	this.parent = parent;
    }

    public int getCenterX ()
    {
	return centerX;
    }

    public void setCenterX (int centerX)
    {
	this.centerX = centerX;
    }

    public int getCenterY ()
    {
	return centerY;
    }

    public void setCenterY (int centerY)
    {
	this.centerY = centerY;
    }

    public int getHalfWidth ()
    {
	return halfWidth;
    }

    public void setHalfWidth (int halfWidth)
    {
	this.halfWidth = halfWidth;
    }

    public int getHalfHeight ()
    {
	return halfHeight;
    }

    public void setHalfHeight (int halfHeight)
    {
	this.halfHeight = halfHeight;
    }

}
