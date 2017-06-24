package com.tom.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.tom.gameobjects.Goal;
import com.tom.gameobjects.Player;
import com.tom.gameobjects.ScrollHandler;
import com.tom.screens.GameScreen;
import com.tom.trHelpers.AssetLoader;

import org.w3c.dom.css.Rect;

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
    private Rectangle ground = new Rectangle(0, GAME_HEIGHT - 20, GAME_WIDTH, 20);
    public Goal goal;
    //for keeping highscore and game state
    private int score = 0;
    public GameState currentState;

    //a variable that can only take certain values that have been defined for it
    public enum GameState {
        READY, RUNNING, GAMEOVER, HIGHSCORE,
    }

    //constructor
    public GameWorld(int midPointX) {
        currentState = GameState.READY;
        this.midPointX = midPointX;
        goal = new Goal(0, this);
        player = new Player(GAME_WIDTH / 4 + 60, 68, 150, 150); //Testing, player starts at center to the left of x axis by 33 pixels
        scroller = new ScrollHandler(this, goal);
    }

    //speed our game up

    public void update(float delta) {

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
        System.out.printf("%d\n", score);


        player.update(delta);
        scroller.update(delta);
        if (player.isAlive()) goal.update(delta);

        //check if the player hits bomb
        if (scroller.collidesBomb(player) && player.isAlive()) {
            endGame();
            //AssetLoader.dead.play();
        }

        //check if the player hits gem
        if (player.isAlive()) {
            scroller.collidesGem(player);
        }

        //check if the player hits the ground
        if (Intersector.overlaps(player.getBoundingRect(), ground) ||
                Intersector.overlaps(player.getBoundingRect(), ground)  ) {
            endGame();
            player.decelerate();
            currentState = GameState.GAMEOVER;
            if (score > AssetLoader.getHighScore()) {
                AssetLoader.setHighScore(score);
                currentState = GameState.HIGHSCORE;
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
        score = 0;
        player.onRestart((int) GAME_WIDTH/4 +60);
        scroller.onRestart();//need fix
        goal.onRestart();
        currentState = GameState.READY;
        STATE_STACK.pop();
        STATE_STACK.push(GameScreen.GameState.MENU);
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
}
