package com.pelezinorr.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pelezinorr.game.FlappyPR;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = FlappyPR.WIDTH;
		config.height = FlappyPR.HEIGHT;
		config.title = FlappyPR.TITLE;
		new LwjglApplication(new FlappyPR(), config);
	}
}
