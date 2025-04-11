
package myGame.gameObjects;

import gameEngine.GameContainer;
import gameEngine.Renderer;
import gameEngine.gfx.ImageTile;
import java.awt.event.KeyEvent;

import static java.lang.Math.floor;
import static java.lang.Math.signum;
import myGame.Camera;
import myGame.GameManager;
import myGame.components.AABBComponent;

public class Player extends GameObject {

	private final float SPEED_MAX = 150;
	private float runSpeed = 80;
	private final float ACC_X = 10;

	private final float fallSpeed = 10f; // acceleration?
	private final float jump = -5f;
	private boolean ground = false;
	private float fallDistance = 0f; // velocity?

	private final ImageTile smallMarioSpriteSheet = new ImageTile("/resources/images/mario/MarioSpriteSheet_small.png",
			16, 16);
	private final ImageTile bigMarioSpriteSheet = new ImageTile("/resources/images/mario/MarioSpriteSheet_big.png", 16,
			32);
	private final ImageTile fireMarioSpriteSheet = new ImageTile("/resources/images/mario/MarioSpriteSheet_fire.png", 16,
			32);
	private float anim = 3f;

	public final int SMALL_MARIO = 0, SUPER_MARIO = 1, FIRE_MARIO = 2, STAR_MARIO = 3, DEAD_MARIO = 4;
	public int marioState;

	private boolean renderMario = true;

	private boolean isBig = false;

	// relaterat till winAnimation
	private boolean climbAnimation = false;
	private boolean castleAnimation = false;
	private float timer = 2f;

	private boolean deathStatsApplied = false;
	private boolean tas = false;
	private int[] tasJumpFrames = { 59, 163, 223, 283, 490, 553, 615, 670, 738, 816, 908, 1003, 1103, 1147, 1223 };

	private boolean canTeleport = false;
	private boolean standingOnPlatform = false;

	private GameObject freeObjectSpace;
	private boolean withinPlatformBounds = false;
	//private boolean isInvincible;
	private long invincibilityStartTime;
	private static final long INVINCIBILITY_DURATION = 2000; // 2 seconds
	private static final long FIREBALL_COOLDOWN_DURATION = 500; // 0.5 seconds
private long lastFireballTime = 0;

	public Player(int tileX, int tileY, int startMarioState) {
		this.tag = "player";
		this.tileX = tileX;
		this.tileY = tileY;
		this.offX = 0;
		this.offY = 0;
		this.posX = GameManager.TS * tileX;
		this.posY = GameManager.TS * tileY;
		this.width = GameManager.TS;
		this.height = GameManager.TS;
		this.speedY = 10;
		centerX = (int) (tileX * GameManager.TS) + (int) (width / 2);
		centerY = (int) (tileY * GameManager.TS) + (int) (height / 2);
		this.padding = 1;
		this.paddingTop = 0;
		dieAnimationPlaying = false;
		lifes = 100;

		winAnimation = false;

		isInvincible = false;
    	invincibilityStartTime = 0;

		marioState = startMarioState;

		this.addComponent(new AABBComponent(this));
	}

	@Override
	public void update(GameContainer gc, GameManager gm, float dt) {
		if (freeObjectSpace != null && freeObjectSpace.tag.equals("movingPlatform") &&
				(posX > freeObjectSpace.getPosX() + freeObjectSpace.getWidth()
						|| posX + width < freeObjectSpace.getPosX())) {

			withinPlatformBounds = false;
			standingOnPlatform = false;
		}

		
		if (isInvincible) { //check cooldown period
			if (System.currentTimeMillis() - invincibilityStartTime > INVINCIBILITY_DURATION) {
				isInvincible = false;
			}
		}

		if (winAnimation) {
			winAnimation(gc, gm, dt);
		} else {
			updatePosX(gc, gm, dt);
			updatePosY(gc, gm, dt);
		}

		finalPosition(gm, gc);

		animation(gc, dt);

		updateComponents(gc, gm, dt);

		switch (marioState) {
			case SMALL_MARIO:
				height = GameManager.TS;
				isBig = false;
				break;

			case SUPER_MARIO:
				if (!isBig) {
					// paddingTop = -16;
					height = 32;
					centerY -= GameManager.TS / 2;
					isBig = true;
				}

				break;

			case FIRE_MARIO:
				if (!isBig) {
					// paddingTop = -16;
					height = 32;
					centerY -= GameManager.TS / 2;
					isBig = true;
				}
			//	System.out.println(direction);
				if (gc.getInput().isKey(KeyEvent.VK_SPACE)) {
					long currentTime = System.currentTimeMillis();
					if (currentTime - lastFireballTime > FIREBALL_COOLDOWN_DURATION) {
						gm.addObject(new FireBall(posX, posY, direction));
						lastFireballTime = currentTime;
					}
				}

				break;

			case STAR_MARIO:

				break;

			case DEAD_MARIO:
				setDead(true);
		}

		if (dieAnimationPlaying)
			deathAnimation();
	}

