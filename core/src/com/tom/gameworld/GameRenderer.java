package com.tom.gameworld;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tom.gameobjects.Background;
import com.tom.gameobjects.Goal;
import com.tom.gameobjects.Grid;
import com.tom.gameobjects.Player;
import com.tom.gameobjects.ScrollHandler;
import com.tom.screens.GameScreen;
import com.tom.trHelpers.AssetLoader;


import java.util.Random;

import static com.tom.gameobjects.Powerup.POPUP_TIME;
import static com.tom.gameobjects.Powerup.POWERUP_TIME;
import static com.tom.gameobjects.Powerup.TYPE;
import static com.tom.gameobjects.ScrollHandler.GAME_HEIGHT;
import static com.tom.gameobjects.ScrollHandler.GAME_WIDTH;
import static com.tom.gameobjects.ScrollHandler.SCROLL_SPEED;
import static com.tom.screens.GameScreen.STATE_STACK;

public class GameRenderer {
    private GameWorld myWorld;
    private OrthographicCamera cam; //make 2D game in 3D dimension
    private ShapeRenderer shapeRenderer; //draw shapes and lines
    private SpriteBatch batcher; //draw textures and texture regions
    public static boolean isPlayingExhaust = false;
    private int midPointX;
    private int gameWidth; //need game width as it varies on different devices
    //game objects
    private Player player;
    private ScrollHandler scroller;
    public Grid grid, grid2;
    private Goal goal;
    private Background bg1, bg2;

    //textures
    private TextureRegion background, backgroundver2, backgroundver3;
    private TextureRegion playerTexture;
    private TextureRegion scroll;

    //random
    private Random r = new Random();


    //constructor, as the renderer needs a world to render(draw)
    public GameRenderer(GameWorld world, int gameWidth, int midPointX) {
        myWorld = world;
        this.goal = world.goal;
        this.gameWidth = gameWidth;
        this.midPointX = midPointX;

        cam = new OrthographicCamera();
        cam.setToOrtho(true, 1920, 1080); //true if you want y down
        //everything in game world is scaled up 2x when drawn

        batcher = new SpriteBatch();
        // Attach batcher to camera
        batcher.setProjectionMatrix(cam.combined);

        shapeRenderer = new ShapeRenderer();
        //attach shapeRenderer to our camera
        shapeRenderer.setProjectionMatrix(cam.combined);
        initGameObjects();
        initAssets();
    }

    private void initGameObjects() {
        player = myWorld.getPlayer();
        scroller = myWorld.getScroller();
        grid = scroller.getGrid();
        grid2 = scroller.getGrid2();
    }

    private void initAssets() {

        background = AssetLoader.background;
        backgroundver2 = AssetLoader.backgroundver2;
        backgroundver3 = AssetLoader.backgroundver3;
        playerTexture = AssetLoader.player;
        scroll = AssetLoader.scroll;

    }

    private void drawBG() {
        // Draw the background
        int count = myWorld.getBGCount();
        if (count == 0) {
            batcher.draw(background, bg1.background.getX(), bg1.background.getY(),
                    bg1.background.getWidth(), bg1.background.getHeight());
            batcher.draw(background, bg2.background.getX(), bg2.background.getY(),
                    bg2.background.getWidth(), bg2.background.getHeight());
        }
        if (count == 1) {
            batcher.draw(backgroundver2, bg1.background.getX(), bg1.background.getY(),
                    bg1.background.getWidth(), bg1.background.getHeight());
            batcher.draw(backgroundver2, bg2.background.getX(), bg2.background.getY(),
                    bg2.background.getWidth(), bg2.background.getHeight());
        }
        if (count == 2) {
            batcher.draw(backgroundver3, bg1.background.getX(), bg1.background.getY(),
                    bg1.background.getWidth(), bg1.background.getHeight());
            batcher.draw(backgroundver3, bg2.background.getX(), bg2.background.getY(),
                    bg2.background.getWidth(), bg2.background.getHeight());
        }
    }

