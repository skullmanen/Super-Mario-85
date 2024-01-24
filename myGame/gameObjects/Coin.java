
package myGame.gameObjects;


import gameEngine.GameContainer;
import gameEngine.Renderer;
import gameEngine.gfx.ImageTile;
import myGame.GameManager;



public class Coin extends GameObject
{
    private ImageTile spriteSheet;
    private float anim = 1;
    private int padding, paddingTop;
    
    private final int BEGINNING_SPEED = 300;
    private int speed, acc;
    
    public Coin(int tileX, int tileY)
    {
	spriteSheet = new ImageTile("/images/coin/CoinSpriteSheet.png", 8, 14);
	tag = "coin";
	this.posX = tileX * GameManager.TS;
	this.posY = tileY * GameManager.TS;
	this.width = spriteSheet.getTileW();
	this.height = spriteSheet.getTileH();
	padding = (GameManager.TS - spriteSheet.getTileW()) / 2;
	paddingTop = 1;
	speed = BEGINNING_SPEED;
	acc = -20;
    }

    @Override
    public void update (GameContainer gc, GameManager gm, float dt)
    {
	speed += acc;
	posY -= dt * speed;
	
	anim += dt * 13;
	if (anim > 4) anim = 0;
	
	if (speed < -BEGINNING_SPEED)
	    dead = true;
    }

    @Override
    public void render (GameContainer gc, Renderer r)
    {
	r.drawImageTile(spriteSheet, (int)posX + padding, (int)posY + paddingTop, (int)anim, 0);
    }

    @Override
    public void collision (GameObject other)
    {
	throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