	private void winAnimation(GameContainer gc, GameManager gm, float dt) {
		int bottomOfFlag = 11 * GameManager.TS;
		GameObject flag = gm.getObject("flag");

		if (tileY * GameManager.TS + offY < bottomOfFlag) // if player is not down
		{
			offY += flag.getSpeedY() * dt;
		}

		if ((int) timer == 0) {
			fallDistance = 0;
		}

		if ((int) flag.getPosY() >= bottomOfFlag - 4 && !castleAnimation) // if flag is down
		{
			direction = 1;
			offX += GameManager.TS;

			castleAnimation = true;
			anim = 6;
		}

		if (!((int) flag.getPosY() >= bottomOfFlag) && !(tileY * GameManager.TS + offY < bottomOfFlag)) {
			climbAnimation = false;
		} else {
			climbAnimation = true;
		}

		if (castleAnimation && (int) timer != 0) {
			timer -= dt;
		} else if (castleAnimation) // gå till slottet
		{

			offX += 80 * dt;
			tileY = 12 - marioState;
			direction = 0;

			switch(gm.getCurrentLevel()){
				case 1:
					if (tileX == 203) {
						gm.getCamera().setMoveCamera(false);
		
					} else if (tileX == 204){
						renderMario = false;	
					}
					break;

				case 2:
					if(tileX == 233){
						gm.getCamera().setMoveCamera(false);
					}else if (tileX == 238){
						renderMario = false;	
					}
					break;
			}
			
			
		}

	}

	private void updatePosX(GameContainer gc, GameManager gm, float dt) {
		// Right
		if ((gc.getInput().isKey(KeyEvent.VK_D)) && !dieAnimationPlaying) {
			if ((gm.getCollision(tileX + 1, tileY + (isBig ? 1 : 0))
					|| gm.getCollision(tileX + 1, tileY + (isBig ? 1 : 0) + (int) Math.signum((int) offY)))) {
				runSpeed += ACC_X;
				runSpeed = constrain(runSpeed, SPEED_MAX, -SPEED_MAX);
				offX += dt * runSpeed;

				if (offX > padding) {
					runSpeed = 0;
					offX = padding;
				}
			}
			direction = 1;
			runSpeed += ACC_X;
			runSpeed = constrain(runSpeed, SPEED_MAX, -SPEED_MAX);
			offX += dt * runSpeed;
		}

		// Left
		if (gc.getInput().isKey(KeyEvent.VK_A) && !dieAnimationPlaying) {
			if (posX > gm.getCamera().getOffX()) { // Collision to camera
				if ((gm.getCollision(tileX - 1, tileY + (isBig ? 1 : 0))
						|| gm.getCollision(tileX - 1, tileY + (isBig ? 1 : 0) + (int) Math.signum((int) offY)))) {
					runSpeed -= ACC_X;
					runSpeed = constrain(runSpeed, SPEED_MAX, -SPEED_MAX);
					offX += dt * runSpeed;

					if (offX < -padding) {
						runSpeed = 0;
						offX = -padding;
					}
				} else if (!(runSpeed > 0 && gc.getInput().isKey(KeyEvent.VK_D))) {
					runSpeed -= ACC_X;
					runSpeed = constrain(runSpeed, SPEED_MAX, -SPEED_MAX);
					offX += dt * runSpeed;
				}
			} else {
				runSpeed = 0;
				anim = 3;
			}

			

		}

		// Stopping
		if (!(gc.getInput().isKey(KeyEvent.VK_A) || gc.getInput().isKey(KeyEvent.VK_D)) && (int) runSpeed != 0) {

			if ((int) runSpeed != 0) {
				if (runSpeed > 0) {
					runSpeed -= ACC_X;
				} else if (runSpeed < 0) {
					runSpeed += ACC_X;
				}

				offX += dt * runSpeed;
			}
			runSpeed = 0;
		}
	}

