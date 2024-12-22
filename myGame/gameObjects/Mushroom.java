
package myGame.gameObjects;

import gameEngine.GameContainer;
import gameEngine.Renderer;
import gameEngine.gfx.Image;
import gameEngine.gfx.ImageTile;
import java.awt.Point;
import myGame.GameManager;
import myGame.components.AABBComponent;

public class Mushroom extends PowerUp {

	

	public Mushroom(int tileX, int tileY) {
		tag = "mushroom";
		width = GameManager.TS;
		height = GameManager.TS;
		this.tileX = tileX;
		this.tileY = tileY;
		posX = tileX * GameManager.TS;
		posY = tileY * GameManager.TS;
		spawnPosY = posY;
		centerX = (int) posX + (int) (width / 2);
		centerY = (int) posY + (int) (height / 2);
		direction = 1;
		offX = 0;
		offY = 0;
		speedX = 80;
		padding = 0;
		paddingTop = 0;
		dieAnimationPlaying = false;
		spriteSheet = new ImageTile("/resources/images/mushroom/mushroom.png", 16, 16);
		state = SPAWN_STATE;

		this.addComponent(new AABBComponent(this));
	}
}
