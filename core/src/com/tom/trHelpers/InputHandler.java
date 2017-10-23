package com.tom.trHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;

import com.tom.gameobjects.Player;
import com.tom.gameworld.GameWorld;
import com.tom.screens.GameScreen;
import com.tom.screens.GameScreen.GameState;

import java.io.IOException;

import static com.tom.gameobjects.ScrollHandler.GAME_HEIGHT;
import static com.tom.gameobjects.ScrollHandler.GAME_WIDTH;
import static com.tom.screens.GameScreen.STATE_STACK;


/**
 * Created by Nhat Quang on 6/17/2017.
 */

public class InputHandler implements InputProcessor {
    public static float ROLL;
    private Player myPlayer;
    private GameWorld myWorld;
    private String purchasing_item = null;

    //constructor
    // Ask for a reference to the Bird when InputHandler is created.
    public InputHandler(GameWorld myWorld) {
        this.myWorld = myWorld;
        myPlayer = myWorld.getPlayer();
    }


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }


    @Override //what we use as input method
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenX =  (int) ((float) screenX / (float) Gdx.graphics.getWidth() * 1920f);
        screenY =  (int) ((float) screenY / (float) Gdx.graphics.getHeight() * 1080f);
        System.out.printf("%d %d", screenX, screenY);
        Preferences prefs = Gdx.app.getPreferences("PREFERENCES");
        switch (STATE_STACK.peek()) {
            case MENU: {
                //turn on/off sounds
                if (screenX > 50 && screenX < 150 && screenY > 950 && screenY < 1050 ) {
                    if (prefs.getBoolean("playMusic")) {
                        prefs.putBoolean("playMusic", false);
                        prefs.flush();
                    } else {
                        prefs.putBoolean("playMusic", true);
                        prefs.flush();
                    }
                    if (prefs.getBoolean("playMusic")) AssetLoader.on_click_sound.play(1.0f);
                }

                if (screenX > 176 && screenX < 461 && screenY > 452 && screenY < 638) {
                    STATE_STACK.push(GameState.SHOP);
                    if (prefs.getBoolean("playMusic")) AssetLoader.on_click_sound.play(1.0f);
                }

                if (screenX > 1449 && screenX < 1736 && screenY > 452 && screenY < 638){
                    STATE_STACK.push(GameState.GAME);
                    System.out.println("High score registered");
                    if (prefs.getBoolean("playMusic")) AssetLoader.on_click_sound.play(1.0f);
                }
                if (screenX > 1322 && screenX < 1605 && screenY > 716 && screenY < 900) {
                    //STATE_STACK.push(GameState.HIGHSCORE);
                }
                if (screenX > 305 && screenX < 585 && screenY > 716 && screenY < 900) {
                    STATE_STACK.push(GameState.ABOUT);
                    if (prefs.getBoolean("playMusic")) AssetLoader.on_click_sound.play(1.0f);
                }
                return true;
            }

            case GAME: {
                if (myWorld.isReady()) {
                    myWorld.start();
                    ROLL = Gdx.input.getRoll();
                }

                else if (myWorld.isGameOver() || myWorld.isHighScore()) {
                    // Reset all variables, go to GameState.READY and open up main menu
                    //Play Again
                    if (screenX > 474 && screenX < 770 && screenY > 657 && screenY < 855) {
                        myWorld.restart();
                        if (prefs.getBoolean("playMusic")) AssetLoader.on_click_sound.play(1.0f);
                    }

                    //Main Menu
                    if (screenX > 815 && screenX < 1112 && screenY > 657 && screenY < 855) {
                        STATE_STACK.pop();
                        myWorld.restart();
                        if (prefs.getBoolean("playMusic")) AssetLoader.on_click_sound.play(1.0f);
                    }

                    //Rate button
                    if (screenX > 1157 && screenX < 1454 && screenY > 657 && screenY < 855) {
                        if (prefs.getBoolean("playMusic")) AssetLoader.on_click_sound.play(1.0f);
                        Gdx.net.openURI("https://play.google.com/store/apps/details?id=com.tom.treasurerush");
                    }

                }

                else if (myWorld.getPlayer().isAlive()){
                    //use slow down
                    if (screenY > 341+180+350 && screenY < 341+180+125+350 && screenX > 0 && screenX < 125) {
                        System.out.println(prefs.getInteger("slow_down"));
                        if (prefs.getInteger("slow_down") >= 1) {
                            prefs.putInteger("slow_down", prefs.getInteger("slow_down") - 1);
                            prefs.flush();
                            myWorld.usePowerUp(1);
                            if (prefs.getBoolean("playMusic")) AssetLoader.power_up_sound.play(1.0f);
                        }
                    }

                    //use plus time
                    if (screenY > 341+350 && screenY < 341+125+350 && screenX > 0 && screenX < 125) {
                        if (prefs.getInteger("plus_time") >= 1) {
                            prefs.putInteger("plus_time", prefs.getInteger("plus_time") - 1);
                            prefs.flush();
                            myWorld.usePowerUp(2);
                            if (prefs.getBoolean("playMusic")) AssetLoader.power_up_sound.play(1.0f);
                        }
                    }
                }

                return true;
            }
            case HIGHSCORE: {
                STATE_STACK.pop();
                STATE_STACK.push(GameState.MENU);
                if (prefs.getBoolean("playMusic")) AssetLoader.on_click_sound.play(1.0f);
                return true;
            }
            case SHOP: {
                //exit shop
                if (screenX > 48 && screenX < 169 && screenY > 37 && screenY < 114) {
                    STATE_STACK.pop();
                    if (prefs.getBoolean("playMusic")) AssetLoader.on_click_sound.play(1.0f);
                }

                //select default helicopter
                if (screenX > 689 && screenX < 854 && screenY > 250 && screenY < 250 + 165) {
                    prefs.putInteger("plane", 0);
                    prefs.flush();
                    if (prefs.getBoolean("playMusic")) AssetLoader.on_click_sound.play(1.0f);
                    return true;

                }

                //select/buy Ninja
                if (screenX > 1136 && screenX < 1136 + 165 && screenY > 250 && screenY < 250 + 165) {
                    if (prefs.getBoolean("playMusic")) AssetLoader.on_click_sound.play(1.0f);
                    if (prefs.getBoolean("plane1_purchased") == true) {
                        prefs.putInteger("plane", 1);
                        prefs.flush();
                        return true;
                    }
                    if (prefs.getInteger("coin") >= 5000) {
                        STATE_STACK.push(GameState.SHOP_PURCHASING);
                        purchasing_item = "plane1";
                    }
                    else STATE_STACK.push(GameState.NOMONEY);
                }

                //select/buy Alien
                if (screenX > 1570 && screenX < 1570 + 165 && screenY > 250 && screenY < 250 + 165) {
                    if (prefs.getBoolean("playMusic")) AssetLoader.on_click_sound.play(1.0f);
                    if (prefs.getBoolean("plane2_purchased") == true) {
                        prefs.putInteger("plane", 2);
                        prefs.flush();
                        return true;
                    }
                    if (prefs.getInteger("coin") >= 5000) {
                        STATE_STACK.push(GameState.SHOP_PURCHASING);
                        purchasing_item = "plane2";
                    }
                    else STATE_STACK.push(GameState.NOMONEY);
                }

                //consummable plus time
                if (screenX > 836 && screenX < 836 + 150 && screenY > 813 && screenY < 813+150) {
                    if (prefs.getBoolean("playMusic")) AssetLoader.on_click_sound.play(1.0f);
                    if (prefs.getInteger("coin") >= 500) {
                        STATE_STACK.push(GameState.SHOP_PURCHASING);
                        purchasing_item = "plus_time";
                    }
                    else STATE_STACK.push(GameState.NOMONEY);
                }

                //consummable slow down
                if (screenX > 1347 +150 && screenX < 1347 + 300 && screenY > 813 && screenY < 813 + 150) {
                    if (prefs.getBoolean("playMusic")) AssetLoader.on_click_sound.play(1.0f);
                    if (prefs.getInteger("coin") >= 500) {
                        STATE_STACK.push(GameState.SHOP_PURCHASING);
                        purchasing_item = "slow_down";
                    }
                    else STATE_STACK.push(GameState.NOMONEY);
                }

                if (screenX > 681 && screenX < 878 && screenY > 553 && screenY < 650) {
                    if (prefs.getBoolean("playMusic")) AssetLoader.on_click_sound.play(1.0f);
                    if (prefs.getBoolean("flag1_purchased") == true) {
                        if (prefs.getInteger("flag") == 1) {
                            prefs.putInteger("flag", 0);
                            prefs.flush();
                        }
                        else {
                            prefs.putInteger("flag", 1);
                            prefs.flush();
                        }
                        return true;
                    }
                    if (prefs.getInteger("coin") >= 5000) {
                        STATE_STACK.push(GameState.SHOP_PURCHASING);
                        purchasing_item = "flag1";
                    }
                    else STATE_STACK.push(GameState.NOMONEY);
                }

                if (screenX > 1125 && screenX < 1325 && screenY > 553 && screenY < 650) {
                    if (prefs.getBoolean("playMusic")) AssetLoader.on_click_sound.play(1.0f);
                    if (prefs.getBoolean("flag2_purchased") == true) {
                        if (prefs.getInteger("flag") == 2) {
                            prefs.putInteger("flag", 0);
                            prefs.flush();
                        }
                        else {
                            prefs.putInteger("flag", 2);
                            prefs.flush();
                        }
                        return true;
                    }
                    if (prefs.getInteger("coin") >= 5000) {
                        STATE_STACK.push(GameState.SHOP_PURCHASING);
                        purchasing_item = "flag2";
                    }
                    else STATE_STACK.push(GameState.NOMONEY);
                }

                if (screenX > 1562 && screenX < 1762 && screenY > 553 && screenY < 650) {
                    if (prefs.getBoolean("playMusic")) AssetLoader.on_click_sound.play(1.0f);
                    if (prefs.getBoolean("flag3_purchased") == true) {
                        if (prefs.getInteger("flag") == 3) {
                            prefs.putInteger("flag", 0);
                            prefs.flush();
                        }
                        else {
                            prefs.putInteger("flag", 3);
                            prefs.flush();
                        }
                        return true;
                    }
                    if (prefs.getInteger("coin") >= 5000) {
                        STATE_STACK.push(GameState.SHOP_PURCHASING);
                        purchasing_item = "flag3";
                    }
                    else STATE_STACK.push(GameState.NOMONEY);
                }

                return true;
            }
            case SHOP_PURCHASING: {
                if (screenX > 591 && screenX < 889 && screenY > 608 && screenY < 811) {
                    if (prefs.getBoolean("playMusic")) AssetLoader.on_click_sound.play(1.0f);
                    if (purchasing_item == "plane1" || purchasing_item == "plane2") {
                        prefs.putInteger("coin", prefs.getInteger("coin") - 5000);
                        prefs.flush();
                        prefs.putBoolean(purchasing_item + "_purchased", true);
                        prefs.flush();
                        if (purchasing_item == "plane1") {
                            prefs.putInteger("plane", 1); prefs.flush();
                        }
                        if (purchasing_item == "plane2") {
                            prefs.putInteger("plane", 2); prefs.flush();
                        }
                        prefs.flush();
                        purchasing_item = null;
                        STATE_STACK.pop();
                        return true;
                    }

                    if (purchasing_item == "flag1" || purchasing_item == "flag2" || purchasing_item == "flag3") {
                        prefs.putInteger("coin", prefs.getInteger("coin") - 5000);
                        prefs.flush();
                        prefs.putBoolean(purchasing_item + "_purchased", true);
                        prefs.flush();
                        if (purchasing_item == "flag1") {
                            prefs.putInteger("flag", 1); prefs.flush();
                        }
                        if (purchasing_item == "flag2") {
                            prefs.putInteger("flag", 2); prefs.flush();
                        }
                        if (purchasing_item == "flag3") {
                            prefs.putInteger("flag", 3); prefs.flush();
                        }
                        prefs.flush();
                        purchasing_item = null;
                        STATE_STACK.pop();
                        return true;
                    }

                    if (purchasing_item == "plus_time" || purchasing_item == "slow_down") {
                        prefs.putInteger("coin", prefs.getInteger("coin") - 500);
                        prefs.flush();
                        prefs.putInteger(purchasing_item+"", prefs.getInteger(purchasing_item+"") + 1);
                        prefs.flush();
                        purchasing_item = null;
                        STATE_STACK.pop();
                        return true;
                    }
                }

                if (screenX > 1031 && screenX < 1329 && screenY > 608 && screenY < 811) {
                    if (prefs.getBoolean("playMusic")) AssetLoader.on_click_sound.play(1.0f);
                    purchasing_item = null;
                    STATE_STACK.pop();
                    return true;
                }

                return true;
            }
            case ABOUT: case NOMONEY: {
                if (prefs.getBoolean("playMusic")) AssetLoader.on_click_sound.play(1.0f);
                STATE_STACK.pop();
            }
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public void convertToDegree(float x, float z) {

    }
}