	private void updatePosY(GameContainer gc, GameManager gm, float dt) {

		/*
		 * if(gc.getInput().isKeyDown(KeyEvent.VK_E)){
		 * System.out.println(gm.frameCounter);
		 * }
		 */
		// Jump and Gravity
		// System.out.println("ground before"+ground);
		if (fallDistance != 0) {
			ground = false;
		}

		// System.out.println("ground after"+ground);

		fallDistance += dt * fallSpeed;
		if (ground) {
			// System.out.println(gm.frameCounter);
			boolean tasJump = false;
			if (tas) {

				for (int frame : tasJumpFrames) {
					if (GameManager.frameCounter == frame) {
						tasJump = true;
						break;
					}
				}
			}

			if (gc.getInput().isKeyDown(KeyEvent.VK_W) || tasJump) {
				standingOnPlatform = false;
				fallDistance = jump;
			}
		}

		offY += fallDistance;

		if (fallDistance < 0) {
			if ((gm.getCollision(tileX, tileY - 1)
					|| gm.getCollision(tileX + (int) Math.signum((int) Math.abs(offX) > padding ? offX : 0), tileY - 1))
					&& offY < paddingTop && !dieAnimationPlaying) {
				fallDistance = 0;
				offY = paddingTop;

				// Update the collision block
				if (gm.getCollision(tileX, tileY - 1)) {
					gm.getMap().blockSetHit(tileX, tileY - 1);
				} else {
					gm.getMap().blockSetHit(tileX + (int) Math.signum((int) Math.abs(offX) > padding ? offX : padding),
							tileY - 1);
				}
			}
		} else if (fallDistance > 0) {
			if ((gm.getCollision(tileX, tileY + 1 + (isBig?1:0)) || gm.getCollision(
					tileX + (int) Math.signum((int) Math.abs(offX) > padding ? offX : 0), tileY + 1 + (isBig?1:0))
					&& offY > 0) && !dieAnimationPlaying) {
				fallDistance = 0;
				offY = 0;
				System.out.println("fall down");
				ground = true;
			}
		}

		if (tileY > 20)

			marioState = DEAD_MARIO;
		// System.exit(1);
	}

	private void finalPosition(GameManager gm, GameContainer gc) {

		if (offY > GameManager.TS / 2) {
			tileY++;
			offY -= GameManager.TS;
		}

		if (offY < -GameManager.TS / 2) {
			tileY--;
			offY += GameManager.TS;
		}

		if (offX > GameManager.TS / 2) {
			tileX++;
			offX -= GameManager.TS;
		}

		if (offX < -GameManager.TS / 2) {
			tileX--;
			offX += GameManager.TS;
		}

		posX = tileX * GameManager.TS + offX;
		posY = tileY * GameManager.TS + offY;
		//System.out.println(tileX);
		if (canTeleport) {
			TeleportPipe teleportPipe = gm.getTeleportPipe(freeObjectSpace);
			if (gc.getInput().isKey(teleportPipe.getEntranceKeycode())) {
				tileX = teleportPipe.getTeleportToCoords().x;
				tileY = teleportPipe.getTeleportToCoords().y;
				gm.getCamera().setMoveCamera(true);
				gm.getCamera().setOffX(teleportPipe.getCameraPosition() * GameManager.TS);
			}

		}

		canTeleport = false;

		if (standingOnPlatform) {
			System.out.println("standing on platform");
			posY = gm.getMovingPlatform(freeObjectSpace).getPosY() - height;
			tileY = (int) posY / GameManager.TS;
			fallDistance = 0;
			ground = true;

		}

		System.out.println(dieAnimationPlaying);

	}

	private void animation(GameContainer gc, float dt) {
		if (!winAnimation) {
			if (!ground) // In air
			{
				if (runSpeed > 0)
					direction = 0;
				else if (runSpeed < 0)
					direction = 1;

				anim = 4;

			} // else if ( (int) runSpeed == 0 ) // Standing still
			else if (!gc.getInput().isKey(KeyEvent.VK_A) && !gc.getInput().isKey(KeyEvent.VK_D)) {
				anim = 3;

			} else if (runSpeed > 0) // Right direction
			{
				direction = 0;

				if (gc.getInput().isKey(KeyEvent.VK_A) && !gc.getInput().isKey(KeyEvent.VK_D))// Slide
				{
					direction = 1;
					anim = 5;
				} else // Running
				{
					anim += dt * 5;
					anim %= 3;
				}

			} else if (runSpeed < 0) // Left direction
			{
				direction = 1;

				if (gc.getInput().isKey(KeyEvent.VK_D) && !gc.getInput().isKey(KeyEvent.VK_A))// Slide
				{
					direction = 0;
					anim = 5;
				} else {
					anim += dt * 5;
					anim %= 3;
				}
			}

			if (dieAnimationPlaying) {
				anim = 0;
				direction = 2;
			}
		} else {
			if (climbAnimation) {
				anim += dt * 10;

				anim %= 2;
				anim += 6;
			} else {
				// anim = 6;
			}

			if (castleAnimation && (int) timer <= 0) {
				climbAnimation = false;
				anim += dt * 5;
				anim %= 3;

			}
		}
	}

