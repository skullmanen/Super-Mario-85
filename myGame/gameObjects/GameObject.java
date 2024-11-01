package myGame.gameObjects;

import myGame.components.Component;
import gameEngine.GameContainer;
import gameEngine.Renderer;
import java.util.ArrayList;
import myGame.GameManager;
import java.awt.Point;

public abstract class GameObject {
    
    protected String tag;
    protected float posX, posY;
    protected int width, height;
    protected float speedX, speedY;   
    protected int centerX, centerY;
    protected int tileX, tileY;
    protected float offX, offY;
    protected int padding, paddingTop;
    protected boolean dead = false;
    protected int lifes;
    protected boolean dieAnimationPlaying;
    protected boolean shellForm;
    protected int direction;
    protected boolean winAnimation;
    
    protected ArrayList<Component> components = new ArrayList<>();
    
    public abstract void update(GameContainer gc, GameManager gm, float dt);
    public abstract void render(GameContainer gc, Renderer r);
    public abstract void collision(GameObject other);
    
    public void updateComponents(GameContainer gc, GameManager gm, float dt){
	for(Component c: components){
	    c.update(gc, gm, dt);
	}
    }
    
    public void renderComponents(GameContainer gc, Renderer r){
	for(Component c: components){
	    c.render(gc, r);
	}
    }
    
    public void addComponent(Component c){
	components.add(c);
    }
    
    public void removeComponent(Component c){
	for(int i = 0; i < components.size(); i++){
	    if(components.get(i).getTag().equalsIgnoreCase(tag)){
		components.remove(c);
	    }
	}
    }
    
     public Component findComponent(String tag){
	for(int i = 0; i < components.size(); i++){
	    if(components.get(i).getTag().equalsIgnoreCase(tag)){
		return components.get(i);
	    }
	}
	
	return null;
    }
    

    public String getTag ()
    {
	return tag;
    }

    public void setTag (String tag)
    {
	this.tag = tag;
    }

    public float getPosX ()
    {
	return posX;
    }

    public void setPosX (float posX)
    {
	this.posX = posX;
    }

    public float getPosY ()
    {
	return posY;
    }

    public void setPosY (float posY)
    {
	this.posY = posY;
    }

    public int getTileX ()
    {
	return tileX;
    }

    public void setTileX (int tileX)
    {
	this.tileX = tileX;
    }
    
    

    public int getWidth ()
    {
	return width;
    }

    public void setWidth (int width)
    {
	this.width = width;
    }

    public int getHeight ()
    {
	return height;
    }

    public void setHeight (int height)
    {
	this.height = height;
    }

    public float getSpeedX ()
    {
	return speedX;
    }

    public void setSpeedX (float speedX)
    {
	this.speedX = speedX;
    }
    
    public float getSpeedY ()
    {
	return speedY;
    }

    public void setSpeedY (float speedY)
    {
	this.speedY = speedY;
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
    
    

    public boolean isDead ()
    {
	return dead;
    }

    public void setDead (boolean dead)
    {
	this.dead = dead;
    }

    public int getPadding ()
    {
	return padding;
    }

    public void setPadding (int padding)
    {
	this.padding = padding;
    }

    public int getPaddingTop ()
    {
	return paddingTop;
    }

    public void setPaddingTop (int paddingTop)
    {
	this.paddingTop = paddingTop;
    }     
       

    public boolean isShellForm ()
    {
	return shellForm;
    }

    public void setShellForm (boolean shellForm)
    {
	this.shellForm = shellForm;
    }

    public int getDirection ()
    {
	return direction;
    }

    public void setDirection (int direction)
    {
	this.direction = direction;
    }
    
    
  

    public boolean isDieAnimationPlaying ()
    {
	return dieAnimationPlaying;
    }

    public void setDieAnimationPlaying (boolean dieAnimationPlaying)
    {
	this.dieAnimationPlaying = dieAnimationPlaying;
    }
    
    public void setWinAnimation (boolean b) 
    {
	winAnimation = b;
    }



    

}
