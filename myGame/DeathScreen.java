

package myGame;


import gameEngine.Renderer;
import gameEngine.gfx.Image;




public class DeathScreen
{
    private Image img = new Image("/images/Death_Screen/game_over.jpg");
    
    public DeathScreen(){
	
    }
    
    public void render(Renderer r){
	r.drawImage(img, 0, 0);
	
    }
}
