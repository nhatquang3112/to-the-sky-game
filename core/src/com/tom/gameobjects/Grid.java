package com.tom.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Random;

import static com.tom.gameobjects.ScrollHandler.BOMB_SIZE;
import static com.tom.gameobjects.ScrollHandler.GEM_SIZE;
import static com.tom.gameobjects.ScrollHandler.SCROLL_SPEED;

/**
 * Created by NewUser on 6/19/2017.
 */

public class Grid extends Scrollable {
    //elements here
    private Rectangle grid;
    private Random r = new Random();
    public ArrayList<Bomb> bomb = new ArrayList<Bomb>();
    public Gem[] gem = new Gem[r.nextInt(5) + 5];
    public int[][] grid_matrix;
    public int matrix_col, matrix_row;
    public Background background;

    //constructor
    public Grid(float x, float y, int gameWidth, int gameHeight, float scrollSpeed) {
        super(x, y, gameWidth, gameHeight, scrollSpeed);
        grid = new Rectangle();
        background = new Background(x, y, gameWidth, gameHeight, SCROLL_SPEED);

        //remember y then x here
        matrix_col = gameWidth / BOMB_SIZE;
        matrix_row = gameHeight / BOMB_SIZE;
        grid_matrix = new int[matrix_row][matrix_col];

        int i = 0;

        while (i < this.matrix_col - 8) {
            randomDirection(grid_matrix, i);
            i += 8;
        }

        randomGem();

        updateGameObjects();

        Gdx.app.log("Grid", "New Grid");
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        grid.set(position.x, position.y, width, height);

        for (int i = 0; i < bomb.size(); i++) {
            bomb.get(i).update(delta);
        }

        for (int i = 0; i < gem.length; i++) {
            gem[i].update(delta);
        }

        background.update(delta);
    }

    public void randomDirection(int[][] grid_matrix, int starting_col) {
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

    public Gem randomGem() {
        int row = r.nextInt(this.matrix_row);
        int col = r.nextInt(this.matrix_col);

        while (grid_matrix[row][col] != 0) {
            row = r.nextInt(this.matrix_row);
            col = r.nextInt(this.matrix_col);
        }

        grid_matrix[row][col] = 1;

        return new Gem(GEM_SIZE * col + this.getX(), GEM_SIZE * row, GEM_SIZE, GEM_SIZE, SCROLL_SPEED);
    }

    public void updateGameObjects() {
        //update bomb for algorithm matrix to bomb array
        int bomb_counter = 0;
        for (int row = 0; row < matrix_row; row++) {
            for (int col = 0; col < matrix_col; col++) {
                if (grid_matrix[row][col] == 9) {
                    bomb.add(bomb_counter++, new Bomb(BOMB_SIZE * col + this.getX(), BOMB_SIZE * row, BOMB_SIZE, BOMB_SIZE, SCROLL_SPEED));
                }
            }
        }

        System.out.println("Gem size");
        for (int i = 0; i < gem.length; i++) {
            gem[i] = randomGem();
            System.out.printf("%f %f\n", gem[i].getX(), gem[i].getY());
        }
    }

    @Override
    public void stop() {
        super.stop();
        for (int i = 0; i < bomb.size(); i++) {
            bomb.get(i).stop();
        }

        for (int i = 0; i < gem.length; i++) {
            gem[i].stop();
        }

        background.stop();
    }
}
