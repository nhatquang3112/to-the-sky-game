package com.tom.treasurerush;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.tom.screens.GameScreen;
import com.tom.trHelpers.AssetLoader;

public class TRGame extends Game {
	//this is where it all started
	//TRGame is called by flatform-dependent codes




	public void create() {
		Preferences prefs = Gdx.app.getPreferences("PREFERENCES");

		if (!prefs.contains("playMusic")) {
			prefs.putBoolean("playMusic", true);
			prefs.flush();
		}

		if (!prefs.contains("plane")) {
			prefs.putInteger("plane", 0);
			prefs.flush();
		}
		if (!prefs.contains("flag")) {
			prefs.putInteger("flag", 0);
			prefs.flush();
		}
		if (!prefs.contains("slow_down")) {
			prefs.putInteger("slow_down", 0);
			prefs.flush();
		}
		if (!prefs.contains("plus_time")) {
			prefs.putInteger("plus_time", 0);
			prefs.flush();
		}

		if (!prefs.contains("coin")){
			prefs.putInteger("coin", 15000); //initial money for the player
			prefs.flush();
		}
		if (!prefs.contains("plane1_purchased")) {
			prefs.putBoolean("plane1_purchased", false);
			prefs.flush();
		}
		if (!prefs.contains("plane2_purchased")) {
			prefs.putBoolean("plane2_purchased", false);
			prefs.flush();
		}
		if (!prefs.contains("flag1_purchased")) {
			prefs.putBoolean("flag1_purchased", false);
			prefs.flush();
		}
		if (!prefs.contains("flag2_purchased")) {
			prefs.putBoolean("flag2_purchased", false);
			prefs.flush();
		}
		if (!prefs.contains("flag3_purchased")) {
			prefs.putBoolean("flag3_purchased", false);
			prefs.flush();
		}

		AssetLoader.load();

		setScreen(new GameScreen());
	}

	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}



}
