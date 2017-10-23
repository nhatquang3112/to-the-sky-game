package com.tom.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import org.w3c.dom.css.Rect;

import java.util.ArrayList;

import static com.tom.gameobjects.Powerup.TYPE;
import static com.tom.gameobjects.ScrollHandler.GAME_HEIGHT;
import static com.tom.gameobjects.ScrollHandler.GAME_WIDTH;
import static com.tom.trHelpers.InputHandler.ROLL;

/**
 * Created by Nhat Quang on 6/17/2017.
 */

public class Player {
    public static Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;
    private int width;
    private int height;
    public Polygon boundingPolygon;
    private boolean isAlive;
    private float rotation;
    private ArrayList<Float> vertices_original;
    private ArrayList<Float> vertices_actual;
    public int death_animation_counter = 0;

    //constructor
    public Player(float x, float y, int width, int height) {
        this.width = width;
        this.height = height;
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, 2000);
        isAlive = true;

        Preferences prefs = Gdx.app.getPreferences("PREFERENCES");

        switch(prefs.getInteger("plane")) {
            case 0: {
                float[] temp = {15, 0, 69, 0, 70, 38, 100, 39, 100, 83, 44, 84, 37, 100, 16, 99, 16, 83, 3, 83, 3, 24, 15, 38};

                vertices_actual = new ArrayList<Float>();
                for (int i = 0; i < temp.length; i++) {
                    if (i % 2 == 0) {
                        vertices_actual.add(i, new Float(temp[i]) + this.getX());
                    }
                    else vertices_actual.add(i, new Float(temp[i]) + this.getY());
                }

                float[] vertices_actual_array = new float[this.vertices_actual.size()];

                for (int i = 0; i < vertices_actual_array.length; i++) {
                    vertices_actual_array[i] = vertices_actual.get(i);
                }
                boundingPolygon = new Polygon(vertices_actual_array);

                break;
            }

            case 1: {
                float[] temp = {15,0,69,0, 70, 28, 79, 27, 85,33,86,41,99,42,94,49,86,48,86,62,68,59,69,85,100,85,91,97,16,96,15,100,12,100,12,96,0,96,0,85,14,85,16,18,0,18,0,5,16,5};

                vertices_actual = new ArrayList<Float>();
                for (int i = 0; i < temp.length; i++) {
                    if (i % 2 == 0) {
                        vertices_actual.add(i, new Float(temp[i]) + this.getX());
                    }
                    else vertices_actual.add(i, new Float(temp[i]) + this.getY());
                }

                float[] vertices_actual_array = new float[this.vertices_actual.size()];

                for (int i = 0; i < vertices_actual_array.length; i++) {
                    vertices_actual_array[i] = vertices_actual.get(i);
                }
                boundingPolygon = new Polygon(vertices_actual_array);

                break;
            }

            case 2: {
                float[] temp = {11,0,78,0,79,40,92,55,100,55,100,68,94,68,61,99,28,99,12,87,0,67,0,54,11,40};

                vertices_actual = new ArrayList<Float>();
                for (int i = 0; i < temp.length; i++) {
                    if (i % 2 == 0) {
                        vertices_actual.add(i, new Float(temp[i]) + this.getX());
                    }
                    else vertices_actual.add(i, new Float(temp[i]) + this.getY());
                }

                float[] vertices_actual_array = new float[this.vertices_actual.size()];

                for (int i = 0; i < vertices_actual_array.length; i++) {
                    vertices_actual_array[i] = vertices_actual.get(i);
                }
                boundingPolygon = new Polygon(vertices_actual_array);

                break;
            }
        }


    }

    public void onRestart() {
        rotation = 0;
        position.x = GAME_WIDTH / 6;
        position.y = GAME_HEIGHT / 2 - 100;
        velocity.x = 0;
        velocity.y = 0;
        acceleration.x = 0;
        acceleration.y = 2000;
        isAlive = true;
        death_animation_counter = 0;
    }

    public void update(float delta) {

        if (isAlive() == false) velocity.add(acceleration.cpy().scl(delta));
        else if (TYPE == 3) velocityUpdateReversed();
        else velocityUpdate();

        // CEILING CHECK
        if (position.y < -13) {
            position.y = -13;
            velocity.y = 0;
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

        Preferences prefs = Gdx.app.getPreferences("PREFERENCES");

        switch(prefs.getInteger("plane")) {
            case 0: {
                float[] temp = {15, 0, 69, 0, 70, 38, 100, 39, 100, 83, 44, 84, 37, 100, 16, 99, 16, 83, 3, 83, 3, 24, 15, 38};

                vertices_actual = new ArrayList<Float>();
                for (int i = 0; i < temp.length; i++) {
                    if (i % 2 == 0) {
                        vertices_actual.add(i, new Float(temp[i]) + this.getX());
                    }
                    else vertices_actual.add(i, new Float(temp[i]) + this.getY());
                }

                float[] vertices_actual_array = new float[this.vertices_actual.size()];

                for (int i = 0; i < vertices_actual_array.length; i++) {
                    vertices_actual_array[i] = vertices_actual.get(i);
                }
                boundingPolygon = new Polygon(vertices_actual_array);

                break;
            }

            case 1: {
                float[] temp = {15,0,69,0, 70, 28, 79, 27, 85,33,86,41,99,42,94,49,86,48,86,62,68,59,69,85,100,85,91,97,16,96,15,100,12,100,12,96,0,96,0,85,14,85,16,18,0,18,0,5,16,5};

                vertices_actual = new ArrayList<Float>();
                for (int i = 0; i < temp.length; i++) {
                    if (i % 2 == 0) {
                        vertices_actual.add(i, new Float(temp[i]) + this.getX());
                    }
                    else vertices_actual.add(i, new Float(temp[i]) + this.getY());
                }

                float[] vertices_actual_array = new float[this.vertices_actual.size()];

                for (int i = 0; i < vertices_actual_array.length; i++) {
                    vertices_actual_array[i] = vertices_actual.get(i);
                }
                boundingPolygon = new Polygon(vertices_actual_array);

                break;
            }

            case 2: {
                float[] temp = {11,0,78,0,79,40,92,55,100,55,100,68,94,68,61,99,28,99,12,87,0,67,0,54,11,40};

                vertices_actual = new ArrayList<Float>();
                for (int i = 0; i < temp.length; i++) {
                    if (i % 2 == 0) {
                        vertices_actual.add(i, new Float(temp[i]) + this.getX());
                    }
                    else vertices_actual.add(i, new Float(temp[i]) + this.getY());
                }

                float[] vertices_actual_array = new float[this.vertices_actual.size()];

                for (int i = 0; i < vertices_actual_array.length; i++) {
                    vertices_actual_array[i] = vertices_actual.get(i);
                }
                boundingPolygon = new Polygon(vertices_actual_array);

                break;
            }
        }
    }

    private void velocityUpdateReversed() {
        float this_roll = Gdx.input.getRoll();

        if (ROLL <= 120 && ROLL >= -120) {
            if (Math.abs(this_roll - ROLL) > 60) {
                return;
            }
            velocity.y = (this_roll - ROLL) * 100;
        }

        if (ROLL > 120) {
            if (this_roll < ROLL - 60 && this_roll > ROLL + 60 - 360) return;

            if (this_roll <= ROLL - 300) velocity.y = (this_roll + 360 - ROLL) * 100;
            else velocity.y = (this_roll - ROLL) * 100;
        }

        if (ROLL < -120) {
            if (this_roll > -60 && this_roll < ROLL + 300) return;

            if (this_roll >= ROLL + 300) velocity.y = - (this_roll - 360 - ROLL) * 100;
            else velocity.y = (this_roll - ROLL) * 100;
        }
    }

    private void velocityUpdate() {
        float this_roll = Gdx.input.getRoll();

        if (ROLL <= 120 && ROLL >= -120) {
            if (Math.abs(this_roll - ROLL) > 60) {
                return;
            }
            velocity.y = - (this_roll - ROLL) * 100;
        }

        if (ROLL > 120) {
            if (this_roll < ROLL - 60 && this_roll > ROLL + 60 - 360) return;

            if (this_roll <= ROLL - 300) velocity.y = - (this_roll + 360 - ROLL) * 100;
            else velocity.y = - (this_roll - ROLL) * 100;
        }

        if (ROLL < -120) {
            if (this_roll > -60 && this_roll < ROLL + 300) return;

            if (this_roll >= ROLL + 300) velocity.y = - (this_roll - 360 - ROLL) * 100;
            else velocity.y = - (this_roll - ROLL) * 100;
        }
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
    public void die() {
        velocity.y = -1000;
        isAlive = false;
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

    public Polygon getBoundingPolygon() {
        return boundingPolygon;
    }
}



