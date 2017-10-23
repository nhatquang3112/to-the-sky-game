package com.tom.gameobjects;

import com.badlogic.gdx.Gdx;
import com.tom.gameworld.GameWorld;

import static com.tom.gameobjects.Powerup.POPUP_TIME;
import static com.tom.gameobjects.Powerup.TYPE;
import static com.tom.gameobjects.Powerup.POWERUP_TIME;

/**
 * Created by Nhat Quang on 6/17/2017.
 */

public class ScrollHandler {
    private GameWorld gameWorld;
    private Goal goal;
    public static int SCROLL_SPEED = -500;
    public static final int BOMB_SIZE = 75;
    public static final int GEM_SIZE = 75;
    public static final int POWER_SIZE = 75;
    public static int GRID_COUNT = 0;
    public boolean isEthereal = false;
    private Grid grid, grid2;
    public Background bg1, bg2;
    private float gameHeight = 1080; private float gameWidth = 1920;
    public static final float GAME_HEIGHT = 1080;
    public static final float GAME_WIDTH = 1920;

    public ScrollHandler(GameWorld gameWorld, Goal goal) {
        this.gameWorld = gameWorld;
        this.goal = goal;
        grid = new Grid(0, 0, (int) GAME_WIDTH, (int) GAME_HEIGHT, SCROLL_SPEED, "FIRST GRID IS EMPTY");
        grid2 = new Grid(GAME_WIDTH, 0, (int) GAME_WIDTH, (int) GAME_HEIGHT, SCROLL_SPEED);
        bg1 = new Background(0, 0, (int) GAME_WIDTH, (int) GAME_HEIGHT, SCROLL_SPEED);
        bg2 = new Background(GAME_WIDTH, 0, (int) GAME_WIDTH, (int) GAME_HEIGHT, SCROLL_SPEED);
    }

    public void update(float delta) {
        // Update our objects

        grid.update(delta);
        grid2.update(delta);
        bg1.update(delta);
        bg2.update(delta);
        //check isScrolledLeft for grids
        if(grid.isScrolledLeft()) {
            grid = new Grid(grid2.getX() + gameWidth, 0, (int) gameWidth, (int) gameHeight, SCROLL_SPEED);
        }
        if(grid2.isScrolledLeft()) {
            grid2 = new Grid(grid.getX() + gameWidth, 0, (int) gameWidth, (int) gameHeight, SCROLL_SPEED);
        }
        if(bg1.isScrolledLeft()) {
            bg1 = new Background(bg2.getX() + gameWidth, 0, (int) gameWidth, (int) gameHeight, SCROLL_SPEED);
        }
        if(bg2.isScrolledLeft()) {
            bg2 = new Background(bg1.getX() + gameWidth, 0, (int) gameWidth, (int) gameHeight, SCROLL_SPEED);
        }
        // Same check with background

    }

    public boolean collidesBomb(Player player) {
        if (TYPE == 0) return false;

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
            for (int i = 0; i < grid.gem.size(); i++) {
                grid.gem.get(i).collides(player, goal);
            }
        }

        if (grid2.gem != null) {
            for (int i = 0; i < grid2.gem.size(); i++) {
                grid2.gem.get(i).collides(player, goal);
            }
        }
    }

    public void collidesPowerup(Player player) {
        if (grid.power_up != null) {
            if (grid.power_up.collides(player)) {
                TYPE = grid.power_up.type;
                switch (TYPE) {
                    case 0: POWERUP_TIME = 3; POPUP_TIME = 3; break;
                    case 1: POWERUP_TIME = 0; POPUP_TIME = 3; break;
                    case 2: POWERUP_TIME = 0; POPUP_TIME = 3; break;
                    case 3: POWERUP_TIME = 3; POPUP_TIME = 3; break;
                }
                grid.power_up = null;
            }
        }
        if (grid2.power_up != null) {
            if (grid2.power_up.collides(player)) {
                TYPE = grid2.power_up.type;
                switch (TYPE) {
                    case 0: POWERUP_TIME = 3; POPUP_TIME = 3; break;
                    case 1: POWERUP_TIME = 0; POPUP_TIME = 3; break;
                    case 2: POWERUP_TIME = 0; POPUP_TIME = 3; break;
                    case 3: POWERUP_TIME = 3; POPUP_TIME = 3; break;
                }
                grid2.power_up = null;
            }
        }

    }

    public void stop() {
        grid.stop();
        grid2.stop();
        bg1.stop();
        bg2.stop();
    }

    //scroller on restart
    public void onRestart() {
        GRID_COUNT = 0;
        grid = new Grid(0, 0, (int) gameWidth, (int) gameHeight, SCROLL_SPEED, "FIRST GRID IS EMPTY");
        grid2 = new Grid(gameWidth, 0, (int) gameWidth, (int) gameHeight, SCROLL_SPEED);
        bg1 = new Background(0, 0, (int) GAME_WIDTH, (int) GAME_HEIGHT, SCROLL_SPEED);
        bg2 = new Background(GAME_WIDTH, 0, (int) GAME_WIDTH, (int) GAME_HEIGHT, SCROLL_SPEED);

    }

    public Grid getGrid() { return grid; }

    public Grid getGrid2() { return  grid2; }
}
