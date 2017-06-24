package com.tom.gameworld;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.tom.gameobjects.Goal;
import com.tom.gameobjects.Grid;
import com.tom.gameobjects.Player;
import com.tom.gameobjects.ScrollHandler;
import com.tom.screens.GameScreen;
import com.tom.trHelpers.AssetLoader;


import static com.tom.gameobjects.ScrollHandler.GAME_HEIGHT;
import static com.tom.gameobjects.ScrollHandler.GAME_WIDTH;
import static com.tom.screens.GameScreen.STATE_STACK;

public class GameRenderer {
    private GameWorld myWorld;
    private OrthographicCamera cam; //make 2D game in 3D dimension
    private ShapeRenderer shapeRenderer; //draw shapes and lines
    private SpriteBatch batcher; //draw textures and texture regions

    private int midPointX;
    private int gameWidth; //need game width as it varies on different devices
    //game objects
    private Player player;
    private ScrollHandler scroller;
    public Grid grid, grid2;
    private Goal goal;

    //textures
    private TextureRegion background;
    private TextureRegion playerTexture;
    private TextureRegion scroll;


    //constructor, as the renderer needs a world to render(draw)
    public GameRenderer(GameWorld world, int gameWidth, int midPointX) {
        myWorld = world;
        this.goal = world.goal;
        this.gameWidth = gameWidth;
        this.midPointX = midPointX;

        cam = new OrthographicCamera();
        cam.setToOrtho(true, gameWidth, 1080); //true if you want y down
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
        playerTexture = AssetLoader.player;
        scroll = AssetLoader.scroll;
    }

    private void drawBG() {
        // Draw the background
        batcher.draw(background, grid.getX(), grid.getY(),
                grid.getWidth(), grid.getHeight());
        batcher.draw(background, grid2.getX(), grid2.getY(),
                grid2.getWidth(), grid2.getHeight());
    }

    private void drawGround() { //temporary
        batcher.draw(AssetLoader.floor, 0, GAME_HEIGHT-20, GAME_WIDTH, 20);
        batcher.draw(AssetLoader.floor, GAME_WIDTH/2, GAME_HEIGHT-20, GAME_WIDTH, 20);
        batcher.draw(AssetLoader.floor, GAME_WIDTH-250, GAME_HEIGHT-20, GAME_WIDTH, 20);
    }

