package com.tom.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.sun.org.apache.bcel.internal.generic.POP;
import com.tom.gameobjects.Goal;
import com.tom.gameobjects.Player;
import com.tom.gameobjects.ScrollHandler;
import com.tom.screens.GameScreen;
import com.tom.trHelpers.AssetLoader;

import org.w3c.dom.css.Rect;

import java.util.Random;
import java.util.prefs.PreferenceChangeEvent;

import static com.tom.gameobjects.Powerup.POPUP_TIME;
import static com.tom.gameobjects.Powerup.POWERUP_TIME;
import static com.tom.gameobjects.Powerup.TYPE;
import static com.tom.gameobjects.ScrollHandler.GAME_HEIGHT;
import static com.tom.gameobjects.ScrollHandler.GAME_WIDTH;
import static com.tom.gameobjects.ScrollHandler.SCROLL_SPEED;
import static com.tom.screens.GameScreen.STATE_STACK;

/**
 * Created by Nhat Quang on 6/17/2017.
 */

public class GameWorld {
    private Player player;
    private ScrollHandler scroller;
    private int midPointX;
    private Rectangle ground = new Rectangle(0, GAME_HEIGHT - 50, GAME_WIDTH, 50);
    public Goal goal;
    //for keeping highscore and game state
    public static int score = 0;
    public GameState currentState;
    private int backgroundCount;
    Preferences prefs = Gdx.app.getPreferences("PREFERENCES");
    private Random r = new Random();

    //a variable that can only take certain values that have been defined for it
    public enum GameState {
        READY, RUNNING, GAMEOVER, HIGHSCORE,
    }

    //constructor
    public GameWorld(int midPointX) {
        backgroundCount = r.nextInt(3);
        currentState = GameState.READY;
        this.midPointX = midPointX;
        goal = new Goal(0, this);
        player = new Player(GAME_WIDTH / 6, GAME_HEIGHT / 2 - 100, 100, 100); //Testing, player starts at center to the left of x axis by 33 pixels
        scroller = new ScrollHandler(this, goal);
    }

    //speed our game up

    public void update(float delta) {
       // Preferences prefs = Gdx.app.getPreferences("PREFERENCES");
        //System.out.println(prefs.getInteger("plane"));
        switch (currentState) {
            case READY:
                updateReady(delta);
                break;

            case RUNNING:
                updateRunning(delta);
                break;
            default:
                break;
        }

    }

    private void updateReady(float delta) {
        // Do nothing for now
    }



    public void updateRunning(float delta) {
        // Add a delta cap so that if our game takes too long
        // to update, we will not break our collision detection.

        if (delta > .15f) {
            delta = .15f;
        }



        player.update(delta);
        scroller.update(delta);
        if (player.isAlive()) goal.update(delta);

        //check if the player hits bomb
        if (scroller.collidesBomb(player) && player.isAlive()) {
            if (prefs.getBoolean("playMusic")) AssetLoader.explosion.play(1.0f);
            player.die();
            AssetLoader.play_music.stop();
            //AssetLoader.dead.play();
        }

        //check if the player hits gem
        if (player.isAlive()) {
            scroller.collidesGem(player);
        }

        //check if player hits powerup
        if (player.isAlive()) {
            scroller.collidesPowerup(player);
        }

        //check if the player hits the ground
         if (isCollision(player.getBoundingPolygon(), ground)) {
            if (prefs.getBoolean("playMusic")) AssetLoader.metal.play(1.0f);
            endGame();
            player.decelerate();
            currentState = GameState.GAMEOVER;
            if (score > AssetLoader.getHighScore()) {
                AssetLoader.setHighScore(score);
                currentState = GameState.HIGHSCORE;
            }
        }

        //these parts for power up

        //power up count down, reaches 0 then resets
        if (TYPE != -1) {
            switch (TYPE) {
                case 0: {
                    scroller.isEthereal = true;
                    POPUP_TIME -= delta; POWERUP_TIME -= delta;

                    if (POWERUP_TIME <= 0) {
                        TYPE = -1; POWERUP_TIME = 0; POPUP_TIME = 0;
                        scroller.isEthereal = false;
                    }

                    break;
                }
                case 1: {
                    if (POPUP_TIME == 3) SCROLL_SPEED *= 0.9;
                    POPUP_TIME -= delta;

                    if (POPUP_TIME <= 0) {
                        POPUP_TIME = 0; TYPE = -1;
                    }

                    break;
                }
                case 2: {
                    if (POPUP_TIME == 3) goal.remaining_time += 1;
                    POPUP_TIME -= delta;

                    if (POPUP_TIME <= 0) {
                        POPUP_TIME = 0; TYPE = -1;
                    }

                    break;
                }
                case 3: {
                    POPUP_TIME -= delta; POWERUP_TIME -= delta;

                    if (POWERUP_TIME <= 0) {
                        TYPE = -1; POWERUP_TIME = 0; POPUP_TIME = 0;
                    }
                    break;
                }
            }
        }
    }


    //check the state of the game
    public boolean isHighScore() {
        return currentState == GameState.HIGHSCORE;
    }

    public boolean isGameOver() {
        return currentState == GameState.GAMEOVER;
    }

    public boolean isReady() {
        return currentState == GameState.READY;
    }


    //restart and start the game
    public void start() {
        currentState = GameState.RUNNING;
    }
    public void restart() {
        SCROLL_SPEED = -500;
        currentState = GameState.READY;
        prefs.putInteger("coin", prefs.getInteger("coin") + score);
        prefs.flush();
        score = 0;
        player.onRestart();
        scroller.onRestart();//need fix
        goal.onRestart();
        currentState = GameState.READY;
        TYPE = -1; POPUP_TIME = 0; POWERUP_TIME = 0;
        backgroundCount = r.nextInt(3);
    }

    public int getBGCount() {
        return backgroundCount;
    }


    //Manipulating the score
    public int getScore() {
        return score;
    }

    public void addScore(int increment) {
        score += increment;
    }


    //getters
    public Player getPlayer() {
        return player;
    }

    public ScrollHandler getScroller() {
        return scroller;
    }

    public void endGame() {
        scroller.stop();
        player.die();
        SCROLL_SPEED = 0;
    }

    private boolean isCollision(Polygon p, Rectangle r) {
        Polygon rPoly = new Polygon(new float[] { 0, 0, r.width, 0, r.width,
                r.height, 0, r.height });
        rPoly.setPosition(r.x, r.y);
        if (Intersector.overlapConvexPolygons(rPoly, p))
            return true;
        return false;
    }

    public void usePowerUp(int type) {
        switch (type) {
            case 1: {
                SCROLL_SPEED *= 0.7;
                break;
            }
            case 2: {
                goal.remaining_time += 10;
                break;
            }
        }
    }

}
