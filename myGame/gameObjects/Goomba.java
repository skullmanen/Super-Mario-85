
package myGame.gameObjects;

import gameEngine.GameContainer;
import gameEngine.Renderer;
import gameEngine.gfx.ImageTile;
import myGame.GameManager;
import myGame.components.AABBComponent;

public class Goomba extends MovingEntity {

	private final ImageTile deadImage;
	private boolean crushed = false;
	private boolean dieAnimation2 = false;

	int killTimer = 0;

	public Goomba(int tileX, int tileY) {

		tag = "goomba";
		this.tileX = tileX;
		this.tileY = tileY;
		posX = tileX * GameManager.TS;
		posY = tileY * GameManager.TS;
		speedX = 80;
		direction = -1;

		width = GameManager.TS;
		height = GameManager.TS;
		centerX = (int) posX + (int) (width / 2);
		centerY = (int) posY + (int) (height / 2);
		spriteSheet = new ImageTile("/resources/images/enemies/Goomba/goomba.png", 16, 16);
		deadImage = new ImageTile("/resources/images/enemies/Goomba/Goomba_3.png", 16, 16);
		offX = 0;
		offY = 0;
		padding = 0;
		paddingTop = 0;
		dieAnimationPlaying = false;
		lifes = 1;

		this.addComponent(new AABBComponent(this));
	}

	@Override
	public void update(GameContainer gc, GameManager gm, float dt) {
		if (playerProximity) {
			anim += dt * 5;

			updatePosX(gm, dt);
			updatePosY(gm, dt);
			finalPosition();

			if (crushed) {
				dieAnimation1();
			} else if (dieAnimation2 || blockDeath) {
				dieAnimation2();
			}
		}

		if (gm.getObject("player") != null && Math.abs(gm.getObject("player").getTileX() - tileX) <= 12) {
			playerProximity = true;
		}

		// dieAnimation2Condition();

		this.updateComponents(gc, gm, dt);
	}

	@Override
	public void render(GameContainer gc, Renderer r) {

		if (crushed) {
			paddingTop = 4;
			r.drawImageTile(deadImage, (int) posX, (int) posY + paddingTop, (int) (0), 0);
		} else {
			r.drawImageTile(spriteSheet, (int) posX, (int) posY, (int) (anim % 2), 0);
		}

		this.renderComponents(gc, r);
	}

	@Override
	public void collision(GameObject other) {
		if (!other.isDieAnimationPlaying()) {
			if (other.getTag().equals("player") && !other.isInvincible) {

				AABBComponent myC = (AABBComponent) this.findComponent("aabb");
				AABBComponent otherC = (AABBComponent) other.findComponent("aabb");
				if (posY + height < otherC.getCenterY()) { // this over other

				}

				else if (other.getPosY() + other.getHeight() < myC.getCenterY() ) { // player is over goomba
					crushed = true;

				}

			} else if (other.getTag().equals("koopa")) {
				AABBComponent myC = (AABBComponent) this.findComponent("aabb");
				AABBComponent otherC = (AABBComponent) other.findComponent("aabb");

				if (otherC.getCenterY() < myC.getCenterY()) { // koopa is over goomba

				} else {

					if (other.isShellForm() && other.getSpeedX() != 0) {
						if (other.getPosX() < this.getPosX()) // other left of me
						{
							direction = 1;
						} else // other right of me
						{
							direction = -1;
						}

						dieAnimation2 = true;
					} else {
						direction = -direction;
					}

				}
			} else if (other.getTag().equals("goomba") && other != this) {
				AABBComponent myC = (AABBComponent) this.findComponent("aabb");
				AABBComponent otherC = (AABBComponent) other.findComponent("aabb");

				if (otherC.getCenterY() < myC.getCenterY()) { // koopa is over goomba

				} else if (myC.getCenterY() < otherC.getCenterY()) {
					// koopa over
				} else {

					direction = -direction;

				}
			}
		}
		if(other.getTag().equals("fireball")){
			direction = other.getDirection();
			dieAnimation2 = true;
		}

	}

	private void dieAnimation1() {//crushed
		dieAnimationPlaying = true;
		killTimer++;
		// dead = true;
		if (!newStatsApplied) {
			setPaddingTop(-10);
			setHeight(8);
			newStatsApplied = true;
		}
		speedX = 0;
		fallDistance = 0;
		offY = 0;

		if (killTimer == 120) { // möjliga buggar kan uppkomma här
			dead = true;

			// dieAnimationPlaying = false;

		}
	}

	private void dieAnimation2() {//not crushed
		dieAnimationPlaying = true;
		if (newStatsApplied == false) {
			speedX = 75;
			fallDistance = -5f;

			newStatsApplied = true;
		}

		if (getPosY() > 600) {
			setDead(true);
			newStatsApplied = false;
			dieAnimation2 = false;
			// dieAnimationPlaying = false;
		}
	}
}
