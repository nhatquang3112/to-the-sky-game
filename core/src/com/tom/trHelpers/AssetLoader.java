package com.tom.trHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Nhat Quang on 6/17/2017.
 */

public class AssetLoader {
    public static BitmapFont font, shadow;
    public static Preferences prefs;
    public static TextureRegion bomb, green_gem, blue_gem, red_gem;
    public static Texture background;

    public static void load() {
        font = new BitmapFont(Gdx.files.internal("text.fnt.txt"));
        font.getData().setScale(.25f, -.25f);
        shadow = new BitmapFont(Gdx.files.internal("shadow.fnt.txt"));
        shadow.getData().setScale(.25f, -.25f);
        bomb = new TextureRegion(new Texture(Gdx.files.internal("bomb.png")), 0, 0, 100, 100);
        red_gem = new TextureRegion(new Texture(Gdx.files.internal("red_gem.png")), 0, 0, 100, 100);
        green_gem = new TextureRegion(new Texture(Gdx.files.internal("green_gem.png")), 0, 0, 100, 100);
        blue_gem = new TextureRegion(new Texture(Gdx.files.internal("blue_gem.png")), 0, 0, 100, 100);
        background = new Texture(Gdx.files.internal("bg.png"));

        // Create (or retrieve existing) preferences file
        prefs = Gdx.app.getPreferences("Treasure Rush");
        // Provide default high score of 0
        if (!prefs.contains("highScore")) {
            prefs.putInteger("highScore", 0);
        }
    }

    // Receives an integer and maps it to the String highScore in prefs
    public static void setHighScore(int val) {
        prefs.putInteger("highScore", val);
        prefs.flush();
    }

    // Retrieves the current high score
    public static int getHighScore() {
        return prefs.getInteger("highScore");
    }

    //called when the game closes
    public static void dispose() {
        // We must dispose of the texture when we are finished.
        font.dispose();
        shadow.dispose();
        background.dispose();
    }
}
