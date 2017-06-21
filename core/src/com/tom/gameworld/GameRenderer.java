package com.tom.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.tom.gameobjects.Bomb;
import com.tom.gameobjects.Grid;
import com.tom.gameobjects.Player;
import com.tom.gameobjects.ScrollHandler;
import com.tom.trHelpers.AssetLoader;

import java.util.Random;

import static com.tom.gameobjects.ScrollHandler.GAME_HEIGHT;
import static com.tom.gameobjects.ScrollHandler.GAME_WIDTH;

/**
 * Created by Nhat Quang on 6/17/2017.
 */

public class GameRenderer {
    private GameWorld myWorld;
    private OrthographicCamera cam; //make 2D game in 3D dimension
    private ShapeRenderer shapeRenderer; //draw shapes and lines
    private SpriteBatch batcher; //draw textures and texture regions

    private int midPointX;
    private int gameWidth; //need game width as it varies on different devices
    //game objects
    private Player player;
    private Bomb bomb1, bomb2, bomb3;
    private ScrollHandler scroller;
    public Grid grid, grid2;
    private Random r;

    //constructor, as the renderer needs a world to render(draw)
    public GameRenderer(GameWorld world, int gameWidth, int midPointX) {
        myWorld = world;
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
    }

    private void initGameObjects() {
        player = myWorld.getPlayer();
        scroller = myWorld.getScroller();
        grid = scroller.getGrid();
    }

    public void render(float runTime) {
        //draw background
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        grid = scroller.getGrid();
        grid2 = scroller.getGrid2();

        batcher.begin();

        //background
        batcher.draw(AssetLoader.background, grid.background.getX(), grid.background.getY(), GAME_WIDTH, GAME_HEIGHT);
        batcher.draw(AssetLoader.background, grid2.background.getX(), grid.background.getY(), GAME_WIDTH, GAME_HEIGHT);

        //bombs
        for (int i = 0; i < grid.bomb.size(); i++) {
            batcher.draw(AssetLoader.bomb, grid.bomb.get(i).getX(), grid.bomb.get(i).getY());
        }

        for (int i = 0; i < grid2.bomb.size(); i++) {
            batcher.draw(AssetLoader.bomb, grid2.bomb.get(i).getX(), grid2.bomb.get(i).getY());
        }

        //gems
        for (int i = 0; i < grid.gem.length; i++) {
            if (grid.gem[i].isScored == true) continue;
            if (grid.gem[i].color == 1) batcher.draw(AssetLoader.blue_gem, grid.gem[i].getX(), grid.gem[i].getY());
            if (grid.gem[i].color == 2) batcher.draw(AssetLoader.red_gem, grid.gem[i].getX(), grid.gem[i].getY());
            if (grid.gem[i].color == 3) batcher.draw(AssetLoader.green_gem, grid.gem[i].getX(), grid.gem[i].getY());
            System.out.println("Gem Drawn");
        }

        for (int i = 0; i < grid2.gem.length; i++) {
            if (grid2.gem[i].isScored == true) continue;
            if (grid2.gem[i].color == 1) batcher.draw(AssetLoader.blue_gem, grid2.gem[i].getX(), grid2.gem[i].getY());
            if (grid2.gem[i].color == 2) batcher.draw(AssetLoader.red_gem, grid2.gem[i].getX(), grid2.gem[i].getY());
            if (grid2.gem[i].color == 3) batcher.draw(AssetLoader.green_gem, grid2.gem[i].getX(), grid2.gem[i].getY());
            System.out.println("Gem Drawn");
        }

        batcher.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        //draw player in brown
        shapeRenderer.setColor(111 / 255.0f, 186 / 255.0f, 45 / 255.0f, 1);
        shapeRenderer.rect(player.getX(), player.getY(), player.getWidth(), player.getHeight());


        shapeRenderer.end();
    }


}
