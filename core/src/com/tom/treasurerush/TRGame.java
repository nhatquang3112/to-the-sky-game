package com.tom.treasurerush;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tom.screens.GameScreen;
import com.tom.trHelpers.AssetLoader;

public class TRGame extends Game {
	//this is where it all started
	//TRGame is called by flatform-dependent codes
	public void create() {
		Gdx.app.log("TRGame", "created"); //name of current class, what you wanna print
		AssetLoader.load(); //need graphic first to draw screen
		setScreen(new GameScreen()); //do a hell lot of things
	}

	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}

}
