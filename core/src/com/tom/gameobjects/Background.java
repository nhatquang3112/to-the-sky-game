package com.tom.gameobjects;

import com.badlogic.gdx.math.Rectangle;

import static com.tom.gameobjects.ScrollHandler.GAME_HEIGHT;
import static com.tom.gameobjects.ScrollHandler.SCROLL_SPEED;

/**
 * Created by NewUser on 7/17/2017.
 */

public class Background extends Scrollable {
    public Rectangle background;
    public Rectangle ground;

    public Background(float x, float y, int width, int height, float scrollSpeed) {
        super(x, y, width, height, scrollSpeed * 0.9f);

        background = new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        ground = new Rectangle(this.getX(), GAME_HEIGHT - 50, this.getWidth(), 50);
    }

    @Override
    public void update(float delta) {
        position.add(velocity.cpy().scl(delta));
        velocity.x = SCROLL_SPEED * 0.9f;

        // If the Scrollable object is no longer visible:
        if (position.x + width < 0) {
            isScrolledLeft = true;
        }

        background.set(position.x, position.y, width, height);
        ground.set(position.x, GAME_HEIGHT - 50, width, 50);
    }
}
