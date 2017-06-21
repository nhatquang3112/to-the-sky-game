package com.tom.treasurerush.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tom.treasurerush.TRGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Treasure Rush";
		//width and height of the window, resolution
		config.width = 1920;
		config.height = 1080;
		new LwjglApplication(new TRGame(), config);
	}
}
