package com.tom.gameobjects;

import static com.tom.gameobjects.ScrollHandler.SCROLL_SPEED;

/**
 * Created by Nhat Quang on 6/19/2017.
 */

public class Background extends Scrollable {

    //constructor
    public Background(float x, float y, int width, int height, float scrollSpeed) {
        super(x, y, width, height, SCROLL_SPEED);
    }

    //restart the position x of background when game restarts. Scrollspeed stays the same
    public void onRestart(float x, float scrollSpeed) {
        position.x = x;
        velocity.x = scrollSpeed;
    }
}
