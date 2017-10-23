package com.tom.trHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Nhat Quang on 6/17/2017.
 */

public class AssetLoader {
    public static BitmapFont font, shadow, consolas, expressway, expressway_green, expressway_red, expressway_blue, expressway_white, expressway_black_gameover;
    public static BitmapFont bigFont, bigShadow, big_expressway, parisine_36_brown, parisine_36_white;
    public static Preferences prefs;
    public static TextureRegion background, backgroundver2, backgroundver3, player_dead_texture, player, scroll, clock, floor, button, player_1, player_2, player_3, player_4, bomb_1, bomb_2, bomb_3, bomb_4;
    public static TextureRegion player1, player1_dead_texture, player1_1, player1_2, player1_3, player1_4;
    public static TextureRegion player2, player2_dead_texture, player2_1, player2_2, player2_3, player2_4;
    public static Texture player_texture, player1_texture, player2_texture, bomb_texture;
    public static Animation<TextureRegion> playerAnimation, player1Animation, player2Animation, bombAnimation;
    public static Sprite player_dead, player1_dead, player2_dead;
    public static TextureRegion intro, gameover, shop;
    public static TextureRegion main_menu, green_tick, purchasing;
    public static TextureRegion coin, about;
    public static TextureRegion soundON, soundOFF;

    //sound
    public static Sound gem_sound = Gdx.audio.newSound(Gdx.files.internal("GemSound.wav"));
    public static Sound on_click_sound = Gdx.audio.newSound(Gdx.files.internal("OnClickSound.wav"));
    public static Sound power_up_sound = Gdx.audio.newSound(Gdx.files.internal("PowerUpSound.wav"));
    public static Sound explosion = Gdx.audio.newSound(Gdx.files.internal("explosion.wav"));
    public static Sound metal = Gdx.audio.newSound(Gdx.files.internal("metal.mp3"));

    //music
    public static Music menu_music = Gdx.audio.newMusic(Gdx.files.internal("MenuMusic.wav"));
    public static Music play_music = Gdx.audio.newMusic(Gdx.files.internal("PlayMusic.mp3"));

    //these for green gem
    public static Texture green_gem_texture;
    public static TextureRegion green_gem_1, green_gem_2, green_gem_3, green_gem_4;
    public static Animation<TextureRegion> green_gem_Animation;

    //these for blue gem
    public static Texture blue_gem_texture;
    public static TextureRegion blue_gem_1, blue_gem_2, blue_gem_3, blue_gem_4;
    public static Animation<TextureRegion> blue_gem_Animation;

    //these for exhaust
    public static Texture exhaust_texture;
    public static TextureRegion exhaust_1, exhaust_2, exhaust_3, exhaust_4;
    public static Animation<TextureRegion> exhaustAnimation;

    //these for flag1
    public static Texture flag1_texture;
    public static TextureRegion flag1_1, flag1_2, flag1_3, flag1_4;
    public static Animation<TextureRegion> flag1Animation;

    //these for flag2
    public static Texture flag2_texture;
    public static TextureRegion flag2_1, flag2_2, flag2_3, flag2_4;
    public static Animation<TextureRegion> flag2Animation;

    //these for flag3
    public static Texture flag3_texture;
    public static TextureRegion flag3_1, flag3_2, flag3_3, flag3_4;
    public static Animation<TextureRegion> flag3Animation;

    //these for red gem
    public static Texture red_gem_texture;
    public static TextureRegion red_gem_1, red_gem_2, red_gem_3, red_gem_4;
    public static Animation<TextureRegion> red_gem_Animation;

    //these for powerup
    public static Texture powerup_texture;
    public static TextureRegion powerup_1, powerup_2, powerup_3, powerup_4, nomoney;
    public static Animation<TextureRegion> powerup_Animation;

    //item
    public static TextureRegion plus_time, slow_down;

