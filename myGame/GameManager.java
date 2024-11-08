
package myGame;

import gameEngine.Window;
import myGame.gameObjects.GameObject;
import myGame.gameObjects.Player;
import myGame.gameObjects.Map;
import gameEngine.AbstractGame;
import gameEngine.GameContainer;
import gameEngine.Renderer;
import gameEngine.audio.SoundClip;

import static java.awt.Event.ESCAPE;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.sql.SQLOutput;
import java.util.ArrayList;

import myGame.gameObjects.Flag;
import myGame.gameObjects.Goomba;
import myGame.gameObjects.Koopa;
import myGame.gameObjects.TeleportPipe;

import javax.swing.*;

import java.awt.image.*;
import javax.imageio.ImageIO;

public class GameManager extends AbstractGame {

    public final int MENU_STATE = 0, TRANSITION_STATE = 1, PLAY_STATE = 2, WIN_STATE = 3, FAIL_STATE = 4;
    public int gameState = MENU_STATE;

    public static final int TS = 16; // tile size

    public final ArrayList<GameObject> objects = new ArrayList<>();
    public Camera camera;
    private Map map;
    private Menu menu;
    private DeathScreen death;
    private SoundClip mouseKlick;
    private LeaderBoard leaderBoard;

    public float gameTime;
    public static int frameCounter = 0;

    private ArrayList<TeleportPipe> teleportPipes = new ArrayList<>();

    public GameManager() {
        initialize_game();
    }

    @Override
    public void update(GameContainer gc, float dt) {

        if (gc.getInput().isKey(KeyEvent.VK_R)) {
            leaderBoard.showTimes();
            initialize_game();
            camera.update(gc, this, dt);
            gameState = MENU_STATE;
        } else if (gc.getInput().isKey(ESCAPE)) {
            System.out.println(leaderBoard);
            leaderBoard.writeTimesToTextfile();
            System.exit(1);
        }

        switch (gameState) {
            case MENU_STATE:

                if (gc.getInput().isButtonDown(MouseEvent.BUTTON1) || gc.getInput().isKey(KeyEvent.VK_SPACE)) {
                    mouseKlick.play();
                    gameState = PLAY_STATE;
                }

                break;

            case TRANSITION_STATE:
                break;

            case PLAY_STATE:
                frameCounter++;
                gameTime -= dt;
                map.update(gc, this, dt);
                if (getObject("player").getTileX() == getObject("flag").getTileX()) {
                    gameState = WIN_STATE;
                    getObject("player").setWinAnimation(true);
                    leaderBoard.addTime(gameTime);
                }

                for (int i = 0; i < objects.size(); i++) {
                    objects.get(i).update(gc, this, dt);

                    if (objects.get(i).isDead()) {
                        if ("player".equals(objects.get(i).getTag())) {
                            gameState = FAIL_STATE;
                        }
                        objects.get(i).setDieAnimationPlaying(false);
                        objects.remove(i);
                        i--;

                    }
                }

                AABBCollision.update();

                camera.update(gc, this, dt);

                break;

            case WIN_STATE:

                map.update(gc, this, dt);

                for (int i = 0; i < objects.size(); i++) {
                    objects.get(i).update(gc, this, dt);
                    if (objects.get(i).isDead()) {
                        objects.get(i).setDieAnimationPlaying(false);
                        objects.remove(i);
                        i--;
                    }
                }

                AABBCollision.update();

                camera.update(gc, this, dt);

                break;

            case FAIL_STATE:
                // System.out.println("hllo");

                break;

        }
    }

    @Override
    public void render(GameContainer gc, Renderer r) {

        map.renderBackground(gc, r);

        for (GameObject obj : objects) {
            obj.render(gc, r);
        }

        map.renderBlocks(gc, r);

        if (gameState == MENU_STATE) {
            menu.render(r);
        } else if (gameState == FAIL_STATE) {
            // death.render(r);
        }

        r.drawText("Time:" + gameTime, r.getCamX() + 200, r.getCamY(), 0xffffffff);
        r.drawText("Frame Counter:" + frameCounter, r.getCamX() + 100, r.getCamY(), 0xffffffff);

    }

