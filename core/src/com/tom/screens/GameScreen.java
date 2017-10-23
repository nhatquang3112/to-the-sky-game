package com.tom.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.tom.gameworld.GameRenderer;
import com.tom.gameworld.GameWorld;
import com.tom.trHelpers.AssetLoader;
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
    public enum GameState { READY, GAME, GAMEOVER, HIGHSCORE, MENU, ABOUT, SHOP, SHOP_PURCHASING, NOMONEY }
    String buying_item = null;


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
        runTime += delta;
        Preferences prefs = Gdx.app.getPreferences("PREFERENCES");
        switch (STATE_STACK.peek()) {

            case MENU: {
                renderer.render(runTime);
                if (prefs.getBoolean("playMusic")) {
                    AssetLoader.play_music.stop();
                    AssetLoader.menu_music.play();
                } else {
                    AssetLoader.play_music.stop();
                    AssetLoader.menu_music.pause();
                }

                break;
            }

            case READY:
                    AssetLoader.play_music.stop();
                    AssetLoader.menu_music.stop();

                break;

            case GAME: {
                if (prefs.getBoolean("playMusic")) {
                    if (world.getPlayer().isAlive()) AssetLoader.play_music.play();
                    else AssetLoader.play_music.stop();
                    AssetLoader.menu_music.stop();
                } else {
                    AssetLoader.play_music.stop();
                    AssetLoader.menu_music.pause();
                }
                world.update(delta);
                renderer.render(runTime);
                break;
            }

            case GAMEOVER:
                    AssetLoader.play_music.stop();
                    AssetLoader.menu_music.stop();
                break;

            case HIGHSCORE: {
                if (prefs.getBoolean("playMusic")) {
                    AssetLoader.play_music.stop();
                    AssetLoader.menu_music.play();
                } else {
                    AssetLoader.play_music.stop();
                    AssetLoader.menu_music.pause();
                }

                renderer.render(runTime);
                break;
            }

            case SHOP: {
                if (prefs.getBoolean("playMusic")) {
                    AssetLoader.play_music.stop();
                    AssetLoader.menu_music.play();
                } else {
                    AssetLoader.play_music.stop();
                    AssetLoader.menu_music.pause();
                }

                renderer.render(runTime);
                break;
            }
            case SHOP_PURCHASING: {
                if (prefs.getBoolean("playMusic")) {
                    AssetLoader.play_music.stop();
                    AssetLoader.menu_music.play();
                } else {
                    AssetLoader.play_music.stop();
                    AssetLoader.menu_music.pause();
                }

                renderer.render(runTime);
                break;

            }
            case ABOUT: {
                if (prefs.getBoolean("playMusic")) {
                    AssetLoader.play_music.stop();
                    AssetLoader.menu_music.play();
                } else {
                    AssetLoader.play_music.stop();
                    AssetLoader.menu_music.pause();
                }

                renderer.renderAbout(runTime);
                break;
            }

            case NOMONEY: {
                if (prefs.getBoolean("playMusic")) {
                    AssetLoader.play_music.stop();
                    AssetLoader.menu_music.play();
                } else {
                    AssetLoader.play_music.stop();
                    AssetLoader.menu_music.pause();
                }

                renderer.render(runTime);
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
}
