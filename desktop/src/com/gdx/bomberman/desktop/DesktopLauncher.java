package com.gdx.bomberman.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gdx.bomberman.Main;
import com.gdx.bomberman.Constants;



public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
                config.width = Constants.SCREENWIDTH;
                config.height = Constants.SCREENHEIGHT;
                config.fullscreen = Constants.FULLSCREEN;
                config.title = Constants.WINDOWTITEL;
                config.addIcon(Constants.WINDOWICONPATH, Files.FileType.Internal);

                
		new LwjglApplication(new Main(), config);
	}
}
