package com.gdx.bomberman.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gdx.bomberman.Main;
import gui.Constants;



public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
                config.width = Constants.WIDTH;
                config.height = Constants.HEIGHT;
                config.title = "Bomberman Reloaded";
                
		new LwjglApplication(new Main(), config);
	}
}