    public static void load() {
        font = new BitmapFont(Gdx.files.internal("text.fnt.txt"));
        font.getData().setScale(1, -1);

        parisine_36_brown = new BitmapFont(Gdx.files.internal("parisine-36-brown.fnt"));
        parisine_36_brown.getData().setScale(1, -1);

        parisine_36_white = new BitmapFont(Gdx.files.internal("parisine-36-white.fnt"));
        parisine_36_white.getData().setScale(1, -1);

        shop = new TextureRegion(new Texture(Gdx.files.internal("shop.png")), 0, 0, 1920, 1080);
        shop.flip(false, true);

        //button for sound
        soundON = new TextureRegion(new Texture(Gdx.files.internal("SoundButtonON.png")),0,0,100,100);
        soundON.flip(false, true);
        soundOFF = new TextureRegion(new Texture(Gdx.files.internal("SoundButtonOFF.png")),0,0,100,100);
        soundOFF.flip(false, true);

        coin = new TextureRegion(new Texture(Gdx.files.internal("coin.png")), 0, 0, 105, 105);
        coin.flip(false, true);

        slow_down = new TextureRegion(new Texture(Gdx.files.internal("slow_down.png")), 0, 0, 204, 200);
        slow_down.flip(false, true);

        plus_time = new TextureRegion(new Texture(Gdx.files.internal("plus_time.png")), 0, 0, 204, 200);
        plus_time.flip(false, true);

        green_tick = new TextureRegion(new Texture(Gdx.files.internal("greentick.png")), 0, 0, 125, 123);
        green_tick.flip(false, true);

        shop = new TextureRegion(new Texture(Gdx.files.internal("shop.png")), 0, 0, 1920, 1080);
        shop.flip(false, true);

        about = new TextureRegion(new Texture(Gdx.files.internal("about.png")), 0, 0, 1700, 800);
        about.flip(false, true);

        purchasing = new TextureRegion(new Texture(Gdx.files.internal("purchasing.png")), 0, 0, 1200, 750);
        purchasing.flip(false, true);


        nomoney = new TextureRegion(new Texture(Gdx.files.internal("nomoney.png")), 0, 0, 1200, 750);
        nomoney.flip(false, true);

        expressway = new BitmapFont(Gdx.files.internal("expressway.fnt"));
        expressway.getData().setScale(1, -1);

        expressway_black_gameover = new BitmapFont(Gdx.files.internal("expressway_black_gameover.fnt"));
        expressway_black_gameover.getData().setScale(1, -1);

        expressway_green = new BitmapFont(Gdx.files.internal("expressway_green.fnt"));
        expressway_green.getData().setScale(1, -1);

        expressway_white = new BitmapFont(Gdx.files.internal("expressway_white.fnt"));
        expressway_white.getData().setScale(1, -1);

        expressway_red = new BitmapFont(Gdx.files.internal("expressway_red.fnt"));
        expressway_red.getData().setScale(1, -1);

        expressway_blue = new BitmapFont(Gdx.files.internal("expressway_blue.fnt"));
        expressway_blue.getData().setScale(1, -1);

        big_expressway = new BitmapFont(Gdx.files.internal("big_expressway.fnt"));
        big_expressway.getData().setScale(1, -1);

        shadow = new BitmapFont(Gdx.files.internal("shadow.fnt.txt"));
        shadow.getData().setScale(1, -1);

        bigFont = new BitmapFont(Gdx.files.internal("text.fnt.txt"));
        bigFont.getData().setScale(2, -2);
        bigShadow = new BitmapFont(Gdx.files.internal("shadow.fnt.txt"));
        bigShadow.getData().setScale(2, -2);

        consolas = new BitmapFont(Gdx.files.internal("consolas.fnt"));
        consolas.getData().setScale(1, -1);

        intro = new TextureRegion(new Texture(Gdx.files.internal("intro.png")), 0, 0, 1700, 800);
        intro.flip(false, true);

        gameover = new TextureRegion(new Texture(Gdx.files.internal("gameover.png")), 0, 0, 1200, 750);
        gameover.flip(false, true);

        //backgrounds
        background = new TextureRegion(new Texture(Gdx.files.internal("background.png")), 0, 0, 1920, 1080);
        background.flip(false, true); //flip the bakground
        backgroundver2 = new TextureRegion(new Texture(Gdx.files.internal("backgroundver2.png")), 0, 0, 1920, 1080);
        backgroundver2.flip(false, true); //flip the bakground
        backgroundver3 = new TextureRegion(new Texture(Gdx.files.internal("backgroundver3.png")), 0, 0, 1920, 1080);
        backgroundver3.flip(false, true); //flip the bakground

        main_menu = new TextureRegion(new Texture(Gdx.files.internal("main_menu.png")), 0, 0, 1920, 1080);
        main_menu.flip(false, true);

        //player_dead
        player_texture = new Texture(Gdx.files.internal("player.png"));
        player_dead_texture = new TextureRegion(player_texture, 0, 0, 100, 100);
        player_dead = new Sprite(player_dead_texture);

        //player1_dead
        player1_texture = new Texture(Gdx.files.internal("player1.png"));
        player1_dead_texture = new TextureRegion(player1_texture, 0, 0, 100, 100);
        player1_dead = new Sprite(player1_dead_texture);

        //player2_dead
        player2_texture = new Texture(Gdx.files.internal("player2.png"));
        player2_dead_texture = new TextureRegion(player2_texture, 0, 0, 100, 100);
        player2_dead = new Sprite(player2_dead_texture);

        //player animation
        player_1 = new TextureRegion(player_texture, 0, 0, 100, 100);
        player_2 = new TextureRegion(player_texture, 100, 0, 100, 100);
        player_3 = new TextureRegion(player_texture, 200, 0, 100, 100);
        player_4 = new TextureRegion(player_texture, 300, 0, 100, 100);
        player_1.flip(false, true); player_2.flip(false, true); player_3.flip(false, true); player_4.flip(false, true);
        TextureRegion[] player = {player_1, player_2, player_3, player_4};
        playerAnimation = new Animation<TextureRegion>(0.06f, player);
        playerAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        //exhaust animation
        exhaust_texture = new Texture(Gdx.files.internal("exhaust.png"));
        exhaust_1 = new TextureRegion(exhaust_texture, 0, 0, 100, 100);
        exhaust_2 = new TextureRegion(exhaust_texture, 100, 0, 100, 100);
        exhaust_3 = new TextureRegion(exhaust_texture, 200, 0, 100, 100);
        exhaust_4 = new TextureRegion(exhaust_texture, 300, 0, 100, 100);
        exhaust_1.flip(false, true); exhaust_2.flip(false, true); exhaust_3.flip(false, true); exhaust_4.flip(false, true);
        TextureRegion[] exhaust = {exhaust_1, exhaust_2, exhaust_3, exhaust_4};
        exhaustAnimation = new Animation<TextureRegion>(0.06f, exhaust);
        exhaustAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        //flag1 animation
        flag1_texture = new Texture(Gdx.files.internal("flag1.png"));
        flag1_1 = new TextureRegion(flag1_texture, 0, 0, 200, 100);
        flag1_2 = new TextureRegion(flag1_texture, 200, 0, 200, 100);
        flag1_3 = new TextureRegion(flag1_texture, 400, 0, 200, 100);
        flag1_4 = new TextureRegion(flag1_texture, 600, 0, 200, 100);
        flag1_1.flip(false, true); flag1_2.flip(false, true); flag1_3.flip(false, true); flag1_4.flip(false, true);
        TextureRegion[] flag1 = {flag1_1, flag1_2, flag1_3, flag1_4};
        flag1Animation = new Animation<TextureRegion>(0.06f, flag1);
        flag1Animation.setPlayMode(Animation.PlayMode.LOOP);

        //flag2 animation
        flag2_texture = new Texture(Gdx.files.internal("flag2.png"));
        flag2_1 = new TextureRegion(flag2_texture, 0, 0, 200, 100);
        flag2_2 = new TextureRegion(flag2_texture, 200, 0, 200, 100);
        flag2_3 = new TextureRegion(flag2_texture, 400, 0, 200, 100);
        flag2_4 = new TextureRegion(flag2_texture, 600, 0, 200, 100);
        flag2_1.flip(false, true); flag2_2.flip(false, true); flag2_3.flip(false, true); flag2_4.flip(false, true);
        TextureRegion[] flag2 = {flag2_1, flag2_2, flag2_3, flag2_4};
        flag2Animation = new Animation<TextureRegion>(0.06f, flag2);
        flag2Animation.setPlayMode(Animation.PlayMode.LOOP);

        //flag3 animation
        flag3_texture = new Texture(Gdx.files.internal("flag3.png"));
        flag3_1 = new TextureRegion(flag3_texture, 0, 0, 200, 100);
        flag3_2 = new TextureRegion(flag3_texture, 200, 0, 200, 100);
        flag3_3 = new TextureRegion(flag3_texture, 400, 0, 200, 100);
        flag3_4 = new TextureRegion(flag3_texture, 600, 0, 200, 100);
        flag3_1.flip(false, true); flag3_2.flip(false, true); flag3_3.flip(false, true); flag3_4.flip(false, true);
        TextureRegion[] flag3 = {flag3_1, flag3_2, flag3_3, flag3_4};
        flag3Animation = new Animation<TextureRegion>(0.06f, flag3);
        flag3Animation.setPlayMode(Animation.PlayMode.LOOP);

        //player1 animation
        player1_1 = new TextureRegion(player1_texture, 0, 0, 100, 100);
        player1_2 = new TextureRegion(player1_texture, 100, 0, 100, 100);
        player1_3 = new TextureRegion(player1_texture, 200, 0, 100, 100);
        player1_4 = new TextureRegion(player1_texture, 300, 0, 100, 100);
        player1_1.flip(false, true); player1_2.flip(false, true); player1_3.flip(false, true); player1_4.flip(false, true);
        TextureRegion[] player1 = {player1_1, player1_2, player1_3, player1_4};
        player1Animation = new Animation<TextureRegion>(0.06f, player1);
        player1Animation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        //player2 animation
        player2_1 = new TextureRegion(player2_texture, 0, 0, 100, 100);
        player2_2 = new TextureRegion(player2_texture, 100, 0, 100, 100);
        player2_3 = new TextureRegion(player2_texture, 200, 0, 100, 100);
        player2_4 = new TextureRegion(player2_texture, 300, 0, 100, 100);
        player2_1.flip(false, true); player2_2.flip(false, true); player2_3.flip(false, true); player2_4.flip(false, true);
        TextureRegion[] player2 = {player2_1, player2_2, player2_3, player2_4};
        player2Animation = new Animation<TextureRegion>(0.06f, player2);
        player2Animation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        //green gem animation
        green_gem_texture = new Texture(Gdx.files.internal("green_gem.png"));
        green_gem_1 = new TextureRegion(green_gem_texture, 0, 0, 75, 75);
        green_gem_2 = new TextureRegion(green_gem_texture, 75, 0, 75, 75);
        green_gem_3 = new TextureRegion(green_gem_texture, 150, 0, 75, 75);
        green_gem_4 = new TextureRegion(green_gem_texture, 225, 0, 75, 75);
        TextureRegion[] green_gem = {green_gem_1, green_gem_2, green_gem_3, green_gem_4};
        green_gem_Animation = new Animation<TextureRegion>(0.5f, green_gem);
        green_gem_Animation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        //blue gem animation
        blue_gem_texture = new Texture(Gdx.files.internal("blue_gem.png"));
        blue_gem_1 = new TextureRegion(blue_gem_texture, 0, 0, 75, 75);
        blue_gem_2 = new TextureRegion(blue_gem_texture, 75, 0, 75, 75);
        blue_gem_3 = new TextureRegion(blue_gem_texture, 150, 0, 75, 75);
        blue_gem_4 = new TextureRegion(blue_gem_texture, 225, 0, 75, 75);
        TextureRegion[] blue_gem = {blue_gem_1, blue_gem_2, blue_gem_3, blue_gem_4};
        blue_gem_Animation = new Animation<TextureRegion>(0.5f, blue_gem);
        blue_gem_Animation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        //red gem animation
        red_gem_texture = new Texture(Gdx.files.internal("red_gem.png"));
        red_gem_1 = new TextureRegion(red_gem_texture, 0, 0, 75, 75);
        red_gem_2 = new TextureRegion(red_gem_texture, 75, 0, 75, 75);
        red_gem_3 = new TextureRegion(red_gem_texture, 150, 0, 75, 75);
        red_gem_4 = new TextureRegion(red_gem_texture, 225, 0, 75, 75);
        TextureRegion[] red_gem = {red_gem_1, red_gem_2, red_gem_3, red_gem_4};
        red_gem_Animation = new Animation<TextureRegion>(0.5f, red_gem);
        red_gem_Animation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        //powerup animation
        powerup_texture = new Texture(Gdx.files.internal("powerup.png"));
        powerup_1 = new TextureRegion(powerup_texture, 0, 0, 75, 75);
        powerup_2 = new TextureRegion(powerup_texture, 75, 0, 75, 75);
        powerup_3 = new TextureRegion(powerup_texture, 150, 0, 75, 75);
        powerup_4 = new TextureRegion(powerup_texture, 225, 0, 75, 75);
        powerup_1.flip(false, true); powerup_2.flip(false, true); powerup_3.flip(false, true); powerup_4.flip(false, true);
        TextureRegion[] powerup = {powerup_1, powerup_2, powerup_3, powerup_4};
        powerup_Animation = new Animation<TextureRegion>(1f, powerup);
        powerup_Animation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        //bomb
        bomb_texture = new Texture(Gdx.files.internal("bomb.png"));
        bomb_1 = new TextureRegion(bomb_texture, 0, 0, 75, 75);
        bomb_2 = new TextureRegion(bomb_texture, 75, 0, 75, 75);
        bomb_3 = new TextureRegion(bomb_texture, 150, 0, 75, 75);
        bomb_4 = new TextureRegion(bomb_texture, 225, 0, 75, 75);
        TextureRegion[] bomb = {bomb_1, bomb_2, bomb_3, bomb_4};
        bombAnimation = new Animation<TextureRegion>(0.25f, bomb);
        bombAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        scroll = new TextureRegion(new Texture(Gdx.files.internal("scroll3.png")), 0, 0, 300, 1080);
        scroll.flip(false, true); //flip the scroll
        clock = new TextureRegion(new Texture(Gdx.files.internal("clock1.png")), 0, 0, 75, 75);
        clock.flip(false, true); //flip the clock
        floor = new TextureRegion(new Texture(Gdx.files.internal("floor.png")), 0, 0, 1920, 50);
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
