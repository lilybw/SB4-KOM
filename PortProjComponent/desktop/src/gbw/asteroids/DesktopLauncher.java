package gbw.asteroids;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import gbw.asteroids.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("PortProjComponent");
		config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
		new Lwjgl3Application(new Game(), config);
	}
}
