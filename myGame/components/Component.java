

package myGame.components;


import gameEngine.GameContainer;
import gameEngine.Renderer;
import myGame.GameManager;





public abstract class Component
{
    protected String tag;

   
    public abstract void update(GameContainer gc, GameManager gm, float dt);
    public abstract void render(GameContainer gc, Renderer r);
    
     public String getTag ()
    {
	return tag;
    }

    public void setTag (String tag)
    {
	this.tag = tag;
    }
}   
