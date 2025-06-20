
package myGame;

import gameEngine.Window;
import myGame.gameObjects.GameObject;
import myGame.gameObjects.Player;
import myGame.gameObjects.Map;
import myGame.gameObjects.MovingPlatform;
import myGame.gameObjects.PiranhaPlant;
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
import java.util.HashMap;

import myGame.gameObjects.Flag;
import myGame.gameObjects.Goomba;
import myGame.gameObjects.Koopa;
import myGame.gameObjects.TeleportPipe;

import javax.swing.*;

import java.awt.image.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
    private Player player;
    private int startMarioState = 0;
    public float gameTime;
    public static int frameCounter = 0;

    private int amountOfLevels = 3;
    private int currentLevel = 1;

    private ArrayList<TeleportPipe> teleportPipes = new ArrayList<>();
    private ArrayList<MovingPlatform> movingPlatforms = new ArrayList<>();

    private HashMap<Integer, Runnable> initializeLevel = new HashMap<>();

    public GameManager() {
       /*  initializeLevel.put(1, new Runnable(){
            public void run(){ initialize_lvl1();}
        });

        initializeLevel.put(2, this::initialize_lvl2);*/

        for (int i = 1; i <= amountOfLevels; i++) {
            int level = i;
            initializeLevel.put(level, () -> initializeLevelMethod(level));
        }
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
                    startMarioState = player.getMarioState();
                    leaderBoard.addTime(gameTime);
                }

                switch (currentLevel) {
                    case 1:
                        
                        break;
                    case 2:
                        if(getObject("player").getTileX() == 201 && getObject("player").getTileY()<4){
                            System.out.println("FEKKJKJS");
                            camera.setMoveCamera(false);
                        }else if(getObject("player").getTileX() == 235){
                            camera.setMoveCamera(false);
                        }
                        break;
                    default:
                        
                        break;
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
                gameTime-=1;

                switch (currentLevel) {
                    case 1:
                
                    
                        break;
                    case 2:
                        
                        if(getObject("player").getTileX() == 233){
                            camera.setMoveCamera(false);
                        }
                        break;
                    default:
                        break;
                }
                if(gameTime<= 0){
                    gameState = PLAY_STATE;
                    currentLevel++;
                    currentLevel = currentLevel>amountOfLevels ? 1 : currentLevel;
                    initialize_game();
                }

                break;

            case FAIL_STATE:
                startMarioState = 0;

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

    public Player getPlayer(){
        return player;
    }

    public TeleportPipe getTeleportPipe(GameObject object) {
        for (TeleportPipe tp : teleportPipes) {
            if (tp == object)
                return tp;
        }

        return null;
    }

    public MovingPlatform getMovingPlatform(GameObject object) {
        for (MovingPlatform mp : movingPlatforms) {
            if (mp == object)
                return mp;
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

    public int getCurrentLevel() {
        return currentLevel;
    }

    private void initialize_game() {
        objects.clear();
        teleportPipes.clear();
        movingPlatforms.clear();
        frameCounter = 0;
        gameTime = 400;
        // gameState = MENU_STATE;
       
        camera = new Camera("player");
        menu = new Menu();
        death = new DeathScreen();
        leaderBoard = new LeaderBoard();
        mouseKlick = new SoundClip("/resources/audio/test.wav", -50);
        initializeLevel.get(currentLevel).run();
       // initialize_lvl1();

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
        player = new Player(3, 12, startMarioState);
        objects.add(player);

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


        objects.add(new PiranhaPlant(1925, 133));
        objects.add(new PiranhaPlant(2021, 121));
        objects.add(new PiranhaPlant(2118, 153));

        /*objects.add(new PiranhaPlant(120, 8));
        objects.add(new PiranhaPlant(124,   10));
        objects.add(new PiranhaPlant(132, 8));*/
        for (int i = 0; i < goombaSpawnPosX.length; i++) {
            objects.add(new Goomba(goombaSpawnPosX[i], goombaSpawnPosY[i]));
        }

        for (int i = 0; i < koopaSpawnPosX.length; i++) {
           objects.add(new Koopa(koopaSpawnPosX[i], koopaSpawnPosY[i], "green"));
        }

        objects.add(new Koopa(163, 12, "red"));
        objects.add(new Flag(232, 3));
        player = new Player(3, 12, startMarioState);
        objects.add(player);//3

        map = new Map("lvl2/colorworld1-2.png", "lvl2/bakgrund1-2.png");
        teleportPipes.add(new TeleportPipe(new Point(161, 192)/*in pixels */, 2, 2, KeyEvent.VK_D, new Point(20, 4)/*in tiles*/, 17/*in tiles */));
        teleportPipes.add(new TeleportPipe(new Point(2925, 144)/*in pixels */, 2, 2, KeyEvent.VK_D, new Point(213, 10)/*in tiles*/, 210));
        teleportPipes.add(new TeleportPipe(new Point(1921, 144)/*in pixels */, 13, 10, KeyEvent.VK_S, new Point(244, 4)/*in tiles*/, 244));
        teleportPipes.add(new TeleportPipe(new Point(4095, 192)/*in pixels */, 2, 2, KeyEvent.VK_D, new Point(132, 10)/*in tiles*/, 0));
        for(TeleportPipe tp : teleportPipes){
            objects.add(tp);
        }

        movingPlatforms.add(new MovingPlatform(2512, 4, "DOWN", -100, 2000));
        movingPlatforms.add(new MovingPlatform(2512, 100, "DOWN", -100, 2000));
        movingPlatforms.add(new MovingPlatform(2752, 200, "UP", -100, 2000));
        movingPlatforms.add(new MovingPlatform(2752, 104, "UP", -100, 2000));
       // movingPlatforms.add(new MovingPlatform(50, 100, "DOWN"));
        
        for(MovingPlatform mp : movingPlatforms){
            objects.add(mp);
        }
        camera.setMoveCamera(false);

        
    }

    private void initialize_lvl3() {
        int goombaSpawnPosX[] = new int[] {
            45, 47, 81
         };
        int goombaSpawnPosY[] = new int[] {
              4, 4, 5
        };

        int redKoopaSpawnPosX[] = new int[] {
            31
        };
        int koopaSpawnPosY[] = new int[] {
                12, 12, 12
        };
        for (int i = 0; i < goombaSpawnPosX.length; i++) {
            objects.add(new Goomba(goombaSpawnPosX[i], goombaSpawnPosY[i]));
        }

        objects.add(new Flag(152, 3));
        player = new Player(3, 12, startMarioState);
        objects.add(player);//3

        map = new Map("lvl3/colorworld1-3.png", "lvl3/background1-3.png");

        movingPlatforms.add(new MovingPlatform(880, 8*TS, "DOWN", 6*TS, 13*TS));
        movingPlatforms.add(new MovingPlatform(83*TS, 8*TS, "RIGHT", 87*TS, 82*TS));
        movingPlatforms.add(new MovingPlatform(89*TS, 9*TS, "LEFT", 93*TS, 87*TS));
        
        for(MovingPlatform mp : movingPlatforms){
            objects.add(mp);
        }
    }

    private void initializeLevelMethod(int level) {
        System.out.println("in itialexe");
        try {
            Method method = this.getClass().getDeclaredMethod("initialize_lvl" + level);
            method.invoke(this);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
