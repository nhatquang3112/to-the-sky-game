package com.tom.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Random;

import static com.tom.gameobjects.ScrollHandler.BOMB_SIZE;
import static com.tom.gameobjects.ScrollHandler.GAME_HEIGHT;
import static com.tom.gameobjects.ScrollHandler.GAME_WIDTH;
import static com.tom.gameobjects.ScrollHandler.GEM_SIZE;
import static com.tom.gameobjects.ScrollHandler.GRID_COUNT;
import static com.tom.gameobjects.ScrollHandler.POWER_SIZE;
import static com.tom.gameobjects.ScrollHandler.SCROLL_SPEED;

/**
 * Created by NewUser on 6/19/2017.
 */

public class Grid extends Scrollable {
    //elements here
    private Rectangle grid;
    public Rectangle ground;
    private Random r = new Random();
    public ArrayList<Bomb> bomb = new ArrayList<Bomb>();
    public ArrayList<Gem> gem = new ArrayList<Gem>();
    public int[][] grid_matrix;
    public int matrix_col, matrix_row;
    public Powerup power_up = null;

    //constructor
    public Grid(float x, float y, int gameWidth, int gameHeight, float scrollSpeed) {
        super(x, y, gameWidth, gameHeight, scrollSpeed);
        GRID_COUNT++;
        grid = new Rectangle();
        ground = new Rectangle();

        //remember y then x here
        matrix_col = gameWidth / BOMB_SIZE;
        matrix_row = (gameHeight - 50 ) / BOMB_SIZE;
        grid_matrix = new int[matrix_row][matrix_col];

        randomDirection();
        /*switch(r.nextInt(4) + 1) {
            case 1: randomDirection(); break;
            case 2: randomDirection2(); break;
            case 3: randomDirection3(); break;
            case 4: randomDirection4(); break;
        }*/



        updateGameObjects();


    }

    public Grid(float x, float y, int gameWidth, int gameHeight, float scrollSpeed, String args) {
        super(x, y, gameWidth, gameHeight, scrollSpeed);
        grid = new Rectangle();
        ground = new Rectangle(position.x, GAME_HEIGHT - 50, width, 50);

        bomb = null; gem = null;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        grid.set(position.x, position.y, width, height);
        ground.set(position.x, GAME_HEIGHT - 50, width, 50);

        if (power_up != null) power_up.update(delta);

        if (bomb == null && gem == null) return;

        for (int i = 0; i < bomb.size(); i++) {
            bomb.get(i).update(delta);
        }

        for (int i = 0; i < gem.size(); i++) {
            gem.get(i).update(delta);
        }
    }

    public void randomDirection() {
        int i = 0;

        while (i < this.matrix_col - 8) {
            randomBomb(i);
            i += 8;
        }

        for (i = 0; i < 10; i++) randomGem();

        //random powerup
        if (GRID_COUNT % 5 != 1) return;
        int row = r.nextInt(this.matrix_row);
        int col = r.nextInt(this.matrix_col);

        while (grid_matrix[row][col] != 0) {
            row = r.nextInt(this.matrix_row);
            col = r.nextInt(this.matrix_col);
        }

        grid_matrix[row][col] = 8;
    }

    public void randomBomb(int starting_col) {
        int bomb_row = r.nextInt(this.matrix_row);
        int direction = r.nextInt(3);

        if (direction == 0 && bomb_row < 4) bomb_row = 4;
        if ((direction == 2 || direction == 3) && bomb_row > this.matrix_row - 5) bomb_row = this.matrix_row - 5;

        // 0 is up right
        // 1 is right
        // 2 is down right
        // 3 is down

        switch(direction) {
            case 0: { // up right
                for (int i = 0; i < 5; i++) {
                    grid_matrix[bomb_row - i][starting_col + i] = 9;
                }
                break;
            }

            case 1: { //right
                for (int col = starting_col; col < starting_col + 5; col++) {
                    grid_matrix[bomb_row][col] = 9;
                }
                break;
            }

            case 2: { //down right
                for (int i = 0; i < 5; i++) {
                    grid_matrix[bomb_row + i][starting_col + i] = 9;
                }
                break;
            }

            case 3: { //down
                for (int row = bomb_row; row <= bomb_row + 4; row++) {
                    grid_matrix[row][starting_col] = 9;
                }
                break;
            }

            default: break;
        }
    }

