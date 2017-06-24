package com.tom.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.tom.trHelpers.AssetLoader;

import static com.tom.gameobjects.ScrollHandler.GAME_HEIGHT;
import static com.tom.gameobjects.ScrollHandler.GAME_WIDTH;

/**
 * Created by NewUser on 6/24/2017.
 */

public class MenuRenderer {
    private OrthographicCamera cam; //make 2D game in 3D dimension
    private ShapeRenderer shapeRenderer; //draw shapes and lines
    private SpriteBatch batcher; //draw textures and texture regions

    //constructor, as the renderer needs a world to render(draw)
    public MenuRenderer() {
        cam = new OrthographicCamera();
        cam.setToOrtho(true, GAME_WIDTH, 1080); //true if you want y down
        //everything in game world is scaled up 2x when drawn

        batcher = new SpriteBatch();
        // Attach batcher to camera
        batcher.setProjectionMatrix(cam.combined);

        shapeRenderer = new ShapeRenderer();
        //attach shapeRenderer to our camera
        shapeRenderer.setProjectionMatrix(cam.combined);
    }

    public void render(float runTime) {
        //draw background
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batcher.begin();

        AssetLoader.consolas.draw(batcher, "Upper button: play", 0, 0);
        AssetLoader.consolas.draw(batcher, "Lower button: highscore", 0, 100);

        batcher.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(111 / 255.0f, 186 / 255.0f, 45 / 255.0f, 1);
        shapeRenderer.rect(GAME_WIDTH / 2 - 200, GAME_HEIGHT / 6, 400, 200);

        shapeRenderer.setColor(111 / 255.0f, 186 / 255.0f, 45 / 255.0f, 1);
        shapeRenderer.rect(GAME_WIDTH / 2 - 200, GAME_HEIGHT * 5 / 6 - 100, 400, 200);

        shapeRenderer.end();
    }
}

