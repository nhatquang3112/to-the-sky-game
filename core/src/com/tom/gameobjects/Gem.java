package com.tom.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.tom.gameworld.GameRenderer;
import com.tom.trHelpers.AssetLoader;

import static com.tom.gameobjects.ScrollHandler.GAME_WIDTH;
import static com.tom.gameworld.GameWorld.score;
import static com.tom.gameobjects.Player.position;

import java.util.Random;

/**
 * Created by Nhat Quang on 6/17/2017.
 */

public class Gem extends Scrollable {
    private Random r;
    private Circle gem;
    public int color; //1 is blue, 2 is red, 3 is green
    public boolean isScored = false;
    public boolean playing; //for exhaust animation
    Preferences prefs = Gdx.app.getPreferences("PREFERENCES");


    public Gem(float x, float y, int width, int height, float scrollSpeed) {
        super(x, y, width, height, scrollSpeed);
        // Initialize a Random object for Random number generation
        r = new Random();
        color = r.nextInt(3) + 1;
        gem = new Circle();
        playing = true;
    }


    @Override
    public void update(float delta) {
        // Call the update method in the superclass (Scrollable)
        super.update(delta);
        gem.set(position.x + 75 / 2f, position.y + 75 / 2f, 75 / 2f);

    }

    @Override
    public void reset(float newX) {
        // Call the reset method in the superclass (Scrollable)
        super.reset(newX);
        // Change the height to a random number
        //height = r.nextInt(90) + 15;
        isScored = false;
        playing = true;
    }

    public void collides(Player player, Goal goal) {
            if (isCollision(player.boundingPolygon, gem)) {
                if (isScored == false) goal.gemUpdate(color);
                     if (isScored == false) {
                         score += 1;
                         if (prefs.getBoolean("playMusic")) AssetLoader.gem_sound.play(1.0f);
                     }
                isScored = true;
            }

    }

    public Circle getGem() {
        return gem;
    }

    private boolean isCollision(Polygon p, Circle c) {
        float[] vertices = p.getTransformedVertices();
        Vector2 center = new Vector2(c.x, c.y);
        float squareRadius = c.radius * c.radius;
        for (int i = 0; i < vertices.length; i += 2) {
            if (i == 0) {
                if (Intersector.intersectSegmentCircle(new Vector2(
                        vertices[vertices.length - 2],
                        vertices[vertices.length - 1]), new Vector2(
                        vertices[i], vertices[i + 1]), center, squareRadius))
                    return true;
            } else {
                if (Intersector.intersectSegmentCircle(new Vector2(
                        vertices[i - 2], vertices[i - 1]), new Vector2(
                        vertices[i], vertices[i + 1]), center, squareRadius))
                    return true;
            }
        }
        return false;
    }

    public void stopPlaying() {
        playing = false;
    }

    public boolean isPlaying() {
        return playing;
    }

}
