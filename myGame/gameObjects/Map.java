
package myGame.gameObjects;


import gameEngine.GameContainer;
import gameEngine.Renderer;
import gameEngine.gfx.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import myGame.GameManager;
import myGame.blockTypes.BrickBlock;
import myGame.blockTypes.CoinBlock;


public class Map //extends GameObject
{
    private boolean[][] collisionMap;
    private HashMap<Point, Block> blocks = new HashMap<>();

    private final Image background;
    
    private final int width, height;

    public Map ()
    {
	background = new Image("/images/levels/lvl1/background.png");
	loadLevel("lvl1");
	width = background.getW();
	height = background.getH();
    }

    public void update (GameContainer gc, GameManager gm, float dt)
    {
	Block deadBlock = null;
	
	for ( Point key : blocks.keySet() )
	{
	    blocks.get(key).update(gc, gm, dt);
	    
	    // remove dead blocks
	    if ( blocks.get(key).isDead() )
	    {
		deadBlock = blocks.get(key);
	    }
	}
	
	
	
    }

    public void renderBackground (GameContainer gc, Renderer r)
    {
	r.drawImage(background, 0, 0);
    }
    
    public void renderBlocks (GameContainer gc, Renderer r)
    {
	for ( Point key : blocks.keySet() )
	{
	    blocks.get(key).render(gc, r);
	}
    }

    private void loadLevel (String path)
    {
	final Image input = new Image("/images/levels/" + path + "/colorWorld1_1.png");
	collisionMap = new boolean[input.getH()][input.getW()];

	final int deadBlock = -5680600;
	final int brickBlock = -7849968;
	final int coinBlockC = -4298203;
	final int coinBlockM = -65536;
	final int pipeBlock = -16711936;

	for ( int y = 0; y < input.getH(); y++ )
	{
	    for ( int x = 0; x < input.getW(); x++ )
	    {
		int blockType = input.getP()[x + y * input.getW()];

		switch ( blockType )
		{
		    case deadBlock:
			collisionMap[y][x] = true;
			break;
		    case brickBlock:
			collisionMap[y][x] = true;
			blocks.put(new Point(x, y), new BrickBlock(x, y));
			break;
		    case coinBlockM:
			collisionMap[y][x] = true;
			blocks.put(new Point(x, y), new CoinBlock(x, y, "mushroom"));
			break;
			
		    case coinBlockC:
			collisionMap[y][x] = true;
			blocks.put(new Point(x, y), new CoinBlock(x, y, "coin"));
			break;
		    case pipeBlock:
			collisionMap[y][x] = true;
			break;
		    default:
			collisionMap[y][x] = false;
		}
	    }
	}
	
	//collisionMap[12][10] = true;
	//blocks.put(new Point(10, 12), new BrickBlock(10, 12));
	
	//collisionMap[11][10] = true;
	//blocks.put(new Point(10, 11), new BrickBlock(10, 11));
    }
    
    public boolean getCollision (float posX, float posY) //input: kordinater
    {
	Point tile = new Point((int) (posX / GameManager.TS), (int) (posY / GameManager.TS));
	
	if ( tile.y < 0 || tile.y >= collisionMap[0].length )
	    return false;
	if ( tile.x < 0 || tile.x >= collisionMap[0].length )
	    return true;
	
	return collisionMap[tile.y][tile.x];
    }

    public boolean getCollision (int tileX, int tileY) // input: tile
    {
	if ( tileY < 0 || tileY >= collisionMap.length )
	    return false;
	if ( tileX < 0 || tileX >= collisionMap[0].length )
	    return true;
	
	return collisionMap[tileY][tileX];
    }
    
    public Block getBlock(int tileX, int tileY){
	return blocks.get(new Point(tileX, tileY));
    }
    
    public void blockSetHit (int x, int y) 
    {
	if (blocks.get(new Point(x, y)) != null){
	     blocks.get(new Point(x, y)).setHit(true);
	}  
	   
    }

    public int getWidth ()
    {
	return width;
    }

    public int getHeight ()
    {
	return height;
    }
    
    public HashMap getBlocks ()
    {
	return blocks;
    }
}