    public void randomGem() {
        int row = r.nextInt(this.matrix_row);
        int col = r.nextInt(this.matrix_col);

        while (grid_matrix[row][col] != 0) {
            row = r.nextInt(this.matrix_row);
            col = r.nextInt(this.matrix_col);
        }

        grid_matrix[row][col] = 1;
    }

    public void updateGameObjects() {
        //update bomb for algorithm matrix to bomb array
        int bomb_counter = 0;
        for (int row = 0; row < matrix_row; row++) {
            for (int col = 0; col < matrix_col; col++) {
                if (grid_matrix[row][col] == 9) {
                    bomb.add(bomb_counter++, new Bomb(BOMB_SIZE * col + this.getX(), BOMB_SIZE * row, BOMB_SIZE, BOMB_SIZE, SCROLL_SPEED));
                }
                if (grid_matrix[row][col] == 1) {
                    gem.add(new Gem(GEM_SIZE * col + this.getX(), GEM_SIZE * row, GEM_SIZE, GEM_SIZE, SCROLL_SPEED));
                }
                if (grid_matrix[row][col] == 8) {
                    power_up = new Powerup(POWER_SIZE * col + this.getX(), POWER_SIZE * row, POWER_SIZE, POWER_SIZE, SCROLL_SPEED);
                }
            }
        }
    }

    @Override
    public void stop() {

    }

    public void onRestart(float x, float scrollSpeed) {
        velocity.x = scrollSpeed;
        reset(x);
    }

    public void randomDirection2() {
        for (int i = 0; i < matrix_row / 2; i++) {
            for (int j = 0; j < matrix_col; j += 8) {
                grid_matrix[i][j] = 9;
            }
        }

        for (int i = 0; i < matrix_col; i++) {
            if (i % 8 == 3 || i % 8 == 4 || i % 8 == 5) {
                grid_matrix[matrix_row / 2 + 2][i] = 9;
            }
        }

        for (int i = 3; i < matrix_col; i += 8) {
            grid_matrix[matrix_row / 6][i] = 1;
            grid_matrix[matrix_row / 6][i + 2] = 1;
            grid_matrix[matrix_row / 3][i] = 1;
            grid_matrix[matrix_row / 3][i + 2] = 1;
        }
    }

    public void randomDirection3() {
        for (int i = 0; i < matrix_row / 3; i++) {
            grid_matrix[i][i] = 9;
            grid_matrix[matrix_row - 1 - i][matrix_row - 1 - i] = 9;
        }

        for (int i = matrix_row - 1; i > matrix_row * 2 / 3.0; i--) {
            grid_matrix[i][matrix_row - 1 - i] = 9;
            grid_matrix[matrix_row - 1 - i][i] = 9;
        }

        grid_matrix[matrix_row / 3 + 1][matrix_row / 3 + 1] = 1;
        grid_matrix[matrix_row / 3 + 1][matrix_row / 3 + 2] = 1;
        grid_matrix[matrix_row / 3 + 2][matrix_row / 3 + 1] = 1;
        grid_matrix[matrix_row / 3 + 2][matrix_row / 3 + 2] = 1;
    }

    public void randomDirection4() {
        for (int i = matrix_row / 3; i >= 0; i--) {
            grid_matrix[i][matrix_row / 3 - i] = 9;
            grid_matrix[matrix_row - 1 - i][matrix_row / 3 - i] = 9;
        }

        for (int i = 0; i < 9; i++) {
            grid_matrix[matrix_row / 3][matrix_row / 3 + 2 + i] = 9;
            grid_matrix[matrix_row - 1 - matrix_row / 3][matrix_row / 3 + 2 + i] = 9;
        }

        for (int i = 0; i <= 8; i += 2) {
            grid_matrix[1][matrix_row / 3 + 2 + i] = 1;
            grid_matrix[matrix_row - 2][matrix_row / 3 + 2 + i] = 1;
        }
    }
}