    private void drawGround() { //temporary
        if (grid.ground != null) batcher.draw(AssetLoader.floor, bg1.ground.getX(), bg1.ground.getY(), bg1.ground.getWidth(), bg1.ground.getHeight());
        batcher.draw(AssetLoader.floor, bg2.ground.getX(), bg2.ground.getY(), bg2.ground.getWidth(), bg2.ground.getHeight());
    }

    public void render(float runTime) {
        //draw background
        if (STATE_STACK.peek() == GameScreen.GameState.MENU) {
            mainmenu(runTime);
        }
        else if (STATE_STACK.peek() == GameScreen.GameState.GAME) {
            running(runTime);
        }
        else if (STATE_STACK.peek() == GameScreen.GameState.HIGHSCORE) {
            highscore();
        }
        else if (STATE_STACK.peek() == GameScreen.GameState.SHOP) {
            shop(runTime);
        }
        else if (STATE_STACK.peek() == GameScreen.GameState.SHOP_PURCHASING) {
            shop_purchasing(runTime);
        }
        else if (STATE_STACK.peek() == GameScreen.GameState.NOMONEY) {
            nomoney(runTime);
        }
    }

    private void nomoney(float runTime) {
        shop(runTime);

        batcher.begin();

        batcher.draw(AssetLoader.nomoney, 1920 / 2 - 1200 / 2, 1080 / 2 - 750 / 2, 1200, 750);

        batcher.end();
    }

    private void shop(float runTime) {
        batcher.begin();

        batcher.draw(AssetLoader.shop, 0, 0);

        Preferences prefs = Gdx.app.getPreferences("PREFERENCES");
        AssetLoader.parisine_36_white.draw(batcher, ""+prefs.getInteger("coin"), 1552,61);

        //draw helicopters
        batcher.draw(AssetLoader.playerAnimation.getKeyFrame(runTime), 699, 261, 150, 150);
        AssetLoader.parisine_36_white.draw(batcher, "Flyer", 699+170, 261+10);
        batcher.draw(AssetLoader.player1Animation.getKeyFrame(runTime), 1154, 261, 150, 150);
        AssetLoader.parisine_36_white.draw(batcher, "Ninja", 1154+170, 261+10);
        batcher.draw(AssetLoader.player2Animation.getKeyFrame(runTime), 1586, 261, 150, 150);
        AssetLoader.parisine_36_white.draw(batcher, "Alien", 1586+170, 261+10);

        if (prefs.getBoolean("plane1_purchased") == false) {
            batcher.draw(AssetLoader.coin, 1139, 431, 33, 33);
            AssetLoader.parisine_36_white.draw(batcher, "5000", 1182, 431);
        }

        if (prefs.getBoolean("plane2_purchased") == false) {
            batcher.draw(AssetLoader.coin, 1571, 431, 33, 33);
            AssetLoader.parisine_36_white.draw(batcher, "5000", 1614, 431);
        }

        switch (prefs.getInteger("plane")) {
            case 0: {
                batcher.draw(AssetLoader.green_tick, 699 + 150 / 2 - 33 / 2, 431, 33, 33);
                break;
            }
            case 1: {
                batcher.draw(AssetLoader.green_tick, 1154 + 150 / 2 - 33 / 2, 431, 33, 33);
                break;
            }
            case 2: {
                batcher.draw(AssetLoader.green_tick, 1586 + 150 / 2 - 33 / 2, 431, 33, 33);
                break;
            }
        }

        //draw flags
        batcher.draw(AssetLoader.flag1Animation.getKeyFrame(runTime), 699 - 25, 527 + 25, 200, 100);
        batcher.draw(AssetLoader.flag2Animation.getKeyFrame(runTime), 1154 - 25, 527+25, 200, 100);
        batcher.draw(AssetLoader.flag3Animation.getKeyFrame(runTime), 1586 - 25, 527+25, 200, 100);

        if (prefs.getBoolean("flag1_purchased") == false) {
            batcher.draw(AssetLoader.coin, 699-25, 527+145, 33, 33);
            AssetLoader.parisine_36_white.draw(batcher, "5000", 699+28, 527+145);
        }

        if (prefs.getBoolean("flag2_purchased") == false) {
            batcher.draw(AssetLoader.coin, 1139, 527+145, 33, 33);
            AssetLoader.parisine_36_white.draw(batcher, "5000", 1182, 527+145);
        }

        if (prefs.getBoolean("flag3_purchased") == false) {
            batcher.draw(AssetLoader.coin, 1571, 527+145, 33, 33);
            AssetLoader.parisine_36_white.draw(batcher, "5000", 1614, 527+145);
        }

        switch (prefs.getInteger("flag")) {
            case 1: {
                batcher.draw(AssetLoader.green_tick, 699 + 150 / 2 - 33 / 2, 527+145, 33, 33);
                break;
            }
            case 2: {
                batcher.draw(AssetLoader.green_tick, 1154 + 150 / 2 - 33 / 2, 527+145, 33, 33);
                break;
            }
            case 3: {
                batcher.draw(AssetLoader.green_tick, 1586 + 150 / 2 - 33 / 2, 527+145, 33, 33);
                break;
            }
        }



        //draw plus time
        batcher.draw(AssetLoader.plus_time, 838, 813, 150, 150);
        batcher.draw(AssetLoader.coin, 835, 983, 33, 33);
        AssetLoader.parisine_36_white.draw(batcher, "Increase Timer by 10s", 678, 683+60);
        AssetLoader.parisine_36_white.draw(batcher, "500", 878, 983);
        AssetLoader.parisine_36_white.draw(batcher, String.format("x %d", prefs.getInteger("plus_time")), 1025, 863);

        //draw slowdown
        batcher.draw(AssetLoader.slow_down, 1395 +100, 813, 150, 150);
        batcher.draw(AssetLoader.coin, 1395 +100, 983, 33, 33);
        AssetLoader.parisine_36_white.draw(batcher, "Speed down", 1378+50, 683+60);
        AssetLoader.parisine_36_white.draw(batcher, "500", 1438+100, 983);
        AssetLoader.parisine_36_white.draw(batcher, String.format("x %d", prefs.getInteger("slow_down")), 1579+100, 863);


        batcher.end();
    }

