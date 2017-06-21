package com.tom.gameobjects;

import java.util.Random;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Nhat Quang on 6/17/2017.
 */

public class Bomb extends Scrollable {
    private Random r;
    private Rectangle bomb;
    private boolean isScored = false;


    public Bomb(float x, float y, int width, int height, float scrollSpeed) {
        super(x, y, width, height, scrollSpeed);
        // Initialize a Random object for Random number generation
        r = new Random();
        bomb = new Rectangle();
    }


    @Override
    public void update(float delta) {
        // Call the update method in the superclass (Scrollable)
        super.update(delta);
        bomb.set(position.x, position.y, width, height);

    }

    @Override
    public void reset(float newX) {
        // Call the reset method in the superclass (Scrollable)
        super.reset(newX);
        // Change the height to a random number
        //height = r.nextInt(90) + 15;
        isScored = false;
    }

    public boolean collides(Player player) {
        if (position.x < player.getX() + player.getWidth()) {
            return (Intersector.overlaps(player.getBoundingRect(), bomb));
        }
        return false;
    }

    public Rectangle getBomb() {
        return bomb;
    }


}
