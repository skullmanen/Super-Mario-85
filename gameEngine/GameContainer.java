
package gameEngine;


import java.util.logging.Level;
import java.util.logging.Logger;



public class GameContainer implements Runnable
{

    private Thread thread;
    private Window window;
    private Renderer renderer;
    private Input input;
    private AbstractGame game;

    private boolean running = false;
    private static int targetFPS = 60;
    private final int targetTime = 1000000000 / targetFPS; // 1 / 60 sekund i nanosekunder
    
    private int width = 320, height = 240;
    private float scale = 1f;
    private String tiltle = "MyEngine v2.0";

    public GameContainer (AbstractGame game)
    {
	this.game = game;
    }

    public void start ()
    {
	window = new Window(this);
	renderer = new Renderer(this);
	input = new Input(this);

	thread = new Thread(this);
	thread.run();
    }

    public void stop ()
    {

    }

    public void run ()
    {
	running = true;
	
	int totalFrames = 0;
	double lastFpsCheck = 0;
	double currentFPS = 0;

	while ( running )
	{
	    //FPS counter
	    totalFrames++;
	    if (System.nanoTime() > lastFpsCheck + 1000000000)
	    {
		lastFpsCheck = System.nanoTime();
		currentFPS = totalFrames;
		totalFrames = 0;
	    }
	    
	    
	    long startTime = System.nanoTime();
	    
	    update();
	    render((int)currentFPS);

	    long totalTime = System.nanoTime() - startTime;
	    
	    // sleep
	    if ( totalTime < targetTime )
	    {
		try
		{
		    Thread.sleep((targetTime - totalTime) / 1000000 );
		} catch ( InterruptedException ex )
		{
		    Logger.getLogger(GameContainer.class.getName()).log(Level.SEVERE, null, ex);
		}
	    }
	    
	}
    }

    private void update ()
    {
	game.update(this, (float) 1 / targetFPS);
	input.update();
    }

    private void render (int fps)
    {
	renderer.clear();
	game.render(this, renderer);
	//renderer.drawText("FPS:" + fps, renderer.getCamX(), renderer.getCamY(), 0xffffffff);
	window.update();
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

    public float getScale ()
    {
	return scale;
    }

    public void setScale (float scale)
    {
	this.scale = scale;
    }

    public String getTiltle ()
    {
	return tiltle;
    }

    public void setTiltle (String tiltle)
    {
	this.tiltle = tiltle;
    }

    public Window getWindow ()
    {
	return window;
    }

    public Input getInput ()
    {
	return input;
    }

    public Renderer getRenderer ()
    {
	return renderer;
    }
    
}
