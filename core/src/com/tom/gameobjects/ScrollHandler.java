package com.tom.gameobjects;

import com.badlogic.gdx.Gdx;
import com.tom.gameworld.GameWorld;

import java.util.Random;

/**
 * Created by Nhat Quang on 6/17/2017.
 */

public class ScrollHandler {
    private GameWorld gameWorld;
    public static final int SCROLL_SPEED = -1000;
    public static final int BOMB_SIZE = 100;
    public static final int GEM_SIZE = 100;
    private Random r;
    private Grid grid, grid2;
    private float gameHeight = 1080; private float gameWidth = Gdx.graphics.getWidth() / (Gdx.graphics.getHeight() / gameHeight);
    public static float GAME_HEIGHT = 1080;
    public static float GAME_WIDTH = Gdx.graphics.getWidth() / (Gdx.graphics.getHeight() / 1080);

    public ScrollHandler(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        grid = new Grid(gameWidth, 0, (int) gameWidth, (int) gameHeight, SCROLL_SPEED);
        grid2 = new Grid(2 * gameWidth, 0, (int) gameWidth, (int) gameHeight, SCROLL_SPEED);
    }

    public void update(float delta) {
        // Update our objects
        grid.update(delta);
        grid2.update(delta);
        if(grid.isScrolledLeft()) grid = new Grid(grid2.getX() + gameWidth, 0, (int) gameWidth, (int) gameHeight, SCROLL_SPEED);
        if(grid2.isScrolledLeft()) grid2 = new Grid(grid.getX() + gameWidth, 0, (int) gameWidth, (int) gameHeight, SCROLL_SPEED);
        // Check if any of the pipes are scrolled left,
        // and reset accordingly



    }

    public boolean collidesBomb(Player player) {
        for (int i = 0; i < grid.bomb.size(); i++) {
            if (grid.bomb.get(i).collides(player)) {
                stop(); return true;
            }
        }

        for (int i = 0; i < grid2.bomb.size(); i++) {
            if (grid2.bomb.get(i).collides(player)) {
                stop(); return true;
            }
        }

        return false;
    }

    // Check if ANY gem hits the player.
    public void collidesGem(Player player) {
        for (int i = 0; i < grid.gem.length; i++) {
            grid.gem[i].collides(player);
        }

        for (int i = 0; i < grid2.gem.length; i++) {
            grid2.gem[i].collides(player);
        }
    }

    public void stop() {
        grid.stop();
        grid2.stop();
    }

    public Grid getGrid() { return grid; }

    public Grid getGrid2() { return  grid2; }
}
