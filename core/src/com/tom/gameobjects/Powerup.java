package com.tom.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Polygon;
import com.tom.trHelpers.AssetLoader;

import java.util.Random;

/**
 * Created by NewUser on 7/16/2017.
 */

public class Powerup extends Scrollable {
    public static float POWERUP_TIME = 0;
    public static float POPUP_TIME = 0;
    public static int TYPE = -1;
    private Random r = new Random();
    public int type;
    public Polygon boundingPolygon;
    private float[] vertices_original = {0, 27, 25, 23, 37, 0, 49, 23, 75, 27, 56, 46, 60, 72, 37, 60, 14, 71, 18, 46};
    private float[] vertices_actual = new float[20];
    public boolean isScored = false;
    Preferences prefs = Gdx.app.getPreferences("PREFERENCES");

    //0 is ethereal form - goes through anything for 5s
    //1 is reduce scroll speed
    //2 is +10 extra time
    //3 is reversed control

    public Powerup(float x, float y, int width, int height, float scrollSpeed) {
        super(x, y, width, height, scrollSpeed);

        int temp = r.nextInt(11);

        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) vertices_actual[i] = this.getX() + vertices_original[i];
            else vertices_actual[i] = this.getY() + vertices_original[i];
        }

        boundingPolygon = new Polygon(vertices_actual);


        switch(temp) {
            case 0: case 1: case 2: case 3: {
                type = 0;
                break;
            }
            case 4: case 5: case 6: case 7: {
                type = 2;
                break;
            }
            case 8: case 9: {
                type = 1;
                break;
            }
            case 10: {
                type = 3;
                break;
            }
        }
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) vertices_actual[i] = this.getX() + vertices_original[i];
            else vertices_actual[i] = this.getY() + vertices_original[i];
        }

        boundingPolygon = new Polygon(vertices_actual);
    }

    public boolean collides(Player player) {
        if (Intersector.overlapConvexPolygons(player.boundingPolygon, this.boundingPolygon) == true) {
            isScored = true;
            if (prefs.getBoolean("playMusic")) AssetLoader.power_up_sound.play(1.0f);
        }
        return Intersector.overlapConvexPolygons(player.boundingPolygon, this.boundingPolygon);
    }
}
