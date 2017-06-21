package com.tom.trHelpers;

import com.badlogic.gdx.InputProcessor;
import com.tom.gameobjects.Player;
import com.tom.gameworld.GameWorld;

/**
 * Created by Nhat Quang on 6/17/2017.
 */

public class InputHandler implements InputProcessor {
    private Player myPlayer;
    private GameWorld myWorld;

    //constructor
    // Ask for a reference to the Bird when InputHandler is created.
    public InputHandler(GameWorld myWorld) {
        // myBird now represents the gameWorld's bird.
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
        //if (myWorld.isReady()) {
          //  myWorld.start();
        //}

        myPlayer.onClick();

        //if (myWorld.isGameOver() || myWorld.isHighScore()) {
            // Reset all variables, go to GameState.READY
          //  myWorld.restart();
        //}

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
}
