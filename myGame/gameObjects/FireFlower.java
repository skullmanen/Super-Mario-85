
package myGame.gameObjects;

import gameEngine.GameContainer;
import gameEngine.Renderer;
import gameEngine.gfx.ImageTile;
import myGame.GameManager;
import myGame.components.AABBComponent;

public class FireFlower extends PowerUp {
 	

	public FireFlower(int tileX, int tileY) {
		tag = "fireFlower";
		this.tileX = tileX;
		this.tileY = tileY;
		posX = tileX * GameManager.TS;
		posY = tileY * GameManager.TS;
		spawnPosY = posY;
		width = GameManager.TS;
		height = GameManager.TS;
		centerX = (int) posX + (int) (width / 2);
		centerY = (int) posY + (int) (height / 2);
		padding = 0;
		paddingTop = 0;
		direction = 0;
		speedX = 0;
		speedY = 0;
		//fallSpeed = 0;
		fallDistance = 0;
		spriteSheet = new ImageTile("/resources/images/FireFlower/fireFlower.png", 16, 16);

		this.addComponent(new AABBComponent(this));

	}
}
