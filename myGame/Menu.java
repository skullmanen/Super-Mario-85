package myGame;


import gameEngine.Renderer;
import gameEngine.gfx.Image;



public class Menu 
{
    
    private int posX;
    private int posY;
    private int width = 166;
    private int height = 100;
    private Image img = new Image("/images/Menu/marioMeny.png");
    
    public Menu()
    {
	posY = GameManager.TS * 2;
	posX = GameManager.TS * 3;
    }
    
    public void render(Renderer r) {
	r.drawImage(img, posX, posY);
	r.drawText("Klick to play", (int)(posX + GameManager.TS * 3.5f), posY + GameManager.TS * 7, 0xff000000);
    }
    
}
