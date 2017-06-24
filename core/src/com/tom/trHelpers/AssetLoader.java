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
    public static BitmapFont font, shadow, consolas;
    public static BitmapFont bigFont, bigShadow;
    public static Preferences prefs;
    public static TextureRegion bomb, green_gem, blue_gem, red_gem, background, player, scroll, clock, floor, button;


    public static void load() {
        font = new BitmapFont(Gdx.files.internal("text.fnt.txt"));
        font.getData().setScale(1, -1);
        shadow = new BitmapFont(Gdx.files.internal("shadow.fnt.txt"));
        shadow.getData().setScale(1, -1);

        bigFont = new BitmapFont(Gdx.files.internal("text.fnt.txt"));
        bigFont.getData().setScale(2, -2);
        bigShadow = new BitmapFont(Gdx.files.internal("shadow.fnt.txt"));
        bigShadow.getData().setScale(2, -2);

        consolas = new BitmapFont(Gdx.files.internal("consolas.fnt"));
        consolas.getData().setScale(1, -1);
        bomb = new TextureRegion(new Texture(Gdx.files.internal("lavastone.png")), 0, 0, 100, 100);
        bomb.flip(false, true); //flip the bomb
        red_gem = new TextureRegion(new Texture(Gdx.files.internal("red_gem.png")), 0, 0, 100, 100);
        green_gem = new TextureRegion(new Texture(Gdx.files.internal("green_gem.png")), 0, 0, 100, 100);
        green_gem.flip(false, true);
        blue_gem = new TextureRegion(new Texture(Gdx.files.internal("blue_gem.png")), 0, 0, 100, 100);
        background = new TextureRegion(new Texture(Gdx.files.internal("background.png")), 0, 0, 826, 463);
        background.flip(false, true); //flip the bakground
        player = new TextureRegion(new Texture(Gdx.files.internal("player.png")), 0, 0, 100, 100);
        player.flip(false, true); //flip the player
        scroll = new TextureRegion(new Texture(Gdx.files.internal("scroll3.png")), 0, 0, 300, 1080);
        scroll.flip(false, true); //flip the scroll
        clock = new TextureRegion(new Texture(Gdx.files.internal("clock1.png")), 0, 0, 150, 150);
        scroll.flip(false, true); //flip the clock
        floor = new TextureRegion(new Texture(Gdx.files.internal("floor.png")), 0, 0, 1000, 11);
        floor.flip(false, true); //flip the player
        button = new TextureRegion(new Texture(Gdx.files.internal("button.png")), 0, 0, 502, 200);


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
        consolas.dispose();

    }
}
