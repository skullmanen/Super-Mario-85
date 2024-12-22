package myGame.gameObjects;

import gameEngine.GameContainer;
import gameEngine.Renderer;
import myGame.GameManager;

public abstract class PowerUp extends MovingEntity{
    public final int SPAWN_STATE = 0, NORMAL_STATE = 1;
	int state;
	float spawnPosY;
	boolean blockBounce = false;

    public PowerUp(){


    }

    @Override
	public void update(GameContainer gc, GameManager gm, float dt) {

		switch (state) {
			case SPAWN_STATE:
				posY -= 0.5;
				offY -= 0.5;

				if (posY <= spawnPosY - height) {
					state = NORMAL_STATE;
				}

				break;

			case NORMAL_STATE:

				anim += dt * 5;

				finalPosition();
				updatePosY(gm, dt);
				updatePosX(gm, dt);

				if (blockDeath) {
					blockBounce();
				}

				this.updateComponents(gc, gm, dt);
				break;
		}

	}

	@Override
	public void render(GameContainer gc, Renderer r) {

		r.drawImageTile(spriteSheet, (int) posX, (int) posY, (int) 0, 0);

		this.renderComponents(gc, r);
		// if(dead )
	}

	@Override
	public void collision(GameObject other) {

		if (other.getTag().equals("player")) {
			this.setDead(true);
		}
	}

	private void blockBounce() {

		if (newStatsApplied == false) {

			fallDistance = -3f;

			newStatsApplied = true;
		}

		if (posY >= tempPosY) {

			newStatsApplied = false;
			blockDeath = false;
			// dieAnimationPlaying = false;
		}
	}
}