    public GameObject getObject(String tag) {
        for (int i = 0; i < objects.size(); i++) {
            if (objects.get(i).getTag().equals(tag)) {
                return objects.get(i);
            }
        }
        return null;
    }

    public TeleportPipe getTeleportPipe(GameObject object) {
        for (TeleportPipe tp : teleportPipes) {
            if (tp == object)
                return tp;
        }

        return null;
    }

    public boolean getCollision(int x, int y) {
        return map.getCollision(x, y);
    }

    public void addObject(GameObject obj) {
        objects.add(obj);
    }

    public Map getMap() {
        return map;
    }

    public Camera getCamera() {
        return camera;
    }

    private void initialize_game() {
        objects.clear();
        frameCounter = 0;
        gameTime = 400;
        // gameState = MENU_STATE;
       
        camera = new Camera("player");
        menu = new Menu();
        death = new DeathScreen();
        leaderBoard = new LeaderBoard();
        mouseKlick = new SoundClip("/resources/audio/test.wav", -50);
        initialize_lvl2();

    }

    private void initialize_lvl1() {
        int goombaSpawnPosX[] = new int[] {
                22, 40, 51, 53, 80, 82, 97, 99, 114, 116, 124, 126, 128, 130, 174, 176
        };
        int goombaSpawnPosY[] = new int[] {
                12, 12, 12, 12, 4, 4, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12
        };

        for (int i = 0; i < goombaSpawnPosX.length; i++) {
            objects.add(new Goomba(goombaSpawnPosX[i], goombaSpawnPosY[i]));
        }        

        teleportPipes.add(new TeleportPipe(new Point(912, 128), 13, 10, KeyEvent.VK_S, new Point(214, 2), 212));
        teleportPipes.add(new TeleportPipe(new Point(3595, 192), 2, 2, KeyEvent.VK_D, new Point(163, 10), 100));
        for(TeleportPipe tp : teleportPipes){
            objects.add(tp);
        }

        objects.add(new Flag(198, 3));
        objects.add(new Koopa(107, 12, "green"));
        objects.add(new Player(3, 12));

        map = new Map("lvl1/colorWorld1_1(1).png", "lvl1/bakgrund1-1.png");

    }

    private void initialize_lvl2() {
        int goombaSpawnPosX[] = new int[] {
                33, 34, 46, 79, 81, 90, 93,95, 115, 117, 119, 130, 152, 154
        };
        int goombaSpawnPosY[] = new int[] {
                12, 11, 12, 12, 12, 4, 8, 8, 12, 12, 12, 12, 8, 8
        };

        int koopaSpawnPosX[] = new int[] {
             61, 63, 76
    };
    int koopaSpawnPosY[] = new int[] {
            12, 12, 12
    };

        for (int i = 0; i < goombaSpawnPosX.length; i++) {
            //objects.add(new Goomba(goombaSpawnPosX[i], goombaSpawnPosY[i]));
        }

        for (int i = 0; i < koopaSpawnPosX.length; i++) {
           // objects.add(new Koopa(koopaSpawnPosX[i], koopaSpawnPosY[i], "green"));
        }

        objects.add(new Koopa(163, 12, "red"));
        objects.add(new Flag(232, 3));
        objects.add(new Player(3, 12));

        map = new Map("lvl2/colorworld1-2.png", "lvl2/bakgrund1-2.png");
        teleportPipes.add(new TeleportPipe(new Point(161, 192)/*in pixels */, 2, 2, KeyEvent.VK_D, new Point(20, 4)/*in tiles*/, 0));
        teleportPipes.add(new TeleportPipe(new Point(2925, 144)/*in pixels */, 2, 2, KeyEvent.VK_D, new Point(213, 10)/*in tiles*/, 0));
        teleportPipes.add(new TeleportPipe(new Point(1921, 144)/*in pixels */, 13, 10, KeyEvent.VK_S, new Point(244, 4)/*in tiles*/, 0));
        teleportPipes.add(new TeleportPipe(new Point(4095, 192)/*in pixels */, 2, 2, KeyEvent.VK_D, new Point(132, 10)/*in tiles*/, 0));


        for(TeleportPipe tp : teleportPipes){
            objects.add(tp);
        }

        
    }

}
