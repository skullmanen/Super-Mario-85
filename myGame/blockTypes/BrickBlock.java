
package myGame.blockTypes;

import gameEngine.GameContainer;
import gameEngine.Renderer;
import java.awt.Point;
import myGame.GameManager;
import myGame.gameObjects.Block;
import myGame.gameObjects.Coin;

public class BrickBlock extends Block {

	private int containingCoins = 10;

	public BrickBlock(int tileX, int tileY, String path) {
		super(tileX, tileY, "/resources/images/blocks/brick_block/" + path);
		tag = "brickBlock";
	}

	@Override
	public void update(GameContainer gc, GameManager gm, float dt) {
		switch (state) {
			case NORMAL_STATE:
				anim = 0;
				if (hit) {
					if (tileX == 94) {
						containingCoins--;
						gm.addObject(new Coin(tileX, tileY - 1));
						if (containingCoins == 0) {
							gm.getMap().getBlocks().replace(new Point(tileX, tileY),
									new CoinBlock(tileX, tileY, "coin"));
							gm.getMap().getBlock(tileX, tileY).setHit(true);
						}
					}
					state = HIT_STATE;
					anim = 0;
				}
				break;

			case HIT_STATE:
				anim += dt * 20;
				break;
		}
	}

	@Override
	public void render(GameContainer gc, Renderer r) {
		switch (state) {
			case NORMAL_STATE:
				r.drawImageTile(spriteSheet, (int) (posX), (int) (posY + offY), 5, 0);
				break;

			case HIT_STATE:
				r.drawImageTile(spriteSheet, (int) (posX), (int) (posY + offY), (int) (anim % 6), 0);
				if ((int) anim % 7 == 6) {
					hit = false;
					state = NORMAL_STATE;
				}

				break;
		}
	}

}
