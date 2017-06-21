package com.tom.gameobjects;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

/**
 * Created by Nhat Quang on 6/17/2017.
 */

public class Gem extends Scrollable {
    private Random r;
    private Rectangle gem;
    public int color; //1 is blue, 2 is red, 3 is green
    public boolean isScored = false;


    public Gem(float x, float y, int width, int height, float scrollSpeed) {
        super(x, y, width, height, scrollSpeed);
        // Initialize a Random object for Random number generation
        r = new Random();
        color = r.nextInt(3) + 1;
        gem = new Rectangle();
    }


    @Override
    public void update(float delta) {
        // Call the update method in the superclass (Scrollable)
        super.update(delta);
        gem.set(position.x, position.y, width, height);

    }

    @Override
    public void reset(float newX) {
        // Call the reset method in the superclass (Scrollable)
        super.reset(newX);
        // Change the height to a random number
        //height = r.nextInt(90) + 15;
        isScored = false;
    }

    public void collides(Player player, Goal goal) {

            if (Intersector.overlaps(player.getBoundingRect(), gem)) {
               if (isScored == false) goal.gemUpdate(color);
               isScored = true;
            }

    }

    public Rectangle getGem() {
        return gem;
    }


}
