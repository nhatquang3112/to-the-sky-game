package com.tom.gameobjects;

import com.badlogic.gdx.Gdx;
import com.tom.gameworld.GameWorld;

import java.util.Random;

/**
 * Created by Nhat Quang on 6/17/2017.
 */

public class ScrollHandler {
    private GameWorld gameWorld;
    private Goal goal;
    public static int SCROLL_SPEED = -500;
    public static final int BOMB_SIZE = 100;
    public static final int GEM_SIZE = 100;
    private Grid grid, grid2;
    private float gameHeight = 1080; private float gameWidth = Gdx.graphics.getWidth() / (Gdx.graphics.getHeight() / gameHeight);
    public static final float GAME_HEIGHT = 1080;
    public static final float GAME_WIDTH = ((float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight()) * 1080;

    public ScrollHandler(GameWorld gameWorld, Goal goal) {
        this.gameWorld = gameWorld;
        this.goal = goal;
        grid = new Grid(0, 0, (int) gameWidth, (int) gameHeight, SCROLL_SPEED, "FIRST GRID IS EMPTY");
        grid2 = new Grid(gameWidth, 0, (int) gameWidth, (int) gameHeight, SCROLL_SPEED);
        System.out.printf("Width is %f height is %f\n", GAME_WIDTH, GAME_HEIGHT);
        System.out.printf("System width is %d system height is %d\n", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void update(float delta) {
        // Update our objects
        grid.update(delta);
        grid2.update(delta);
        //check isScrolledLeft for grids
        if(grid.isScrolledLeft()) grid = new Grid(grid2.getX() + gameWidth, 0, (int) gameWidth, (int) gameHeight, SCROLL_SPEED);
        if(grid2.isScrolledLeft()) grid2 = new Grid(grid.getX() + gameWidth, 0, (int) gameWidth, (int) gameHeight, SCROLL_SPEED);
        // Same check with background
    }

    public boolean collidesBomb(Player player) {
        if (grid.bomb != null) {
            for (int i = 0; i < grid.bomb.size(); i++) {
                if (grid.bomb.get(i).collides(player)) {
                    stop();
                    return true;
                }
            }
        }

        if (grid2.bomb != null) {
            for (int i = 0; i < grid2.bomb.size(); i++) {
                if (grid2.bomb.get(i).collides(player)) {
                    stop();
                    return true;
                }
            }
        }

        return false;
    }

    // Check if ANY gem hits the player.
    public void collidesGem(Player player) {
        if (grid.gem != null) {
            for (int i = 0; i < grid.gem.length; i++) {
                grid.gem[i].collides(player, goal);
            }
        }

        if (grid2.gem != null) {
            for (int i = 0; i < grid2.gem.length; i++) {
                grid2.gem[i].collides(player, goal);
            }
        }
    }


    public void stop() {
        grid.stop();
        grid2.stop();
    }

    //scroller on restart
    public void onRestart() {
        grid = new Grid(0, 0, (int) gameWidth, (int) gameHeight, SCROLL_SPEED, "FIRST GRID IS EMPTY");
        grid2 = new Grid(gameWidth, 0, (int) gameWidth, (int) gameHeight, SCROLL_SPEED);
    }

    public Grid getGrid() { return grid; }

    public Grid getGrid2() { return  grid2; }
}