    private void highscore() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batcher.begin();

        batcher.draw(background, 0, 0, GAME_WIDTH, GAME_HEIGHT);
        batcher.draw(AssetLoader.button, GAME_WIDTH/2-251, GAME_HEIGHT/2-100);
        int highscore = AssetLoader.getHighScore();

        if (highscore < 100) {
            AssetLoader.font.draw(batcher, "" + highscore, GAME_WIDTH/2 - 40 ,GAME_HEIGHT/2-20);
        }

        else if (highscore >= 100 && highscore < 1000) {
            //do this
        }

        batcher.end();
    }

    public void running(float runTime) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        grid = scroller.getGrid();
        grid2 = scroller.getGrid2();
        bg1 = scroller.bg1;
        bg2 = scroller.bg2;

        batcher.begin();

        //background
        drawBG();

        //bombs
        if (grid.bomb != null) {
            for (int i = 0; i < grid.bomb.size(); i++) {
                batcher.draw(AssetLoader.bombAnimation.getKeyFrame(0), grid.bomb.get(i).getX(), grid.bomb.get(i).getY());
            }
        }
        if (grid2.bomb != null) {
            for (int i = 0; i < grid2.bomb.size(); i++) {
                batcher.draw(AssetLoader.bombAnimation.getKeyFrame(0), grid2.bomb.get(i).getX(), grid2.bomb.get(i).getY());
            }
        }

        //gems
        if (grid.gem != null) {
            for (int i = 0; i < grid.gem.size(); i++) {
                if (grid.gem.get(i).isScored) {
                    if (grid.gem.get(i).isPlaying()) {
                        batcher.draw(AssetLoader.exhaustAnimation.getKeyFrame(runTime), grid.gem.get(i).getX(), grid.gem.get(i).getY());
                        if (AssetLoader.exhaustAnimation.getKeyFrameIndex(runTime)==3) grid.gem.get(i).stopPlaying();
                    }
                    continue;
                }
                if (grid.gem.get(i).color == 1)
                    batcher.draw(AssetLoader.blue_gem_Animation.getKeyFrame(runTime), grid.gem.get(i).getX(), grid.gem.get(i).getY());
                if (grid.gem.get(i).color == 2)
                    batcher.draw(AssetLoader.red_gem_Animation.getKeyFrame(runTime), grid.gem.get(i).getX(), grid.gem.get(i).getY());
                if (grid.gem.get(i).color == 3)
                    batcher.draw(AssetLoader.green_gem_Animation.getKeyFrame(runTime), grid.gem.get(i).getX(), grid.gem.get(i).getY());
                //System.out.println("Gem Drawn");
            }
        }

        if (grid2.gem != null) {
            for (int i = 0; i < grid2.gem.size(); i++) {
                if (grid2.gem.get(i).isScored) {
                    if (grid2.gem.get(i).isPlaying()) {
                        batcher.draw(AssetLoader.exhaustAnimation.getKeyFrame(runTime), grid2.gem.get(i).getX(), grid2.gem.get(i).getY());
                        if (AssetLoader.exhaustAnimation.getKeyFrameIndex(runTime)==3) grid2.gem.get(i).stopPlaying();
                    }
                    continue;
                }
                if (grid2.gem.get(i).color == 1)
                    batcher.draw(AssetLoader.blue_gem_Animation.getKeyFrame(runTime), grid2.gem.get(i).getX(), grid2.gem.get(i).getY());
                if (grid2.gem.get(i).color == 2)
                    batcher.draw(AssetLoader.red_gem_Animation.getKeyFrame(runTime), grid2.gem.get(i).getX(), grid2.gem.get(i).getY());
                if (grid2.gem.get(i).color == 3)
                    batcher.draw(AssetLoader.green_gem_Animation.getKeyFrame(runTime), grid2.gem.get(i).getX(), grid2.gem.get(i).getY());
                //System.out.println("Gem Drawn");
            }
        }

        //powerup
        if (grid.power_up != null && grid.power_up.isScored == false) {
            //if (grid.power_up.isScored == true) continue;
            batcher.draw(AssetLoader.powerup_Animation.getKeyFrame(runTime), grid.power_up.getX(), grid.power_up.getY());
        }

        if (grid2.power_up != null && grid2.power_up.isScored == false) {
            //if (grid.power_up.isScored == true) continue;
            batcher.draw(AssetLoader.powerup_Animation.getKeyFrame(runTime), grid2.power_up.getX(), grid2.power_up.getY());
        }


        drawGround();

        //draw scroll

        /*
        AssetLoader.expressway.draw(batcher, "   " + goal.red_gem_goal, 120, 85);
        batcher.draw(AssetLoader.red_gem_Animation.getKeyFrame(0), 70, 65, 75, 75);

        AssetLoader.expressway.draw(batcher, "   " + goal.green_gem_goal, 120, 215);
        batcher.draw(AssetLoader.green_gem_Animation.getKeyFrame(0), 70, 195, 75, 75);

        AssetLoader.expressway.draw(batcher, "   " + goal.blue_gem_goal, 120, 345);
        batcher.draw(AssetLoader.blue_gem_Animation.getKeyFrame(0), 70, 325, 75, 75);

        AssetLoader.expressway.draw(batcher, "  " + (int) goal.remaining_time, 120, 475);
        batcher.draw(AssetLoader.clock, 70, 455, 75, 75);
        */

        AssetLoader.expressway_green.draw(batcher, String.format("%d", goal.green_gem_goal), player.getX() - 50, player.getY() - 40);
        AssetLoader.expressway_red.draw(batcher, String.format("   %d", goal.red_gem_goal), player.getX() - 50, player.getY() - 40);
        AssetLoader.expressway_blue.draw(batcher, String.format("      %d", goal.blue_gem_goal), player.getX() - 50, player.getY() - 40);
        batcher.draw(AssetLoader.clock, player.getX() + 45, player.getY() - 43, 35, 35);
        if (goal.remaining_time >= 5 ) AssetLoader.expressway_white.draw(batcher, String.format("            %.1f", goal.remaining_time), player.getX() - 50, player.getY() - 40);
        else AssetLoader.expressway_red.draw(batcher, String.format("            %.1f", goal.remaining_time), player.getX() - 50, player.getY() - 40);

        //render pop up for power up
        if (TYPE != -1) {
            switch (TYPE) {
                case 0: {
                    AssetLoader.expressway.draw(batcher, String.format("Ethereal %.2f", POPUP_TIME), player.getX() - 100, player.getY() - 100);

                    break;
                }
                case 1: {
                    AssetLoader.expressway.draw(batcher, "Slow down!", player.getX() - 100, player.getY() - 100);

                    break;
                }
                case 2: {
                    AssetLoader.expressway.draw(batcher, "+2 seconds", player.getX() - 100, player.getY() - 100);

                    break;
                }
                case 3: {
                    AssetLoader.expressway.draw(batcher, String.format("Reversed! %.2f", POPUP_TIME), player.getX() - 120, player.getY() - 100);
                    break;
                }
            }
        }

        //draw player and flags
        Preferences prefs = Gdx.app.getPreferences("PREFERENCES");
        switch (prefs.getInteger("flag")) {
            case 1: {
                batcher.draw(AssetLoader.flag1Animation.getKeyFrame(runTime), player.getX() - 185, player.getY());
                break;
            }
            case 2: {
                batcher.draw(AssetLoader.flag2Animation.getKeyFrame(runTime), player.getX() - 185, player.getY());
                break;
            }
            case 3: {
                batcher.draw(AssetLoader.flag3Animation.getKeyFrame(runTime), player.getX() - 185, player.getY());
                break;
            }
        }

        if (player.isAlive()) {
            switch (prefs.getInteger("plane")) {
                case 0: {
                    batcher.draw(AssetLoader.playerAnimation.getKeyFrame(runTime), player.getX(), player.getY(), player.getWidth(), player.getHeight());
                    break;
                }
                case 1: {
                    batcher.draw(AssetLoader.player1Animation.getKeyFrame(runTime), player.getX(), player.getY(), player.getWidth(), player.getHeight());
                    break;
                }
                case 2: {
                    batcher.draw(AssetLoader.player2Animation.getKeyFrame(runTime), player.getX(), player.getY(), player.getWidth(), player.getHeight());
                    break;
                }
            }
        }
        else {

            switch (prefs.getInteger("plane")) {
                case 0: {
                    if (SCROLL_SPEED != 0) AssetLoader.player_dead.rotate(20);
                    AssetLoader.player_dead.setPosition(player.getX(), player.getY());
                    AssetLoader.player_dead.draw(batcher);
                    break;
                }
                case 1: {
                    if (SCROLL_SPEED != 0) AssetLoader.player1_dead.rotate(20);
                    AssetLoader.player1_dead.setPosition(player.getX(), player.getY());
                    AssetLoader.player1_dead.draw(batcher);
                    break;
                }
                case 2: {
                    if (SCROLL_SPEED != 0) AssetLoader.player2_dead.rotate(20);
                    AssetLoader.player2_dead.setPosition(player.getX(), player.getY());
                    AssetLoader.player2_dead.draw(batcher);
                    break;
                }
            }
            //if (SCROLL_SPEED != 0) player.death_animation_counter++;
            //if (player.death_animation_counter >= 36) player.death_animation_counter -= 36;
        }

        //draw high score
        if (myWorld.isReady()) {
            //Our main menu
            // Draw shadow first
            batcher.draw(AssetLoader.intro, GAME_WIDTH / 2 - 1700 / 2, GAME_HEIGHT / 2 - 400);
        } else {
            if (myWorld.isGameOver() || myWorld.isHighScore()) {
                batcher.draw(AssetLoader.gameover, GAME_WIDTH / 2 - 600, GAME_HEIGHT / 2 - 750 / 2);


                    String highscore = AssetLoader.getHighScore() + "";
                    String score = myWorld.getScore() + "";
                    AssetLoader.parisine_36_brown.draw(batcher, score+"", 1097, 399);
                    AssetLoader.parisine_36_brown.draw(batcher, highscore+"", 1023, 490);

            }
            /*
            if (myWorld.isGameOver() || myWorld.isHighScore()) {

                if (myWorld.isGameOver()) {
                    AssetLoader.shadow.draw(batcher, "Game Over", (gameWidth/2 - 200)-5, 101);
                    AssetLoader.font.draw(batcher, "Game Over", gameWidth/2 - 200, 100);

                    AssetLoader.shadow.draw(batcher, "High Score:", (gameWidth/2 - 200)-5, 301);
                    AssetLoader.font.draw(batcher, "High Score:",gameWidth/2 - 200, 300);

                    String highScore = AssetLoader.getHighScore() + "";

                    // Draw shadow first
                    AssetLoader.shadow.draw(batcher, highScore, (gameWidth / 2)
                            - (3 * highScore.length()) -50, 401);
                    // Draw text
                    AssetLoader.font.draw(batcher, highScore, (gameWidth / 2)
                            - (3 * highScore.length() - 5) -50, 400);
                } else {
                    AssetLoader.shadow.draw(batcher, "High Score!", (gameWidth/2 - 200)-5, 101);
                    AssetLoader.font.draw(batcher, "High Score!", gameWidth/2 - 200, 100);
                }

                AssetLoader.shadow.draw(batcher, "Try again?",(gameWidth/2 - 200)-5, 201);
                AssetLoader.font.draw(batcher, "Try again?", gameWidth/2 - 200, 200);

                // Convert integer into String
                String score = myWorld.getScore() + "";

            }

            */

            // Convert integer into String
            String score = myWorld.getScore() + "";

            //SCORE WHEN ALIVE

            if (player.isAlive()) {
                AssetLoader.big_expressway.draw(batcher, "" + myWorld.getScore(), (GAME_WIDTH / 2 - 830) - (25 * score.length()) -5, 130);
                AssetLoader.expressway.draw(batcher, "Score", 50, 50);
                //draw consummables
                batcher.draw(AssetLoader.plus_time, 0, 341+350, 125, 125);
                batcher.draw(AssetLoader.slow_down, 0, 341 + 150 + 30 +350, 125, 125);
                AssetLoader.parisine_36_white.draw(batcher, String.format("x %d", prefs.getInteger("plus_time")), 150, 385+350);
                AssetLoader.parisine_36_white.draw(batcher, String.format("x %d", prefs.getInteger("slow_down")), 150, 565+350);
            }
            // Draw text

        }

        batcher.end();
    }

    public void mainmenu(float runTime) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batcher.begin();

        batcher.draw(background, 0, 0, GAME_WIDTH, GAME_HEIGHT);

        batcher.draw(AssetLoader.main_menu, 0, 0, 1920, 1080);

        int highscore = AssetLoader.getHighScore();

        AssetLoader.expressway.draw(batcher, ""+highscore, 1479 - String.valueOf(highscore).length() * 15, 845);

        Preferences prefs = Gdx.app.getPreferences("PREFERENCES");

        switch (prefs.getInteger("plane")) {
            case 0: {
                batcher.draw(AssetLoader.playerAnimation.getKeyFrame(runTime), 857, 509, 250, 250);
                break;
            }
            case 1: {
                batcher.draw(AssetLoader.player1Animation.getKeyFrame(runTime), 857, 509, 250, 250);
                break;
            }
            case 2: {
                batcher.draw(AssetLoader.player2Animation.getKeyFrame(runTime), 857, 509, 250, 250);
                break;
            }
        }

        if (prefs.getBoolean("playMusic"))
            batcher.draw(AssetLoader.soundON,50,950,100,100);
        else
            batcher.draw(AssetLoader.soundOFF,50,950,100,100);

        batcher.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.end();
    }

    public void renderAbout(float runTime) {
        batcher.begin();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batcher.draw(background, 0, 0, GAME_WIDTH, GAME_HEIGHT);

        batcher.draw(AssetLoader.main_menu, 0, 0, 1920, 1080);

        batcher.draw(AssetLoader.about, 1920 / 2 - 1700 / 2, 1080 / 2 - 800 / 2, 1700, 800);

        batcher.end();
    }

    public void shop_purchasing(float runTime) {
        shop(runTime);

        batcher.begin();

        batcher.draw(AssetLoader.purchasing, 1920 / 2 - 1200 / 2, 1080 / 2 - 750 / 2, 1200, 750);

        batcher.end();
    }
}