    public void render(float runTime) {
        //draw background
        if (STATE_STACK.peek() == GameScreen.GameState.MENU) {
            mainmenu();
        }
        else if (STATE_STACK.peek() == GameScreen.GameState.GAME) {
            running(runTime);
        }
        else if (STATE_STACK.peek() == GameScreen.GameState.HIGHSCORE) {
            highscore();
            System.out.println("Rendering highscore");
        }
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

        batcher.begin();

        //background
        drawBG();

        //bombs
        if (grid.bomb != null) {
            for (int i = 0; i < grid.bomb.size(); i++) {
                batcher.draw(AssetLoader.bomb, grid.bomb.get(i).getX(), grid.bomb.get(i).getY());
            }
        }
        if (grid2.bomb != null) {
            for (int i = 0; i < grid2.bomb.size(); i++) {
                batcher.draw(AssetLoader.bomb, grid2.bomb.get(i).getX(), grid2.bomb.get(i).getY());
            }
        }

        //gems
        if (grid.gem != null) {
            for (int i = 0; i < grid.gem.length; i++) {
                if (grid.gem[i].isScored == true) continue;
                if (grid.gem[i].color == 1)
                    batcher.draw(AssetLoader.blue_gem, grid.gem[i].getX(), grid.gem[i].getY());
                if (grid.gem[i].color == 2)
                    batcher.draw(AssetLoader.red_gem, grid.gem[i].getX(), grid.gem[i].getY());
                if (grid.gem[i].color == 3)
                    batcher.draw(AssetLoader.green_gem, grid.gem[i].getX(), grid.gem[i].getY());
                //System.out.println("Gem Drawn");
            }
        }

        if (grid2.gem != null) {
            for (int i = 0; i < grid2.gem.length; i++) {
                if (grid2.gem[i].isScored == true) continue;
                if (grid2.gem[i].color == 1)
                    batcher.draw(AssetLoader.blue_gem, grid2.gem[i].getX(), grid2.gem[i].getY());
                if (grid2.gem[i].color == 2)
                    batcher.draw(AssetLoader.red_gem, grid2.gem[i].getX(), grid2.gem[i].getY());
                if (grid2.gem[i].color == 3)
                    batcher.draw(AssetLoader.green_gem, grid2.gem[i].getX(), grid2.gem[i].getY());
                //System.out.println("Gem Drawn");
            }
        }

        drawGround();

        //draw scroll
        batcher.draw(scroll, 0, 0, 300, 1080);



        AssetLoader.shadow.draw(batcher, "   " + goal.red_gem_goal, 95, 200);
        AssetLoader.font.draw(batcher, "   " + goal.red_gem_goal, 100, 200);
        batcher.draw(AssetLoader.red_gem, 53,170, 100, 100);
        AssetLoader.shadow.draw(batcher, "   " + goal.green_gem_goal, 95, 350);
        AssetLoader.font.draw(batcher, "   " + goal.green_gem_goal, 100, 350);
        batcher.draw(AssetLoader.green_gem, 53,320, 100, 100);
        AssetLoader.shadow.draw(batcher, "   " + goal.blue_gem_goal, 95, 500);
        AssetLoader.font.draw(batcher, "   " + goal.blue_gem_goal, 100, 500);
        batcher.draw(AssetLoader.blue_gem, 53,470, 100, 100);

        AssetLoader.shadow.draw(batcher, "  " + (int) goal.remaining_time, 100, 650);
        AssetLoader.font.draw(batcher, "  " + (int) goal.remaining_time, 105, 650);
        batcher.draw(AssetLoader.clock, 23,600, 150, 150);


        //draw player
        batcher.draw(playerTexture, player.getX(), player.getY(), player.getWidth(), player.getHeight());

        //draw high score
        if (myWorld.isReady()) {
            //Our main menu
            // Draw shadow first
            AssetLoader.shadow.draw(batcher, "Touch screen to play", (gameWidth / 2)
                    - (380), 1080/2);
            // Draw text
            AssetLoader.font.draw(batcher, "Touch screen to play", (gameWidth / 2)
                    - (380 - 10), (1080/2)-1);
        } else {


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

                //SCORE WHEN DEAD
                // Draw shadow first
                AssetLoader.bigShadow.draw(batcher, score,
                        (midPointX -880) - (3 * score.length())-5, 801);
                // Draw text
                AssetLoader.bigFont.draw(batcher, score,
                        (midPointX -880) - (3 * score.length()), 800);

            }

            // Convert integer into String
            String score = myWorld.getScore() + "";

            //SCORE WHEN ALIVE
            // Draw shadow first
            AssetLoader.bigShadow.draw(batcher, "" + myWorld.getScore(), (midPointX -880) - (3 * score.length()) -5, 801);
            // Draw text
            AssetLoader.bigFont.draw(batcher, "" + myWorld.getScore(), (midPointX -880) - (3 * score.length()), 800);
        }

        batcher.end();
    }

    public void mainmenu() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batcher.begin();

        batcher.draw(background, 0, 0, GAME_WIDTH, GAME_HEIGHT);
        batcher.draw(AssetLoader.button, 200, (float) (GAME_HEIGHT * 0.6));
        batcher.draw(AssetLoader.button, GAME_WIDTH - 702, (float) (GAME_HEIGHT * 0.6));

        AssetLoader.font.draw(batcher, "Play", 380, (float) (GAME_HEIGHT * 0.6 + 80));
        AssetLoader.font.draw(batcher, "Highscore", GAME_WIDTH - 702 + 80, (float) (GAME_HEIGHT * 0.6 + 80));

        batcher.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.end();
    }
}
