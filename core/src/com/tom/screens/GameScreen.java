package com.tom.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.tom.gameworld.GameRenderer;
import com.tom.gameworld.GameWorld;
import com.tom.trHelpers.InputHandler;

/**
 * Created by Nhat Quang on 6/17/2017.
 */

public class GameScreen implements Screen {
    private GameWorld world; //to update
    private GameRenderer renderer; //to render
    private float runTime = 0; //for animation


    public GameScreen() {
        Gdx.app.log("GameScreen", "Attached");
        float screenWidth = Gdx.graphics.getWidth(); //return pixel size of cam view, client area
        float screenHeight = Gdx.graphics.getHeight(); //return pixel size of cam view
        float gameHeight = 1080; //arbitrary unit
        float gameWidth = screenWidth / (screenHeight / gameHeight); //as width varies in different devices


        int midPointX = (int) (gameWidth / 2);
        world = new GameWorld(midPointX);
        renderer = new GameRenderer(world, (int) gameWidth, midPointX); //put world into renderer as reference to render

        //we are telling libGDX to take our new InputHandler as its processor
        Gdx.input.setInputProcessor(new InputHandler(world));

    }

    @Override
    public void show() {

    }

    @Override
    //Basically our GameLoop
    public void render(float delta) {
        world.update(delta);
        renderer.render(runTime);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
