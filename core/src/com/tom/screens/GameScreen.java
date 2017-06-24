package com.tom.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.tom.gameworld.GameRenderer;
import com.tom.gameworld.GameWorld;
import com.tom.trHelpers.InputHandler;

import java.util.Stack;

import static com.tom.gameobjects.ScrollHandler.GAME_HEIGHT;
import static com.tom.gameobjects.ScrollHandler.GAME_WIDTH;

/**
 * Created by Nhat Quang on 6/17/2017.
 */

public class GameScreen implements Screen {
    private GameWorld world; //to update
    private GameRenderer renderer; //to render
    private float runTime = 0; //for animation

    public static Stack<GameState> STATE_STACK = new Stack<GameState>();
    public enum GameState { READY, GAME, GAMEOVER, HIGHSCORE, MENU }


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

        STATE_STACK.push(GameState.MENU);
    }

    @Override
    public void show() {

    }

    @Override
    //Basically our GameLoop
    public void render(float delta) {
        switch (STATE_STACK.peek()) {

            case MENU: {
                renderer.render(delta);
                break;
            }

            case READY:
                break;

            case GAME: {
                world.update(delta);
                renderer.render(delta);
                break;
            }

            case GAMEOVER:
                break;

            case HIGHSCORE: {
                renderer.render(delta);
                break;
            }

            default: break;
        }
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

    public void onClick(float screenX, float screenY) {
        if (STATE_STACK.peek() == GameState.MENU) {
            if (screenX > GAME_WIDTH / 2 - 200 && screenX < GAME_WIDTH / 2 + 200 && screenY > GAME_HEIGHT / 6 && screenY < GAME_HEIGHT / 6 + 200) {
                STATE_STACK.push(GameState.GAME);
            }
        }
    }
}