	@Override
	public void render(GameContainer gc, Renderer r) {
		if (renderMario) {
			switch (marioState) {
				case SMALL_MARIO:
					r.drawImageTile(smallMarioSpriteSheet, (int) posX, (int) posY, (int) anim, direction);

					break;

				case SUPER_MARIO:
					r.drawImageTile(bigMarioSpriteSheet, (int) posX, (int) posY, (int) anim, direction);

					break;

				case FIRE_MARIO:
					r.drawImageTile(fireMarioSpriteSheet, (int) posX, (int) posY, (int) anim, direction);
					
					break;

				case STAR_MARIO:

					break;
			}

			r.drawFillRect((int) posX, (int) posY, width, height, 2);
		}

		this.renderComponents(gc, r);
	}

	private float constrain(float value, float max_val, float min_val) {
		if (value > max_val)
			return max_val;

		if (value < min_val)
			return min_val;

		return value;
	}

	private void loseLife() {
		
			switch (marioState) {
				case SMALL_MARIO:
					dieAnimationPlaying = true;
					break;
	
				case SUPER_MARIO:
					startCooldown();
					marioState = SMALL_MARIO; 
					break;
	
				case FIRE_MARIO:
					startCooldown();
					marioState = SUPER_MARIO;
					break;
	
				case STAR_MARIO:
	
					break;
			
		
		}
	}

	private void startCooldown() {
		if(!isInvincible){
			isInvincible = true;
			invincibilityStartTime = System.currentTimeMillis();
		}
	}

	private void deathAnimation() {
		if (!deathStatsApplied) {
			marioState = 0;
			runSpeed = 0;
			fallDistance = -5f;
			deathStatsApplied = true;

		}

	}

	@Override
	public void collision(GameObject other) {

		if (other.tag == "player") {
			return;
		}

		AABBComponent myC = (AABBComponent) this.findComponent("aabb");
		AABBComponent otherC = (AABBComponent) other.findComponent("aabb");
		if(dieAnimationPlaying)return;

		
		if (!other.isDieAnimationPlaying() && !dieAnimationPlaying  && !isInvincible) {
			if ((other.getTag().equals("goomba") || other.getTag().equals("koopa"))) {

				if (other.getPosY() + other.getHeight() < myC.getCenterY()) { // other is over player'

					loseLife();
				} else if (posY + height < otherC.getCenterY()) { // jag över han
					fallDistance = -2f;
				} else { // bredvid
					if ((!other.isShellForm()
							|| (other.isShellForm() && other.getDirection() == signum(posX - other.getPosX())))
							&& other.getSpeedX() != 0) {

						loseLife();
					}
				}

			} else if(other.getTag().equals("piranhaPlant")){
				loseLife();
			}
		}
		
		if (other.tag == "movingPlatform") {
			// System.out.println("platfrom collide");
			if (fallDistance < 0 && posY + height / 2 > other.posY) {
				// System.out.println("fall up");
				fallDistance = 0;
				offY = paddingTop;
			} else if (fallDistance >= 0) {
				// System.out.println("fall down");
				fallDistance = 0;
				// offY = 0;
				// posY = other.posY - height;
				// offY = paddingTop;
				System.out.println("in collidion with movingplatform");
				ground = true;

				standingOnPlatform = true;
				freeObjectSpace = other;
			}
		} else if (other.tag == "teleportPipe") {
			//System.out.println("collide with pipe");
			freeObjectSpace = other;
			canTeleport = true;
		} else if (other.getTag().equals("mushroom")) {
			marioState = SUPER_MARIO;
			posY-=16;
		
		}else if(other.getTag().equals("fireFlower")){
			marioState = FIRE_MARIO;
		}else {

			freeObjectSpace = null;
			canTeleport = false;
			standingOnPlatform = false;

		}

	}

	public boolean isisBig(){
		return isBig;
	}

	public void setRenderMario(boolean renderMario) {
		this.renderMario = renderMario;
	}	

	public int getMarioState(){
		return marioState;
	}

}
