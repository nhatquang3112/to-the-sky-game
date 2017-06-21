package com.tom.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.tom.gameobjects.Goal;
import com.tom.gameobjects.Player;
import com.tom.gameobjects.ScrollHandler;
import com.tom.trHelpers.AssetLoader;

import org.w3c.dom.css.Rect;

import static com.tom.gameobjects.ScrollHandler.GAME_HEIGHT;
import static com.tom.gameobjects.ScrollHandler.GAME_WIDTH;

/**
 * Created by Nhat Quang on 6/17/2017.
 */

public class GameWorld {
    private Player player;
    private ScrollHandler scroller;
    private int score = 0;
    private GameState currentState;
    private int midPointX;
    private Rectangle ground = new Rectangle(0, GAME_HEIGHT, GAME_WIDTH, 20);
    public Goal goal;

    //a variable that can only take certain values that have been defined for it
    public enum GameState {
        READY, RUNNING, GAMEOVER, HIGHSCORE
    }

    //constructor
    public GameWorld(int midPointX) {
        currentState = GameState.READY;
        this.midPointX = midPointX;
        goal = new Goal(0, this);
        player = new Player(GAME_WIDTH/6, 68, 100, 100); //Testing, player starts at center to the left of x axis by 33 pixels
        scroller = new ScrollHandler(this, goal);
    }



    public void update(float delta) {
        //Gdx.app.log("GameWorld", "update");
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

        /*
    public float updateHighScore(float score) {
        FileHandle high_score_file = Gdx.files.local("data/high_score.txt");
        String high_score_text = high_score_file.readString();
        float high_score = Float.parseFloat(high_score_text);

        if (score > high_score) {

        }

        return high_score;
    } */

    public boolean isHighScore() {
        return currentState == GameState.HIGHSCORE;
    }

    public boolean isGameOver() {
        return currentState == GameState.GAMEOVER;
    }

    public boolean isReady() {
        return currentState == GameState.READY;
    }

    public void start() {
        currentState = GameState.RUNNING;
    }

    public Player getPlayer() {
        return player;
    }

    public ScrollHandler getScroller() {
        return scroller;
    }

    public void endGame() {
        scroller.stop();
        player.die();
    }
}
