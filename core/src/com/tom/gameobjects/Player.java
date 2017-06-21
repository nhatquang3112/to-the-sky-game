package com.tom.gameobjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import org.w3c.dom.css.Rect;

/**
 * Created by Nhat Quang on 6/17/2017.
 */

public class Player {
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;
    private int width;
    private int height;
    public Rectangle boundingRect = new Rectangle();
    private boolean isAlive;
    private float rotation;

    //constructor
    public Player(float x, float y, int width, int height) {
        this.width = width;
        this.height = height;
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, 2000);
        isAlive = true;
    }

    public void onRestart(int y) {
        position.y = y;
        velocity.x = 0;
        velocity.y = 0;
        acceleration.x = 0;
        acceleration.y = 460;
        isAlive = true;
    }

    public void update(float delta) {

        velocity.add(acceleration.cpy().scl(delta));

        // CEILING CHECK
        if (position.y < -13) {
            position.y = -13;
            velocity.y = 0;
        }

        if (velocity.y > 1000) {
            velocity.y = 1000; //reaching terminal velocity = 200
        }

        // Rotate counterclockwise
        if (velocity.y < 0) {
            rotation -= 600 * delta;

            if (rotation < -20) {
                rotation = -20;
            }
        }

        // Rotate clockwise
        if (isFalling() || !isAlive) {
            rotation += 480 * delta;
            if (rotation > 90) {
                rotation = 90; //facing down to ground
            }

        }
        position.add(velocity.cpy().scl(delta));
        // Set the circle's center to be (9, 6) with respect to the bird.
        // Set the circle's radius to be 6.5f;
        //the bird is 17x12
        boundingRect.set(position.x, position.y, this.getWidth(), this.getHeight());


    }

    //check if the bird is falling based on velocity
    public boolean isFalling() {
        return velocity.y > 110;
    }

    //check if the bird should not do animation anymore
    public boolean shouldntFlap() {
        return velocity.y > 70 || !isAlive;
    }


    //make the bird goes up, negative as the y axis is pointing down
    public void onClick() {
        if (isAlive) {
            velocity.y = -750;
        }
    }

    public void die() {
        isAlive = false;
        velocity.y = 0;
    }

    public void decelerate() {
        //We want the bird to stop accelerating downwards once it is dead.
        acceleration.y = 0;
    }

    //Getters
    public boolean isAlive() {
        return isAlive;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Rectangle getBoundingRect() {
        return boundingRect;
    }
}



